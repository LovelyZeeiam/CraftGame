package xueli.craftgame.world.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.google.gson.JsonArray;

import xueli.craftgame.world.World;
import xueli.gamengine.resource.Options;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.math.MatrixHelper;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.utils.vector.Vector;
import xueli.utils.Logger;

public class SkyRenderer extends IWorldRenderer {

	private float sun_distance;
	private float sun_size;
	private String shaderName, cloud_shader_name;
	private float[] day_color;
	private int[] day_to_night_duration;
	private float[] night_color;
	private int[] night_to_day_duration;

	private int cloud_texture_size;
	private int cloud_above;
	private float cloud_move_speed;
	private float cloud_day_alpha, cloud_night_alpha;

	private final HashMap<String, Texture> environmentTextures = new HashMap<>();

	private float[] sunVertices, cloudVertices;
	private double sun_angle = 0;

	private Shader shader, cloudShader;
	private int loc_viewMatrix, loc_modelMatrix;
	private int loc_cloud_model_matrix, loc_cloud_view_matrix, loc_cloud_alpha;

	private float[] sky_color = new float[3];
	private float cloud_alpha;

	public SkyRenderer(World world) {
		// 只需要一个矩形的顶点: 4 * 8 * sizeof(float)
		super(world, 768, GL15.GL_STATIC_DRAW);

		prepareJson();

		this.shader = world.getWorldLogic().getCg().getShaderResource().get(shaderName);
		this.cloudShader = world.getWorldLogic().getCg().getShaderResource().get(cloud_shader_name);

		size();

		prepareUniformLocation();
		prepareVertex();

		world.getWorldLogic().getCg().getTextureManager().getTextures().forEach((namespace, t) -> {
			if (namespace.startsWith("environment.")) {
				environmentTextures.put(namespace, t);
			}
		});

	}

	private void prepareJson() {
		Options options = world.getWorldLogic().getCg().getOptions();

		sun_distance = options.get("sun_distance").getAsFloat();
		sun_size = options.get("sun_size").getAsFloat();
		shaderName = options.get("sun_shader_name").getAsString();
		cloud_shader_name = options.get("cloud_shader_name").getAsString();
		cloud_move_speed = options.get("sky_cloud_move_speed").getAsFloat();

		JsonArray sun_sky_color_day_array = options.get("sun_sky_color_day").getAsJsonArray();
		day_color = new float[] { sun_sky_color_day_array.get(0).getAsFloat(),
				sun_sky_color_day_array.get(1).getAsFloat(), sun_sky_color_day_array.get(2).getAsFloat(), };
		JsonArray sun_sky_color_night_array = options.get("sun_sky_color_night").getAsJsonArray();
		night_color = new float[] { sun_sky_color_night_array.get(0).getAsFloat(),
				sun_sky_color_night_array.get(1).getAsFloat(), sun_sky_color_night_array.get(2).getAsFloat(), };

		JsonArray sun_sky_color_day_to_night_duration_array = options.get("sun_sky_color_day_to_night_duration")
				.getAsJsonArray();
		day_to_night_duration = new int[] { sun_sky_color_day_to_night_duration_array.get(0).getAsInt(),
				sun_sky_color_day_to_night_duration_array.get(1).getAsInt(), };
		JsonArray sun_sky_color_night_to_day_duration_array = options.get("sun_sky_color_night_to_day_duration")
				.getAsJsonArray();
		night_to_day_duration = new int[] { sun_sky_color_night_to_day_duration_array.get(0).getAsInt(),
				sun_sky_color_night_to_day_duration_array.get(1).getAsInt(), };

		cloud_texture_size = options.get("sky_cloud_size").getAsInt();
		cloud_above = options.get("sky_cloud_above").getAsInt();
		cloud_day_alpha = options.get("sky_cloud_day_alpha").getAsFloat();
		cloud_night_alpha = options.get("sky_cloud_night_alpha").getAsFloat();

		sunVertices = new float[] {
				// sun
				/* uv */ /* color */ /* vertices */
				0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, sun_distance, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f, sun_size, 0.0f,
				sun_distance, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, sun_size, sun_distance, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f,
				sun_size, sun_size, sun_distance,

				// moon
				/* uv */ /* color */ /* vertices */
				0.5f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, -sun_distance, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, sun_size, 0.0f,
				-sun_distance, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, sun_size, -sun_distance, 1.0f, 0.0f, 1.0f, 1.0f,
				1.0f, sun_size, sun_size, -sun_distance,

				// clouds
				0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -cloud_texture_size, cloud_above, -cloud_texture_size, 1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, cloud_texture_size, cloud_above, -cloud_texture_size, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
				-cloud_texture_size, cloud_above, cloud_texture_size, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, cloud_texture_size,
				cloud_above, cloud_texture_size,

				// clouds
				0.0f, 1.0f, 1.0f, 1.0f, 1.0f, cloud_texture_size, cloud_above, -cloud_texture_size, 1.0f, 1.0f, 1.0f,
				1.0f, 1.0f, 3 * cloud_texture_size, cloud_above, -cloud_texture_size, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
				cloud_texture_size, cloud_above, cloud_texture_size, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
				3 * cloud_texture_size, cloud_above, cloud_texture_size,

				// clouds
				0.0f, 1.0f, 1.0f, 1.0f, 1.0f, -3 * cloud_texture_size, cloud_above, -cloud_texture_size, 1.0f, 1.0f,
				1.0f, 1.0f, 1.0f, -cloud_texture_size, cloud_above, -cloud_texture_size, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f,
				-3 * cloud_texture_size, cloud_above, cloud_texture_size, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f,
				-cloud_texture_size, cloud_above, cloud_texture_size,

		};

	}

	private void prepareUniformLocation() {
		this.shader.use();
		this.loc_viewMatrix = this.shader.getUnifromLocation("viewMatrix");
		this.loc_modelMatrix = this.shader.getUnifromLocation("modelMatrix");

		this.shader.unbind();

		this.cloudShader.use();
		loc_cloud_model_matrix = this.cloudShader.getUnifromLocation("modelMatrix");
		loc_cloud_view_matrix = this.cloudShader.getUnifromLocation("viewMatrix");
		loc_cloud_alpha = this.cloudShader.getUnifromLocation("cloud_alpha");

		this.cloudShader.unbind();

	}

	private void prepareVertex() {
		this.renderer.initDraw();

		ByteBuffer byteBuffer = this.renderer.mapBuffer();
		if (byteBuffer == null) {
			Logger.error(new Exception("[Sky Renderer] Can't init buffer!"));
		}
		FloatBuffer buffer = byteBuffer.asFloatBuffer();
		buffer.put(sunVertices);
		this.renderer.unmap();

		this.renderer.postDraw();

	}

	private void calculateSkyColor() {
		float time = world.time;

		if (time <= day_to_night_duration[0]) {
			sky_color[0] = day_color[0];
			sky_color[1] = day_color[1];
			sky_color[2] = day_color[2];
			cloud_alpha = cloud_day_alpha;
			
		} else if (time >= day_to_night_duration[1] && time <= night_to_day_duration[0]) {
			sky_color[0] = night_color[0];
			sky_color[1] = night_color[1];
			sky_color[2] = night_color[2];
			cloud_alpha = cloud_night_alpha;

		} else if (time >= day_to_night_duration[0] && time <= day_to_night_duration[1]) {
			sky_color[0] = day_color[0] + (night_color[0] - day_color[0]) * (time - day_to_night_duration[0])
					/ (day_to_night_duration[1] - day_to_night_duration[0]);
			sky_color[1] = day_color[1] + (night_color[1] - day_color[1]) * (time - day_to_night_duration[0])
					/ (day_to_night_duration[1] - day_to_night_duration[0]);
			sky_color[2] = day_color[2] + (night_color[2] - day_color[2]) * (time - day_to_night_duration[0])
					/ (day_to_night_duration[1] - day_to_night_duration[0]);
			cloud_alpha = cloud_day_alpha + (cloud_night_alpha - cloud_day_alpha) * (time - day_to_night_duration[0])
					/ (day_to_night_duration[1] - day_to_night_duration[0]);

		} else {
			sky_color[0] = night_color[0] + (day_color[0] - night_color[0]) * (time - night_to_day_duration[0])
					/ (night_to_day_duration[1] - night_to_day_duration[0]);
			sky_color[1] = night_color[1] + (day_color[1] - night_color[1]) * (time - night_to_day_duration[0])
					/ (night_to_day_duration[1] - night_to_day_duration[0]);
			sky_color[2] = night_color[2] + (day_color[2] - night_color[2]) * (time - night_to_day_duration[0])
					/ (night_to_day_duration[1] - night_to_day_duration[0]);
			cloud_alpha = cloud_night_alpha + (cloud_day_alpha - cloud_night_alpha) * (time - night_to_day_duration[0])
					/ (night_to_day_duration[1] - night_to_day_duration[0]);

		}

	}

	private float cloud_offset = 0;

	@Override
	public void render() {
		sun_angle = world.time / 24000.0 * 360.0;

		calculateSkyColor();
		GL11.glClearColor(sky_color[0], sky_color[1], sky_color[2], 1.0f);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		renderer.initDraw();

		{
			this.shader.use();

			Vector vector = world.getWorldLogic().getCameraPos();
			Matrix4f viewMatrix = MatrixHelper.player(new Vector(0, 0, 0, vector.rotX, vector.rotY, vector.rotZ));
			this.shader.setUniformMatrix(loc_viewMatrix, viewMatrix);

			Matrix4f modelMatrix = new Matrix4f();
			modelMatrix.setIdentity();
			Matrix4f.rotate((float) Math.toRadians(sun_angle + 10), new Vector3f(-1, 0, 0), modelMatrix, modelMatrix);
			Matrix4f.translate(new Vector3f(0, -10, 0), modelMatrix, modelMatrix);
			this.shader.setUniformMatrix(loc_modelMatrix, modelMatrix);

			environmentTextures.get("environment.sun_moon").bind();
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 0, 4);
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 4, 4);
			Texture.unbind();

			this.shader.unbind();

		}

		{
			cloud_offset += Time.deltaTime * cloud_move_speed;
			if (cloud_offset >= cloud_texture_size)
				cloud_offset = -cloud_texture_size;

			cloudShader.use();

			Matrix4f cloud_translate_matrix = new Matrix4f();
			cloud_translate_matrix.setIdentity();
			Matrix4f.translate(new Vector3f(cloud_offset, 0, 0), cloud_translate_matrix, cloud_translate_matrix);
			this.cloudShader.setUniformMatrix(loc_cloud_model_matrix, cloud_translate_matrix);

			this.cloudShader.setUniformMatrix(loc_cloud_view_matrix,
					MatrixHelper.player(world.getWorldLogic().getCameraPos()));
			
			this.cloudShader.setFloat(loc_cloud_alpha, cloud_alpha);

			environmentTextures.get("environment.clouds").bind();
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 8, 4);
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 12, 4);
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 16, 4);
			Texture.unbind();

			cloudShader.unbind();

		}

		renderer.postDraw();

		GL11.glDisable(GL11.GL_BLEND);

		GLHelper.checkGLError("Sky Renderer");

	}

	@Override
	public void size() {
		this.shader.use();

		Shader.setProjectionMatrix(world.getWorldLogic().getCg(), this.shader);
		Shader.setProjectionMatrix(world.getWorldLogic().getCg(), this.cloudShader);

		this.shader.unbind();
	}

	@Override
	protected void release() {
		environmentTextures.clear();

	}

}
