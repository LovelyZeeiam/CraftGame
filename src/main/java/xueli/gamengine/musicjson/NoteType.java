package xueli.gamengine.musicjson;

import fr.delthas.javamp3.Sound;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import xueli.gamengine.utils.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

public class NoteType {

    private static HashMap<String, Integer> noteTypes = new HashMap<>();

    static {
        File file = new File("res/sounds/note/");
        Arrays.stream(file.listFiles()).forEach((f) -> {

        });

    }

    public static int getBuffer(String name){
        return noteTypes.get(name);
    }

    public static void playSound(int buffer, float rate) {


    }

    public static void release() {


    }

}
