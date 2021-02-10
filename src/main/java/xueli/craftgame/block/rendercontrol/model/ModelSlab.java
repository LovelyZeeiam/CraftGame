package xueli.craftgame.block.rendercontrol.model;

import com.google.gson.JsonObject;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.block.data.SlabAndStairData;
import xueli.craftgame.block.rendercontrol.frameface.FrameFaceSign;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

import java.util.ArrayList;

public class ModelSlab extends IModel {

    private static ArrayList<AABB> upAabbs = new ArrayList<>();
    private static ArrayList<AABB> downAabbs = new ArrayList<>();

    static {
        upAabbs.add(new AABB(0, 1, 0.5f, 1, 0, 1));
        downAabbs.add(new AABB(0, 1, 0f, 0.5f, 0, 1));

    }

    public ModelSlab(JsonObject renderArgs) {
        super(renderArgs);

    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                 TextureAtlas blockTextureAtlas, BlockParameters tile, Chunk chunk, World world) {
        Vector2s textureVector2s = data.getTextures()[face];

        if (tile == null || tile.slabOrStairData == SlabAndStairData.DOWN) {
            float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
            float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
            float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
            float v2 = (float) (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;
            switch (face) {
                case BlockFace.FRONT:
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z);
                    break;
                case BlockFace.RIGHT:
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    break;
                case BlockFace.BACK:
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    break;
                case BlockFace.LEFT:
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u2).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u2).put(v1);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    break;
                case BlockFace.TOP:
                    buffer.put(u1).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    break;
                case BlockFace.BOTTOM:
                    buffer.put(u1).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z);
                    buffer.put(u3).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y).put(z + 1);
                    break;
            }
        } else if (tile.slabOrStairData == SlabAndStairData.UP) {
            float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
            float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
            float v2 = (textureVector2s.y + 0.5f) / blockTextureAtlas.height;
            float u3 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
            float v3 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;
            switch (face) {
                case BlockFace.FRONT:
                    buffer.put(u1).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    break;
                case BlockFace.RIGHT:
                    buffer.put(u1).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1.0f).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    break;
                case BlockFace.BACK:
                    buffer.put(u1).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z + 1);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x + 1).put(y + 1.0f).put(z + 1);
                    break;
                case BlockFace.LEFT:
                    buffer.put(u1).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z);
                    buffer.put(u1).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v2);
                    buffer.put(0.7f).put(0.7f).put(0.7f);
                    buffer.put(x).put(y + 1.0f).put(z + 1);
                    break;
                case BlockFace.TOP:
                    buffer.put(u1).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1.0f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1.0f).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1.0f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x).put(y + 1.0f).put(z + 1);
                    buffer.put(u3).put(v1);
                    buffer.put(1.0f).put(1.0f).put(1.0f);
                    buffer.put(x + 1).put(y + 1.0f).put(z + 1);
                    break;
                case BlockFace.BOTTOM:
                    buffer.put(u1).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y + 0.5f).put(z);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    buffer.put(u1).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y + 0.5f).put(z);
                    buffer.put(u3).put(v1);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x + 1).put(y + 0.5f).put(z + 1);
                    buffer.put(u3).put(v3);
                    buffer.put(0.5f).put(0.5f).put(0.5f);
                    buffer.put(x).put(y + 0.5f).put(z + 1);
                    break;
            }
        }

        return 6;
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
        if (params.slabOrStairData == SlabAndStairData.UP)
            return upAabbs;
        else if (params.slabOrStairData == SlabAndStairData.DOWN)
            return downAabbs;
        return new ArrayList<>();
    }

}
