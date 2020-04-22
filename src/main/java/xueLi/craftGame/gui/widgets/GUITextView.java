package xueLi.craftGame.gui.widgets;

import java.io.File;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontMeshCreator.TextMeshData;
import xueLi.craftGame.gui.GUIWidget;
import xueLi.craftGame.utils.DisplayManager;
import xueLi.craftGame.utils.GLHelper;

public class GUITextView extends GUIWidget {

	private GUIText text;
	//TODO: 字体
	private static FontType font;
	
	static {
		font = new FontType(GLHelper.registerTexture("res/fonts/YaHeiLight-16.png"),new File("res/fonts/YaHeiLight-16.fnt"));
		
	}
	
	private int vao = -1,vbo = -1,tbo = -1;
	
	/**
	 * 
	 * @param text 文字
	 * @param pos 坐标
	 * @param backgroundColor 目前还没有用到
	 */
	public GUITextView(String text,Vector2f pos, Vector4f backgroundColor) {
		super(pos, new Vector2f(10,text.length() * 10), backgroundColor);
		this.text = new GUIText(text,1.5f,font,pos,1.0f,true);
		
		vao = GL30.glGenVertexArrays();
		vbo = GL15.glGenBuffers();
		tbo = GL15.glGenBuffers();
		genBuffer();
		
	}
	
	private void genBuffer() {
		GL30.glBindVertexArray(vao);
		TextMeshData data = font.loadText(text);
		float[] vertices = data.getVertexPositions();
		float[] texCoords = data.getTextureCoords();
		FloatBuffer vert = BufferUtils.createFloatBuffer(vertices.length);
		vert.put(vertices).flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vert, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(0);
		vert.clear();
		FloatBuffer tex = BufferUtils.createFloatBuffer(texCoords.length);
		tex.put(texCoords).flip();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, tex, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
		GL20.glEnableVertexAttribArray(1);
		tex.clear();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
	}
	
	public void setText(String text) {
		this.text.setText(text);
		genBuffer();
	}

	@Override
	public void draw() {
		if(DisplayManager.tickResize())
			genBuffer();
		
		GL30.glBindVertexArray(vao);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindVertexArray(0);
		
		
		
	}

	@Override
	public void cleanUp() {
		GL15.glDeleteBuffers(vbo);
		GL15.glDeleteBuffers(tbo);
		GL30.glDeleteVertexArrays(vao);
	}
	

}
