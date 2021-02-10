package xueli.craftgame.world.renderer;

import com.google.gson.JsonArray;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import xueli.craftgame.world.World;
import xueli.gamengine.resource.Options;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.math.MatrixHelper;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.utils.vector.Vector;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.HashMap;

public class SkyRenderer extends IWorldRenderer {

	private float sun_distance;
	private float sun_size;
	private String shaderName;
	private float[] day_color;
	private int[] day_to_night_duration;
	private float[] night_color;
	private int[] night_to_day_duration;

	private final HashMap<String, Texture> environmentTextures = new HashMap<>();

	private float[] sunVertices;
	private double sun_angle = 0;
	
	private Shader shader;
	private int loc_viewMatrix, loc_modelMatrix;

	private float[] sky_color = new float[3];
	
	public SkyRenderer(World world) {
		// 只需要一个矩形的顶点: 4 * 8 * sizeof(float)
		super(world, 256, GL15.GL_STATIC_DRAW);

		prepareJson();

		this.shader = world.getWorldLogic().getCg().getShaderResource().get(shaderName);
		size();

		prepareUniformLocation();
		prepareVertex();

		world.getWorldLogic().getCg().getTextureManager().getTextures().forEach((namespace, t) -> {
			if(namespace.startsWith("environment.")) {
				environmentTextures.put(namespace, t);
			}
		});
		
	}

	private void prepareJson() {
		Options options = world.getWorldLogic().getCg().getOptions();

		sun_distance = options.get("sun_distance").getAsFloat();
		sun_size = options.get("sun_size").getAsFloat();
		shaderName = options.get("sun_shader_name").getAsString();

		JsonArray sun_sky_color_day_array = options.get("sun_sky_color_day").getAsJsonArray();
		day_color = new float[] {
				sun_sky_color_day_array.get(0).getAsFloat(),
				sun_sky_color_day_array.get(1).getAsFloat(),
				sun_sky_color_day_array.get(2).getAsFloat(),
		};
		JsonArray sun_sky_color_night_array = options.get("sun_sky_color_night").getAsJsonArray();
		night_color = new float[] {
				sun_sky_color_night_array.get(0).getAsFloat(),
				sun_sky_color_night_array.get(1).getAsFloat(),
				sun_sky_color_night_array.get(2).getAsFloat(),
		};

		JsonArray sun_sky_color_day_to_night_duration_array = options.get("sun_sky_color_day_to_night_duration").getAsJsonArray();
		day_to_night_duration = new int[] {
				sun_sky_color_day_to_night_duration_array.get(0).getAsInt(),
				sun_sky_color_day_to_night_duration_array.get(1).getAsInt(),
		};
		JsonArray sun_sky_color_night_to_day_duration_array = options.get("sun_sky_color_night_to_day_duration").getAsJsonArray();
		night_to_day_duration = new int[] {
				sun_sky_color_night_to_day_duration_array.get(0).getAsInt(),
				sun_sky_color_night_to_day_duration_array.get(1).getAsInt(),
		};

		sunVertices = new float[] {
				// sun
				/*  uv  */ /*    color    */  /*       vertices      */
				0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, sun_distance,
				0.5f, 1.0f, 1.0f, 1.0f, 1.0f, sun_size, 0.0f, sun_distance,
				0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, sun_size, sun_distance,
				0.5f, 0.0f, 1.0f, 1.0f, 1.0f, sun_size, sun_size, sun_distance,

				// moon
				/*  uv  */ /*    color    */  /*       vertices      */
				0.5f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, -sun_distance,
				1.0f, 1.0f, 1.0f, 1.0f, 1.0f, sun_size, 0.0f, -sun_distance,
				0.5f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, sun_size, -sun_distance,
				1.0f, 0.0f, 1.0f, 1.0f, 1.0f, sun_size, sun_size, -sun_distance,

		};

	}

	private void prepareUniformLocation() {
		this.shader.use();
		this.loc_viewMatrix = this.shader.getUnifromLocation("viewMatrix");
		this.loc_modelMatrix = this.shader.getUnifromLocation("modelMatrix");

		this.shader.unbind();
	}
	
	private void prepareVertex() {
		this.renderer.initDraw();

		ByteBuffer byteBuffer = this.renderer.mapBuffer();
		if(byteBuffer == null) {
			Logger.error(new Exception("[Sky Renderer] Can't init buffer!"));
		}

		FloatBuffer buffer = byteBuffer.asFloatBuffer();
		buffer.put(sunVertices);
		
		this.renderer.unmap();
		
		this.renderer.postDraw();
		
	}

	private void calculateSkyColor() {
		float time = world.time;

		if(time <= day_to_night_duration[0]) {
			sky_color[0] = day_color[0];
			sky_color[1] = day_color[1];
			sky_color[2] = day_color[2];
			//System.out.println("day: " + time);
		}
		else if(time >= day_to_night_duration[1] && time <= night_to_day_duration[0]) {
			sky_color[0] = night_color[0];
			sky_color[1] = night_color[1];
			sky_color[2] = night_color[2];
			//System.out.println("night: " + time);
		}
		else if(time >= day_to_night_duration[0] && time <= day_to_night_duration[1]) {
			sky_color[0] = day_color[0] + (night_color[0] - day_color[0]) * (time - day_to_night_duration[0]) / (day_to_night_duration[1] - day_to_night_duration[0]);
			sky_color[1] = day_color[1] + (night_color[1] - day_color[1]) * (time - day_to_night_duration[0]) / (day_to_night_duration[1] - day_to_night_duration[0]);
			sky_color[2] = day_color[2] + (night_color[2] - day_color[2]) * (time - day_to_night_duration[0]) / (day_to_night_duration[1] - day_to_night_duration[0]);
			//System.out.println("day->night: " + time);
		} else {
			sky_color[0] = night_color[0] + (day_color[0] - night_color[0]) * (time - night_to_day_duration[0]) / (night_to_day_duration[1] - night_to_day_duration[0]);
			sky_color[1] = night_color[1] + (day_color[1] - night_color[1]) * (time - night_to_day_duration[0]) / (night_to_day_duration[1] - night_to_day_duration[0]);
			sky_color[2] = night_color[2] + (day_color[2] - night_color[2]) * (time - night_to_day_duration[0]) / (night_to_day_duration[1] - night_to_day_duration[0]);
			//System.out.println("night->day: " + time);
		}

	}

	@Override
	public void render() {
		sun_angle = world.time / 24000.0 * 360.0;

		calculateSkyColor();
		GL11.glClearColor(sky_color[0],sky_color[1],sky_color[2],1.0f);

		this.shader.use();
		
		Vector vector = world.getWorldLogic().getClientPlayer().pos;
		Matrix4f viewMatrix = MatrixHelper.player(new Vector(0, 0, 0, vector.rotX, vector.rotY, vector.rotZ));
		this.shader.setUniformMatrix(loc_viewMatrix, viewMatrix);

		Matrix4f modelMatrix = new Matrix4f();
		modelMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(sun_angle + 10), new Vector3f(-1,0,0), modelMatrix, modelMatrix);
		Matrix4f.translate(new Vector3f(0, -10, 0), modelMatrix, modelMatrix);
		this.shader.setUniformMatrix(loc_modelMatrix, modelMatrix);

		renderer.initDraw();
		environmentTextures.get("environment.sun_moon").bind();
		renderer.draw(GL11.GL_TRIANGLE_STRIP,0, 4);
		renderer.draw(GL11.GL_TRIANGLE_STRIP,4, 4);
		Texture.unbind();
		renderer.postDraw();
		
		this.shader.unbind();

		GLHelper.checkGLError("Sky Renderer");
		
	}
	
	@Override
	public void size() {
		this.shader.use();
		
		Shader.setProjectionMatrix(world.getWorldLogic().getCg(), this.shader);
		
		this.shader.unbind();
	}

	@Override
	protected void release() {
		environmentTextures.clear();

	}

}
