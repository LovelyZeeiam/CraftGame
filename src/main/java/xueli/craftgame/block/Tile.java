package xueli.craftgame.block;

import xueli.craftgame.world.BlockPos;
import xueli.gamengine.resource.TextureAtlas;
import xueli.gamengine.utils.FloatList;

public class Tile {

    public BlockData data;
    public BlockPos pos;
    private BlockListener listener;

    public Tile(String namespace) {
        this.data = BlockResource.blockDatas.get(namespace);
        if (this.data == null)
            this.data = BlockResource.blockDatas.get("craftgame:" + namespace);
        this.listener = data.getListener();

    }

    public Tile(BlockData data) {
        this.data = data;
        this.listener = data.getListener();

    }

    public BlockListener getListener() {
        return listener;
    }

    public int getX() {
        return pos.getX();
    }

    public int getY() {
        return pos.getY();
    }

    public int getZ() {
        return pos.getZ();
    }

    public int getDrawData(int x, int y, int z, byte face, TextureAtlas blockTextureAtlas, FloatList buffer) {
        return listener.getDrawData(buffer, this, x, y, z, face, blockTextureAtlas);
    }

}
