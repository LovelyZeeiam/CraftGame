package xueli.craftgame.renderer.model;

import java.util.ArrayList;

public class Model {

	private String namespace;
	private ArrayList<Cube> cubes;

	public Model(String namespace, ArrayList<Cube> cubes) {
		this.namespace = namespace;
		this.cubes = cubes;
	}

	public String getNamespace() {
		return namespace;
	}

	public ArrayList<Cube> getCubes() {
		return cubes;
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
