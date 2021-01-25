package xueli.craftgame.block.model;

import com.google.gson.JsonObject;
import org.lwjgl.nanovg.NVGColor;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;
import xueli.gamengine.utils.vector.Vector2s;

import java.util.ArrayList;

public abstract class IModel {

    private static ArrayList<AABB> aabbs = new ArrayList<>();

    static {
        aabbs.add(new AABB(0, 1, 0, 1, 0, 1));

    }

    public IModel(JsonObject renderArgs) {

    }

    /**
     * @return 绘制的有多少个面
     */
    public abstract int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                          TextureAtlas blockTextureAtlas, BlockParameters params, Chunk chunk, World world);

    public boolean isAlpha(World world) {
        return false;
    }
    public boolean isCompleteBlock(World world) { return true; }

    public ArrayList<AABB> getAabbs(BlockParameters params) {
        return aabbs;
    }

    protected int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                    TextureAtlas blockTextureAtlas, Chunk chunk, World world, NVGColor multiplyColor) {
        Vector2s textureVector2s = data.getTextures()[face];

        float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
        float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
        float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
        float v2 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

        float r = multiplyColor.r();
        float g = multiplyColor.g();
        float b = multiplyColor.b();

        switch (face) {
            case BlockFace.FRONT:
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                break;
            case BlockFace.RIGHT:
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                break;
            case BlockFace.BACK:
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                break;
            case BlockFace.LEFT:
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 1).put(z + 1);
                break;
            case BlockFace.TOP:
                buffer.put(u1).put(v2);
                buffer.put(r).put(g).put(b);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(r).put(g).put(b);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(r).put(g).put(b);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u1).put(v1);
                buffer.put(r).put(g).put(b);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(r).put(g).put(b);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(r).put(g).put(b);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                break;
            case BlockFace.BOTTOM:
                buffer.put(u1).put(v2);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.5f * r).put(0.5f * g).put(0.5f * b);
                buffer.put(x).put(y).put(z + 1);
                break;
        }

        return 6;
    }

}
