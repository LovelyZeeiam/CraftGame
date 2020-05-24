package xueLi.craftGame.utils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.vector.Matrix4f;

public class FrameBuffer {
	
	private int fb_id,fb_texture_id;
	
	public void createBuffer() {
		fb_id = GL30.glGenFramebuffers();
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fb_id);
		
		fb_texture_id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, fb_texture_id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, 1024, 1024, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer)null);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL13.GL_CLAMP_TO_BORDER);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, fb_texture_id, 0);
		GL11.glDrawBuffer(GL11.GL_NONE);
		
		if(GL30.glCheckFramebufferStatus(GL30.GL_FRAMEBUFFER) != GL30.GL_FRAMEBUFFER_COMPLETE) {
			System.err.println("Can't create Frame Buffer!");
		}
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
		
	}
	
	private static String[] frameBufferRenderShader = {
			
		"#version 330 core\r\n" + 
		"layout(location = 0) in vec3 vert;\r\n" + 
		"uniform mat4 viewMatrix;\r\n" +
		"uniform mat4 projMatrix;\r\n" + 
		"void main(){\r\n" + 
		"	gl_Position =  viewMatrix * projMatrix * vec4(vert,1);\r\n" + 
		"}",
		
		"#version 330 core\r\n" + 
		"layout(location = 0) out float fragmentdepth;\r\n" + 
		"void main(){\r\n" + 
		"    fragmentdepth = gl_FragCoord.z;\r\n" + 
		"}"
		
	};
	private static int loc_viewMatrix,loc_projMatrix;
	
	private static Matrix4f depthMVPMatrix = new Matrix4f();
	private static int depthShaderRenderID;
	
	private static int quad_vao,quad_vbo;
	
	static {
		depthMVPMatrix.m00 = 0.5f;
		depthMVPMatrix.m11 = 0.5f;
		depthMVPMatrix.m22 = 0.5f;
		depthMVPMatrix.m33 = 1.0f;
		depthMVPMatrix.m03 = 0.5f;
		depthMVPMatrix.m13 = 0.5f;
		depthMVPMatrix.m23 = 0.5f;
		
		depthShaderRenderID = Shader.getShader(frameBufferRenderShader[0], frameBufferRenderShader[1]);
		loc_viewMatrix = GL20.glGetUniformLocation(depthShaderRenderID, "viewMatrix");
		loc_projMatrix = GL20.glGetUniformLocation(depthShaderRenderID, "projMatrix");
		
		float[] quad = {
				-1,-1,
				-1,0,
				0,0,
				0,-1,
		};
		quad_vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(quad_vao);
		quad_vbo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, quad_vbo);
		FloatBuffer b = BufferUtils.createFloatBuffer(8);
		b.put(quad).flip();
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, b, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
	}
	
	public void bind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fb_id);
	}
	
	public void unbind() {
		GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
	}
	
	public void draw() {
		GL20.glUseProgram(depthShaderRenderID);
		
		FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
		GLHelper.lastTimeProjMatrix.store(matrix);
		GL20.glUniformMatrix4fv(loc_projMatrix, false, matrix);
		
		FloatBuffer matrix2 = BufferUtils.createFloatBuffer(16);
		GLHelper.lastTimeViewMatrix.store(matrix2);
		GL20.glUniformMatrix4fv(loc_viewMatrix, false, matrix2);
		
		GL30.glBindVertexArray(quad_vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, quad_vbo);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}
	
	
	
	
	
	

}
