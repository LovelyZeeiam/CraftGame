package xueli.craftgame.block.rendercontrol.model;

import com.google.gson.JsonObject;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.util.vector.Vector3i;
import xueli.craftgame.block.BlockData;
import xueli.craftgame.block.BlockParameters;
import xueli.craftgame.block.data.BlockFace;
import xueli.craftgame.world.Chunk;
import xueli.craftgame.world.World;
import xueli.gamengine.physics.AABB;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.store.FloatList;
import xueli.gamengine.utils.vector.Vector2s;
import xueli.gamengine.view.GuiColor;

import java.util.ArrayList;

public class ModelWater extends IModel {

    public ModelWater(JsonObject renderArgs) {
        super(renderArgs);

    }

    @Override
    public int getRenderCubeData(FloatList buffer, BlockData data, int x, int y, int z, byte face,
                                 TextureAtlas blockTextureAtlas, BlockParameters tile, Chunk chunk, World world) {
        Vector2s textureVector2s = data.getTextures()[face];

        float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
        float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
        float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
        float v2 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

        NVGColor multiplyColor = chunk == null ? GuiColor.BLUE : chunk.getBiome().getWater_color_nvg();

        float r = multiplyColor.r();
        float g = multiplyColor.g();
        float b = multiplyColor.b();

        switch (face) {
            case BlockFace.FRONT:
                if(world != null && world.hasBlock(new Vector3i(x,y,z-1)) && world.getBlock(x,y,z-1).getData().getNamespace().equals(data.getNamespace()))
                    break;
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                break;
            case BlockFace.RIGHT:
                if(world != null && world.hasBlock(new Vector3i(x+1,y,z)) && world.getBlock(x+1,y,z).getData().getNamespace().equals(data.getNamespace()))
                    break;
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                break;
            case BlockFace.BACK:
                if(world != null && world.hasBlock(new Vector3i(x,y,z+1)) && world.getBlock(x,y,z+1).getData().getNamespace().equals(data.getNamespace()))
                    break;
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z + 1);
                break;
            case BlockFace.LEFT:
                if(world != null && world.hasBlock(new Vector3i(x-1,y,z)) && world.getBlock(x-1,y,z).getData().getNamespace().equals(data.getNamespace()))
                    break;
                buffer.put(u1).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f * r).put(0.7f * g).put(0.7f * b);
                buffer.put(x).put(y + 0.8f).put(z + 1);
                break;
            case BlockFace.TOP:
                if(world != null && world.hasBlock(new Vector3i(x,y+1,z)) && world.getBlock(x,y+1,z).getData().getNamespace().equals(data.getNamespace()))
                    break;
                buffer.put(u1).put(v2);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x).put(y + 0.8f).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z);
                buffer.put(u1).put(v1);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z);
                buffer.put(u2).put(v2);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x).put(y + 0.8f).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(1.0f * r).put(1.0f * g).put(1.0f * b);
                buffer.put(x + 1).put(y + 0.8f).put(z + 1);
                break;
            case BlockFace.BOTTOM:
                if(world != null && world.hasBlock(new Vector3i(x,y-1,z)) && world.getBlock(x,y-1,z).getData().getNamespace().equals(data.getNamespace()))
                    break;
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

    @Override
    public boolean isAlpha(World world) {
        return true;
    }

    @Override
    public ArrayList<AABB> getAabbs(BlockParameters param,World world, int x, int y, int z) {
        return new ArrayList<>();
    }

}
