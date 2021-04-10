package xueli.craftgame.state;

import java.net.InetAddress;

import xueli.game.net.Client;
import xueli.game.renderer.NVGRenderer;

public class StateWorldIngame extends NVGRenderer {

	private boolean isMultiPlayer = false;
	private Client client;

	public StateWorldIngame(String path) {

	}

	public StateWorldIngame(String name, String path) {

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

	@Override
	public void update() {

	}

	public void runLevelSave() {
		if (!isMultiPlayer) {

		}

	}

}
