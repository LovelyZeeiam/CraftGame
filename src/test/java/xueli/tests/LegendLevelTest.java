package xueli.tests;

import java.io.File;

import xueli.mcremake.level.LevelInfo;
import xueli.mcremake.level.LevelReader;

public class LegendLevelTest {
    
    public static void main(String[] args) throws Exception {
        File file = new File("D:/Minecraft/LegendSaves/minecraftWorlds/05s-ZNPeDgA=-copy");
        try(LevelInfo info = LevelReader.readLevelInfo(file)) {
            System.out.println(info);
        }

    }

}
