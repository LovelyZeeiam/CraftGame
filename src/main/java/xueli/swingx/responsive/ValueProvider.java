package xueli.swingx.responsive;

import java.awt.Component;
import java.awt.Dimension;

public interface ValueProvider<T, K> {
	
	public K get(T parent);
	
	public static ValueProvider<Double, Double> parseDouble(String str) {
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
	
	public static ValueProvider<Integer, Integer> parseInteger(String str) {
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
	
	public static ValueProvider<Dimension, Double> vminForDouble(double percentage, Component reference) {
		if(reference == null)
			return d -> percentage * Math.min(d.width, d.height) / 100.0;
		else
			return d -> {
				Dimension referenceSize = reference.getSize();
				return percentage * Math.min(referenceSize.width, referenceSize.height) / 100.0;
			};
	}
	
	public static ValueProvider<Dimension, Double> vmaxForDouble(double percentage, Component reference) {
		if(reference == null)
			return d -> percentage * Math.max(d.width, d.height) / 100.0;
		else
			return d -> {
				Dimension referenceSize = reference.getSize();
				return percentage * Math.max(referenceSize.width, referenceSize.height) / 100.0;
			};
	}
	
	// The ratio should be the result of width / height
	public static ValueProvider<Dimension, Dimension> newProviderRatioHeight(ValueProvider<Integer, Integer> heightProvider, double ratio) {
		return d -> {
			int height = heightProvider.get(d.height);
			int width = (int) (height * ratio);
			return new Dimension(width, height);
		};
	}

	// The ratio should be the result of width / height
	public static ValueProvider<Dimension, Dimension> newProviderRatioWidth(ValueProvider<Integer, Integer> widthProvider, double ratio) {
		return d -> {
			int width = widthProvider.get(d.height);
			int height = (int) (width / ratio);
			return new Dimension(width, height);
		};
	}

	// The ratio should be the result of width / height
	public static ValueProvider<Dimension, Dimension> newProviderRatioVMin(ValueProvider<Integer, Integer> minProvider, double ratio) {
		return d -> {
			int childWidth, childHeight;
			if(d.width >= d.height) {
				childHeight = minProvider.get(d.height);
				childWidth = (int) (childHeight * ratio);
			} else {
				childWidth = minProvider.get(d.width);
				childHeight = (int) (childWidth / ratio);
			}
			return new Dimension(childWidth, childHeight);
		};
	}

	// The ratio should be the result of width / height
	public static ValueProvider<Dimension, Dimension> newProviderRatioVMax(ValueProvider<Integer, Integer> maxProvider, double ratio) {
		return d -> {
			int childWidth, childHeight;
			if(d.width <= d.height) {
				childHeight = maxProvider.get(d.height);
				childWidth = (int) (childHeight * ratio);
			} else {
				childWidth = maxProvider.get(d.width);
				childHeight = (int) (childWidth / ratio);
			}
			return new Dimension(childWidth, childHeight);
		};
	}
	
}
