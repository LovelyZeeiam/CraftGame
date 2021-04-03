package xueli.craftgame.state;

import xueli.craftgame.level.Level;
import xueli.game.net.Client;
import xueli.game.utils.renderer.NVGRenderer;

import java.net.InetAddress;

public class StateWorld extends NVGRenderer {

	private boolean isMultiPlayer = false;
	private Client client;
	
	private Level level;
	
	public StateWorld(String path) {
		this.level = new Level(path);
		
		
		
	}
	
	public StateWorld(String name, String path) {
		this.level = new Level(name, path);
		
		
	}
	
	public StateWorld(InetAddress ipaddress) {
		isMultiPlayer = true;
		
		
		
	}
	
	public void runLevelInit() {
		if(!isMultiPlayer) {
			
			
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
		if(!isMultiPlayer) {
			
			
		}
		
		
	}
	
}
