package cloud7c.speedrunrecorder.util;

public class Logger {
    public static void log(String message){
        System.out.println("[" + TimeUtil.formatDateTime(System.currentTimeMillis()) + "] " + message);
    }
}
