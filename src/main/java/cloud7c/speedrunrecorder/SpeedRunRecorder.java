package cloud7c.speedrunrecorder;

import cloud7c.speedrunrecorder.io.AtumSettingsReader;
import cloud7c.speedrunrecorder.io.IgtLatestWorldReader;
import cloud7c.speedrunrecorder.io.LatestRunReader;
import cloud7c.speedrunrecorder.io.LatestRunTimeLoader;
import cloud7c.speedrunrecorder.model.Preferences;
import cloud7c.speedrunrecorder.model.Record;
import cloud7c.speedrunrecorder.util.Logger;
import cloud7c.speedrunrecorder.util.SleepUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class SpeedRunRecorder {
    private static SpeedRunRecorder instance;
    private static final Pattern RANDOM_WORLD_PATTERN = Pattern.compile("^Random Speedrun #\\d+$");
    private static final Set<String> END_EVENTS = new HashSet<>(Arrays.asList("common.multiplayer", "common.old_world", "common.open_to_lan", "common.enable_cheats", "common.view_seed"));
    private final Preferences preferences;
    private volatile String latestWorldContent;
    private final RecordManager recordManager;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition newRun = lock.newCondition();
    private LatestWorldPathMonitor latestWorldPathMonitor;
    private UpdateTask updateTask;
    ExecutorService executor = Executors.newFixedThreadPool(2);

    public SpeedRunRecorder(Preferences preferences){
        instance = this;
        this.preferences = preferences;
        recordManager = new RecordManager();
    }

    public static SpeedRunRecorder getInstance() {
        return instance;
    }

    public void start(){
        recordManager.init();
        updateTask = new UpdateTask();
        latestWorldPathMonitor = new LatestWorldPathMonitor(updateTask);
        executor.submit(latestWorldPathMonitor);
        executor.submit(updateTask);
    }

    public void stop(){
        latestWorldPathMonitor.stop();
        updateTask.stop();
        lock.lock();
        try {
            newRun.signal();
        } finally {
            lock.unlock();
        }
        executor.shutdown();
        Logger.log("Waiting for tasks to finish");
        try {
            if(!executor.awaitTermination(10, TimeUnit.SECONDS)){
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        Logger.log("All records saved, exiting...");
    }

    public Preferences getPreferences(){
        return preferences;
    }

    private class LatestWorldPathMonitor implements Runnable {
        private final AtomicBoolean running = new AtomicBoolean(true);
        private long lastCheckTime = -1;
        private final UpdateTask updateTask;

        LatestWorldPathMonitor(UpdateTask updateTask){
            this.updateTask = updateTask;
        }

        public void stop(){
            running.set(false);
        }

        @Override
        public void run() {
            lastCheckTime = LatestRunTimeLoader.loadLatestRunTime();
            Logger.log("Loaded last check time: " + lastCheckTime);
            while(running.get()){
                String newWorldContent = IgtLatestWorldReader.readLatestWorld(lastCheckTime);
                if(newWorldContent == null){
//                    Logger.log("No new world found, waiting for new run...");
                    SleepUtil.sleep(5000);
                    continue;
                }
                lastCheckTime = System.currentTimeMillis();

                if (!newWorldContent.equals(latestWorldContent)) {
                    Logger.log("New world found: " + newWorldContent);
                    lock.lock();
                    try {
                        latestWorldContent = newWorldContent;
                        updateTask.stopCurrent();
                        newRun.signal();
                    } finally {
                        lock.unlock();
                    }
                }
                SleepUtil.sleep(5000);
            }
            Logger.log("Saving latest run time");
            LatestRunTimeLoader.saveLatestRunTime();
        }
    }

    private class UpdateTask implements Runnable {
        private final AtomicBoolean running = new AtomicBoolean(true);
        private final AtomicBoolean stop = new AtomicBoolean(false);
        private static final String EVENT_LOG_PATH = "/speedrunigt/events.log";
        private Record currentRecord;
        private String eventsLogPath;
        long lastCheckTime = -1;
        int lineCount = 0;

        public void stopCurrent(){
            running.set(false);
        }

        public void stop(){
            running.set(false);
            stop.set(true);
        }

        @Override
        public void run() {
            waitForNewRun();
            running.set(true);
            while(!stop.get()){
                String worldPath = getWorldPath();
                if(worldPath == null){
                    continue;
                }

                Logger.log("Starting new run: " + worldPath);
                eventsLogPath = worldPath + EVENT_LOG_PATH;
                currentRecord = new Record();
                currentRecord.setWorldPath(worldPath);
                currentRecord.setStart(LatestRunReader.getCreationTime(eventsLogPath));

                lineCount = 0;
                lastCheckTime = -1;
                boolean endRun = false;

                running.set(true);

                while(running.get()){
                    endRun = update();
                    if(endRun){
                        break;
                    }

                    SleepUtil.sleep(5000);
                } // running
                if(endRun){
                    waitForNewRun();
                    if(stop.get()){
                        break;
                    }
                }
                else{
                    recordManager.saveRecord(currentRecord);
                }
            } // stop

        }

        private void waitForNewRun(){
            Logger.log("Waiting for new run...");
            lock.lock();
            try{
                while(running.get()){
                    newRun.await();
                }
                running.set(true);
            } catch (InterruptedException e){
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        private String getWorldPath(){
            String worldPath;
            while(!stop.get()){
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(latestWorldContent, JsonObject.class);
                worldPath = jsonObject.get("world_path").getAsString();
                String worldName = Paths.get(worldPath).getFileName().toString();

                boolean isRandomWorld = RANDOM_WORLD_PATTERN.matcher(worldName).matches();
                boolean checkAtumSettings = checkAtumSettings(worldPath);
                if(isRandomWorld && checkAtumSettings){
                    return worldPath;
                }

                Logger.log(worldPath + " is not a random world or Atum settings not comply with rsg rules, skipping");
                waitForNewRun();
            }
            return null;
        }

        private boolean checkAtumSettings(String worldPath){
            Map<String, String> atumSettings = AtumSettingsReader.readAtumSettings(worldPath);
            return atumSettings.getOrDefault("generatorType", "0").trim().equals("0")
                    && atumSettings.getOrDefault("bonusChest", "false").trim().equals("false");
        }

        private boolean update(){
            List<String> newLines = LatestRunReader.readNewLines(eventsLogPath, lastCheckTime, lineCount);

            lineCount += newLines.size();
            lastCheckTime = System.currentTimeMillis();
            boolean endRun;
            for(String line : newLines){
                endRun = handleEvent(line);
                if(endRun){
                    return true;
                }
            }
            return false;
        }

        private boolean handleEvent(String event){
            String[] parts = event.split(" ");
            if(END_EVENTS.contains(parts[0])){
                recordManager.saveRecord(currentRecord);
                Logger.log("match end event: " + parts[0] + ", saving record");
                return true;
            }

            Logger.log("handle event: " + parts[0]);
            boolean update = true;

            if(parts.length < 3){
                return false;
            }
            switch (parts[0]) {
                case "rsg.enter_nether":
                    currentRecord.setEnterNether(Long.parseLong(parts[2]));
                    break;
                case "rsg.enter_bastion":
                    currentRecord.setEnterBastion(Long.parseLong(parts[2]));
                    break;
                case "rsg.enter_fortress":
                    currentRecord.setEnterFortress(Long.parseLong(parts[2]));
                    break;
                case "rsg.first_portal":
                    currentRecord.setFirstPortal(Long.parseLong(parts[2]));
                    break;
                case "rsg.enter_stronghold":
                    currentRecord.setEnterStronghold(Long.parseLong(parts[2]));
                    break;
                case "rsg.enter_end":
                    currentRecord.setEnterEnd(Long.parseLong(parts[2]));
                    break;
                case "rsg.credits":
                    currentRecord.setFinish(Long.parseLong(parts[2]));
                    break;
                default:
                    update = false;
                    break;
            }
            if(update){
                recordManager.updateRecord(currentRecord);
            }
            return false;
        }
    }
}
