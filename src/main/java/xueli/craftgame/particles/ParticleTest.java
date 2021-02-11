package xueli.craftgame.particles;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;

import xueli.gamengine.IGame;
import xueli.gamengine.data.WhenComponentComeToEnd;
import xueli.gamengine.particle.Particle;
import xueli.gamengine.particle.ParticleRenderer;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.utils.resource.Shader;

public class ParticleTest extends Particle {

	public ParticleTest(IGame game) {
		super(game);
		
	}

	@Override
	public WhenComponentComeToEnd tickAndDraw(Shader shader, ParticleRenderer renderer) {
		renderer.initDraw();
		
		setPointSize(shader, 100.0f);
		
		FloatBuffer mappedBuffer = renderer.mapBuffer().asFloatBuffer();
		mappedBuffer.put(0).put(10).put(0);
		mappedBuffer.put(1).put(1).put(1);
		renderer.unmap();
		
		game.getTextureManager().getTexture("particle.test_particle").bind();
		renderer.draw(GL11.GL_POINTS, 0, 1);
		Texture.unbind();
		
		renderer.postDraw();
		return WhenComponentComeToEnd.NOT_YET;
	}

}
