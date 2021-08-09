package xueli.craftgame.model;

import java.util.HashMap;
import java.util.Objects;

import xueli.game.module.Module;

public class Model extends Module {

	private HashMap<String, Cube> cubes;

	public Model(String namespace, HashMap<String, Cube> cubes) {
		super(namespace);
		this.cubes = cubes;
	}

	public HashMap<String, Cube> getCubes() {
		return cubes;
	}

	@Override
	public boolean checkInvaild() {
		return Objects.nonNull(cubes) && Objects.nonNull(getNamespace());
	}

	/*
	 * public static Model loadFromFile(String path) throws JsonIOException,
	 * JsonSyntaxException, FileNotFoundException { JsonObject object = new
	 * Gson().fromJson(new JsonReader(new FileReader(new File(path))),
	 * JsonObject.class); String namespace = object.get("namespace").getAsString();
	 *
	 * JsonObject modelJson = object.get("models").getAsJsonObject();
	 *
	 *
	 * }
	 */

}
