package cloud7c.speedrunrecorder.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class LatestRunReader {
    public static List<String> readNewLines(String path, long lastCheckTime, int oldLineCount) {
        File file = new File(path);
        List<String> newLines = new ArrayList<>();

        // If the file does not exist or the file was last modified before the last check time, return an empty list.
        if (!file.exists() || file.exists() && file.lastModified() < lastCheckTime) {
            return newLines;
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null && !line.trim().equals("")) {
                lineCount++;
                if(lineCount <= oldLineCount){
                    continue;
                }
                newLines.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newLines;
    }

    // Some file systems may not support getting the file creation time.
    public static long getCreationTime(String path) {
        try {
            return Files.readAttributes(Paths.get(path), BasicFileAttributes.class).creationTime().toMillis();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
