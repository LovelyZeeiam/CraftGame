package xueli.craftgame.block;

import xueli.craftgame.renderer.blocks.BlockRenderable;

public class BlockType {

	private String namespace;
	private String name;

	private BlockRenderable renderable;
	private BlockListener listener = BlockListener.EMPTY;

	public BlockType(String namespace, String name) {
		this.namespace = namespace;
		this.name = name;
	}

	public BlockType setRenderable(BlockRenderable renderable) {
		this.renderable = renderable;
		return this;
	}

	public BlockType setListener(BlockListener listener) {
		this.listener = listener;
		return this;
	}

	public BlockRenderable getRenderable() {
		return renderable;
	}

	public BlockListener getListener() {
		return listener;
	}

	public String getName() {
		return name;
	}

	public String getNamespace() {
		return namespace;
	}

}