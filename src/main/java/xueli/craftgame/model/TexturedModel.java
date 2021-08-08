package xueli.craftgame.model;

import xueli.game.module.Module;
import xueli.game.utils.FloatList;
import xueli.game.utils.Light;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class TexturedModel extends Module {

	private ArrayList<TexturedCube> cubes;

	public TexturedModel(String namespace, ArrayList<TexturedCube> cubes) {
		super(namespace);
		this.cubes = cubes;
	}

	public TexturedModel(String namespace, TexturedCube cube) {
		super(namespace);

		this.cubes = new ArrayList<>();
		this.cubes.add(cube);
	}

	public int getRenderData(float x, float y, float z, byte face, Light light, FloatList buffer) {
		int vertCount = 0;
		for (TexturedCube c : cubes) {
			vertCount += c.getDrawData(x, y, z, face, light, buffer);
		}
		return vertCount;
	}

	public int getAllRenderData(int x, int y, int z, Color color, Light light, FloatList buffer) {
		int vertCount = 0;
		for (TexturedCube c : cubes) {
			for (byte face = 0; face < 6; face++)
				vertCount += c.getDrawData(x, y, z, face, light, buffer);
		}
		return vertCount;
	}

	@Override
	public boolean checkInvaild() {
		return Objects.nonNull(cubes) && Objects.nonNull(getNamespace());
	}

}
