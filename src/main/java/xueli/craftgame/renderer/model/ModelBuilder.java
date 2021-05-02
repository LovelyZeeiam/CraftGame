package xueli.craftgame.renderer.model;

import java.util.HashMap;

public class ModelBuilder {
	
	private String namespace;
	private HashMap<String,Cube> cubes = new HashMap<>();
	
	public ModelBuilder(String namespace) {
		this.namespace = namespace;
		
	}
	
	public ModelBuilder add(String namespace, Cube e) {
		cubes.put(namespace, e);
		return this;
	}
	
	public Model build() {
		return new Model(namespace, cubes);
	}

}
