package xueli.gamengine.utils.evalable;

import javax.script.ScriptException;

import xueli.gamengine.utils.Display;

public class EvalableFloat {

	private String expression;
	private boolean needEval = true;
	private float value = 0;

	public EvalableFloat(String expressionString) {
		this.expression = expressionString;
		eval();

	}

	public void needEvalAgain() {
		needEval = true;

	}

	private void eval() {
		if (!needEval)
			return;

		String newExpressionString = expression
				.replaceAll("win_width", Float.valueOf(Display.currentDisplay.getWidth()).toString())
				.replaceAll("win_height", Float.valueOf(Display.currentDisplay.getHeight()).toString())
				.replaceAll("scale", Float.valueOf(Display.currentDisplay.getScale()).toString());
		try {
			this.value = Evaler.evalToFloat(newExpressionString);
		} catch (ScriptException e) {
			e.printStackTrace();
		}

		needEval = false;

	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
		needEval = true;
	}

	public float getValue() {
		if (needEval)
			eval();
		return value;
	}

	@Override
	public String toString() {
		return "EvalableFloat{" + "expression='" + expression + '\'' + ", needEval=" + needEval + ", value=" + value
				+ '}';
	}
}
