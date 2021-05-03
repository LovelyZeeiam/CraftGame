package xueli.craftgame.renderer.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;

import xueli.game.module.Module;
import xueli.game.utils.FloatList;

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

	public int getRenderData(int x, int y, int z, byte face, Color color, FloatList buffer) {
		int vertCount = 0;
		for (TexturedCube c : cubes) {
			vertCount += c.getDrawData(x, y, z, face, color, buffer);
		}
		return vertCount;
	}
	
	public int getAllRenderData(int x, int y, int z, Color color, FloatList buffer) {
		int vertCount = 0;
		for (TexturedCube c : cubes) {
			for(byte face = 0; face < 6; face++)
				vertCount += c.getDrawData(x, y, z, face, color, buffer);
		}
		return vertCount;
	}

	@Override
	public boolean checkInvaild() {
		return Objects.nonNull(cubes) && Objects.nonNull(getNamespace());
	}

}
