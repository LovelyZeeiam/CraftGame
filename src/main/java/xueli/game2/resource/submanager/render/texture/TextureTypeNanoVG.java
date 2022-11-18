package xueli.game2.resource.submanager.render.texture;

import java.util.Arrays;

public class TextureTypeNanoVG implements TextureLoaderBuilder {

	private final NvgImageFlag[] flags;

	public TextureTypeNanoVG(NvgImageFlag... flags) {
		this.flags = flags;
	}

	@Override
	public AbstractTextureLoader getLoader(TextureRenderResource resManager) {
		return new TextureLoaderNanoVG(resManager.gui.getContext(), flags);
	}

	@Override
	public String toString() {
		return "TextureTypeNanoVG{" +
				"flags=" + Arrays.toString(flags) +
				'}';
	}

}
