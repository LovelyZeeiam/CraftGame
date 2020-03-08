package xueLi.craftGame.entity.renderer;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Queue;

import xueLi.craftGame.world.World;

public class EntityRenderer {

	private static final int[] ELEMENTS = {
		0,1,2,
		0,3,2,
		
		3,2,6,
		3,7,6,
		
		0,1,5,
		0,4,5,
		
		4,5,6,
		4,7,6,
		
		0,4,7,
		0,3,7,
		
		1,5,6,
		1,2,6
	};
	
	private static FloatBuffer buffer;
	private static Queue<RenderArgs> renderList = new LinkedList<RenderArgs>();

	private static World mw;

	public static void bindWorld(World w) {
		mw = w;
	}
	
	public static void render() {
		
		
		
	}

}
