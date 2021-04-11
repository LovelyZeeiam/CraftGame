package xueli.game.renderer.widgets;

import xueli.game.Game;

public abstract class Dialog extends IWidget {

	protected DialogManager manager;

	public Dialog(DialogManager manager) {
		super(0, 0, Game.INSTANCE_GAME.getWidth(), Game.INSTANCE_GAME.getHeight());
		this.manager = manager;

	}

	public String getSignature() {
		return "EMPTY_SIGNATURE";
	}

}
