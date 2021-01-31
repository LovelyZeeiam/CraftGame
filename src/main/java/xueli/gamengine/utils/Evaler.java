package xueli.gamengine.utils;

import javax.script.ScriptException;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Evaler {

	public static float evalToFloat(String evalString) throws ScriptException {
		Context context = Context.enter();
		Scriptable scriptable = context.initStandardObjects();
		Object result = context.evaluateString(scriptable, evalString, "<cmd>", 1, null);
		Context.exit();

		if (result instanceof Integer)
			return ((Integer) result).floatValue();
		else
			return ((Double) result).floatValue();
	}

}
