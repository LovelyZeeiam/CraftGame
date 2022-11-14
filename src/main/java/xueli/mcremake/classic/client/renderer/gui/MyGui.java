package xueli.mcremake.classic.client.renderer.gui;

import xueli.game2.lifecycle.LifeCycle;
import xueli.mcremake.classic.client.CraftGameClient;

import java.awt.*;

public class MyGui implements LifeCycle {

	private final CraftGameClient ctx;

	private final MyFont font;

	public MyGui(CraftGameClient ctx) {
		this.ctx = ctx;
		this.font = new MyFont(ctx);

	}

	@Override
	public void init() {
		this.font.init();

	}

	public void drawFont(float x, float y, float size, String str, Color color) {
		this.font.drawFont(x, y, size, str, color);
	}

	@Override
	public void tick() {
		this.font.tick();

	}

	@Override
	public void release() {
		this.font.release();

	}

}
