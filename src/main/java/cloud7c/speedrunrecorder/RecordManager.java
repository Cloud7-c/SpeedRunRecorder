package cloud7c.speedrunrecorder;

import cloud7c.speedrunrecorder.io.OverlayWriter;
import cloud7c.speedrunrecorder.io.RecordLoader;
import cloud7c.speedrunrecorder.model.Preferences;
import cloud7c.speedrunrecorder.model.Record;
import cloud7c.speedrunrecorder.model.RecordsMetrics;
import cloud7c.speedrunrecorder.util.Logger;
import cloud7c.speedrunrecorder.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RecordManager {
    private Record lastRecord = new Record();
    private final RecordsMetrics recordsMetrics = new RecordsMetrics();
    private List<Record> records;

    public void init() {
        if(SpeedRunRecorder.getInstance().getPreferences().getLoadHistoryRecords()){
            Logger.log("Load all history records and update metrics");
            records = RecordLoader.loadAllRecords();
            for(Record recordToUpdate : records){
                updateRecordsMetrics(recordToUpdate);
            }
        }
        else{
            records = new ArrayList<>();
        }
        generateOverlay();
    }

    public void saveRecord(Record recordToSave) {
        if(recordToSave == null || recordToSave.getEnterNether() == null){
            return;
        }
        RecordLoader.saveRecord(recordToSave);
    }

    public void updateRecord(Record recordToUpdate) {
        if(recordToUpdate == null || recordToUpdate.getEnterNether() == null){
            return;
        }
        Logger.log("Updating record: " + recordToUpdate.getWorldPath());
        if(lastRecord != null){
            if(recordToUpdate.getStart().equals(lastRecord.getStart())){
                updateRecordsMetricsWithLastRecord(recordToUpdate);
            }
            else{
                updateRecordsMetrics(recordToUpdate);
                records.add(recordToUpdate);
            }
            lastRecord.copyRecord(recordToUpdate);
        }
        else{
            lastRecord = new Record(recordToUpdate);
            updateRecordsMetrics(recordToUpdate);
            records.add(recordToUpdate);
        }
        generateOverlay();
    }

    private void updateRecordsMetrics(Record recordToUpdate){
        if(recordToUpdate == null){
            return;
        }
        updateMetric(recordToUpdate::getEnterNether, recordsMetrics::getEnteredNetherCount, recordsMetrics::getEnteredNetherTime, recordsMetrics::setEnteredNetherCount, recordsMetrics::setEnteredNetherTime);
        // do not update Metric if it's fortress first
        if(recordToUpdate.getEnterFortress() == null
                || recordToUpdate.getEnterBastion() != null && recordToUpdate.getEnterBastion() < recordToUpdate.getEnterFortress())
        {
            updateMetric(recordToUpdate::getEnterBastion, recordsMetrics::getEnteredBastionCount, recordsMetrics::getEnteredBastionTime, recordsMetrics::setEnteredBastionCount, recordsMetrics::setEnteredBastionTime);
        }
        if(recordToUpdate.getEnterBastion() != null && recordToUpdate.getEnterFortress() != null
                && recordToUpdate.getEnterBastion() < recordToUpdate.getEnterFortress())
        {
            updateMetric(recordToUpdate::getEnterFortress, recordsMetrics::getEnteredFortressCount, recordsMetrics::getEnteredFortressTime, recordsMetrics::setEnteredFortressCount, recordsMetrics::setEnteredFortressTime);
        }
        updateMetric(recordToUpdate::getFirstPortal, recordsMetrics::getFirstPortalCount, recordsMetrics::getFirstPortalTime, recordsMetrics::setFirstPortalCount, recordsMetrics::setFirstPortalTime);
        updateMetric(recordToUpdate::getEnterStronghold, recordsMetrics::getEnteredStrongholdCount, recordsMetrics::getEnteredStrongholdTime, recordsMetrics::setEnteredStrongholdCount, recordsMetrics::setEnteredStrongholdTime);
        updateMetric(recordToUpdate::getEnterEnd, recordsMetrics::getEnteredEndCount, recordsMetrics::getEnteredEndTime, recordsMetrics::setEnteredEndCount, recordsMetrics::setEnteredEndTime);
        updateMetric(recordToUpdate::getFinish, recordsMetrics::getFinishedCount, recordsMetrics::getFinishedTime, recordsMetrics::setFinishedCount, recordsMetrics::setFinishedTime);
        updateBestRecord(recordToUpdate);
    }

    private void updateMetric(Supplier<Long> recordToUpdateGetter, Supplier<Integer> countGetter, Supplier<Long> timeGetter, Consumer<Integer> countSetter, Consumer<Long> timeSetter) {
        if (recordToUpdateGetter.get() != null) {
            countSetter.accept(countGetter.get() + 1);
            timeSetter.accept(timeGetter.get() + recordToUpdateGetter.get());
        }
    }

    private void updateRecordsMetricsWithLastRecord(Record recordToUpdate){
        if(lastRecord == null || recordToUpdate == null){
            return;
        }
        updateMetricWithLast(lastRecord::getEnterNether, recordToUpdate::getEnterNether, recordsMetrics::getEnteredNetherCount, recordsMetrics::getEnteredNetherTime, recordsMetrics::setEnteredNetherCount, recordsMetrics::setEnteredNetherTime);
        // do not update the Metric if it's fortress first
        if(recordToUpdate.getEnterFortress() == null
                || recordToUpdate.getEnterBastion() != null && recordToUpdate.getEnterBastion() < recordToUpdate.getEnterFortress())
        {
            updateMetricWithLast(lastRecord::getEnterBastion, recordToUpdate::getEnterBastion, recordsMetrics::getEnteredBastionCount, recordsMetrics::getEnteredBastionTime, recordsMetrics::setEnteredBastionCount, recordsMetrics::setEnteredBastionTime);
        }
        if(recordToUpdate.getEnterBastion() != null && recordToUpdate.getEnterFortress() != null
                && recordToUpdate.getEnterBastion() < recordToUpdate.getEnterFortress())
        {
            updateMetricWithLast(lastRecord::getEnterFortress, recordToUpdate::getEnterFortress, recordsMetrics::getEnteredFortressCount, recordsMetrics::getEnteredFortressTime, recordsMetrics::setEnteredFortressCount, recordsMetrics::setEnteredFortressTime);
        }
        updateMetricWithLast(lastRecord::getFirstPortal, recordToUpdate::getFirstPortal, recordsMetrics::getFirstPortalCount, recordsMetrics::getFirstPortalTime, recordsMetrics::setFirstPortalCount, recordsMetrics::setFirstPortalTime);
        updateMetricWithLast(lastRecord::getEnterStronghold, recordToUpdate::getEnterStronghold, recordsMetrics::getEnteredStrongholdCount, recordsMetrics::getEnteredStrongholdTime, recordsMetrics::setEnteredStrongholdCount, recordsMetrics::setEnteredStrongholdTime);
        updateMetricWithLast(lastRecord::getEnterEnd, recordToUpdate::getEnterEnd, recordsMetrics::getEnteredEndCount, recordsMetrics::getEnteredEndTime, recordsMetrics::setEnteredEndCount, recordsMetrics::setEnteredEndTime);
        updateMetricWithLast(lastRecord::getFinish, recordToUpdate::getFinish, recordsMetrics::getFinishedCount, recordsMetrics::getFinishedTime, recordsMetrics::setFinishedCount, recordsMetrics::setFinishedTime);
        updateBestRecord(recordToUpdate);
    }

    private void updateMetricWithLast(Supplier<Long> lastRecordGetter, Supplier<Long> recordToUpdateGetter, Supplier<Integer> countGetter, Supplier<Long> timeGetter, Consumer<Integer> countSetter, Consumer<Long> timeSetter) {
        if (lastRecordGetter.get() == null && recordToUpdateGetter.get() != null) {
            countSetter.accept(countGetter.get() + 1);
            timeSetter.accept(timeGetter.get() + recordToUpdateGetter.get());
        }
    }

    private void updateBestRecord(Record recordToUpdate){
        if(recordToUpdate == null){
            return;
        }

        Record bestRecord = recordsMetrics.getBestRecord();
        bestRecord.setEnterNether(getBestTime(bestRecord.getEnterNether(), recordToUpdate.getEnterNether()));
        bestRecord.setEnterBastion(getBestTime(bestRecord.getEnterBastion(), recordToUpdate.getEnterBastion()));
        // do not update the Best if it's fortress first
        if(recordToUpdate.getEnterBastion() != null && recordToUpdate.getEnterFortress() != null
                && recordToUpdate.getEnterBastion() < recordToUpdate.getEnterFortress())
        {
            bestRecord.setEnterFortress(getBestTime(bestRecord.getEnterFortress(), recordToUpdate.getEnterFortress()));
        }
        bestRecord.setFirstPortal(getBestTime(bestRecord.getFirstPortal(), recordToUpdate.getFirstPortal()));
        bestRecord.setEnterStronghold(getBestTime(bestRecord.getEnterStronghold(), recordToUpdate.getEnterStronghold()));
        bestRecord.setEnterEnd(getBestTime(bestRecord.getEnterEnd(), recordToUpdate.getEnterEnd()));
        bestRecord.setFinish(getBestTime(bestRecord.getFinish(), recordToUpdate.getFinish()));
    }

    private Long getBestTime(Long time1, Long time2){
        if(time1 == null){
            return time2;
        }
        if(time2 == null){
            return time1;
        }
        return Math.min(time1, time2);
    }

    private void generateOverlay(){
        Logger.log("Generating overlay");
        Preferences preferences = SpeedRunRecorder.getInstance().getPreferences();
        if(preferences.getGenerateAverage()){
            OverlayWriter.writeAverageOverlay(getAverageRecordMap(recordsMetrics));
        }
        if(preferences.getGenerateCurrent()){
            OverlayWriter.writeCurrentOverlay(getCurrentRecordMap(lastRecord));
        }
        if(preferences.getGenerateCount()){
            OverlayWriter.writeCountOverlay(recordsMetrics);
        }
        if(preferences.getGenerateRecords()){
            OverlayWriter.writeRecordsOverlay(getRecordsMapList(), getCurrentRecordMap(recordsMetrics.getBestRecord()), getAverageRecordMap(recordsMetrics));
        }
    }

    public Map<String, String> getCurrentRecordMap(Record recordToWrite){
        Map<String, String> recordToWriteMap = new HashMap<>();
        if(recordToWrite == null){
            return recordToWriteMap;
        }
        addTimeToMap(recordToWrite::getStart, "start", recordToWriteMap, TimeUtil::formatDateTime);
        addTimeToMap(recordToWrite::getEnterNether, "enterNether", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getEnterBastion, "enterBastion", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getEnterFortress, "enterFortress", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getFirstPortal, "firstPortal", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getEnterStronghold, "enterStronghold", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getEnterEnd, "enterEnd", recordToWriteMap, TimeUtil::formatTime);
        addTimeToMap(recordToWrite::getFinish, "finish", recordToWriteMap, TimeUtil::formatTime);
        return recordToWriteMap;
    }

    private void addTimeToMap(Supplier<Long> timeGetter, String key, Map<String, String> map, Function<Long, String> formatter) {
        Long time = timeGetter.get();
        if (time != null) {
            map.put(key, formatter.apply(time));
        }
    }

    public Map<String, String> getAverageRecordMap(RecordsMetrics recordsMetrics){
        Map<String, String> recordToWriteMap = new HashMap<>();
        addAverageTimeToMap(recordsMetrics::getEnteredNetherCount, recordsMetrics::getEnteredNetherTime, "enterNether", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getEnteredBastionCount, recordsMetrics::getEnteredBastionTime, "enterBastion", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getEnteredFortressCount, recordsMetrics::getEnteredFortressTime, "enterFortress", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getFirstPortalCount, recordsMetrics::getFirstPortalTime, "firstPortal", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getEnteredStrongholdCount, recordsMetrics::getEnteredStrongholdTime, "enterStronghold", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getEnteredEndCount, recordsMetrics::getEnteredEndTime, "enterEnd", recordToWriteMap);
        addAverageTimeToMap(recordsMetrics::getFinishedCount, recordsMetrics::getFinishedTime, "finish", recordToWriteMap);
        return recordToWriteMap;
    }

    private void addAverageTimeToMap(Supplier<Integer> countGetter, Supplier<Long> timeGetter, String key, Map<String, String> map) {
        if (countGetter.get() != 0) {
            map.put(key, TimeUtil.formatTime(timeGetter.get() / countGetter.get()));
        }
    }

    public List<Map<String, String>> getRecordsMapList(){
        List<Map<String, String>> recordsMapList = new ArrayList<>();
        if(records == null){
            return recordsMapList;
        }
        for(Record recordToAdd : records){
            recordsMapList.add(getCurrentRecordMap(recordToAdd));
        }
        return recordsMapList;
    }
}
