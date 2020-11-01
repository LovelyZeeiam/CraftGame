package xueLi.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;

public class KeyCallback extends GLFWKeyCallback {

    public static boolean[] keys = new boolean[65536];
    public static boolean[] keysOnce = new boolean[65536];
    private static ArrayList<Integer> keyPressed = new ArrayList<Integer>();

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key < 0) {
            System.out.println("What is this key? " + key);
            return;
        }

        keys[key] = action != GLFW.GLFW_RELEASE;

        keysOnce[key] = action != GLFW.GLFW_RELEASE;
        keyPressed.add(key);

    }

    public void tick() {
        for (int keyID : keyPressed)
            keysOnce[keyID] = false;
    }

}
