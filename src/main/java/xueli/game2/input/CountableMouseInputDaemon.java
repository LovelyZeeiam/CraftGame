package xueli.game2.input;

@Deprecated
public abstract class CountableMouseInputDaemon implements MouseInputDaemon {

	protected int count = 0;

	@Override
	public void tick() {
		count++;
	}

}
