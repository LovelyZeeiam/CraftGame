package xueli.craftgame.state;

import java.net.InetAddress;

import xueli.craftgame.level.Level;
import xueli.game.net.Client;
import xueli.game.utils.renderer.NVGRenderer;

public class StateWorldIngame extends NVGRenderer {

	private boolean isMultiPlayer = false;
	private Client client;

	private Level level;

	public StateWorldIngame(String path) {
		this.level = new Level(path);

	}

	public StateWorldIngame(String name, String path) {
		this.level = new Level(name, path);

	}

	public StateWorldIngame(InetAddress ipaddress) {
		isMultiPlayer = true;

	}

	public void runLevelInit() {
		if (!isMultiPlayer) {

		}

	}

	@Override
	public void stroke() {

	}

	@Override
	public void render() {
		super.render();

	}

	public void runLevelSave() {
		if (!isMultiPlayer) {

		}

	}

}
