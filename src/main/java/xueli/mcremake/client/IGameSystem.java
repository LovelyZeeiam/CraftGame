package xueli.mcremake.client;

public interface IGameSystem {

    default public void start(CraftGameClient ctx) {};
	
	default public void tick(CraftGameClient ctx) {};

	default public void update(CraftGameClient ctx) {};
	
	default public void release(CraftGameClient ctx) {};

}
