package xueli.craftgame.renderer.model;

import java.awt.Color;
import java.util.ArrayList;

import xueli.game.utils.FloatList;
import xueli.game.utils.texture.AtlasTextureHolder;

public class TexturedModel {

	private String namespace;
	private ArrayList<TexturedCube> cubes;

	public TexturedModel(String namespace, ArrayList<TexturedCube> cubes) {
		this.namespace = namespace;
		this.cubes = cubes;
	}

	public TexturedModel(String namespace, TexturedCube cube) {
		this.namespace = namespace;

		this.cubes = new ArrayList<>();
		this.cubes.add(cube);
	}

	public String getNamespace() {
		return namespace;
	}

	public int getRenderData(int x, int y, int z, byte face, Color color, FloatList buffer) {
		int vertCount = 0;
		for (TexturedCube c : cubes) {
			vertCount += c.getDrawData(x, y, z, face, color, buffer);
		}
		return vertCount;
	}

	// A fully complete normal
	public static TexturedModel getFullCubeModel(AtlasTextureHolder... faces) {
		TexturedCube cube = new TexturedCube(Cube.FULL_CUBE, faces);
		return new TexturedModel("CUBE", cube);
	}

}
