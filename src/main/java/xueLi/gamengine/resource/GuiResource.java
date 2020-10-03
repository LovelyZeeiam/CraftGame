package xueLi.gamengine.resource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import xueLi.gamengine.utils.EvalableFloat;
import xueLi.gamengine.utils.Logger;
import xueLi.gamengine.view.AnimationWait;
import xueLi.gamengine.view.GUIBackground;
import xueLi.gamengine.view.GUIButton;
import xueLi.gamengine.view.GUIImageView;
import xueLi.gamengine.view.GUIProgressBar;
import xueLi.gamengine.view.GUITextView;
import xueLi.gamengine.view.GuiAnimation;
import xueLi.gamengine.view.GuiAnimationGroup;
import xueLi.gamengine.view.IAnimation;
import xueLi.gamengine.view.View;

public class GuiResource extends IResource {

	private TextureManager textureManager;

	private String real_path;

	public GuiResource(String pathString, TextureManager textureManager) {
		super(pathString);
		this.textureManager = textureManager;

		this.real_path = pathString + "gui/";

	}

	public HashMap<String, View> guisHashMap = new HashMap<String, View>();

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

	private NVGColor loadColor(JsonArray colorArray) {
		NVGColor backgroundColor = NVGColor.create();
		NanoVG.nvgRGBA(colorArray.get(0).getAsByte(), colorArray.get(1).getAsByte(), colorArray.get(2).getAsByte(),
				colorArray.get(3).getAsByte(), backgroundColor);
		return backgroundColor;
	}

	public View loadGui(String filename, LangManager langManager, boolean reloadEnable) {
		if (guisHashMap.containsKey(filename))
			if(!reloadEnable) return guisHashMap.get(filename);
		JsonObject jsonObject = null;
		try {
			jsonObject = gson.fromJson(new FileReader(real_path + filename), JsonObject.class);
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
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
			// 动画列表
			JsonElement animationsElement = widgetJsonObject.get("animation");
			HashMap<String, IAnimation> animations = new HashMap<String, IAnimation>();
			if (animationsElement != null) {
				for (Map.Entry<String, JsonElement> entry : animationsElement.getAsJsonObject().entrySet()) {
					String name = entry.getKey();
					JsonObject value = entry.getValue().getAsJsonObject();
					if (value.has("anim")) {
						animations.put(name, new GuiAnimation(value.get("anim").getAsJsonObject(),
								value.get("duration").getAsInt(), value.get("stay").getAsBoolean()));
					} else if (value.has("anim_group")) {
						JsonArray anims = value.get("anim_group").getAsJsonArray();
						ArrayList<IAnimation> group = new ArrayList<IAnimation>();
						anims.forEach(anim -> {
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
								Logger.warn(
										"[GUI Loader] Couldn't find animation type in file " + filename + ": " + type);
								break;
							}
						});
						boolean loop = value.get("loop").getAsBoolean();
						animations.put(name, new GuiAnimationGroup(group, loop));
					}
				}

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
				imageView.animations = animations;
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
				NVGColor chosenBorderColor = loadColor(chosenBorderJsonObject.get("color").getAsJsonArray());
				int chosenBorderWidth = chosenBorderJsonObject.get("width").getAsInt();
				NVGColor chosenTextColor = loadColor(chosenBorderJsonObject.get("text_color").getAsJsonArray());

				GUIButton button = new GUIButton(widgetPosX, widgetPosY, widgetWidth, widgetHeight, labelString,
						textSize, textColor, chosenBorderColor, chosenBorderWidth, chosenTextColor);
				button.animations = animations;
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

				progressBar.animations = animations;
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
				try {
					textString = widgetJsonObject.get("text").getAsString();
				} catch (NullPointerException e2) {
					Logger.error("[GUI] Couldn't find param in " + nameString + " in " + filename + ": text");
					continue;
				}
				textString = langManager.getStringFromLangMap(textString);
				// align
				JsonElement alignElement = widgetJsonObject.get("align");
				int align = 0;
				if (alignElement != null) {
					JsonArray alignArray = alignElement.getAsJsonArray();
					for (JsonElement eash : alignArray) {
						switch (eash.getAsString()) {
						case "left":
							align |= NanoVG.NVG_ALIGN_LEFT;
							break;
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
						default:
							align |= NanoVG.NVG_ALIGN_LEFT;
							break;
						}
					}
				} else
					align = NanoVG.NVG_ALIGN_LEFT;

				GUITextView textView = new GUITextView(widgetPosX, widgetPosY, widgetWidth, widgetHeight, textSize1,
						textColor1, align);
				textView.setText(textString);
				textView.animations = animations;
				gui.widgets.put(nameString, textView);

				break;
			default:
				break;
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
