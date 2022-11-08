package xueli.game2.renderer.legacy;

import java.util.HashMap;

import xueli.game2.lifecycle.LifeCycle;
import xueli.game2.resource.ResourceLocation;

public class RenderSystem<T extends RenderType> implements LifeCycle {

	private final HashMap<ResourceLocation, T> renderTypes = new HashMap<>();

	public RenderSystem() {
	}

	public void registerRenderType(ResourceLocation namespace, T renderType) {
		this.renderTypes.put(namespace, renderType);
	}

	@Override
	public void init() {
		renderTypes.values().forEach(T::doInit);

	}

	@Override
	public void tick() {
		renderTypes.values().forEach(T::render);

	}

	@Override
	public void release() {
		renderTypes.values().forEach(T::release);

	}

}
