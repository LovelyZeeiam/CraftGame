package xueli.utils.eval;

import xueli.game.Game;

import javax.script.ScriptException;

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
		if (Game.INSTANCE_GAME == null)
			return;
		if (!needEval)
			return;

		// Because of too many references of this expression, I choose not to change it
		// to a new format: ${variable_name}
		String newExpressionString = expression
				.replaceAll("win_width", Float.valueOf(Game.INSTANCE_GAME.getWidth()).toString())
				.replaceAll("win_height", Float.valueOf(Game.INSTANCE_GAME.getHeight()).toString())
				.replaceAll("scale", Float.valueOf(Game.INSTANCE_GAME.getDisplayScale()).toString());

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
		eval();
		return value;
	}

	public float getValue(float win_width, float win_height, float scale) {
		String newExpressionString = expression.replaceAll("win_width", Float.valueOf(win_width).toString())
				.replaceAll("win_height", Float.valueOf(win_height).toString())
				.replaceAll("scale", Float.valueOf(scale).toString());

		try {
			return Evaler.evalToFloat(newExpressionString);
		} catch (ScriptException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public String toString() {
		return "EvalableFloat{" + "expression='" + expression + '\'' + ", needEval=" + needEval + ", value=" + value
				+ '}';
	}
}
