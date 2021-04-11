package xueli.game.renderer.widgets;

import java.text.MessageFormat;

import xueli.utils.eval.EvalableFloat;

public class ButtonGroup extends IWidget {

	private EvalableFloat margin;

	private Button[][] grid;

	public ButtonGroup(EvalableFloat x, EvalableFloat y, EvalableFloat width, EvalableFloat height,
			EvalableFloat margin, String[][] grid, String fontName, EvalableFloat fontSize) {
		super(x, y, width, height);
		this.margin = margin;

		calculateButtonPosition(grid, fontName, fontSize);

	}

	private void calculateButtonPosition(String[][] texts, String fontName, EvalableFloat fontSize) {
		int y_count = texts.length;
		this.grid = new Button[texts.length][];
		for (int y = 0; y < texts.length; y++) {
			String[] strings = texts[y];

			int x_count = strings.length;
			this.grid[y] = new Button[x_count];

			/**
			 * Per Block Height: perHeight * n + margin * (n - 1) = height perHeight =
			 * (height - margin * (n - 1)) / n
			 */
			EvalableFloat perHeight = new EvalableFloat(MessageFormat.format("(({0}) - ({1}) * (({2}) - 1)) / ({2})",
					height.getExpression(), margin.getExpression(), y_count));
			EvalableFloat perWidth = new EvalableFloat(MessageFormat.format("(({0}) - ({1}) * (({2}) - 1)) / ({2})",
					width.getExpression(), margin.getExpression(), x_count));

			EvalableFloat y_pointer = new EvalableFloat(
					MessageFormat.format("({0}) + ({1}) * ({2}) + (({1}) - 1) * ({3})", this.y.getExpression(), y,
							perHeight.getExpression(), margin.getExpression()));

			for (int x = 0; x < x_count; x++) {
				String string = strings[x];

				EvalableFloat x_pointer = new EvalableFloat(
						MessageFormat.format("({0}) + ({1}) * ({2}) + (({1}) - 1) * ({3})", this.x.getExpression(), x,
								perWidth.getExpression(), margin.getExpression()));

				this.grid[y][x] = new Button(x_pointer, y_pointer, perWidth, perHeight, string, fontSize, false);

			}

		}

	}

	public void stroke(long nvg, String fontName) {
		for (Button[] buttons : grid) {
			for (Button button : buttons) {
				if (button != null) {
					button.stroke(nvg, fontName);

				}

			}
		}

	}

	@Override
	public void update() {
		for (Button[] buttons : grid) {
			for (Button button : buttons) {
				if (button != null) {
					button.update();

				}

			}
		}

	}

	public void size() {
		super.size();

		margin.needEvalAgain();

		for (Button[] buttons : grid) {
			for (Button button : buttons) {
				button.size();

			}
		}

	}

	public Button getButton(int x, int y) {
		return grid[x][y];
	}
}
