package xueli.craftgame.entity;

import org.lwjgl.util.vector.Vector3f;
import xueli.craftgame.world.World;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.Vector;

public abstract class Entity {

    public Vector pos;
    // TODO: For entity bones
    public Vector3f speed = new Vector3f();
    // 实体所在的世界
    protected World world;

    public Entity(float x, float y, float z, World world) {
        pos = new Vector(x, y, z);
        this.world = world;

    }

    public Entity(float x, float y, float z, float rotX, float rotY, float rotZ, World world) {
        pos = new Vector(x, y, z, rotX, rotY, rotZ);
        this.world = world;

    }

    public void updatePos() {
        Vector3f deltaPos = new Vector3f(speed.x * Time.deltaTime, speed.y * Time.deltaTime, speed.z * Time.deltaTime);

        if (doCollide()) {


        } else {
            pos.x += deltaPos.x;
            pos.y += deltaPos.y;
            pos.z += deltaPos.z;

        }

    }

    public abstract void tick();

    public boolean doCollide() {
        return true;
    }

    public abstract float getSpeed();

}
