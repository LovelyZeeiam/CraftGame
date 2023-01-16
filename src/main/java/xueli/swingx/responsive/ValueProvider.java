package xueli.swingx.responsive;

public interface ValueProvider<T> {
	
	public T get(T parent);
	
	public static ValueProvider<Double> parseDouble(String str) {
		try {
			Double val = Double.parseDouble(str);
			return p -> val;
		} catch (NumberFormatException e) {
		}
		
		if(str.endsWith("px")) {
			double value = Double.valueOf(str.substring(0, str.length() - 2));
			return w -> value;
		} else if(str.endsWith("%")) {
			double value = Double.valueOf(str.substring(0, str.length() - 1));
			return w -> w * value / 100.0;
		}
		
		throw new NumberFormatException();
	}
	
	public static ValueProvider<Integer> parseInteger(String str) {
		try {
			Integer val = Integer.parseInt(str);
			return p -> val;
		} catch (NumberFormatException e) {
		}
		
		if(str.endsWith("px")) {
			Integer value = Integer.valueOf(str.substring(0, str.length() - 2));
			return w -> value;
		} else if(str.endsWith("%")) {
			double value = Double.valueOf(str.substring(0, str.length() - 1));
			return w -> (int) (w * value / 100.0);
		}
		
		throw new NumberFormatException();
		
	}
	
}
