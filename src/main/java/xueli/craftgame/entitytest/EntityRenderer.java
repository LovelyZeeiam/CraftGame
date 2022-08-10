package xueli.craftgame.entitytest;

import java.util.ArrayList;

import xueli.game.Game;
import xueli.game.utils.GLHelper;
import xueli.game.utils.math.MatrixHelper;
import xueli.game.vector.Vector;
import xueli.game2.resource.submanager.render.shader.Shader;

public class EntityRenderer {

	private final Game game;
	private final Shader shader;

	private ArrayList<Entity> entities = new ArrayList<>();

	public EntityRenderer(Game game) {
		this.game = game;

		this.shader = new Shader("res/shaders/entity/vert.txt", "res/shaders/entity/frag.txt");

		size();

	}

	public void size() {
		Shader.setProjectionMatrix(game, shader);

	}

	public void render(Vector camPos) {
		// GL11.glEnable(GL11.GL_CULL_FACE);

		Shader.setViewMatrix(camPos, shader);
		shader.bind();

		shader.setUniformMatrix(shader.getUnifromLocation("boneMatrix"), MatrixHelper.initMatrix);
		shader.setUniformMatrix(shader.getUnifromLocation("transMatrix"), MatrixHelper.initMatrix);

		for (Entity entity : entities) {
			entity.draw(this);
		}

		shader.unbind();

		// GL11.glDisable(GL11.GL_CULL_FACE);

		GLHelper.checkGLError("Entity Renderer");

	}

	public void update() {

	}

	public void release() {

	}

	public Game getGame() {
		return game;
	}

	public Shader getShader() {
		return shader;
	}

	public void addEntity(Entity entity) {
		this.entities.add(entity);
	}

}
