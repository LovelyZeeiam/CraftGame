package xueli.game2.ecs;

import java.util.Set;

/**
 * This is an ECS context which holds an Entity Component System.
 */
public interface ECSContext {

	/**
	 * Spawn an entity
	 * 
	 * @return The ID of the entity
	 */
	public long spawn();

	/**
	 * Delete an entity
	 */
	public void kill(long entityId);

	/**
	 * Add an component to the entity
	 */
	public void addComponent(long entityId, Object component);

	/**
	 * Get an component from the entity
	 * 
	 * @param <T> The type of the component
	 */
	public <T> T getComponent(long entityId, Class<T> componentClazz);

	/**
	 * Remove an component from the entity
	 * 
	 * @param <T> The type of the component
	 */
	public <T> void removeComponent(long entityId, Class<T> componentClazz);

	/**
	 * Add a resource. A resource is a component that isn't bound to any entity and
	 * only one instance of each type exists.
	 * 
	 * @param <R> The type of the resource
	 */
	public void addResource(Object resource);

	/**
	 * Get a resource.
	 * 
	 * @param <R> The type of the resource
	 * @return The unique resource instance in the type R, or null if we don't have
	 *         the corresponding resource.
	 */
	public <R> R getResource(Class<R> resourceClazz);

	/**
	 * Remove a resource
	 */
	public <R> void removeResource(Class<R> resourceClazz);

	/**
	 * Query all the entity that has component T
	 * 
	 * @param <T> The type of the entities that you want to find
	 * @return A list contains all the corresponding entities
	 */
	public <T> Set<Long> query(Class<T> clazz);

	/**
	 * Add a system
	 */
	public void addSystem(ECSSystem system);

	/**
	 * Broadcast an event to the context. When next update occurs, every system
	 * should have access to the events. And when it is the following update, these
	 * events will become inaccessible.
	 * 
	 * @param <E> The type of the event
	 */
	public void broadcast(Object e);

	/**
	 * Read an event
	 * 
	 * @param <E> The type of the event
	 * @return The instance of the event, or null if there is no event belonging to
	 *         type E now
	 */
	public <E> E readEvent(Class<E> clazz);

	/**
	 * Startup all systems
	 */
	public void startUp();

	/**
	 * Update the systems
	 */
	public void update();

	/**
	 * Shut down all systems
	 */
	public void shutdown();

}
