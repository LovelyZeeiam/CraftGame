package xueli.gamengine.view;

import org.lwjgl.nanovg.NVGColor;
import xueli.gamengine.utils.EvalableFloat;
import xueli.gamengine.utils.Time;

import static org.lwjgl.nanovg.NanoVG.*;

public class GUIProgressBar extends ViewWidget {

    // 进度条动画的滚动速度
    private static final float progress_bar_anim_velocity = 0.0005f;
    // 进度条的进度
    float progress = 0.0f;
    // 进度条的动画要达到的目标进度
    float dest_progress;
    // 进度条的边框宽度
    int progress_bar_width = 4;
    // 进度条内部进度的x,y,长宽
    private float real_progress_bar_x, real_progress_bar_y, real_progress_bar_width, real_progress_bar_height;
    private NVGColor backColor, progressColor;

    public GUIProgressBar(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
                          float start_progress, NVGColor backColor, NVGColor progressColor) {
        super(x, y, width, height);
        this.backColor = backColor;
        this.progressColor = progressColor;
        refreshRealProgressBar();

        this.dest_progress = start_progress;

    }

    public void setProgressBarWidth(int width) {
        this.progress_bar_width = width;

    }

    private void refreshRealProgressBar() {
        real_progress_bar_x = real_x + progress_bar_width;
        real_progress_bar_y = real_y + progress_bar_width;
        real_progress_bar_width = real_width - (2 * progress_bar_width);
        real_progress_bar_height = real_height - (2 * progress_bar_width);

    }

    @Override
    public void draw(long nvg) {
        super.anim_tick();

        // 进度条的刷新任务
        if (dest_progress > progress) {
            progress += progress_bar_anim_velocity * Time.deltaTime;
            if (progress > dest_progress) {
                progress = dest_progress;
            }
        } else if (dest_progress < progress) {
            progress -= progress_bar_anim_velocity * Time.deltaTime;
            if (progress < dest_progress) {
                progress = dest_progress;
            }
        }

        // 进度条的背景
        nvgBeginPath(nvg);
        nvgRect(nvg, real_x, real_y, real_width, real_height);
        nvgFillColor(nvg, backColor);
        nvgFill(nvg);
        // 蒸郑的进度条
        nvgBeginPath(nvg);
        nvgRect(nvg, real_progress_bar_x, real_progress_bar_y, real_progress_bar_width * progress,
                real_progress_bar_height);
        nvgFillColor(nvg, progressColor);
        nvgFill(nvg);

    }

    public void setProgress(float progress) {
        if (progress > 1)
            this.dest_progress = 1;
        else if (progress < 0)
            this.dest_progress = 0;
        else
            dest_progress = progress;
    }

    /**
     * 等待直到dest_progress与展示的progress相同
     */
    public void waitUtilProgressFull() {
        int waitTime = (int) ((float) Math.abs(progress - dest_progress) / progress_bar_anim_velocity);
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void size() {
        super.size();
        refreshRealProgressBar();

    }

    @Override
    public void close() throws Exception {
        backColor.free();
        progressColor.free();
        super.close();

    }

}
