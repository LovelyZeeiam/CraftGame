package xueli.utils.eval;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.script.ScriptException;

import xueli.game.Game;

public class EvalableFloat {

	private String expression;
	private boolean needEval = true;
	private float value = 0;

	private HashMap<String, EvalableFloat> variables;

	public EvalableFloat(String expressionString) {
		this.expression = expressionString;
		this.variables = new HashMap<>();
		eval();

	}

	public EvalableFloat(String expressionString, HashMap<String, EvalableFloat> variables) {
		this.expression = expressionString;
		this.variables = variables;
		eval();

	}

	@SafeVarargs
	public EvalableFloat(String expressionString, Map.Entry<String, EvalableFloat>... variables) {
		this.expression = expressionString;

		this.variables = new HashMap<>();
		for (int i = 0; i < variables.length; i++) {
			Map.Entry<String, EvalableFloat> entry = variables[i];
			this.variables.put(entry.getKey(), entry.getValue());

		}

		eval();

	}

	public void addVariables(HashMap<String, EvalableFloat> variables) {
		this.variables.putAll(variables);

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

		// Haven't been tested out
		HashMap<String, Float> varValues = new HashMap<>();
		variables.forEach((k, v) -> {
			varValues.put(k, v.getValue());
		});
		varValues.forEach((k, v) -> newExpressionString.replaceAll(MessageFormat.format("${{{0}}}", k),
				"(" + v.toString() + ")"));

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

	@Override
	public String toString() {
		return "EvalableFloat{" + "expression='" + expression + '\'' + ", needEval=" + needEval + ", value=" + value
				+ '}';
	}
}
