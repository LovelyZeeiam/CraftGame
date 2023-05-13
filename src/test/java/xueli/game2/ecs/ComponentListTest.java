package xueli.game2.ecs;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector3b;
import org.lwjgl.utils.vector.Vector3f;
import org.lwjgl.utils.vector.Vector3i;

public class ComponentListTest {
	
	public static void main(String[] args) {
		ResourceList<Object> list = new ResourceListImpl<>();
		list.add(new Vector3b((byte) 2, (byte) 3, (byte) 3));
		list.add(new Vector3f(2, 3, 3));
		list.add(new Vector3i(2, 3, 3));
		System.out.println(list);
		
		System.out.println(list.get(Vector3b.class));
		System.out.println(list.get(Vector2d.class));
		
		list.remove(Vector3b.class);
		System.out.println(list);
		
	}
	
}
