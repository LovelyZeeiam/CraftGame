package xueli.gamengine.utils.callbacks;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public abstract class CursorPosCallback extends GLFWCursorPosCallback {

    public double mouseDX = 0, mouseDY = 0;
    public double mouseX = 0, mouseY = 0;
    public boolean shouldNotProcessMouseThisTime = false;
    private double lastTimeMouseX = 0, lastTimeMouseY = 0;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        if (!shouldNotProcessMouseThisTime) {
            mouseDX = xpos - lastTimeMouseX;
            mouseDY = lastTimeMouseY - ypos;
        } else {
            mouseDX = mouseDY = 0;
            shouldNotProcessMouseThisTime = false;
        }

        lastTimeMouseX = xpos;
        lastTimeMouseY = ypos;

        mouseX = xpos;
        mouseY = ypos;

        invoke();

    }

    public abstract void invoke();

}
