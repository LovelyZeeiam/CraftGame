package xueli.craftgame.renderer.model;

import java.awt.Color;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.block.data.BlockFace;
import xueli.game.utils.FloatList;
import xueli.game.utils.texture.AtlasTextureHolder;

public class TexturedCube extends Cube {

	private AtlasTextureHolder[] faces;

	public TexturedCube(Vector3f from, Vector3f to, AtlasTextureHolder... faces) {
		super(from, to);
		processAtlasTextureHolder(faces);

	}
	
	public TexturedCube(Cube cube, AtlasTextureHolder... faces) {
		super(cube.getFrom(), cube.getTo());
		processAtlasTextureHolder(faces);

	}
	
	private void processAtlasTextureHolder(AtlasTextureHolder... faces) {
		if (faces.length == 1) {
			this.faces = new AtlasTextureHolder[] { faces[0], faces[0], faces[0], faces[0], faces[0], faces[0], };
		} else if (faces.length != 6) {
			throw new RuntimeException("Please input 6 vertices or 1 vertex for all faces!");
		} else {
			this.faces = faces;
		}
		
	}

	public int getDrawData(int x, int y, int z, byte face, Color color, FloatList buffer) {
		switch (face) {
		case BlockFace.FRONT: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.FRONT];
			CubeDrawer.drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y, z),
					texHolder.p_right_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x, y + 1, z), texHolder.p_left_top,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		case BlockFace.RIGHT: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.RIGHT];
			CubeDrawer.drawQuadFacingBackOrRight(buffer, new Vector3f(x + 1, y, z), texHolder.p_right_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x + 1, y, z + 1), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z + 1),
					texHolder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		case BlockFace.BACK: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.BACK];
			CubeDrawer.drawQuadFacingBackOrRight(buffer, new Vector3f(x, y, z + 1), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y, z + 1),
					texHolder.p_right_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x, y + 1, z + 1), texHolder.p_left_top,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z + 1),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		case BlockFace.LEFT: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.LEFT];
			CubeDrawer.drawQuadFacingFrontOrLeft(buffer, new Vector3f(x, y, z), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x, y + 1, z),
					texHolder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x, y, z + 1), texHolder.p_right_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x, y + 1, z + 1),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		case BlockFace.TOP: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.TOP];
			CubeDrawer.drawQuadFacingTop(buffer, new Vector3f(x, y + 1, z), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z),
					texHolder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x, y + 1, z + 1), texHolder.p_right_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y + 1, z + 1),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		case BlockFace.BOTTOM: {
			AtlasTextureHolder texHolder = this.faces[BlockFace.BOTTOM];
			CubeDrawer.drawQuadFacingBottom(buffer, new Vector3f(x, y, z), texHolder.p_left_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y, z),
					texHolder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f),
					new Vector3f(x, y, z + 1), texHolder.p_right_down,
					new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), new Vector3f(x + 1, y, z + 1),
					texHolder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
			break;
		}
		}

		return 6;
	}

}
