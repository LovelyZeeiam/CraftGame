package xueli.craftgame.world;

import org.lwjgl.util.vector.Vector3f;
import xueli.craftgame.block.Tile;
import xueli.craftgame.entity.Entity;
import xueli.gamengine.physics.AABB;

import java.util.ArrayList;

public class CubeWorldCollider {

    private World world;

    public CubeWorldCollider(World world) {
        this.world = world;

    }

    public void entityCollide(Entity e, Vector3f deltaPos) {
        // 获取实体碰撞箱
        AABB entityAabb = e.getOriginAABB().move(e.pos.x, e.pos.y, e.pos.z);

        // 获取所有有价值检测碰撞的方块的碰撞箱
        ArrayList<AABB> aabbs = new ArrayList<AABB>();
        AABB expendedAabb = entityAabb.expand(deltaPos.x, deltaPos.y, deltaPos.z);
        int x0 = (int) expendedAabb.getX0();
        int x1 = (int) expendedAabb.getX1() + 1;
        int y0 = (int) expendedAabb.getY0();
        int y1 = (int) expendedAabb.getY1() + 1;
        int z0 = (int) expendedAabb.getZ0();
        int z1 = (int) expendedAabb.getZ1() + 1;

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                for (int z = z0; z < z1; z++) {
                    Tile tile = world.getBlock(x, y, z);
                    if (tile != null) {
                        ArrayList<AABB> tileAabbs = tile.getAabbs();
                        for (AABB tileAabb : tileAabbs) {
                            aabbs.add(tileAabb.move((float) x, (float) y, (float) z));
                        }
                    }
                }
            }
        }

        /**
         * the player will stick into the block in a very 45 degree angle
         * 精度问题？
         */
        float xd = deltaPos.x, yd = deltaPos.y, zd = deltaPos.z;
        for (AABB aabb : aabbs) {
            xd = aabb.clipXCollide(entityAabb, deltaPos.x);
        }
        e.pos.x += xd;
        for (AABB aabb : aabbs) {
            yd = aabb.clipYCollide(entityAabb, deltaPos.y);
        }
        e.pos.y += yd;
        for (AABB aabb : aabbs) {
            zd = aabb.clipZCollide(entityAabb, deltaPos.z);
        }
        e.pos.z += zd;

    }

}
