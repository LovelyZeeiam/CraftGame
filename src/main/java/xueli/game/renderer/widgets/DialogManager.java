package xueli.game.renderer.widgets;

import static org.lwjgl.nanovg.NanoVG.nvgResetTransform;
import static org.lwjgl.nanovg.NanoVG.nvgTranslate;

import java.util.LinkedList;

import xueli.game.Game;
import xueli.game.utils.Time;

public class DialogManager {

	public static final Object FORCE_EXIT = "FORCE";

	private static final long dialog_fade_in_out_time = 300;

	private LinkedList<DialogLife> dialogs = new LinkedList<>();
	private LinkedList<DialogExitData> exitDatas = new LinkedList<>();

	private DialogLife currentDialog;

	public DialogManager() {

	}

	public void dialog(Dialog dialog) {
		if (dialog == null && this.hasDialog()) {
			handleDialogExit(FORCE_EXIT);
			return;
		}
		dialog.size();
		dialogs.add(new DialogLife(dialog));

	}

	public void handleDialogExit(Object exitData) {
		currentDialog.state = DialogState.FADE_OUT;

	}

	public void stroke(long nvg, String fontName) {
		if (currentDialog == null && !dialogs.isEmpty())
			currentDialog = dialogs.poll();

		if (this.currentDialog != null) {
			float allOffset = (Game.INSTANCE_GAME.getHeight() + this.currentDialog.dialog.getHeight().getValue()) / 2;
			float progress = (float) Math
					.sin(((double) this.currentDialog.life / dialog_fade_in_out_time) * (Math.PI / 2));
			nvgTranslate(nvg, 0, allOffset * (1 - progress));

			this.currentDialog.dialog.stroke(nvg, fontName);

			nvgResetTransform(nvg);

		}

	}

	public void update() {
		if (this.currentDialog != null) {
			this.currentDialog.dialog.update();

			switch (currentDialog.state) {
			case FADE_IN:
				currentDialog.life += Time.deltaTime;
				if (currentDialog.life > dialog_fade_in_out_time) {
					currentDialog.life = dialog_fade_in_out_time;
					currentDialog.state = DialogState.STATIC;
				}
				break;
			case FADE_OUT:
				currentDialog.life -= Time.deltaTime;
				if (currentDialog.life <= 0) {
					currentDialog = null;
				}
				break;
			default:
				break;
			}

		}

	}

	public void size() {
		if (this.currentDialog != null)
			this.currentDialog.dialog.size();

	}

	public boolean hasDialog() {
		return this.currentDialog != null;
	}

	public DialogExitData pollExitData() {
		return this.exitDatas.poll();
	}

	public static class DialogExitData {

		private String handleSignature;
		private Object exitData;

		public DialogExitData(String handleSignature, Object exitData) {
			this.handleSignature = handleSignature;
			this.exitData = exitData;
		}

		public String getHandleSignature() {
			return handleSignature;
		}

		public Object getExitData() {
			return exitData;
		}

	}

	private static class DialogLife {

		public Dialog dialog;

		public long life = 0;
		public DialogState state;

		public DialogLife(Dialog dialog) {
			this.dialog = dialog;
			this.state = DialogState.FADE_IN;

		}

	}

	private static enum DialogState {
		FADE_IN, STATIC, FADE_OUT
	}

}
