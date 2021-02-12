package xueli.gamengine.utils.renderer;

import java.util.ArrayList;

import xueli.craftgame.CraftGame;
import xueli.craftgame.WorldLogic;

/**
 * 这个类一般都是绘制一些杂七杂八的面 这些面每个都有自己特定的材质 或许还有不同的着色器
 */
public class Faces {

	private ArrayList<Face> faces = new ArrayList<>();

	private CraftGame cg;

	public Faces(WorldLogic logic) {
		cg = logic.getCg();

	}

	public void addFace(Face face) {
		this.faces.add(face);

	}

	public void render() {
		for (Face face : faces) {
			face.draw();

		}

	}

	public void clear() {
		for (Face face : faces) {
			face.release();

		}

		this.faces.clear();

	}

}
