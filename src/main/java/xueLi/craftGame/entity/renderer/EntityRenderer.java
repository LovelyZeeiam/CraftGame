package xueLi.craftGame.entity.renderer;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;

import xueLi.craftGame.entity.Entity;
import xueLi.craftGame.world.Chunk;

public class EntityRenderer {

	public static Matrix4f identity = new Matrix4f();
	
	private static final short[] ELEMENTS = {
		0,1,2,
		0,2,3,
		
		3,2,6,
		3,6,7,
		
		0,5,1,
		0,4,5,
		
		4,6,5,
		4,7,6,
		
		0,7,4,
		0,3,7,
		
		1,5,6,
		1,6,2
	};
	public static final int ELEMENTS_COUNT = ELEMENTS.length;
	
	private static FloatBuffer vertBuffer = BufferUtils.createFloatBuffer(24);
	private static ShortBuffer element = BufferUtils.createShortBuffer(ELEMENTS_COUNT);
	private static List<RenderArgs> renderList = new ArrayList<RenderArgs>();
	
	private static int vbo;
	private static int ebo;
	
	public static void init() {
		identity.setIdentity();
		
		ebo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
		element.put(ELEMENTS);
		element.flip();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, element, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		vbo = GL15.glGenBuffers();
	}
	
	public static void buildMesh(Chunk chunk) {
		for(Entity e : chunk.entities) {
			renderList.addAll(e.render());
		}
	}
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public static void render() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		for(RenderArgs arg:renderList) {
			vertBuffer.put(arg.vertices);
			vertBuffer.flip();
			
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertBuffer, GL15.GL_STATIC_DRAW);
			GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
			GL11.glColor3f(arg.color.x,arg.color.y,arg.color.z);
			
			GL11.glPushMatrix();
			arg.matrix.store(matrixBuffer);
			matrixBuffer.flip();
			GL11.glMultMatrix(matrixBuffer);
			matrixBuffer.clear();
			
			GL11.glDrawElements(GL11.GL_TRIANGLES,ELEMENTS_COUNT, GL11.GL_UNSIGNED_SHORT, 0);
			GL11.glPopMatrix();
			
			vertBuffer.clear();
		}
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		renderList.clear();
	}

}
