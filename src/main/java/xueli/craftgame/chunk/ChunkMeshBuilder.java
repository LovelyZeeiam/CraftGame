package xueli.craftgame.chunk;

import xueli.craftgame.CraftGame;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.model.IModel;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;

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

	public void update() {
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
							for (int y = 0; y < height; y++) {
								int realX = x + offset_x;
								int realY = y + offset_y;
								int realZ = z + offset_z;

								BlockData data = subChunk.getBlockData(x, y, z);
								if (data != null) {
									long detail = subChunk.getDetails(x, y, z);
									IModel model = data.getModel();

									if (model.isAlpha(realX, realY, realZ, world)) {
										vertCount_alpha += model.getRenderData(buffer, realX, realY, realZ,
												BlockFace.JUST_DRAW_ALL_DONT_CARE_ABOUT_THIS, detail, data,
												blockTextureAtlas, world);

									} else if (model.isCompleteBlock(realX, realY, realZ, world)) {
										vertCount += model.getRenderData(buffer, realX, realY, realZ,
												BlockFace.JUST_DRAW_ALL_DONT_CARE_ABOUT_THIS, detail, data,
												blockTextureAtlas, world);

									} else {
										if ((world.getBlock(realX - 1, realY, realZ) == null
												|| world.getBlock(realX - 1, realY, realZ).getModel().isAlpha(realX,
														realY, realZ, world)
												|| !world.getBlock(realX - 1, realY, realZ).getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.LEFT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX + 1, realY, realZ) == null
												|| world.getBlock(realX + 1, realY, realZ).getModel().isAlpha(realX,
														realY, realZ, world)
												|| !world.getBlock(realX + 1, realY, realZ).getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.RIGHT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, realY, realZ - 1) == null
												|| world.getBlock(realX, realY, realZ - 1).getModel().isAlpha(realX,
														realY, realZ, world)
												|| !world.getBlock(realX, realY, realZ - 1).getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.FRONT, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, realY, realZ + 1) == null
												|| world.getBlock(realX, realY, realZ + 1).getModel().isAlpha(realX,
														realY, realZ, world)
												|| !world.getBlock(realX, realY, realZ + 1).getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.BACK, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, y - 1, realZ) == null
												|| world.getBlock(realX, y - 1, realZ).getModel().isAlpha(realX, realY,
														realZ, world)
												|| !world.getBlock(realX, y - 1, realZ).getModel()
														.isCompleteBlock(realX, realY, realZ, world))) {
											vertCount += model.getRenderData(buffer, realX, realY, realZ,
													BlockFace.BOTTOM, detail, data, blockTextureAtlas, world);
										}
										if ((world.getBlock(realX, y + 1, realZ) == null
												|| world.getBlock(realX, y + 1, realZ).getModel().isAlpha(realX, realY,
														realZ, world)
												|| !world.getBlock(realX, y + 1, realZ).getModel()
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
