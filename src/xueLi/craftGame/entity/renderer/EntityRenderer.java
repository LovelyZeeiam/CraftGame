package xueLi.craftGame.entity.renderer;

import java.nio.FloatBuffer;
import java.util.LinkedList;
import java.util.Queue;

import xueLi.craftGame.world.World;

public class EntityRenderer {
	
	private static FloatBuffer buffer;
	private static Queue<RenderArgs> renderList = new LinkedList<RenderArgs>();
	
	private static World mw;
	
	public static void bindWorld(World w) {
		mw = w;
	}
	
	

}
