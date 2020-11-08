package xueli.gamengine.utils.callbacks;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class AWTMouseListener implements MouseMotionListener {

    private int x, y;

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
