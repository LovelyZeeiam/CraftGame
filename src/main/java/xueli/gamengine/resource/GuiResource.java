package xueli.gamengine.resource;

import com.google.gson.*;
import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import xueli.gamengine.utils.IOUtils;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.evalable.EvalableFloat;
import xueli.gamengine.view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GuiResource extends IResource {

	public HashMap<String, View> guisHashMap = new HashMap<String, View>();
	private TextureManager textureManager;
	private String real_path;

	public GuiResource(String pathString, TextureManager textureManager) {
		super(pathString);
		this.textureManager = textureManager;

		this.real_path = pathString + "gui/";

	}

	public static int getAlign(JsonArray alignElement) {
		JsonArray alignArray = alignElement.getAsJsonArray();
		int align = 0;
		for (JsonElement each : alignArray) {
			switch (each.getAsString()) {
			case "right":
				align |= NanoVG.NVG_ALIGN_RIGHT;
				break;
			case "top":
				align |= NanoVG.NVG_ALIGN_TOP;
				break;
			case "bottom":
				align |= NanoVG.NVG_ALIGN_BOTTOM;
				break;
			case "center":
				align |= NanoVG.NVG_ALIGN_CENTER;
				break;
			case "middle":
				align |= NanoVG.NVG_ALIGN_MIDDLE;
				break;
			default: // include when "left"
				align |= NanoVG.NVG_ALIGN_LEFT;
				break;
			}
		}
		return align;
	}

	public static byte[] loadColorToByteArray(JsonArray color) {
		byte[] array = new byte[color.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = color.get(i).getAsByte();
		}
		return array;
	}

	public static NVGColor loadColor(JsonArray colorArray) {
		NVGColor backgroundColor = NVGColor.create();
		if (colorArray.size() == 4)
			NanoVG.nvgRGBA(colorArray.get(0).getAsByte(), colorArray.get(1).getAsByte(), colorArray.get(2).getAsByte(),
					colorArray.get(3).getAsByte(), backgroundColor);
		else if (colorArray.size() == 3)
			NanoVG.nvgRGB(colorArray.get(0).getAsByte(), colorArray.get(1).getAsByte(), colorArray.get(2).getAsByte(),
					backgroundColor);
		else
			return null;
		return backgroundColor;
	}

	public void loadGui(LangManager langManager) {
		ArrayList<File> guiFiles = findAllFiles(new File(real_path));
		guiFiles.forEach(file -> loadGui(file.getName(), langManager, false));
	}

	public void loadGui(LangManager langManager, GUIProgressBar progressBar, float startValue, float endValue) {
		ArrayList<File> guiFiles = findAllFiles(new File(real_path));

		int count = 0;
		float progressPerElement = (endValue - startValue) / guiFiles.size();

		for (File f : guiFiles) {
			loadGui(f.getName(), langManager, false);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

		}

		progressBar.setProgress(endValue);

	}

	public void loadGui(LangManager langManager, GUITextView textView, GUIProgressBar progressBar, float startValue,
			float endValue) {
		ArrayList<File> guiFiles = findAllFiles(new File(real_path));

		int count = 0;
		float progressPerElement = (endValue - startValue) / guiFiles.size();

		String loading_textString = textView.getText();

		for (File f : guiFiles) {
			loadGui(f.getName(), langManager, false);

			++count;
			progressBar.setProgress(startValue + progressPerElement * count);

			textView.setText(loading_textString + " - " + f.getName());

		}

		progressBar.setProgress(endValue);

	}

	public View loadGui(String filename, LangManager langManager, boolean reloadEnable) {
		if (guisHashMap.containsKey(filename))
			if (!reloadEnable)
				return guisHashMap.get(filename);
		JsonObject jsonObject = null;
		try {
			jsonObject = gson.fromJson(new FileReader(real_path + filename), JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			Logger.error("Read gui error: " + e.getMessage());
			return null;
		}
		JsonElement subtitleElement = jsonObject.get("subtitle");
		String subtitleString = subtitleElement.isJsonNull() ? null : subtitleElement.getAsString();
		View gui = new View(subtitleString);
		// 本UI的背景
		JsonObject backgroundObj = null;
		try {
			backgroundObj = jsonObject.get("background").getAsJsonObject();
		} catch (NullPointerException e) {
			Logger.error("[GUI] Couldn't find param in " + filename + ": background");
			return null;
		}
		if (backgroundObj.has("color")) {
			JsonArray colorArray = backgroundObj.getAsJsonArray("color");
			NVGColor backColor = loadColor(colorArray);
			gui.background = new GUIBackground(backColor);
		} else if (backgroundObj.has("image")) {
			int textureID = textureManager.getTexture(backgroundObj.get("image").getAsString()).id;
			JsonArray imageParam = backgroundObj.getAsJsonArray("scale");
			gui.background = new GUIBackground(textureID, imageParam.get(0).getAsInt(), imageParam.get(1).getAsInt(),
					imageParam.get(2).getAsInt(), imageParam.get(3).getAsInt());
		} else {
			Logger.error("[GUI] Couldn't find param of background in " + filename);
			return null;
		}
		// 控件们
		JsonObject guiwidgetsJsonObject = null;
		try {
			guiwidgetsJsonObject = jsonObject.get("widgets").getAsJsonObject();
		} catch (NullPointerException e) {
			Logger.error("[GUI] Couldn't find param in " + filename + ": widgets");
			return null;
		}
		for (Entry<String, JsonElement> e : guiwidgetsJsonObject.entrySet()) {
			String nameString = e.getKey();
			JsonObject widgetJsonObject = e.getValue().getAsJsonObject();
			// 控件位置
			JsonArray widgetPosJsonArray = null;
			try {
				widgetPosJsonArray = widgetJsonObject.get("pos").getAsJsonArray();
			} catch (NullPointerException e2) {
				Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": pos");
				continue;
			}
			EvalableFloat widgetPosX = new EvalableFloat(widgetPosJsonArray.get(0).getAsString());
			EvalableFloat widgetPosY = new EvalableFloat(widgetPosJsonArray.get(1).getAsString());
			// 控件大小
			JsonArray widgetSizeJsonArray = null;
			try {
				widgetSizeJsonArray = widgetJsonObject.get("size").getAsJsonArray();
			} catch (NullPointerException e2) {
				Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": size");
				continue;
			}
			EvalableFloat widgetWidth = new EvalableFloat(widgetSizeJsonArray.get(0).getAsString());
			EvalableFloat widgetHeight = new EvalableFloat(widgetSizeJsonArray.get(1).getAsString());
			// 边框
			JsonElement borderJsonElement = widgetJsonObject.get("border");
			NVGColor borderColor = null;
			int borderWidth = 0;
			boolean borderFlag = false;
			if (borderJsonElement != null) {
				JsonObject borderJsonObject = borderJsonElement.getAsJsonObject();
				JsonArray borderColorArray = borderJsonObject.get("color").getAsJsonArray();
				borderColor = loadColor(borderColorArray);
				borderWidth = borderJsonObject.get("width").getAsInt();
				borderFlag = true;

			}
			// 控件类型
			String widgetTypeString = null;
			try {
				widgetTypeString = widgetJsonObject.get("type").getAsString();
			} catch (NullPointerException e2) {
				Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": type");
				continue;
			}
			switch (widgetTypeString) {
			case "image_view":
				String textureString = null;
				try {
					textureString = widgetJsonObject.get("texture").getAsString();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": texture");
					continue;
				}

				int textureID = textureManager.getTexture(textureString).id;
				GUIImageView imageView;

				if (borderFlag)
					imageView = new GUIImageView(widgetPosX, widgetPosY, widgetWidth, widgetHeight, textureID,
							borderColor, borderWidth);
				else
					imageView = new GUIImageView(widgetPosX, widgetPosY, widgetWidth, widgetHeight, textureID);
				gui.widgets.put(nameString, imageView);

				break;
			case "button":
				JsonElement label = widgetJsonObject.get("text");
				String labelString = null;
				EvalableFloat textSize = null;
				NVGColor textColor = null;
				if (label != null) {
					labelString = langManager.getStringFromLangMap(label.getAsString());
					textSize = new EvalableFloat(widgetJsonObject.get("text_size").getAsString());
					textColor = loadColor(widgetJsonObject.getAsJsonArray("text_color"));

				}

				// 按钮的选中框
				JsonElement chosenBorderJsonElement = widgetJsonObject.get("outline");
				if (chosenBorderJsonElement == null) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": outline");
					continue;
				}
				JsonObject chosenBorderJsonObject = chosenBorderJsonElement.getAsJsonObject();

				if (!chosenBorderJsonObject.has("color") || !chosenBorderJsonObject.get("color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in 'outline' in " + nameString + " in " + filename
							+ ": color");
					continue;
				}
				if (!chosenBorderJsonObject.has("width")) {
					Logger.error(
							"[GUI] Couldn't find param in 'outline' in " + nameString + " in " + filename + ": width");
					continue;
				}

				NVGColor chosenBorderColor = loadColor(chosenBorderJsonObject.get("color").getAsJsonArray());
				int chosenBorderWidth = chosenBorderJsonObject.get("width").getAsInt();
				NVGColor chosenTextColor = loadColor(chosenBorderJsonObject.get("text_color").getAsJsonArray());

				GUIButton button = new GUIButton(widgetPosX, widgetPosY, widgetWidth, widgetHeight, labelString,
						textSize, textColor, chosenBorderColor, chosenBorderWidth, chosenTextColor);
				gui.widgets.put(nameString, button);

				break;
			case "progress_bar":
				float start_progress = widgetJsonObject.has("start_progress")
						? widgetJsonObject.get("start_progress").getAsFloat()
						: 0.0f;
				// back_color
				JsonArray backColorArray = null;
				try {
					backColorArray = widgetJsonObject.get("back_color").getAsJsonArray();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": back_color");
					continue;
				}
				NVGColor backColor = loadColor(backColorArray);
				// progress_color
				JsonArray progressColorArray = null;
				try {
					progressColorArray = widgetJsonObject.get("progress_color").getAsJsonArray();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": progress_color");
					continue;
				}
				NVGColor progressColor = loadColor(progressColorArray);

				GUIProgressBar progressBar = new GUIProgressBar(widgetPosX, widgetPosY, widgetWidth, widgetHeight,
						start_progress, backColor, progressColor);

				if (widgetJsonObject.has("progress_margin")) {
					progressBar.setProgressBarWidth(widgetJsonObject.get("progress_margin").getAsInt());
				}

				gui.widgets.put(nameString, progressBar);

				break;
			case "text_view":
				// text_size
				String textSizeString = null;
				try {
					textSizeString = widgetJsonObject.get("text_size").getAsString();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text_size");
					continue;
				}
				EvalableFloat textSize1 = new EvalableFloat(textSizeString);
				// text_color
				JsonArray textColorJsonArray = null;
				try {
					textColorJsonArray = widgetJsonObject.get("text_color").getAsJsonArray();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text_color");
					continue;
				}
				NVGColor textColor1 = loadColor(textColorJsonArray);

				// text
				String textString = null;
				String[] randomTexts = null;
				boolean isRandomText = false;
				try {
					textString = widgetJsonObject.get("text").getAsString();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text");
					continue;
				}
				if (textString.startsWith("@")) {
					isRandomText = true;

					String textsPath = textString.substring(1);
					try {
						randomTexts = IOUtils.readLines(getPathString() + "text/" + textsPath);
					} catch (IOException e1) {
						Logger.error("[GUI] texts '" + nameString + "' path of a random textview exception: "
								+ e1.getLocalizedMessage());
						continue;
					}

				} else
					textString = langManager.getStringFromLangMap(textString);

				// align
				JsonElement alignElement = widgetJsonObject.get("align");
				int align = alignElement == null ? NanoVG.NVG_ALIGN_LEFT : getAlign(alignElement.getAsJsonArray());

				if (!isRandomText) {
					GUITextView textView = new GUITextView(widgetPosX, widgetPosY, widgetWidth, widgetHeight, textSize1,
							textColor1, align);
					textView.setText(textString);
					gui.widgets.put(nameString, textView);
				} else {
					GUIRandomTextView randomTextView = new GUIRandomTextView(widgetPosX, widgetPosY, widgetWidth,
							widgetHeight, textSize1, textColor1, align);
					randomTextView.setText(randomTexts);
					gui.widgets.put(nameString, randomTextView);
				}

				break;
			case "scroll_bar":
				// TODO: 将参数与Options的json文件勾连起来

				if (!widgetJsonObject.has("default_value")) {
					Logger.error(
							"[GUI] Couldn't find float param in " + nameString + " in " + filename + ": default_value");
					continue;
				}
				float defaultValue = widgetJsonObject.get("default_value").getAsFloat();

				if (!widgetJsonObject.has("background_color")
						|| !widgetJsonObject.get("background_color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in " + nameString + " in " + filename
							+ ": background_color");
					continue;
				}
				NVGColor background_color = loadColor(widgetJsonObject.getAsJsonArray("background_color"));

				if (!widgetJsonObject.has("scrollbar_color")
						|| !widgetJsonObject.get("scrollbar_color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in " + nameString + " in " + filename
							+ ": scrollbar_color");
					continue;
				}
				NVGColor scrollbar_color = loadColor(widgetJsonObject.getAsJsonArray("scrollbar_color"));

				JsonElement outlineElement = widgetJsonObject.get("outline");
				if (outlineElement == null) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": outline");
					continue;
				}
				JsonObject outlineObject = outlineElement.getAsJsonObject();

				if (!outlineObject.has("color") || !outlineObject.get("color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in 'outline' in " + nameString + " in " + filename
							+ ": color");
					continue;
				}
				if (!outlineObject.has("width")) {
					Logger.error(
							"[GUI] Couldn't find param in 'outline' in " + nameString + " in " + filename + ": width");
					continue;
				}

				NVGColor outlineColor = loadColor(outlineObject.get("color").getAsJsonArray());
				int outlineWidth = outlineObject.get("width").getAsInt();

				GUIScrollBar scrollBar = new GUIScrollBar(widgetPosX, widgetPosY, widgetWidth, widgetHeight,
						defaultValue, background_color, scrollbar_color, outlineColor, outlineWidth);
				gui.widgets.put(nameString, scrollBar);

				break;
			case "textbox":
				JsonElement outlineElement1 = widgetJsonObject.get("outline");
				if (outlineElement1 == null) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": outline");
					continue;
				}
				JsonObject outlineObject1 = outlineElement1.getAsJsonObject();

				if (!outlineObject1.has("color") || !outlineObject1.get("color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in 'outline' in " + nameString + " in " + filename
							+ ": color");
					continue;
				}
				if (!outlineObject1.has("width")) {
					Logger.error(
							"[GUI] Couldn't find param in 'outline' in " + nameString + " in " + filename + ": width");
					continue;
				}

				NVGColor outlineColor1 = loadColor(outlineObject1.get("color").getAsJsonArray());
				int outlineWidth1 = outlineObject1.get("width").getAsInt();

				if (!widgetJsonObject.has("background_color")
						|| !widgetJsonObject.get("background_color").isJsonArray()) {
					Logger.error("[GUI] Couldn't find array param in " + nameString + " in " + filename
							+ ": background_color");
					continue;
				}
				NVGColor background_color1 = loadColor(widgetJsonObject.getAsJsonArray("background_color"));

				String hint = null;
				JsonElement hintElement = widgetJsonObject.get("hint");
				if (hintElement != null) {
					hint = hintElement.getAsString();

				}

				// text_size
				textSizeString = null;
				try {
					textSizeString = widgetJsonObject.get("text_size").getAsString();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text_size");
					continue;
				}
				textSize1 = new EvalableFloat(textSizeString);
				// text_color
				textColorJsonArray = null;
				try {
					textColorJsonArray = widgetJsonObject.get("text_color").getAsJsonArray();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text_color");
					continue;
				}
				NVGColor textColor2 = loadColor(textColorJsonArray);

				GUITextBox textBox = new GUITextBox(widgetPosX, widgetPosY, widgetWidth, widgetHeight,
						background_color1, outlineColor1, outlineWidth1, textColor2, textSize1, hint);
				gui.widgets.put(nameString, textBox);

				break;
			default:
				break;
			}

		}

		// 动画列表
		JsonElement animationsElement = jsonObject.get("animation");
		HashMap<String, IAnimation> animations = new HashMap<String, IAnimation>();
		if (animationsElement != null) {
			for (Map.Entry<String, JsonElement> entry : animationsElement.getAsJsonObject().entrySet()) {
				String name = entry.getKey();
				JsonObject value = entry.getValue().getAsJsonObject();

				if (value.has("anims")) {
					int duration = value.get("duration").getAsInt();
					boolean stay = value.get("stay").getAsBoolean();
					JsonObject paramJsonObject = value.get("anims").getAsJsonObject();

					GuiAnimation animation = new GuiAnimation(paramJsonObject, duration, stay);
					animations.put(name, animation);

				} else if (value.has("anim_group")) {
					boolean loop = value.get("loop").getAsBoolean();

					JsonArray groupArray = value.get("anim_group").getAsJsonArray();
					ArrayList<IAnimation> group = new ArrayList<IAnimation>();

					groupArray.forEach(anim -> {
						JsonObject element = anim.getAsJsonObject();
						String type = element.get("type").getAsString();
						switch (type) {
						case "anim":
							group.add(animations.get(element.get("name").getAsString()));
							break;
						case "wait":
							group.add(new AnimationWait(element.get("duration").getAsInt()));
							break;
						default:
							Logger.warn("[GUI Loader] Couldn't find animation type in file " + filename + ": " + type);
							break;
						}
					});

					animations.put(name, new GuiAnimationGroup(group, loop, gui));

				}

				gui.animations = animations;

			}

		}

		guisHashMap.put(filename, gui);
		return gui;
	}

	public View getGui(String name) {
		View view = guisHashMap.get(name);
		if (view == null & name != null) {
			Logger.error("Can't find Gui: " + name);
		}
		return view;
	}

	@Override
	public void close() {

	}

}
