package xueli.craftgame.player;

import xueli.game.vector.Vector;

public interface IViewPerspective {

	public default Vector getViewPerspective(LocalPlayer player) {
		return player.getPlayer().getPos();
	}

}
