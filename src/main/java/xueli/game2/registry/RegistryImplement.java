package xueli.game2.registry;

import xueli.game2.resource.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistryImplement<T> implements WritableRegistry<T> {

	private ArrayList<T> list = new ArrayList<>();
	private HashMap<ResourceLocation, T> map = new HashMap<>();

	private boolean frozen = false;

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
		if(frozen) throw new IllegalStateException("Please call \"cloneToWritable\" to write to another clone object!");
		list.add(t);
		map.put(name, t);
	}

	@Override
	public Registry<T> freeze() {
		this.frozen = true;
		return this;
	}

	@Override
	public WritableRegistry<T> cloneToWritable() {
		RegistryImplement<T> writable = new RegistryImplement<>();
		writable.list = new ArrayList<>(this.list);
		writable.map = new HashMap<>(this.map);
		return writable;
	}

}
