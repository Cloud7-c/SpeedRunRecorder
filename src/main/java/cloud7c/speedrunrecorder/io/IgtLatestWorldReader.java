package cloud7c.speedrunrecorder.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IgtLatestWorldReader {
    public static String readLatestWorld(long lastCheckTime){
        Path igtLatestWorldPath = Paths.get(System.getProperty("user.home"))
                                        .resolve("speedrunigt")
                                        .resolve("latest_world.json")
                                        .toAbsolutePath();
        File igtLatestWorldFile = igtLatestWorldPath.toFile();
        if(!igtLatestWorldFile.exists()){
            return null;
        }

        if(igtLatestWorldFile.lastModified() < lastCheckTime){
            return null;
        }

        String content = null;
        try{
            content = Files.readString(igtLatestWorldPath).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
