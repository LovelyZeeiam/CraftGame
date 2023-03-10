package xueli.game2.renderer.legacy;

import java.util.HashMap;

import xueli.game2.resource.ResourceIdentifier;

public class RenderSystem<T extends RenderType<?>> {

	private final HashMap<ResourceIdentifier, T> renderTypes = new HashMap<>();

	public RenderSystem() {
	}

	public void registerRenderType(ResourceIdentifier namespace, T renderType) {
		this.renderTypes.put(namespace, renderType);
	}

	public void tick() {
		renderTypes.values().forEach(T::render);

	}

	public void release() {
		renderTypes.values().forEach(T::release);

	}

}
