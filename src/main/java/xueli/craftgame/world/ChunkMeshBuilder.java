package xueli.craftgame.world;

import org.graalvm.compiler.lir.alloc.lsra.LinearScan.BlockData;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.Block;
import xueli.craftgame.block.data.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.utils.TextureAtlas;

public class ChunkMeshBuilder {

	private boolean needRebuild = true;

	private final Chunk chunk;
	private final World world;
	private final int chunkX, chunkZ;

	private FloatList buffer;
	private int vertCount = 0;
	private FloatList buffer_alpha;
	private int vertCount_alpha = 0;

	private TextureAtlas blockTextureAtlas;

	public ChunkMeshBuilder(Chunk chunk) {
		this.chunk = chunk;
		this.world = chunk.getWorld();

		this.chunkX = chunk.getX();
		this.chunkZ = chunk.getZ();

		this.blockTextureAtlas = (TextureAtlas) CraftGame.INSTANCE_CRAFT_GAME.getTextureManager().getTexture("blocks");

	}

	public void postRebuild() {
		this.needRebuild = true;

	}

	public void postReadyDrawcall() {
		if (buffer == null) {
			buffer = new FloatList(100000);
		}
		if (buffer_alpha == null) {
			buffer_alpha = new FloatList(50000);
		}

	}

	public void postUnload() {
		if (buffer != null) {
			buffer.postDispose();
			buffer = null;
		}
		if (buffer_alpha != null) {
			buffer_alpha.postDispose();
			buffer_alpha = null;
		}

	}

	public void drawUpdate() {
		if (needRebuild) {
			int offset_x = chunkX << 4;
			int offset_z = chunkZ << 4;

			if (buffer == null) {
				buffer = new FloatList(100000);
			}
			if (buffer_alpha == null) {
				buffer_alpha = new FloatList(50000);
			}

			vertCount = 0;
			vertCount_alpha = 0;
			buffer.clear();
			buffer_alpha.clear();

			for (int i = 0; i < Chunk.SUBCHUNK_COUNT; i++) {
				int offset_y = i << 4;
				SubChunk subChunk = chunk.subChunks[i];
				if (subChunk != null) {
					for (int x = 0; x < 16; x++) {
						for (int z = 0; z < 16; z++) {
							int height = subChunk.heightMap[x][z];
							for (int y = 0; y <= height; y++) {
								int realX = x + offset_x;
								int realY = y + offset_y;
								int realZ = z + offset_z;

								Block block = subChunk.getBlock(x, y, z);

								if (block != null) {
									BlockData data = block.getData();
									long detail = block.getDetails();
									IModel model = data.getModel();

									if (model.isAlpha(realX, realY, realZ, world)) {
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.LEFT, detail, data, blockTextureAtlas, world);
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.RIGHT, detail, data, blockTextureAtlas, world);
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.TOP, detail, data, blockTextureAtlas, world);
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.BOTTOM, detail, data, blockTextureAtlas, world);
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.FRONT, detail, data, blockTextureAtlas, world);
										vertCount_alpha += model.getRenderData(buffer_alpha, realX, realY, realZ,
												BlockFace.BACK, detail, data, blockTextureAtlas, world);

									} else if (!model.isCompleteBlock(realX, realY, realZ, world)) {
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.LEFT,
												detail, data, blockTextureAtlas, world);
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.RIGHT,
												detail, data, blockTextureAtlas, world);
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.TOP,
												detail, data, blockTextureAtlas, world);
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.BOTTOM,
												detail, data, blockTextureAtlas, world);
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.FRONT,
												detail, data, blockTextureAtlas, world);
										vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.BACK,
												detail, data, blockTextureAtlas, world);

									} else {
										if ((world.getBlock(realX - 1, realY, realZ) == null
												|| world.getBlock(realX - 1, realY, realZ).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX - 1, realY, realZ).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.LEFT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX + 1, realY, realZ) == null
												|| world.getBlock(realX + 1, realY, realZ).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX + 1, realY, realZ).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.RIGHT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, realY, realZ - 1) == null
												|| world.getBlock(realX, realY, realZ - 1).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX, realY, realZ - 1).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.FRONT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, realY, realZ + 1) == null
												|| world.getBlock(realX, realY, realZ + 1).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX, realY, realZ + 1).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.BACK, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, y - 1, realZ) == null
												|| world.getBlock(realX, y - 1, realZ).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX, y - 1, realZ).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.BOTTOM, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, y + 1, realZ) == null
												|| world.getBlock(realX, y + 1, realZ).getData().getModel()
														.isAlpha(realX, realY, realZ, world)
												|| !world.getBlock(realX, y + 1, realZ).getData().getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ, BlockFace.TOP,
													detail, data, blockTextureAtlas, world);
										}

									}

								}

							}

						}

					}

				}

			}

			needRebuild = false;
		}

	}

	public FloatList getBuffer() {
		return buffer;
	}

	public FloatList getBuffer_alpha() {
		return buffer_alpha;
	}

	public int getVertCount() {
		return vertCount;
	}

	public int getVertCount_alpha() {
		return vertCount_alpha;
	}

}
