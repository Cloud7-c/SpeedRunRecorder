package cloud7c.speedrunrecorder.io;

import cloud7c.speedrunrecorder.model.Preferences;
import cloud7c.speedrunrecorder.util.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class PreferencesLoader {
    private static final Path PREFERENCES_FILE_PATH = Path.of("config/preferences.json");

    static {
        Path configDir = Path.of("config");
        if (!Files.exists(configDir)) {
            try {
                Files.createDirectories(configDir);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.log("Failed to create config directory");
            }
        }
    }

    public static Preferences loadPreferences(){
        try {
            if (!Files.exists(PREFERENCES_FILE_PATH)) {
                Logger.log("Preferences file not found, creating default preferences file");
                createDefaultPreferencesFile();
                return new Preferences();
            }
            String content = Files.readString(PREFERENCES_FILE_PATH);
            Gson gson = new Gson();
            return gson.fromJson(content, Preferences.class);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Failed to load preferences file, using default preferences");
        }
        return new Preferences();
    }

    public static void savePreferences(Preferences preferences){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        try {
            Files.writeString(PREFERENCES_FILE_PATH, gson.toJson(preferences));
        } catch (IOException e) {
            e.printStackTrace();
            Logger.log("Failed to save preferences file");
        }
    }

    private static void createDefaultPreferencesFile(){
        Preferences defaultPreferences = new Preferences();
        savePreferences(defaultPreferences);
    }
}
