package xueli.game2.ecs;

public interface ECSSystem {
	
	public void start(ECSContext ctx);
	
	public void update(ECSContext ctx);
	
	public void shutdown(ECSContext ctx);
	
}
