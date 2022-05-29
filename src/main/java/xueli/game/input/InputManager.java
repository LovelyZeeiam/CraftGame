package xueli.game.input;

import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import xueli.craftgame.CraftGameContext;

public class InputManager {

	public static final int WEIGH_PLAYER_CONTROL = 0;
	public static final int WEIGH_GUI = 1000000;

	CraftGameContext ctx;
	boolean shouldRecalculateWeightMax = false;

	private TreeMap<Integer, InputHolder> holders = new TreeMap<>(Comparator.comparingInt(e -> e));

	public InputManager(CraftGameContext ctx) {
		this.ctx = ctx;
		ctx.getDisplay().addKeyListener(this::doInputListener);

	}

	public InputHolder createInputHolder(int weight) {
		InputHolder holder = new InputHolder(weight, this);
		holders.put(weight, holder);
		return holder;
	}

	boolean canReceiveSignal(int weight) {
		return weight >= canReceiveWeightMax();
	}

	private int canReceiveWeightMax() {
		int maxReceiveWeight = Integer.MIN_VALUE;
		for (InputHolder value : holders.values()) {
			if (value.passInterrupt)
				maxReceiveWeight = Math.max(maxReceiveWeight, value.weight);
		}
		return maxReceiveWeight;
	}

	private void doInputListener(int key, int scancode, int action, int mods) {
		int maxReceiveWeight = canReceiveWeightMax();
		Entry<Integer, InputHolder> holder = null;
		while (true) {
			holder = holders.higherEntry(maxReceiveWeight);
			if (holder == null)
				break;
			holder.getValue().doInputListener(key, scancode, action, mods);
			maxReceiveWeight = holder.getKey();
		}

	}

	public void release() {
		ctx.getDisplay().removeKeyListener(this::doInputListener);

	}

}
