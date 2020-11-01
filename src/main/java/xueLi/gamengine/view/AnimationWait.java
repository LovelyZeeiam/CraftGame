package xueLi.gamengine.view;

import java.util.HashMap;

public class AnimationWait extends IAnimation {

    private int duration;

    public AnimationWait(int duration) {
        this.duration = duration;

    }

    private boolean tick() {
        if (System.currentTimeMillis() - startTime > duration)
            return true;
        return false;
    }

    @Override
    public boolean tick(ViewWidget widget) {
        return tick();
    }

    @Override
    public boolean tick(HashMap<String, ViewWidget> widgets) {
        return tick();
    }

}
