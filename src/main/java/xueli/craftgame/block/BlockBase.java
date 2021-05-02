package xueli.craftgame.block;

import java.awt.Color;
import java.util.Objects;

import xueli.craftgame.renderer.model.TexturedModel;
import xueli.game.module.Module;
import xueli.game.utils.FloatList;

public class BlockBase extends Module {

	// name in language define
	protected String nameInternational;

	protected TexturedModel model;
	protected boolean isAlpha = false;
	protected boolean isComplete = true;

	protected IBlockListener listener = IBlockListener.EMPTY;

	public BlockBase(String namespace) {
		super(namespace);

	}

	public BlockBase(String namespace, String nameInternational, TexturedModel model, boolean isAlpha,
			boolean isComplete, IBlockListener listener) {
		this(namespace);
		this.nameInternational = nameInternational;
		this.model = model;
		this.isAlpha = isAlpha;
		this.isComplete = isComplete;
		this.listener = listener;
		
	}

	public String getNameInternational() {
		return nameInternational;
	}

	public IBlockListener getListener() {
		return listener;
	}

	@Override
	public boolean checkInvaild() {
		return Objects.nonNull(getNamespace()) && Objects.nonNull(nameInternational) && Objects.nonNull(listener);
	}

	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Color color) {
		return model.getRenderData(x, y, z, face, color, buffer);
	}

	@Override
	public String toString() {
		return "BlockBase [namespace=" + getNamespace() + ", nameInternational=" + nameInternational + ", model=" + model
				+ ", listener=" + listener + "]";
	}

}
