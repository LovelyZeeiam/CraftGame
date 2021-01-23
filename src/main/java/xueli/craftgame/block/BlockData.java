package xueli.craftgame.block;

import java.util.ArrayList;
import java.util.Objects;

import org.lwjgl.nanovg.NVGColor;

import xueli.craftgame.block.model.IModel;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.Color;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

public class BlockData {

	private String namespace;
	private String blockName;
	private BlockType type;
	private long destroyTime;
	private Vector2s[] textures;
	private Color mapColor;
	private boolean isAlpha;

	private BlockListener listener = new BlockListener();

	private IModel model;

	public BlockData(String namespace, String blockName, BlockType type, long destroyTime, Vector2s[] textures,
			byte[] mapColor, boolean isAlpha, IModel model) {
		this.namespace = namespace;
		this.blockName = blockName;
		this.type = type;
		this.destroyTime = destroyTime;
		this.textures = textures;
		this.model = model;
		this.mapColor = new Color(mapColor);
		this.isAlpha = isAlpha;
		

	}

	public String getNamespace() {
		return namespace;
	}

	public String getBlockName() {
		return blockName;
	}

	public BlockType getType() {
		return type;
	}

	public long getDestroyTime() {
		return destroyTime;
	}

	public Vector2s[] getTextures() {
		return textures;
	}

	public BlockListener getListener() {
		return listener;
	}

	public boolean isAlpha() {
		return isAlpha;
	}

	public NVGColor getMapColorNVG() {
		return mapColor.getNVGColor();
	}
	
	public Color getMapColor() {
		return mapColor;
	}
	
	public byte[] getColorArray() {
		return mapColor.getColor();
	}

	public void setListener(BlockListener listener) {
		this.listener = listener;

	}

	public ArrayList<AABB> getAabbs() {
		return model.getAabbs();
	}

	public int getDrawData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
		return model.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BlockData blockData = (BlockData) o;
		return namespace.equals(blockData.namespace) && listener.equals(blockData.listener);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, listener);
	}

}
