package xueli.gamengine.view.anim2d;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.lwjgl.nanovg.NVGColor;
import xueli.gamengine.data.Author;
import xueli.gamengine.resource.GuiResource;
import xueli.gamengine.utils.ClassFinder;
import xueli.gamengine.utils.Display;
import xueli.gamengine.utils.Logger;
import xueli.gamengine.utils.UseReflect;
import xueli.gamengine.utils.evalable.EvalableFloat;
import xueli.gamengine.view.GuiColor;
import xueli.gamengine.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;

public class Anim2DView extends View {

	private JsonObject object;
	private Anim2D processedAnim;

	private long startTime;
	private long temptime = 0;
	private ArrayList<AnimComponent> currentComponents = new ArrayList<>();

	public Anim2DView(String title, String json) {
		super(title);

		object = new Gson().fromJson(json, JsonObject.class);

	}

	public Anim2DView(String title, JsonObject json) {
		super(title);

		this.object = json;

	}

	private static String getErrorMessage(String message) {
		return "Error in processing anim2D: " + message;
	}

	private static Anim2D processAnim(JsonObject data) {
		Anim2D anim = new Anim2D();

		// authors
		JsonArray author = data.getAsJsonArray("author");
		Logger.checkNullAndThrow(author, getErrorMessage("element \"author\" can't be found!"));
		author.forEach(e -> {
			JsonObject o = e.getAsJsonObject();

			JsonElement nameElement = o.get("name");
			Logger.checkNullAndThrow(nameElement, getErrorMessage("element \"name\" in \"author\" can't be found!"));
			JsonElement descElement = o.get("description");
			Logger.checkNullAndThrow(descElement,
					getErrorMessage("element \"description\" in \"author\" can't be found!"));

			anim.authors.add(new Author(nameElement.getAsString(), descElement.getAsString()));

			Logger.info(
					"Find author: name: " + nameElement.getAsString() + ", description: " + descElement.getAsString());

		});

		// scene
		JsonObject sceneObj = data.getAsJsonObject("scene");
		Logger.checkNullAndThrow(sceneObj,
				"element \"scene\" can't be found! But it is still good with a black screen~(neh heh heh)");

		sceneObj.entrySet().forEach(e -> {
			String name = e.getKey();

			JsonObject o = e.getValue().getAsJsonObject();
			Logger.checkNullAndThrow(o, "element null is found: " + name);

			String type = o.get("type").getAsString();
			switch (type) {
			case "text":
				JsonElement textElement = o.get("text");
				Logger.checkNullAndThrow(textElement,
						"element \"text\" in scene \"" + name + "\" can't be found: " + name);
				String text = textElement.getAsString();

				JsonElement widthElement = o.get("width");
				Logger.checkNullAndThrow(widthElement,
						"element \"width\" in scene \"" + name + "\" can't be found: " + name);
				String width = widthElement.getAsString();

				JsonElement heightElement = o.get("height");
				Logger.checkNullAndThrow(heightElement,
						"element \"height\" in scene \"" + name + "\" can't be found: " + name);
				String height = heightElement.getAsString();

				JsonElement sizeElement = o.get("size");
				Logger.checkNullAndThrow(sizeElement,
						"element \"size\" in scene \"" + name + "\" can't be found: " + name);
				String size = sizeElement.getAsString();

				JsonElement alignElement = o.get("align");
				int align = GuiResource.getAlign(alignElement.getAsJsonArray());

				JsonElement colorElement = o.get("color");
				NVGColor color = colorElement == null ? GuiColor.BLACK
						: GuiResource.loadColor(colorElement.getAsJsonArray());

				ElementTextView textView = new ElementTextView(name, color, new EvalableFloat(size),
						new EvalableFloat(width), new EvalableFloat(height), align, text);
				anim.scene.put(name, textView);
				Logger.info("Find 2D element: " + textView);

				break;

			}

		});

		// load all components
		@UseReflect
		Class<?> componentParentClazzes = null;
		try {
			componentParentClazzes = Class.forName("xueli.gamengine.view.anim2d.AnimComponent");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		HashMap<String, Class<?>> componentClazzes = new HashMap<>();
		for (Class<?> clazz : ClassFinder.findClassByPackage("xueli.gamengine.view.anim2d")) {
			if (clazz.getSimpleName().equals("AnimComponent"))
				continue;

			boolean isImplement = true;
			try {
				clazz.asSubclass(componentParentClazzes);
			} catch (ClassCastException e) {
				isImplement = false;
			}

			if (isImplement) {
				ComponentType c = clazz.getAnnotation(ComponentType.class);
				componentClazzes.put(c.type(), clazz);

			}

		}

		// animate it~
		JsonArray animObj = data.getAsJsonArray("anim");
		Logger.checkNullAndThrow(animObj,
				"element \"anim\" can't be found! But it is still good with a static image~(neh heh heh)");

		animObj.forEach(e -> {
			JsonObject object = e.getAsJsonObject();

			int tick = object.get("tick").getAsInt();
			JsonArray components = object.getAsJsonArray("components");

			components.forEach(c -> {
				JsonObject component = c.getAsJsonObject();
				String type = component.get("type").getAsString();

				Class<? extends AnimComponent> clazz = (Class<? extends AnimComponent>) componentClazzes.get(type);
				AnimComponent instance = new Gson().fromJson(c, clazz);
				System.out.println(instance);

				if (!anim.components.containsKey(tick))
					anim.components.put(tick, new ArrayList<>());
				anim.components.get(tick).add(instance);

			});

		});

		return anim;
	}

	@Override
	public void create() {
		this.processedAnim = processAnim(object);

		this.startTime = System.currentTimeMillis();

	}

	private void tick() {
		long time = System.currentTimeMillis();
		if (temptime == time)
			return;

		this.temptime = time;
		int timestamp = (int) (time - startTime);

		ArrayList<AnimComponent> componentsNeedToBeDisposed = new ArrayList<>();

		if (processedAnim.components.get(timestamp) != null)
			currentComponents.addAll(processedAnim.components.get(timestamp));

		processedAnim.components.keySet().forEach(tick -> {
			if (tick < timestamp)
				currentComponents.addAll(processedAnim.components.get(tick));
		});

		for (AnimComponent a : currentComponents)
			if (a.invoke(processedAnim) == Constant.COMPONENT_CAN_BE_DISPOSED)
				componentsNeedToBeDisposed.add(a);

		currentComponents.removeAll(componentsNeedToBeDisposed);

	}

	@Override
	public void size() {
		for (Element2D element : processedAnim.scene.values()) {
			element.requireSized();
		}

	}

	@Override
	public void draw(long nvg) {
		tick();

		nvgBeginFrame(nvg, Display.currentDisplay.getWidth(), Display.currentDisplay.getHeight(),
				Display.currentDisplay.getRatio());

		for (Element2D e : processedAnim.scene.values()) {
			e.paint(nvg);

		}

		nvgEndFrame(nvg);
	}

}
