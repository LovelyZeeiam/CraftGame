package xueli.craftgame.model;

import java.util.ArrayList;
import java.util.HashMap;

import xueli.game.utils.texture.AtlasTextureHolder;

public class TexturedModelBuilder {

	private HashMap<String, Cube> model;
	private ArrayList<TexturedCube> cubes = new ArrayList<>();

	public TexturedModelBuilder(Model model) {
		this.model = model.getCubes();

	}

	public TexturedModelBuilder add(String namespace, AtlasTextureHolder... textures) {
		cubes.add(new TexturedCube(model.get(namespace), textures));
		return this;
	}

	public TexturedModel build(String namespace) {
		return new TexturedModel(namespace, cubes);
	}

}
