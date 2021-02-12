package xueli.craftgame.block.rendercontrol.model;

import com.google.gson.JsonObject;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector3i;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.Tile;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.data.SlabAndStairData;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

import java.util.ArrayList;

public class ModelStair extends IModel {

	public ModelStair(JsonObject renderArgs) {
		super(renderArgs);
	}

	@Override
	public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
			TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
		IModel targetModel = null;

		if (params == null) {
			targetModel = new ModelStairBottomFacingFront();
		} else {
			RotateStat stat = getRotateStat(params.faceTo, params.slabOrStairData, world, x, y, z);
			if (params.slabOrStairData == SlabAndStairData.DOWN) {
				switch (params.faceTo) {
				case BlockFace.FRONT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairBottomCornerFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairBottomAroundFrontRight();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairBottomAroundFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairBottomCornerFrontRight();
							}
						}
					} else
						targetModel = new ModelStairBottomFacingFront();
					break;
				case BlockFace.BACK:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairBottomCornerBackRight();
							} else if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairBottomAroundBackLeft();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairBottomCornerBackLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairBottomAroundBackRight();
							}
						}
					} else
						targetModel = new ModelStairBottomFacingBack();
					break;
				case BlockFace.LEFT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairBottomAroundFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairBottomCornerBackLeft();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairBottomCornerFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairBottomAroundBackLeft();
							}
						}
					} else
						targetModel = new ModelStairBottomFacingLeft();
					break;
				case BlockFace.RIGHT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairBottomCornerFrontRight();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairBottomAroundBackRight();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairBottomAroundFrontRight();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairBottomCornerBackRight();
							}
						}
					} else
						targetModel = new ModelStairBottomFacingRight();
					break;
				}
			} else if (params.slabOrStairData == SlabAndStairData.UP) {
				switch (params.faceTo) {
				case BlockFace.FRONT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairUpCornerFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairUpAroundFrontRight();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairUpAroundFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairUpCornerFrontRight();
							}
						}
					} else
						targetModel = new ModelStairUpFacingFront();
					break;
				case BlockFace.BACK:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairUpCornerBackRight();
							} else if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairUpAroundBackLeft();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.LEFT) {
								targetModel = new ModelStairUpCornerBackLeft();
							} else if (stat.getRotateTo() == BlockFace.RIGHT) {
								targetModel = new ModelStairUpAroundBackRight();
							}
						}
					} else
						targetModel = new ModelStairUpFacingBack();
					break;
				case BlockFace.LEFT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairUpAroundFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairUpCornerBackLeft();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairUpCornerFrontLeft();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairUpAroundBackLeft();
							}
						}
					} else
						targetModel = new ModelStairUpFacingLeft();
					break;
				case BlockFace.RIGHT:
					if (stat != null) {
						if (stat.getRotateSection() == BlockFace.RIGHT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairUpCornerFrontRight();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairUpAroundBackRight();
							}
						} else if (stat.getRotateSection() == BlockFace.LEFT) {
							if (stat.getRotateTo() == BlockFace.FRONT) {
								targetModel = new ModelStairUpAroundFrontRight();
							} else if (stat.getRotateTo() == BlockFace.BACK) {
								targetModel = new ModelStairUpCornerBackRight();
							}
						}
					} else
						targetModel = new ModelStairUpFacingRight();
					break;
				}
			}
		}

		if (targetModel == null)
			return 0;
		return targetModel.getRenderCubeData(buffer, data, x, y, z, face, blockTextureAtlas, params, chunk, world);
	}

	@Override
	public boolean isAlpha(World world) {
		return false;
	}

	@Override
	public boolean isCompleteBlock(World world) {
		return false;
	}

	@Override
	public ArrayList<AABB> getAabbs(BlockParameters params, World world, int x, int y, int z) {
		ArrayList<AABB> aabbs = new ArrayList<>();

		if (params.slabOrStairData == SlabAndStairData.DOWN) {
			aabbs.add(new AABB(0, 1, 0, 0.5f, 0, 1));
			switch (params.faceTo) {
			case BlockFace.FRONT:
				aabbs.add(new AABB(0, 1, 0.5f, 1, 0.5f, 1));
				break;
			case BlockFace.BACK:
				aabbs.add(new AABB(0, 1, 0.5f, 1, 0, 0.5f));
				break;
			case BlockFace.LEFT:
				aabbs.add(new AABB(0.5f, 1, 0.5f, 1, 0, 1));
				break;
			case BlockFace.RIGHT:
				aabbs.add(new AABB(0, 0.5f, 0.5f, 1, 0, 1));
				break;
			}
		} else if (params.slabOrStairData == SlabAndStairData.UP) {
			aabbs.add(new AABB(0, 1, 0.5f, 1, 0, 1));
			switch (params.faceTo) {
			case BlockFace.FRONT:
				aabbs.add(new AABB(0, 1, 0, 0.5f, 0.5f, 1));
				break;
			case BlockFace.BACK:
				aabbs.add(new AABB(0, 1, 0, 0.5f, 0, 0.5f));
				break;
			case BlockFace.LEFT:
				aabbs.add(new AABB(0.5f, 1, 0, 0.5f, 0, 1));
				break;
			case BlockFace.RIGHT:
				aabbs.add(new AABB(0, 0.5f, 0, 0.5f, 0, 1));
				break;
			}
		}

		return aabbs;
	}

	/**
	 * 获取这个台阶方块的转向
	 * 
	 * @param currentBlockFaceTo    当前台阶方块的朝向，食用BlockFace类里面定义的一个参数
	 * @param currentBlockStairData 当前台阶方块的SlabAndStairData，食用SlabAndStairData类里面定义的一个参数
	 * @param world                 当前世界实例
	 * @param x                     当前方块的x坐标
	 * @param y                     当前方块的y坐标
	 * @param z                     当前方块的z坐标
	 * @return 旋转参数
	 */
	private RotateStat getRotateStat(byte currentBlockFaceTo, byte currentBlockStairData, World world, int x, int y,
			int z) {
		switch (currentBlockFaceTo) {
		case BlockFace.FRONT:
		case BlockFace.BACK:
			a: if (world.hasBlock(new Vector3i(x, y, z - 1))) {
				Tile tile = world.getBlock(x, y, z - 1);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break a;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break a;
				if (tile.getParams().faceTo == BlockFace.LEFT)
					return new RotateStat(BlockFace.LEFT, BlockFace.LEFT);
				else if (tile.getParams().faceTo == BlockFace.RIGHT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.RIGHT);
				else
					break a;
			}
			b: if (world.hasBlock(new Vector3i(x, y, z + 1))) {
				Tile tile = world.getBlock(x, y, z + 1);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break b;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break b;
				if (tile.getParams().faceTo == BlockFace.LEFT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.LEFT);
				else if (tile.getParams().faceTo == BlockFace.RIGHT)
					return new RotateStat(BlockFace.LEFT, BlockFace.RIGHT);
				else
					break b;
			}
			break;
		case BlockFace.LEFT:
		case BlockFace.RIGHT:
			c: if (world.hasBlock(new Vector3i(x + 1, y, z))) {
				Tile tile = world.getBlock(x + 1, y, z);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break c;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break c;
				if (tile.getParams().faceTo == BlockFace.FRONT)
					return new RotateStat(BlockFace.LEFT, BlockFace.FRONT);
				else if (tile.getParams().faceTo == BlockFace.BACK)
					return new RotateStat(BlockFace.RIGHT, BlockFace.BACK);
				else
					break c;
			}
			d: if (world.hasBlock(new Vector3i(x - 1, y, z))) {
				Tile tile = world.getBlock(x - 1, y, z);
				if (!tile.getData().getNamespace().endsWith(".stair"))
					break d;
				if (tile.getParams().slabOrStairData != currentBlockStairData)
					break d;
				if (tile.getParams().faceTo == BlockFace.FRONT)
					return new RotateStat(BlockFace.RIGHT, BlockFace.FRONT);
				else if (tile.getParams().faceTo == BlockFace.BACK)
					return new RotateStat(BlockFace.LEFT, BlockFace.BACK);
				else
					break d;
			}
			break;
		}
		return null;
	}

	/**
	 * 台阶的旋转参数
	 */
	private static class RotateStat {

		// 从楼梯的前面看 偏转部分如果是左边就是BlockFace.LEFT, 如果是右边就是BlockFace.RIGHT
		private byte rotateSection;
		// 楼梯偏转将要转到的面 食用BlockFace里面的参数
		private byte rotateTo;

		public RotateStat(byte rotateSection, byte rotateTo) {
			this.rotateSection = rotateSection;
			this.rotateTo = rotateTo;
		}

		public byte getRotateSection() {
			return rotateSection;
		}

		public byte getRotateTo() {
			return rotateTo;
		}

		@Override
		public String toString() {
			return "RotateStat [rotateSection=" + rotateSection + ", rotateTo=" + rotateTo + "]";
		}

	}

	private static class ModelStairBottomFacingFront extends IModel {

		public ModelStairBottomFacingFront() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomFacingBack extends IModel {

		public ModelStairBottomFacingBack() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomFacingLeft extends IModel {

		public ModelStairBottomFacingLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z),
						new Vector2f(u2, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u1, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u2, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u3, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomFacingRight extends IModel {

		public ModelStairBottomFacingRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u3, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomCornerFrontLeft extends IModel {

		public ModelStairBottomCornerFrontLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u1, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u2, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomCornerFrontRight extends IModel {

		public ModelStairBottomCornerFrontRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomCornerBackRight extends IModel {

		public ModelStairBottomCornerBackRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomCornerBackLeft extends IModel {

		public ModelStairBottomCornerBackLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z),
						new Vector2f(u2, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomAroundFrontLeft extends IModel {

		public ModelStairBottomAroundFrontLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z),
						new Vector2f(u2, v3), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomAroundFrontRight extends IModel {

		public ModelStairBottomAroundFrontRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z),
						new Vector2f(u1, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z),
						new Vector2f(u1, v1), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomAroundBackRight extends IModel {

		public ModelStairBottomAroundBackRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairBottomAroundBackLeft extends IModel {

		public ModelStairBottomAroundBackLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u1, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u2, v3), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u3, v2), new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 1, z + 1),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v2),
						new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 0.5f, z + 1),
						new Vector2f(u2, v2), new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u2, v1), new Vector3f(1.0f, 1.0f, 1.0f));
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 0.5f, y + 1, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpFacingFront extends IModel {

		public ModelStairUpFacingFront() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpFacingBack extends IModel {

		public ModelStairUpFacingBack() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));

				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpFacingLeft extends IModel {

		public ModelStairUpFacingLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpFacingRight extends IModel {

		public ModelStairUpFacingRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpCornerFrontLeft extends IModel {

		public ModelStairUpCornerFrontLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpCornerFrontRight extends IModel {

		public ModelStairUpCornerFrontRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpCornerBackRight extends IModel {

		public ModelStairUpCornerBackRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpCornerBackLeft extends IModel {

		public ModelStairUpCornerBackLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpAroundFrontLeft extends IModel {

		public ModelStairUpAroundFrontLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u2, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u3, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpAroundFrontRight extends IModel {

		public ModelStairUpAroundFrontRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u3, v2), new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.7f, 0.7f, 0.7f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y, z), new Vector2f(u2, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u3, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpAroundBackRight extends IModel {

		public ModelStairUpAroundBackRight() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
				break;
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.5f, 0.5f, 0.5f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v2), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y + 0.5f, z + 1),
						new Vector2f(u2, v1), new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

	private static class ModelStairUpAroundBackLeft extends IModel {

		public ModelStairUpAroundBackLeft() {
			super(null);
		}

		@Override
		public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
				TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world) {
			int vertCount = 0;

			Vector2s textureVector2s = data.getTextures()[face];

			float u1 = (float) (textureVector2s.x) / blockTextureAtlas.width;
			float v1 = (float) (textureVector2s.y) / blockTextureAtlas.height;
			float u2 = (float) (textureVector2s.x + 0.5f) / blockTextureAtlas.width;
			float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
			float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
			float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

			switch (face) {
			case BlockFace.FRONT:
				drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u1, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.7f, 0.7f, 0.7f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.7f, 0.7f, 0.7f));
			case BlockFace.BACK:
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y, z + 1), new Vector2f(u2, v3),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 1, y + 0.5f, z + 1), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f));
				vertCount += drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v1),
						new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u2, v1), new Vector3f(0.5f, 0.5f, 0.5f));
				break;
			case BlockFace.LEFT:
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y + 0.5f, z), new Vector2f(u1, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), new Vector2f(u2, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y, z + 0.5f), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				vertCount += drawQuadFacingFrontOrLeft(buffer, new Vector3f(x + 0.5f, y, z + 0.5f),
						new Vector2f(u2, v2), new Vector3f(0.6f, 0.6f, 0.6f),
						new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 0.5f, y + 0.5f, z + 1), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.RIGHT:
				drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), new Vector2f(u3, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z), new Vector2f(u3, v1),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y, z + 1), new Vector2f(u1, v3),
						new Vector3f(0.6f, 0.6f, 0.6f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u1, v1),
						new Vector3f(0.6f, 0.6f, 0.6f));
				break;
			case BlockFace.TOP:
				vertCount += drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), new Vector2f(u1, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z), new Vector2f(u1, v1),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x, y + 1, z + 1), new Vector2f(u3, v3),
						new Vector3f(1.0f, 1.0f, 1.0f), new Vector3f(x + 1, y + 1, z + 1), new Vector2f(u3, v1),
						new Vector3f(1.0f, 1.0f, 1.0f));
				break;
			case BlockFace.BOTTOM:
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 1), new Vector2f(u3, v3),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 1, y, z + 1), new Vector2f(u3, v2),
						new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y + 0.5f, z + 0.5f), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 0.5f),
						new Vector2f(u1, v1), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y + 0.5f, z + 1),
						new Vector2f(u2, v2), new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y + 0.5f, z + 1),
						new Vector2f(u2, v1), new Vector3f(0.4f, 0.4f, 0.4f));
				vertCount += drawQuadFacingBottom(buffer, new Vector3f(x, y, z), new Vector2f(u1, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z), new Vector2f(u1, v1),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x, y, z + 0.5f), new Vector2f(u2, v2),
						new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(x + 0.5f, y, z + 0.5f), new Vector2f(u2, v1),
						new Vector3f(0.4f, 0.4f, 0.4f));
				break;
			}

			return vertCount;
		}

	}

}
