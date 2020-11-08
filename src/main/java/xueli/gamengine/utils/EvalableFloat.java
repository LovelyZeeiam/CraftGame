package xueli.gamengine.utils;

import javax.script.ScriptException;

public class EvalableFloat {

    public String expression;
    public float value = 0;

    public EvalableFloat(String expressionString) {
        this.expression = expressionString;
        eval();

    }

    public void eval() {
        String newExpressionString = expression
                .replaceAll("win_width", Float.valueOf(Display.currentDisplay.getWidth()).toString())
                .replaceAll("win_height", Float.valueOf(Display.currentDisplay.getHeight()).toString())
                .replaceAll("scale", Float.valueOf(Display.currentDisplay.getScale()).toString());
        try {
            this.value = Evaler.evalToFloat(newExpressionString);
        } catch (ScriptException e) {
            e.printStackTrace();
        }

    }

}
