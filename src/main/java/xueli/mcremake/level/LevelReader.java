package xueli.mcremake.level;

import java.io.File;

public class LevelReader {
    
    private LevelReader() {}

    public static LevelInfo readLevelInfo(File levelFolder) throws Exception {
        return new LevelInfo(new File(levelFolder, "level.dat"));
    }

}
