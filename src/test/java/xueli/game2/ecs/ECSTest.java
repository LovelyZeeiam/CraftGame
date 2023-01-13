package xueli.game2.ecs;

import org.lwjgl.utils.vector.Vector2d;
import org.lwjgl.utils.vector.Vector3f;

public class ECSTest {
	
	private final ECSContext ctx = new ECSContextImpl();
	
	ECSTest() {
	}
	
	public void run() {
		ctx.addSystem(new TestSystem());
		ctx.addResource(new Vector2d(0.2333, 0.23333));
		
		ctx.startUp();
		ctx.update();
		ctx.shutdown();
		
	}
	
	private class TestSystem implements ECSSystem {
		
		private long entity;
		
		@Override
		public void start(ECSContext ctx) {
			this.entity = ctx.spawn();
			ctx.addComponent(entity, new Vector3f(2, 3, 3));
			
		}

		@Override
		public void update(ECSContext ctx) {
			Vector3f v = ctx.getComponent(entity, Vector3f.class);
			v.y += 1;
			System.out.println(v);
			
			Vector2d v2 = ctx.getResource(Vector2d.class);
			v2.x += 1;
			System.out.println(v2);
			
		}

		@Override
		public void shutdown(ECSContext ctx) {
			ctx.removeComponent(entity, Vector3f.class);
			
		}
		
	}
	
	public static void main(String[] args) {
		new ECSTest().run();
	}
	
}
