package xueli.game2.renderer.legacy;

import java.util.HashMap;

import xueli.game2.resource.ResourceLocation;

public class RenderSystem<T extends RenderType<?>> {

	private final HashMap<ResourceLocation, T> renderTypes = new HashMap<>();

	public RenderSystem() {
	}

	public void registerRenderType(ResourceLocation namespace, T renderType) {
		this.renderTypes.put(namespace, renderType);
	}

	public void tick() {
		renderTypes.values().forEach(T::render);

	}

	public void release() {
		renderTypes.values().forEach(T::release);

	}

}
