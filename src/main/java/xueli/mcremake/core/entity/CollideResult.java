package xueli.mcremake.core.entity;

public record CollideResult(int xCollide, int yCollide, int zCollide) {

	public static final int NO_COLLIDE = 0;
	public static final int MINUS_COLLIDE = 1;
	public static final int PLUS_COLLIDE = 2;

	public static int calcCollide(double result, double origin) {
		if (result == origin)
			return NO_COLLIDE;
		if (result < origin)
			return MINUS_COLLIDE;
		return PLUS_COLLIDE;
	}

}
