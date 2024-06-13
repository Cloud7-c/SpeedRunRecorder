package cloud7c.speedrunrecorder.io;

import cloud7c.speedrunrecorder.SpeedRunRecorder;
import cloud7c.speedrunrecorder.model.Preferences;
import cloud7c.speedrunrecorder.model.RecordsMetrics;
import cloud7c.speedrunrecorder.util.Logger;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class OverlayWriter {
    private static final Path OVERLAY_DIR = Path.of("overlay");
    private static final String OVERLAY_TOTAL_PATH = "overlay/count.html";
    private static final String OVERLAY_CURRENT_PATH = "overlay/current.html";
    private static final String OVERLAY_AVERAGE_PATH = "overlay/average.html";
    private static final String OVERLAY_RECORDS_PATH = "overlay/records.html";
    private static final Configuration cfg = new Configuration(new Version("2.3.33"));

    static {
        checkOverlayDir();
        cfg.setDefaultEncoding("UTF-8");
    }

    public static void writeCountOverlay(RecordsMetrics recordsMetrics){
        int refreshInterval = SpeedRunRecorder.getInstance().getPreferences().getRefreshInterval();
        writeOverlay(Map.of("recordsMetrics", recordsMetrics, "refreshInterval", refreshInterval), OVERLAY_TOTAL_PATH, "count.ftl");
    }

    public static void writeCurrentOverlay(Map<String, String> recordToWriteMap){
        writeOverlay(recordToWriteMap, OVERLAY_CURRENT_PATH, "current.ftl");
    }

    public static void writeAverageOverlay(Map<String, String> recordToWriteMap){
        writeOverlay(recordToWriteMap, OVERLAY_AVERAGE_PATH, "average.ftl");
    }

    public static void writeRecordsOverlay(List<Map<String, String>> recordsToWriteMapList, Map<String, String> bestRecordMap, Map<String, String> averageRecordMap){
        Preferences preferences = SpeedRunRecorder.getInstance().getPreferences();
        Map<String, Object> dataMap = Map.of(
                "records", recordsToWriteMapList,
                "bestRecord", bestRecordMap,
                "averageRecord", averageRecordMap,
                "refreshInterval", preferences.getRefreshInterval(),
                "renderBestRecord", preferences.getRenderBestRecord(),
                "renderAverageRecord", preferences.getRenderAverageRecord()
        );
        writeOverlay(dataMap, OVERLAY_RECORDS_PATH, "records.ftl");
    }

    private static void writeOverlay(Map<String, String> objectToWriteMap, String path, String templateName){
        objectToWriteMap.put("refreshInterval", String.valueOf(SpeedRunRecorder.getInstance().getPreferences().getRefreshInterval()));
        writeOverlay((Object) objectToWriteMap, path, templateName);
    }

    private static void writeOverlay(Object objectToWriteMap, String path, String templateName){
        try(Writer fileWriter = new FileWriter(path)){
            cfg.setDirectoryForTemplateLoading(new File("templates"));
            Template template = cfg.getTemplate(templateName);
            template.process(objectToWriteMap, fileWriter);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    private static void checkOverlayDir(){
        if (!Files.exists(OVERLAY_DIR)) {
            try {
                Files.createDirectories(OVERLAY_DIR);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("Failed to create records directory");
            }
        }
    }
}
