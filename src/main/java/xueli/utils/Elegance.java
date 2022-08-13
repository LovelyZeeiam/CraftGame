package xueli.utils;

import java.util.function.Function;
import java.util.function.Supplier;

public class Elegance {

	public static <T> T make(Supplier<T> s) {
		return s.get();
	}

	public static <T, R> T make(R r, Function<R, T> function) {
		return function.apply(r);
	}

}
