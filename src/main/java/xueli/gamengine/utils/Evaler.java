package xueli.gamengine.utils;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

public class Evaler {

    public static float evalToFloat(String evalString) throws ScriptException {
        Context context = Context.enter();
        Scriptable scriptable = context.initStandardObjects();
        Object result = context.evaluateString(scriptable, evalString, "<cmd>", 1, null);
        Context.exit();

        if(result instanceof Integer)
            return ((Integer) result).floatValue();
        else
            return ((Double) result).floatValue();
    }

}
