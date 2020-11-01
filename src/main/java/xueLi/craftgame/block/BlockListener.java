package xueLi.craftgame.block;

import xueLi.craftgame.world.Chunk;
import xueLi.craftgame.world.World;
import xueLi.gamengine.resource.TextureAtlas;
import xueLi.gamengine.utils.FloatList;
import xueLi.gamengine.utils.vector.Vector2s;

public class BlockListener {

    public void onCreate(Tile tile, Chunk chunk, World world) {
    }

    public void onLookAt(Tile tile, Chunk chunk, World world) {
    }

    public void onDestroy(Tile tile, Chunk chunk, World world) {
    }

    public void onLeftClick(Tile tile, Chunk chunk, World world) {
    }

    public void onRightClick(Tile tile, Chunk chunk, World world) {
    }

    public int getDrawData(FloatList buffer, Tile tile, int x, int y, int z, byte face,
                           TextureAtlas blockTextureAtlas) {
        Vector2s textureVector2s = tile.data.getTextures()[face];

        float u1 = (float) textureVector2s.x / blockTextureAtlas.width;
        float v1 = (float) textureVector2s.y / blockTextureAtlas.height;
        float u2 = (float) (textureVector2s.x + 1) / blockTextureAtlas.width;
        float v2 = (float) (textureVector2s.y + 1) / blockTextureAtlas.height;

        switch (face) {
            case BlockFace.FRONT:

                buffer.put(u1).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z);
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
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
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
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
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
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(0.7f).put(0.7f).put(0.7f);
                buffer.put(x).put(y + 1).put(z + 1);
                break;
            case BlockFace.TOP:
                buffer.put(u1).put(v2);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u1).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z);
                buffer.put(u2).put(v2);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x).put(y + 1).put(z + 1);
                buffer.put(u2).put(v1);
                buffer.put(1.0f).put(1.0f).put(1.0f);
                buffer.put(x + 1).put(y + 1).put(z + 1);
                break;
            case BlockFace.BOTTOM:
                buffer.put(u1).put(v2);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z);
                buffer.put(u1).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u2).put(v2);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z + 1);
                buffer.put(u1).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z);
                buffer.put(u2).put(v1);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x + 1).put(y).put(z + 1);
                buffer.put(u2).put(v2);
                buffer.put(0.5f).put(0.5f).put(0.5f);
                buffer.put(x).put(y).put(z + 1);
                break;
        }

        return 6;
    }

}
