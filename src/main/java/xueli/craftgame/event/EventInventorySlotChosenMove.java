package xueli.craftgame.event;

public class EventInventorySlotChosenMove {

	private int to;

	public EventInventorySlotChosenMove(int to) {
		this.to = to;
	}

	public int get() {
		return to;
	}

}
