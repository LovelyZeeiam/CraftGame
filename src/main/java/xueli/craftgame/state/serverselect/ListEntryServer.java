package xueli.craftgame.state.serverselect;

import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_BOTTOM;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_LEFT;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_MIDDLE;
import static org.lwjgl.nanovg.NanoVG.NVG_ALIGN_TOP;
import static org.lwjgl.nanovg.NanoVG.nvgBeginPath;
import static org.lwjgl.nanovg.NanoVG.nvgFill;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgRGBAf;
import static org.lwjgl.nanovg.NanoVG.nvgRect;
import static org.lwjgl.nanovg.NanoVG.nvgText;
import static org.lwjgl.nanovg.NanoVG.nvgTextAlign;

import org.lwjgl.nanovg.NVGColor;

import xueli.game.Game;
import xueli.game.renderer.widgets.IListEntry;
import xueli.game.utils.NVGColors;

public class ListEntryServer implements IListEntry {

	private static float border_width = 2.0f;
	private static float margin = 5.0f;

	private static NVGColor background = NVGColor.create();
	private static NVGColor chosen_background = NVGColor.create();
	private static NVGColor hovered_background = NVGColor.create();

	private static NVGColor entry_status_text_color = NVGColor.create();
	private static NVGColor address_text_color = NVGColor.create();

	static {
		nvgRGBAf(0.4f, 0.4f, 0.4f, 0.1f, background);
		nvgRGBAf(0.5f, 0.5f, 0.5f, 0.2f, hovered_background);
		nvgRGBAf(0.1f, 0.1f, 0.1f, 1.0f, chosen_background);

		nvgRGBAf(0.5f, 0.5f, 0.5f, 1.0f, entry_status_text_color);

		nvgRGBAf(0.33f, 0.33f, 0.33f, 0.8f, address_text_color);

	}

	private String name;
	private String address;
	private int port;

	public ListEntryServer(String name, String address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;

	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void stroke(long nvg, int element_count, float entry_width, float entry_height, String fontName,
			boolean hover, boolean chosen) {
		float scale = Game.INSTANCE_GAME.getDisplayScale();

		if (chosen) {
			nvgBeginPath(nvg);
			nvgRect(nvg, 0, 0, entry_width, entry_height);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgFill(nvg);

			nvgBeginPath(nvg);
			nvgRect(nvg, border_width * scale, border_width * scale, entry_width - 2 * border_width * scale,
					entry_height - 2 * border_width * scale);
			nvgFillColor(nvg, chosen_background);
			nvgFill(nvg);

		} else {
			nvgBeginPath(nvg);
			nvgRect(nvg, 0, 0, entry_width, entry_height);
			if (hover)
				nvgFillColor(nvg, hovered_background);
			else
				nvgFillColor(nvg, background);
			nvgFill(nvg);

		}

		// Server Name
		{
			nvgFontSize(nvg, entry_height * 0.26f);
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_TOP);
			nvgFillColor(nvg, NVGColors.WHITE);
			nvgText(nvg, margin * scale, margin * scale, name);

		}

		// Server Status
		{
			nvgFontSize(nvg, entry_height * 0.26f);
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_MIDDLE);
			nvgFillColor(nvg, entry_status_text_color);
			nvgText(nvg, margin * scale, entry_height / 2, name);

		}

		// Ip Addr
		{
			nvgFontSize(nvg, entry_height * 0.26f);
			nvgFontFace(nvg, fontName);
			nvgTextAlign(nvg, NVG_ALIGN_LEFT | NVG_ALIGN_BOTTOM);
			nvgFillColor(nvg, address_text_color);
			nvgText(nvg, margin * scale, entry_height - margin * scale, address + ":" + port);

		}

	}

}
