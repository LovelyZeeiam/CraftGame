package xueli.utils.eval;

import javax.script.ScriptException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

@Deprecated
public class Evaler {

	private static Context context;
	private static Scriptable scriptable;

	static {
		context = Context.enter();
		scriptable = context.initStandardObjects();

	}

	public static float evalToFloat(String evalString) throws ScriptException {
		Object result = context.evaluateString(scriptable, evalString, "<cmd>", 1, null);

		if (result instanceof Integer)
			return ((Integer) result).floatValue();
		else
			return ((Double) result).floatValue();
	}

}
