package xueli.craftgame.particles;

import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import xueli.gamengine.IGame;
import xueli.gamengine.data.WhenComponentComeToEnd;
import xueli.gamengine.particle.Particle;
import xueli.gamengine.particle.ParticleRenderer;
import xueli.gamengine.resource.Texture;
import xueli.gamengine.utils.FloatBufferWrapper;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.resource.Shader;

public class ParticleFirework extends Particle {

	private Vector3f position;
	
	public ParticleFirework(IGame game, Vector3f position) {
		super(game);
		this.position = position;

	}

	@Override
	public WhenComponentComeToEnd tickAndDraw(Shader shader, ParticleRenderer renderer) {
		renderer.initDraw();

		WhenComponentComeToEnd returnState = WhenComponentComeToEnd.NOT_YET;
		
		FloatBuffer mappedBuffer = renderer.mapBuffer().asFloatBuffer();
		FloatBufferWrapper wrapper = new FloatBufferWrapper(mappedBuffer);
		
		long duration = Time.thisTime - startTime;
		if(duration < 1500) {
			// 烟花上升
			wrapper.
				putVector3f(new Vector3f(position.x, position.y + (duration / 1000.0f) * (duration / 1000.0f) * 5, position.z)).putVector4f(new Vector4f(1.0f, 1.0f, 1.0f, 1.0f)).putVector2f(new Vector2f(114514, 1919180));
			
			renderer.unmap();
			
			game.getTextureManager().getTexture("particle.firework").bind();
			
			setPointSize(shader, 40.0f);
			renderer.draw(GL11.GL_POINTS, 0, 1);
			
		} else if(duration >= 1500 && duration <= 5000) {
			renderer.unmap();
			
			float size = (float) Math.log(duration - 1500) / 3.0f;
			float alpha = 1.0f - (duration - 1500) / 3500.0f;
			
			float y = position.y + 1.5f * 1.5f * 5;
			
			wrapper.
				putVector3f(new Vector3f(position.x + size, y - size, position.z)).putVector4f(new Vector4f(1.0f, 1.0f, 1.0f, alpha)).putVector2f(new Vector2f(1, 1)).
				putVector3f(new Vector3f(position.x + size, y + size, position.z)).putVector4f(new Vector4f(1.0f, 1.0f, 1.0f, alpha)).putVector2f(new Vector2f(1, 0)).
				putVector3f(new Vector3f(position.x - size, y - size, position.z)).putVector4f(new Vector4f(1.0f, 1.0f, 1.0f, alpha)).putVector2f(new Vector2f(0, 1)).
				putVector3f(new Vector3f(position.x - size, y + size, position.z)).putVector4f(new Vector4f(1.0f, 1.0f, 1.0f, alpha)).putVector2f(new Vector2f(0, 0));
			
			game.getTextureManager().getTexture("particle.test_particle").bind();
			renderer.draw(GL11.GL_TRIANGLE_STRIP, 0, 4);
			
		} else {
			renderer.unmap();
			returnState = WhenComponentComeToEnd.ITS_TIME_TO_DISPOSE;
			
		}
		
		Texture.unbind();

		renderer.postDraw();
		
		return returnState;
	}

}
