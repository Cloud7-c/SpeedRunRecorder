package cloud7c.speedrunrecorder.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AtumSettingsReader {
    public static Map<String, String> readAtumSettings(String worldPath){
        Map<String, String> settings = new HashMap<>();
        Path atumSettingsPath = Paths.get(worldPath)
                                    .getParent().getParent()
                                    .resolve("config").resolve("atum")
                                    .resolve("atum.properties");
        try {
            String content = new String(Files.readAllBytes(atumSettingsPath));
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split("=");
                if(parts.length == 2){
                    settings.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return settings;
    }
}
