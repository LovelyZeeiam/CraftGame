package xueli.craftgame.block;

import java.awt.Color;
import java.util.Objects;

import xueli.craftgame.renderer.model.TexturedModel;
import xueli.game.utils.FloatList;

public class BlockBase {

	// namespace
	protected String namespace;
	// name in language define
	protected String nameInternational;

	protected TexturedModel model;
	protected boolean isAlpha = false;
	protected boolean isComplete = true;

	protected IBlockListener listener = IBlockListener.EMPTY;

	public BlockBase() {

	}

	public BlockBase(String namespace, String nameInternational, TexturedModel model, boolean isAlpha,
			boolean isComplete, IBlockListener listener) {
		this();
		this.namespace = namespace;
		this.nameInternational = nameInternational;
		this.model = model;
		this.isAlpha = isAlpha;
		this.isComplete = isComplete;
		this.listener = listener;
		
	}

	public String getNamespace() {
		return namespace;
	}

	public String getNameInternational() {
		return nameInternational;
	}

	public IBlockListener getListener() {
		return listener;
	}

	public boolean checkInvaildBlockbase() {
		return Objects.nonNull(namespace) && Objects.nonNull(nameInternational) && Objects.nonNull(listener);
	}

	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Color color) {
		return model.getRenderData(x, y, z, face, color, buffer);
	}

	@Override
	public String toString() {
		return "BlockBase [namespace=" + namespace + ", nameInternational=" + nameInternational + ", model=" + model
				+ ", listener=" + listener + "]";
	}

}
