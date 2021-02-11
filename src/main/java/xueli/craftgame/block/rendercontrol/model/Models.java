package xueli.craftgame.block.rendercontrol.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import com.google.gson.JsonObject;

public class Models {

	private static HashMap<String, Class<? extends IModel>> models = new HashMap<>();

	static {
		models.put("cube", ModelCube.class);
		models.put("water", ModelWater.class);
		models.put("leaf", ModelLeaf.class);
		models.put("slab", ModelSlab.class);
		models.put("stair", ModelStair.class);
		models.put("fence", ModelFence.class);

	}

	public static IModel getModel(String name, JsonObject renderArgs) {
		Class<? extends IModel> model = models.get(name);
		Constructor<? extends IModel> c = null;
		try {
			c = model.getConstructor(JsonObject.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		IModel instance = null;
		try {
			instance = c.newInstance(renderArgs);
		} catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return instance;
	}

	public static void addModel(String name, Class<? extends IModel> clazz) {
		models.put(name, clazz);

	}

}
