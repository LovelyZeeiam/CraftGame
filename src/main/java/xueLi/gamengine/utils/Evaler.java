package xueLi.gamengine.utils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Evaler {

    private static ScriptEngineManager manager;
    private static ScriptEngine jsScriptEngine;

    static {
        manager = new ScriptEngineManager();
        jsScriptEngine = manager.getEngineByName("js");
        jsScriptEngine.put("console", System.out);

    }

    public static float evalToFloat(String evalString) throws ScriptException {
        Double value = (double) jsScriptEngine.eval(evalString);
        return value.floatValue();
    }

}
