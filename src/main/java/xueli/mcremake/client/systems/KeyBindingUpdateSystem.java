package xueli.mcremake.client.systems;

import xueli.game2.display.event.WindowKeyEvent;
import xueli.game2.display.event.WindowMouseButtonEvent;
import xueli.game2.input.DefaultKeyListener;
import xueli.game2.input.DefaultMouseListener;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.GameSystem;

public class KeyBindingUpdateSystem implements GameSystem {

    private DefaultKeyListener keyListener;
	private DefaultMouseListener mouseListener;

    @Override
    public void start(CraftGameClient ctx) {
        keyListener = new DefaultKeyListener(ctx.state.keyBindings);
        mouseListener = new DefaultMouseListener(ctx.state.keyBindings);

        ctx.eventbus.register(WindowKeyEvent.class, this::processKeyEvent);
        ctx.eventbus.register(WindowMouseButtonEvent.class, this::processMouseButtonEvent);

    }

    private void processKeyEvent(WindowKeyEvent event) {
        keyListener.onKey(event.key(), event.scancode(), event.action(), event.mods());
    }

    private void processMouseButtonEvent(WindowMouseButtonEvent event) {
        mouseListener.onMouseButton(event.button(), event.action(), event.mods());
    }

    @Override
    public void update(CraftGameClient ctx) {
        
    }

    @Override
    public void release(CraftGameClient ctx) {
        ctx.eventbus.unregister(WindowKeyEvent.class, this::processKeyEvent);
        ctx.eventbus.unregister(WindowMouseButtonEvent.class, this::processMouseButtonEvent);
    }

}
