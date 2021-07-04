package xueli.utils;

import java.util.function.Function;

public class Asserts {

	public static <T> boolean assertNull(T t, Function<T, Boolean> ifNotNull) {
		if (t == null)
			return false;
		return ifNotNull.apply(t);
	}

}
