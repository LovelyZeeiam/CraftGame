package xueli.utils;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Actually I don't always remember I have something like this
 */
@Deprecated
public class Elegance {

	public static <T> T make(Supplier<T> s) {
		return s.get();
	}

	public static <T, R> T make(R r, Function<R, T> function) {
		return function.apply(r);
	}

	public static <T> void ifNull(T t, Consumer<T> ifNotNull) {
		if (t != null)
			ifNotNull.accept(t);
	}

	public static <T> boolean ifNull(T t, Function<T, Boolean> ifNotNull) {
		if (t == null)
			return false;
		return ifNotNull.apply(t);
	}

	public static <T, R> R nullOrFunc(T t, Function<T, R> ifNotNull) {
		if (t == null)
			return null;
		return ifNotNull.apply(t);
	}

	public static <T> T trueOrDefault(boolean b, Callable<T> ifTrue, T defaultValue) {
		if (b) {
			try {
				return ifTrue.call();
			} catch (Exception e) {
				e.printStackTrace();
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	public static <T> T trueOrDefault(boolean b, Callable<T> ifTrue, Callable<T> ifFalse) {
		if (b) {
			try {
				return ifTrue.call();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			try {
				return ifFalse.call();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

}
