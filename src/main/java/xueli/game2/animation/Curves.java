package xueli.game2.animation;

/**
 * All the internal curves. Actually this is a clone of "https://easings.net/",
 * so we have the same names
 */
public class Curves {
	
	public final static Curve linear = x -> x;
	
	public final static Curve easeInSine = x -> 1 - Math.cos((x * Math.PI) / 2);
	
	public final static Curve easeOutSine = x -> Math.sin((x * Math.PI) / 2);
	
	public final static Curve easeInOutSine = x -> -(Math.cos(Math.PI * x) - 1) / 2;
	
	public final static Curve easeInQuad = x -> x * x;
	
	public final static Curve easeOutQuad = x -> 1 - (1 - x) * (1 - x);
	
	public final static Curve easeInOutQuad = x -> x < 0.5 ? 2 * x * x : 1 - Math.pow(-2 * x + 2, 2) / 2;
	
	public final static Curve easeInCubic = x -> x * x * x;
	
	public final static Curve easeOutCubic = x -> 1 - Math.pow(1 - x, 3);
	
	public final static Curve easeInOutCubic = x -> x < 0.5 ? 4 * x * x * x : 1 - Math.pow(-2 * x + 2, 3) / 2;
	
	public final static Curve easeInQuart = x -> x * x * x * x;
	
	public final static Curve easeOutQuart = x -> 1 - Math.pow(1 - x, 4);
	
	public final static Curve easeInOutQuart = x -> x < 0.5 ? 8 * x * x * x * x : 1 - Math.pow(-2 * x + 2, 4) / 2;
	
	public final static Curve easeInQuint = x -> x * x * x * x * x;
	
	public final static Curve easeOutQuint = x -> 1 - Math.pow(1 - x, 5);
	
	public final static Curve easeInOutQuint = x -> x < 0.5 ? 16 * x * x * x * x * x : 1 - Math.pow(-2 * x + 2, 5) / 2;
	
	public final static Curve easeInExpo = x -> x == 0 ? 0 : Math.pow(2, 10 * x - 10);
	
	public final static Curve easeOutExpo = x -> x == 1 ? 1 : 1 - Math.pow(2, -10 * x);
	
	public final static Curve easeInOutExpo = x -> x == 0 ? 0 : x == 1 ? 1 : x < 0.5 ? Math.pow(2, 20 * x - 10) / 2 : (2 - Math.pow(2, -20 * x + 10)) / 2;
	
	public final static Curve easeInCirc = x -> 1 - Math.sqrt(1 - Math.pow(x, 2));
	
	public final static Curve easeOutCirc = x -> Math.sqrt(1 - Math.pow(x - 1, 2));
	
	public final static Curve easeInOutCirc = x -> x < 0.5 ? (1 - Math.sqrt(1 - Math.pow(2 * x, 2))) / 2 : (Math.sqrt(1 - Math.pow(-2 * x + 2, 2)) + 1) / 2;
	
	public final static Curve easeInBack = x -> {
		double c1 = 1.70158;
		double c3 = c1 + 1;
		return c3 * x * x * x - c1 * x * x;
	};
	
	public final static Curve easeOutBack = x -> {
		double c1 = 1.70158;
		double c3 = c1 + 1;
		return 1 + c3 * Math.pow(x - 1, 3) + c1 * Math.pow(x - 1, 2);
	};
	
	public final static Curve easeInOutBack = x -> {
		double c1 = 1.70158;
		double c2 = c1 * 1.525;
		return x < 0.5 ? (Math.pow(2 * x, 2) * ((c2 + 1) * 2 * x - c2)) / 2
				: (Math.pow(2 * x - 2, 2) * ((c2 + 1) * (x * 2 - 2) + c2) + 2) / 2;
	};
	
	public final static Curve easeInElastic = x -> {
		double c4 = (2 * Math.PI) / 3;
		return x == 0 ? 0 : x == 1 ? 1 : -Math.pow(2, 10 * x - 10) * Math.sin((x * 10 - 10.75) * c4);
	};
	
	public final static Curve easeOutElastic = x -> {
		double c4 = (2 * Math.PI) / 3;
		return x == 0 ? 0 : x == 1 ? 1 : Math.pow(2, -10 * x) * Math.sin((x * 10 - 0.75) * c4) + 1;
	};
	
	public final static Curve easeInOutElastic = x -> {
		double c5 = (2 * Math.PI) / 4.5;
		return x == 0 ? 0
				: x == 1 ? 1
						: x < 0.5 ? -(Math.pow(2, 20 * x - 10) * Math.sin((20 * x - 11.125) * c5)) / 2
								: (Math.pow(2, -20 * x + 10) * Math.sin((20 * x - 11.125) * c5)) / 2 + 1;
	};
	
	public final static Curve easeOutBounce = x -> {
		double n1 = 7.5625;
		double d1 = 2.75;
		if (x < 1 / d1) {
			return n1 * x * x;
		} else if (x < 2 / d1) {
			return n1 * (x -= 1.5 / d1) * x + 0.75;
		} else if (x < 2.5 / d1) {
			return n1 * (x -= 2.25 / d1) * x + 0.9375;
		} else {
			return n1 * (x -= 2.625 / d1) * x + 0.984375;
		}
	};
	
	public final static Curve easeInBounce = x -> 1 - easeOutBounce.getValue(1 - x);
	
	public final static Curve easeInOutBounce = x -> x < 0.5 ? (1 - easeOutBounce.getValue(1 - 2 * x)) / 2 : (1 + easeOutBounce.getValue(2 * x - 1)) / 2;

}
