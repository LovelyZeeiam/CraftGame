package xueli.utils;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class Asserts {

	public static <T> void assertNullVoid(T t, Consumer<T> ifNotNull) {
		if (t != null)
			ifNotNull.accept(t);
	}

	public static <T> boolean assertNull(T t, Function<T, Boolean> ifNotNull) {
		if (t == null)
			return false;
		return ifNotNull.apply(t);
	}

	public static <T, R> R assertNullAndMethod(T t, Function<T, R> ifNotNull) {
		if (t == null)
			return null;
		return ifNotNull.apply(t);
	}

	public static <T> T assertBooleanAndMethod(boolean b, Callable<T> ifTrue, T defaultValue) {
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

	public static <T> T assertBooleanAndMethod(boolean b, Callable<T> ifTrue, Callable<T> ifFalse) {
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
