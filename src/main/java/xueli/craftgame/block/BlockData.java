package xueli.craftgame.block;

import xueli.gamengine.physics.AABB;
import xueli.gamengine.utils.vector.Vector2s;

import java.util.ArrayList;

public class BlockData {

    private String blockName;
    private BlockType type;
    private long destroyTime;
    private Vector2s[] textures;

    private BlockListener listener = new BlockListener();

    private ArrayList<AABB> aabbs;

    public BlockData(String blockName, BlockType type, long destroyTime, Vector2s[] textures, ArrayList<AABB> aabbs) {
        this.blockName = blockName;
        this.type = type;
        this.destroyTime = destroyTime;
        this.textures = textures;
        this.aabbs = aabbs;

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

    public void setListener(BlockListener listener) {
        this.listener = listener;

    }

    public ArrayList<AABB> getAabbs() {
        return aabbs;
    }

}
