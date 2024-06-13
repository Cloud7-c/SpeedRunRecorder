package cloud7c.speedrunrecorder.io;

import cloud7c.speedrunrecorder.util.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LatestRunTimeLoader {
    private static final Path LATEST_RUN_TIME_PATH = Path.of("records").resolve("latest_run_time");

    static {
        Path latestRunTimeFileDir = Path.of("records");
        if (!Files.exists(latestRunTimeFileDir)) {
            try {
                Files.createDirectories(latestRunTimeFileDir);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("Failed to create records directory");
            }
        }
    }

    public static long loadLatestRunTime() {
        if(!LATEST_RUN_TIME_PATH.toFile().exists()){
            return -1;
        }
        try {
            return Long.parseLong(Files.readString(LATEST_RUN_TIME_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void saveLatestRunTime(){
        try {
            Files.writeString(LATEST_RUN_TIME_PATH, String.valueOf(System.currentTimeMillis()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
