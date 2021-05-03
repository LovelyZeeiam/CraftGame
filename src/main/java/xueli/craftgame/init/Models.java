package xueli.craftgame.init;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.renderer.model.Cube;
import xueli.craftgame.renderer.model.Model;
import xueli.craftgame.renderer.model.ModelBuilder;
import xueli.game.module.Modules;

public class Models extends Modules<Model> {

	public Models() {
		
	}

	@Override
	public void init() {
		add(new ModelBuilder("cg:cube").add("cube", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1))).build());

		add(new ModelBuilder("cg:stair_down_front")
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 1)))
				.add("up", new Cube(new Vector3f(0, 0.5f, 0.5f), new Vector3f(1, 1, 1))).build());
		add(new ModelBuilder("cg:stair_down_back")
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 1)))
				.add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 0.5f))).build());
		add(new ModelBuilder("cg:stair_down_left")
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 1)))
				.add("up", new Cube(new Vector3f(0.5f, 0.5f, 0), new Vector3f(1, 1, 1))).build());
		add(new ModelBuilder("cg:stair_down_right")
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 1)))
				.add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(0.5f, 1, 1))).build());
		add(new ModelBuilder("cg:stair_up_front").add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 1)))
				.add("down", new Cube(new Vector3f(0, 0, 0.5f), new Vector3f(1, 0.5f, 1))).build());
		add(new ModelBuilder("cg:stair_up_back").add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 1)))
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 0.5f))).build());
		add(new ModelBuilder("cg:stair_up_left").add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 1)))
				.add("down", new Cube(new Vector3f(0.5f, 0, 0), new Vector3f(1, 0.5f, 1))).build());
		add(new ModelBuilder("cg:stair_up_right").add("up", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 1)))
				.add("down", new Cube(new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 1))).build());
		
		add(new ModelBuilder("cg:slab_down").add("slab", new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 0.5f, 1))).build());
		add(new ModelBuilder("cg:slab_up").add("slab", new Cube(new Vector3f(0, 0.5f, 0), new Vector3f(1, 1, 1))).build());
		

	}

}
