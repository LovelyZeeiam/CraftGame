package xueli.game2.resource.submanager.render.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.utils.vector.Matrix4f;
import org.lwjgl.utils.vector.Vector2f;
import org.lwjgl.utils.vector.Vector3f;

import xueli.game.Game;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector;
import xueli.game2.renderer.legacy.buffer.Bindable;

public class Shader implements Bindable {
	
	public static final Shader EMPTY_SHADER = new Shader(0, 0, 0, 0);
	
	private int shaderID, vertID, fragID, geoID = -1;
	private HashMap<String, Integer> uniforms = new HashMap<String, Integer>();
	private FloatBuffer b = BufferUtils.createFloatBuffer(16);

	public Shader(String vertPath, String fragPath) {
		vertID = getShader(vertPath, GL20.GL_VERTEX_SHADER);
		fragID = getShader(fragPath, GL20.GL_FRAGMENT_SHADER);

		this.shaderID = GL20.glCreateProgram();
		GL20.glAttachShader(this.shaderID, vertID);
		GL20.glAttachShader(this.shaderID, fragID);
		GL20.glLinkProgram(this.shaderID);
		GL20.glValidateProgram(this.shaderID);

	}

	public Shader(String vertPath, String geoPath, String fragPath) {
		vertID = getShader(vertPath, GL20.GL_VERTEX_SHADER);
		geoID = getShader(geoPath, GL32.GL_GEOMETRY_SHADER);
		fragID = getShader(fragPath, GL20.GL_FRAGMENT_SHADER);

		this.shaderID = GL20.glCreateProgram();
		GL20.glAttachShader(this.shaderID, vertID);
		GL20.glAttachShader(this.shaderID, geoID);
		GL20.glAttachShader(this.shaderID, fragID);
		GL20.glLinkProgram(this.shaderID);
		GL20.glValidateProgram(this.shaderID);

	}

	private Shader(int shaderID, int vertID, int fragID, int geoID) {
		this.shaderID = shaderID;
		this.vertID = vertID;
		this.fragID = fragID;
		this.geoID = geoID;
	}

	public static Shader getShader(String vertCode, String fragCode) {
		int vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertID, vertCode);
		GL20.glCompileShader(vertID);
		if (GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(vertID, 500));
		}

		int fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragID, fragCode);
		GL20.glCompileShader(fragID);
		if (GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(fragID, 500));
		}

		int id = GL20.glCreateProgram();
		GL20.glAttachShader(id, vertID);
		GL20.glAttachShader(id, fragID);
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);

		return new Shader(id, vertID, fragID, 0);
	}

	private int getShader(String shaderPath, int type) {
		StringBuilder source = new StringBuilder();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(new File(shaderPath)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				source.append(line).append("\r\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		int id = GL20.glCreateShader(type);
		GL20.glShaderSource(id, source.toString());
		GL20.glCompileShader(id);

		if (GL20.glGetShaderi(id, GL20.GL_COMPILE_STATUS) != GL11.GL_TRUE) {
			throw new RuntimeException(GL20.glGetShaderInfoLog(id, 500));
		}

		return id;
	}

	@Override
	public void bind() {
		GL20.glUseProgram(this.shaderID);
	}

	protected void bindAttribute(String name, int attrib_id) {
		GL20.glBindAttribLocation(this.shaderID, attrib_id, name);
	}

	public int getUnifromLocation(String name) {
		int location = -1;
		if (uniforms.containsKey(name)) {
			location = uniforms.get(name);
		} else {
			location = GL20.glGetUniformLocation(this.shaderID, name);
			uniforms.put(name, location);
		}
		if (location == -1)
			System.err.println("Can't find uniform location: " + name);
		return location;
	}

	public void setUniformMatrix(int loc, Matrix4f mat) {
		b.clear();
		mat.store(b);
		b.flip();
		GL20.glUniformMatrix4fv(loc, false, b);
	}

	public void setUniformVector3(int loc, Vector3f v) {
		GL20.glUniform3f(loc, v.x, v.y, v.z);
	}

	public void setUniformVector2(int loc, Vector2f v) {
		GL20.glUniform2f(loc, v.x, v.y);
	}

	public void setInt(int loc, int v) {
		GL20.glUniform1i(loc, v);
	}

	public void setFloat(int loc, float v) {
		GL20.glUniform1f(loc, v);
	}

	@Override
	public void unbind() {
		GL20.glUseProgram(0);
	}

	public void release() {
		GL20.glDetachShader(this.shaderID, vertID);
		GL20.glDetachShader(this.shaderID, fragID);
		GL20.glDeleteProgram(this.shaderID);
		GL20.glDeleteShader(vertID);
		GL20.glDeleteShader(fragID);
	}

	public static void setProjectionMatrix(Game game, Shader shader) {
		setProjectionMatrix(shader, game.getDisplay().getWidth(), game.getDisplay().getHeight(), 110.0f);
	}

	public static Matrix4f setProjectionMatrix(Shader shader, float width, float height, float fov) {
		Matrix4f matrix = MatrixHelper.perspecive(width, height, fov, 0.01f, 114514.0f);

		shader.bind();
		shader.setUniformMatrix(shader.getUnifromLocation("projMatrix"), matrix);
		shader.unbind();

		return matrix;
	}

	public static void setProjectionMatrix(Shader shader, Matrix4f mat) {
		shader.bind();
		shader.setUniformMatrix(shader.getUnifromLocation("projMatrix"), mat);
		shader.unbind();

	}

	public static Matrix4f setViewMatrix(Vector cam, Shader shader) {
		Matrix4f matrix = MatrixHelper.player(cam);

		shader.bind();
		shader.setUniformMatrix(shader.getUnifromLocation("viewMatrix"), matrix);
		shader.unbind();

		return matrix;
	}

	public static void setViewMatrix(Matrix4f mat, Shader shader) {
		shader.bind();
		shader.setUniformMatrix(shader.getUnifromLocation("viewMatrix"), mat);
		shader.unbind();

	}

}