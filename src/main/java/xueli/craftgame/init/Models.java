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
		add(new ModelBuilder("cg:cube").add("cube",new Cube(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1))).build());
		add(new ModelBuilder("cg:little_cube").add("cube",new Cube(new Vector3f(0.4f, 0.4f, 0.4f), new Vector3f(0.6f, 0.6f, 0.6f))).build());
		
	}

}
