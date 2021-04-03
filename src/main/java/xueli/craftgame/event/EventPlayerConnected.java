package xueli.craftgame.event;

import xueli.game.event.Event;

public class EventPlayerConnected implements Event {

	private static final long serialVersionUID = 8895340084828035418L;

	private String name;
	private byte[] icon;
	
	public EventPlayerConnected(String name, byte[] icon) {
		this.name = name;
		this.icon = icon;
		
	}
	
	public String getName() {
		return name;
	}
	
	public byte[] getIcon() {
		return icon;
	}
	
}
