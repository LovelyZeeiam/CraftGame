package xueli.gamengine.particle;

import java.util.ArrayList;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import xueli.gamengine.IGame;
import xueli.gamengine.data.WhenComponentComeToEnd;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.utils.vector.Vector;

public class ParticleManager {

	private IGame ctxGame;
	private ParticleRenderer renderer;
	private Shader shader;

	private ArrayList<Particle> particles = new ArrayList<Particle>();

	public ParticleManager(IGame game, Shader particleShader) {
		this.renderer = new ParticleRenderer(65536, GL15.GL_DYNAMIC_DRAW);
		this.shader = particleShader;
		this.ctxGame = game;

		size();

		// Opengl点参数
		GL20.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
		float quadratic[] = { 1.0f, 0.0f, 0.01f };
		GL20.glPointParameterfv(GL20.GL_POINT_DISTANCE_ATTENUATION, quadratic);
		GL20.glPointParameterf(GL20.GL_POINT_FADE_THRESHOLD_SIZE, 60.0f);
		GL20.glPointParameterf(GL20.GL_POINT_SIZE_MIN, 13.0f);
		GL20.glPointParameterf(GL20.GL_POINT_SIZE_MAX, 15.f);
		
	}

	public void addParticle(Particle particle) {
		particles.add(particle);

	}

	public void draw(Vector camPos) {
		Shader.setViewMatrix(camPos, shader);
		shader.use();

		ArrayList<Particle> particlesThatNeedDeleting = new ArrayList<Particle>();
		particles.forEach(p -> {
			if (p.tickAndDraw(shader, renderer) == WhenComponentComeToEnd.ITS_TIME_TO_DISPOSE)
				particlesThatNeedDeleting.add(p);
		});
		particles.removeAll(particlesThatNeedDeleting);

		shader.unbind();
		
		GLHelper.checkGLError("Particle Renderer");

	}

	public void size() {
		shader.use();

		Shader.setProjectionMatrix(ctxGame, shader);

		shader.unbind();

	}

}
