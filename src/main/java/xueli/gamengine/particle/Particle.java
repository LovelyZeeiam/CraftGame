package xueli.gamengine.particle;

import xueli.gamengine.IGame;
import xueli.gamengine.data.WhenComponentComeToEnd;
import xueli.gamengine.utils.Time;
import xueli.gamengine.utils.resource.Shader;

public abstract class Particle {

	protected long startTime;
	protected IGame game;

	public Particle(IGame game) {
		this.startTime = Time.thisTime;
		this.game = game;

	}

	public abstract WhenComponentComeToEnd tickAndDraw(Shader shader, ParticleRenderer renderer);

	protected void setPointSize(Shader shader, float pointSize) {
		shader.setFloat(shader.getUnifromLocation("point_size"), pointSize);

	}

}
