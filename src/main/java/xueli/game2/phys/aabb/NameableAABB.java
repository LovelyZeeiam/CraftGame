package xueli.game2.phys.aabb;

import org.lwjgl.utils.vector.Vector3d;

public class NameableAABB extends AABB {

	private final String name;

	public NameableAABB(String name, Vector3d v1, Vector3d v2) {
		super(v1, v2);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "NameableAABB{" + "name='" + name + '\'' + ", v1=" + v1 + ", v2=" + v2 + '}';
	}

}
