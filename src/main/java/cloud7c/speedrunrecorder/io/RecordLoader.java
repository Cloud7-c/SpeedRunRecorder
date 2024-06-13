package cloud7c.speedrunrecorder.io;

import cloud7c.speedrunrecorder.model.Record;
import cloud7c.speedrunrecorder.util.Logger;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecordLoader {
    private static final Path RECORDS_PATH = Path.of("records");

    static {
        if (!Files.exists(RECORDS_PATH)) {
            try {
                Files.createDirectories(RECORDS_PATH);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("Failed to create records directory");
            }
        }
    }

    public static List<Record> loadAllRecords() {
        List<Record> records = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(RECORDS_PATH, path -> path.toString().endsWith(".json"))) {
            for (Path file: stream) {
                if (!Files.isDirectory(file)) {
                    Logger.log("Loading record: " + file.getFileName());
                    Record newRecord = loadRecord(file);
                    if (newRecord != null) {
                        records.add(newRecord);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Failed to load records");
        }
        return records;
    }

    public static Record loadRecord(Path path) {
        try {
            String content = Files.readString(path);
            Gson gson = new Gson();
            return gson.fromJson(content, Record.class);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Failed to load record");
            return null;
        }
    }

    public static void saveRecord(Record recordToSave){
        Gson gson = new Gson();
        String json = gson.toJson(recordToSave);

        String fileName = recordToSave.getStart().toString() + ".json";
        Path filePath = RECORDS_PATH.resolve(fileName);

        try{
            Files.writeString(filePath, json);
            Logger.log("Saved record: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Failed to save record");
        }
    }
}
