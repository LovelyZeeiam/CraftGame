package xueLi.gamengine.utils;

import javax.script.ScriptException;

import xueLi.gamengine.resource.Evaler;

public class EvalableFloat {

	public String expression;
	public float value = 0;

	public EvalableFloat(String expressionString) {
		this.expression = expressionString;
		eval();

	}

	public void eval() {
		String newExpressionString = expression
				.replaceAll("win_width", new Float(Display.currentDisplay.getWidth()).toString())
				.replaceAll("win_height", new Float(Display.currentDisplay.getHeight()).toString())
				.replaceAll("scale", new Float(Display.currentDisplay.getScale()).toString());
		try {
			this.value = Evaler.evalToFloat(newExpressionString);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

	}

}
