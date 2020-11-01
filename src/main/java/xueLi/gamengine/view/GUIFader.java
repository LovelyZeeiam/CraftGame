package xueLi.gamengine.view;

import xueLi.gamengine.utils.Shader;
import xueLi.gamengine.utils.Time;

public class GUIFader {

    public static enum Faders {

        LINEAR(new Fader() {

            @Override
            public boolean fade(long fade_duration, Shader guiShader, long fadeStartTime) {
                float fade = (float) (Time.thisTime - fadeStartTime) / fade_duration;
                guiShader.setFloat(guiShader.getUnifromLocation("mix_value"), fade);
                return fade >= 1.0f;
            }

        });

        public Fader fader;

        Faders(Fader fader) {
            this.fader = fader;

        }

    }

    public static abstract class Fader {

        /**
         * @return 当动画完成时 返回true
         */
        public abstract boolean fade(long fade_duration, Shader guiShader, long fadeStartTime);

    }

}
