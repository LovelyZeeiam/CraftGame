package xueli.utils.collection;

import java.util.Collection;

public class CollectionHelper {
	
	public static String toString(int[][] array) {
		StringBuilder str = new StringBuilder();
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[i].length; j++) {
				str.append(array[i][j]).append(" ");
			}
			str.append(System.lineSeparator());
		}
		return str.toString();
	}
	
	public static String toString(Collection<?> c) {
		return toString(c, 0);
	}

	private static String toString(Collection<?> c, int tabLevel) {
		StringBuilder b = new StringBuilder();

		b.append(c.getClass().getName()).append(": ").append(c.size()).append(" {");
		for (Object object : c) {
			b.append(System.lineSeparator());

			for (int i = 0; i <= tabLevel; i++)
				b.append("\t");

			if (object instanceof Collection<?>) {
				b.append(toString((Collection<?>) object, tabLevel + 1));
			} else
				b.append(object.toString());
		}
		b.append(System.lineSeparator());
		for (int i = 0; i < tabLevel; i++)
			b.append("\t");
		b.append("}");

		return b.toString();

	}

}
