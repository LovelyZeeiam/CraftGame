package xueli.game2.registry;

import java.util.ArrayList;
import java.util.HashMap;

import xueli.game2.resource.ResourceLocation;

public class RegistryImplement<T> implements WritableRegistry<T> {

	private ArrayList<T> list = new ArrayList<>();
	private HashMap<ResourceLocation, T> map = new HashMap<>();

	@Override
	public T getByName(ResourceLocation name) {
		return map.get(name);
	}

	@Override
	public T getById(int id) {
		return list.get(id);
	}

	@Override
	public int getId(T t) {
		return list.indexOf(t);
	}

	@Override
	public void register(ResourceLocation name, T t) {
		list.add(t);
		map.put(name, t);

	}

}
