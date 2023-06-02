package xueli.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class RegistryImplement<T> implements WritableRegistry<T> {

	private ArrayList<T> list = new ArrayList<>();
	private HashMap<Identifier, T> map = new HashMap<>();

	private HashMap<Identifier, HashSet<Identifier>> tagToRegistryMap = new HashMap<>();
	private HashMap<Identifier, HashSet<Identifier>> registryToTagMap = new HashMap<>();

	private boolean frozen = false;

	@Override
	public T getByName(Identifier name) {
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
	public void register(Identifier name, T t) {
		if (frozen)
			throw new IllegalStateException("Please call \"cloneToWritable\" to write to another clone object!");
		list.add(t);
		map.put(name, t);
	}

	@Override
	public void addTag(Identifier name, Identifier... tags) {
		HashSet<Identifier> thisRegistryTagSet = registryToTagMap.computeIfAbsent(name, n -> new HashSet<>());

		for (int i = 0; i < tags.length; i++) {
			Identifier tag = tags[i];
			thisRegistryTagSet.add(tag);
			tagToRegistryMap.computeIfAbsent(tag, t -> new HashSet<>()).add(name);
		}

	}

	@Override
	public Set<Identifier> getAllContainTag(Identifier tag) {
		return tagToRegistryMap.get(tag);
	}

	@Override
	public Set<Identifier> getTags(Identifier name) {
		return registryToTagMap.get(name);
	}

	@Override
	public void forEach(BiConsumer<Identifier, T> c) {
		map.forEach(c);
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
