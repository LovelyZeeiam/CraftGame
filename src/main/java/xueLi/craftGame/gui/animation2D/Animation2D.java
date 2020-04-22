package xueLi.craftGame.gui.animation2D;

import org.lwjgl.util.vector.Vector2f;

import xueLi.craftGame.utils.DisplayManager;

public class Animation2D {
	
	private static AnimationCallBack normalAnimationCallBack = new AnimationCallBack(){
		//线性插值间隔
		private Vector2f posOffset;
		private Vector2f sizeOffset;
		
		private long lastTime = 0;
		
		@Override
		public void initAnimation(Animation2D animation) {
			posOffset = new Vector2f((animation.pos2.x - animation.pos1.x) / animation.length,(animation.pos2.y - animation.pos1.y) / animation.length);
			sizeOffset = new Vector2f((animation.size2.x - animation.size1.x) / animation.length,(animation.size2.y - animation.size1.y) / animation.length);
		}
		
		@Override
		public void startAnimation(Animation2D animation) {
			lastTime = DisplayManager.currentTime;
		}
		
		@Override
		public void processAnimation(long offset, Animation2D animation) {
			long nowTime = DisplayManager.currentTime;
			long duration = nowTime - lastTime;
			animation.pos.x += posOffset.x * duration;
			animation.pos.y += posOffset.y * duration;
			animation.size.x += sizeOffset.x * duration;
			animation.size.y += sizeOffset.y * duration;
			lastTime = nowTime;
		}

		@Override
		public void stopAnimation(Animation2D animation) {
			
		}
	};

	Vector2f pos1;
	Vector2f size1;
	Vector2f pos2;
	Vector2f size2;

	public int length;
	private AnimationCallBack callback;

	public boolean started = false;
	public Vector2f pos, size;
	
	public Animation2D(Vector2f pos1, Vector2f size1, Vector2f pos2, Vector2f size2, int length) {
		this(pos1,size1,pos2,size2,length,normalAnimationCallBack);
	}

	public Animation2D(Vector2f pos1, Vector2f size1, Vector2f pos2, Vector2f size2, int length, AnimationCallBack callback) {
		this.pos1 = pos1;
		this.size1 = size1;
		this.pos2 = pos2;
		this.size2 = size2;
		this.callback = callback;
		this.length = length;
		callback.initAnimation(this);
	}
	
	private long startTime = 0;
	
	private Thread animationThread = new Thread(() -> {
		callback.startAnimation(this);
		long offset = DisplayManager.currentTime - startTime;
		while(started && offset < length) {
			callback.processAnimation(offset, this);
			offset = DisplayManager.currentTime - startTime;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		callback.stopAnimation(this);
		started = false;
		pos = null;
		size = null;
	});

	public void startAnimation() {
		pos = new Vector2f(pos1.x,pos1.y);
		size = new Vector2f(size1.x,size1.y);
		started = true;
		startTime = DisplayManager.currentTime;
		animationThread.start();
	}

	public void stopAnimation() {
		started = false;
		
	}

}
