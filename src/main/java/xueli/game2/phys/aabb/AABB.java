package xueli.game2.phys.aabb;

import org.lwjgl.utils.vector.Vector3d;

public class AABB {

	protected Vector3d v1 = new Vector3d(), v2 = new Vector3d();

	private AABB() {}

	public AABB(Vector3d v1, Vector3d v2) {
		this.v1.x = Math.min(v1.x, v2.x);
		this.v1.y = Math.min(v1.y, v2.y);
		this.v1.z = Math.min(v1.z, v2.z);

		this.v2.x = Math.max(v1.x, v2.x);
		this.v2.y = Math.max(v1.y, v2.y);
		this.v2.z = Math.max(v1.z, v2.z);

	}

	public Vector3d getVmin() {
		return v1;
	}

	public Vector3d getVmax() {
		return v2;
	}

	public AABB add(Vector3d pos) {
		return new AABB(Vector3d.add(this.v1, pos), Vector3d.add(this.v2, pos));
	}

	public AABB expand(Vector3d v) {
		AABB aabb = new AABB();
		if(v.x < 0) {
			aabb.v1.x = this.v1.x + v.x;
			aabb.v2.x = this.v2.x;
		} else {
			aabb.v1.x = this.v1.x;
			aabb.v2.x = this.v2.x + v.x;
		}

		if(v.y < 0) {
			aabb.v1.y = this.v1.y + v.y;
			aabb.v2.y = this.v2.y;
		} else {
			aabb.v1.y = this.v1.y;
			aabb.v2.y = this.v2.y + v.y;
		}

		if(v.z < 0) {
			aabb.v1.z = this.v1.z + v.z;
			aabb.v2.z = this.v2.z;
		} else {
			aabb.v1.z = this.v1.z;
			aabb.v2.z = this.v2.z + v.z;
		}

		return aabb;
	}

	@Override
	public String toString() {
		return "AABB{" +
				"v1=" + v1 +
				", v2=" + v2 +
				'}';
	}

}
