package xueli.game2.renderer.legacy;

import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.renderer.legacy.system.RenderState;
import xueli.game2.renderer.legacy.system.RenderSystem;

import java.util.HashMap;
import java.util.function.Consumer;

public class RenderMaster implements LifeCycle {

	private final HashMap<RenderState, RenderSystem> systems = new HashMap<>();

	public RenderMaster() {
	}

	public RenderSystem get(RenderState state) {
		return systems.computeIfAbsent(state, RenderSystem::withState);
	}

	public void remove(RenderSystem system) {
		systems.remove(system);
	}

	@Override
	public void init() {
		systems.values().forEach(RenderSystem::init);

	}

	public RenderSystem getAndInit(RenderState state) {
		RenderSystem system = this.get(state);
		system.init();
		return system;
	}

	@Override
	public void tick() {
		systems.values().forEach(RenderSystem::tick);

	}

	@Override
	public void release() {
		systems.values().forEach(RenderSystem::release);
		systems.clear();
	}

	public void systems(Consumer<RenderSystem> consumer) {
		systems.values().forEach(consumer);
	}

	public void reportRebuilt() {
		systems.values().forEach(RenderSystem::reportRebuilt);
	}

}
