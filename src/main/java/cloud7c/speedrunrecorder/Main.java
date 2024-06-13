package cloud7c.speedrunrecorder;

import cloud7c.speedrunrecorder.gui.SpeedRunRecorderGUI;
import cloud7c.speedrunrecorder.io.PreferencesLoader;

public class Main {
    public static void main(String[] args) {
        SpeedRunRecorder speedRunRecorder = new SpeedRunRecorder(PreferencesLoader.loadPreferences());
        speedRunRecorder.start();
        new SpeedRunRecorderGUI();
    }
}
