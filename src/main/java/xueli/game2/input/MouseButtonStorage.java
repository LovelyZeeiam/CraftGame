package xueli.game2.input;

import java.util.LinkedList;
import java.util.Queue;

import xueli.game2.display.MouseInputListener;

@Deprecated
public class MouseButtonStorage implements MouseInputListener {

	private record MouseButtonRecord(int button, int mods, int action) {}
	private Queue<MouseButtonRecord> records = new LinkedList<>();

	@Override
	public void onMouseButton(int button, int action, int mods) {
		this.records.add(new MouseButtonRecord(button, mods, action));
	}

	public void pull(MouseInputListener listener) {
		MouseButtonRecord rec;
		while((rec = this.records.poll()) != null) {
			listener.onMouseButton(rec.button, rec.mods, rec.action);
		}
	}

}
