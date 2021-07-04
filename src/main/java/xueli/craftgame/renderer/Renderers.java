package xueli.craftgame.renderer;

import xueli.craftgame.renderer.world.SolidRenderer;

public enum Renderers {

	SOLID(new SolidRenderer());

	Renderer renderer;

	Renderers(Renderer r) {
		this.renderer = r;
	}

	public Renderer getRenderer() {
		return renderer;
	}

}
