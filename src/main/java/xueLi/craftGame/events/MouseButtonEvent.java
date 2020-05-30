package xueLi.craftGame.events;

public class MouseButtonEvent {
	
	public double x,y;
	public int button;
	public int action;
	
	public MouseButtonEvent(double x,double y,int button, int action) {
		this.x = x;
		this.y = y;
		this.button = button;
		this.action = action;
	}
	
	

}
