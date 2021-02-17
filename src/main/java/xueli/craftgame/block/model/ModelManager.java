package xueli.craftgame.block.model;

import java.util.HashMap;

public class ModelManager {

	private HashMap<String, IModel> modelsHashMap = new HashMap<String, IModel>();

	public IModel getModel(String namespace) {
		return modelsHashMap.get(namespace);
	}

	public void init() {
		modelsHashMap.put("cube", new ModelCube());

	}

}
