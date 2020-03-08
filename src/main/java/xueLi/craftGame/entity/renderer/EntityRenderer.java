package xueLi.craftGame.entity.renderer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.world.Chunk;
import xueLi.craftGame.world.World;

public class EntityRenderer {

	private static final short[] ELEMENTS = {
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
	
	private static FloatBuffer buffer = BufferUtils.createFloatBuffer(16777216);
	private static ShortBuffer element = BufferUtils.createShortBuffer(ELEMENTS.length);
	private static List<RenderArgs> renderList = new ArrayList<RenderArgs>();
	public static int offset = 0;
	
	private static int vbo;
	private static int ebo;
	
	public static void init() {
		ebo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		element.put(ELEMENTS);
		element.flip();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, element, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, 16777216, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public static void buildMesh(Chunk chunk) {
		for(Entity e : chunk.entities) {
			renderList.addAll(e.render(buffer));
		}
	}
	
	public static void render() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		for(RenderArgs arg:renderList) {
			
		}
		buffer.clear();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		renderList.clear();
	}

}
