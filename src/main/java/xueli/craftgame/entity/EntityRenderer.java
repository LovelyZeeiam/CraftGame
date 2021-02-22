package xueli.craftgame.entity;

import java.util.ArrayList;

import xueli.gamengine.IGame;
import xueli.gamengine.utils.GLHelper;
import xueli.gamengine.utils.math.MatrixHelper;
import xueli.gamengine.utils.renderer.IRenderer;
import xueli.gamengine.utils.resource.Shader;
import xueli.gamengine.utils.vector.Vector;

public class EntityRenderer implements IRenderer {

	private final IGame game;
	private final Shader shader;

	private ArrayList<Entity> entities = new ArrayList<>();

	public EntityRenderer(IGame game, Shader shader) {
		this.shader = shader;
		this.game = game;

		size();
		init();

	}

	@Override
	public void init() {

	}

	@Override
	public void size() {
		Shader.setProjectionMatrix(game, shader);

	}

	@Override
	public void render(Vector camPos) {
		// GL11.glEnable(GL11.GL_CULL_FACE);

		Shader.setViewMatrix(camPos, shader);
		shader.use();

		shader.setUniformMatrix(shader.getUnifromLocation("boneMatrix"), MatrixHelper.initMatrix);
		shader.setUniformMatrix(shader.getUnifromLocation("transMatrix"), MatrixHelper.initMatrix);

		for (Entity entity : entities) {
			entity.draw(this);
		}

		shader.unbind();

		// GL11.glDisable(GL11.GL_CULL_FACE);

		GLHelper.checkGLError("Entity Renderer");

	}

	@Override
	public void release() {

	}

	public IGame getGame() {
		return game;
	}

	public Shader getShader() {
		return shader;
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
