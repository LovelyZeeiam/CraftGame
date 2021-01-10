package xueli.gamengine.physics;

public class AABB {

    private float x0, x1, y0, y1, z0, z1;
    private float margin = 0.0f;

    public AABB(float x0, float x1, float y0, float y1, float z0, float z1) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.z0 = z0;
        this.z1 = z1;
    }

    public float getX0() {
        return x0;
    }

    public float getX1() {
        return x1;
    }

    public float getY0() {
        return y0;
    }

    public float getY1() {
        return y1;
    }

    public float getZ0() {
        return z0;
    }

    public float getZ1() {
        return z1;
    }

    public AABB expand(float x, float y, float z) {
        float newX0 = this.x0;
        float newY0 = this.y0;
        float newZ0 = this.z0;
        float newX1 = this.x1;
        float newY1 = this.y1;
        float newZ1 = this.z1;
        if (x < 0.0f) {
            newX0 += x;
        }
        if (x > 0.0f) {
            newX1 += x;
        }
        if (y < 0.0f) {
            newY0 += y;
        }
        if (y > 0.0f) {
            newY1 += y;
        }
        if (z < 0.0f) {
            newZ0 += z;
        }
        if (z > 0.0f) {
            newZ1 += z;
        }
        return new AABB(newX0, newX1, newY0, newY1, newZ0, newZ1);
    }

    public AABB grow(float xa, float ya, float za) {
        return new AABB(this.x0 - xa, this.x1 + xa, this.y0 - ya, this.y1 + ya, this.z0 - za, this.z1 + za);
    }

    public float clipXCollide(AABB target, float xd) {
        if (target.y1 <= this.y0 || target.y0 >= this.y1) {
            return xd;
        }
        if (target.z1 <= this.z0 || target.z0 >= this.z1) {
            return xd;
        }
        if (xd > 0.0f && target.x1 <= this.x0) {
            float max = (this.x0 - target.x1) - this.margin;
            if (max < xd) {
                xd = max;
            }
        }
        if (xd < 0.0f && target.x0 >= this.x1) {
            float max2 = (this.x1 - target.x0) + this.margin;
            if (max2 > xd) {
                xd = max2;
            }
        }
        return xd;
    }

    public float clipYCollide(AABB target, float yd) {
        if (target.x1 <= this.x0 || target.x0 >= this.x1) {
            return yd;
        }
        if (target.z1 <= this.z0 || target.z0 >= this.z1) {
            return yd;
        }
        if (yd > 0.0f && target.y1 <= this.y0) {
            float max = (this.y0 - target.y1) - this.margin;
            if (max < yd) {
                yd = max;
            }
        }
        if (yd < 0.0f && target.y0 >= this.y1) {
            float max2 = (this.y1 - target.y0) + this.margin;
            if (max2 > yd) {
                yd = max2;
            }
        }
        return yd;
    }

    public float clipZCollide(AABB target, float zd) {
        if (target.x1 <= this.x0 || target.x0 >= this.x1) {
            return zd;
        }
        if (target.y1 <= this.y0 || target.y0 >= this.y1) {
            return zd;
        }
        if (zd > 0.0f && target.z1 <= this.z0) {
            float max = (this.z0 - target.z1) - this.margin;
            if (max < zd) {
                zd = max;
            }
        }
        if (zd < 0.0f && target.z0 >= this.z1) {
            float max2 = (this.z1 - target.z0) + this.margin;
            if (max2 > zd) {
                zd = max2;
            }
        }
        return zd;
    }

    public boolean intersect(AABB c) {
        if (c.x1 <= this.x0 || c.x0 >= this.x1 || c.y1 <= this.y0 || c.y0 >= this.y1 || c.z1 <= this.z0 || c.z0 >= this.z1) {
            return false;
        }
        return true;
    }

    public AABB move(float xd, float yd, float zd) {
        return new AABB(x0 + xd, x1 + xd, y0 + yd, y1 + yd, z0 + zd, z1 + zd);
    }

    @Override
    public String toString() {
        return "[Hitbox] " + x0 + ", " + y0 + ", " + z0 + " | " + x1 + ", " + y1 + ", " + z1;
    }

}
