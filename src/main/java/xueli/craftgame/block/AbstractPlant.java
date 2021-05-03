package xueli.craftgame.block;

import java.awt.Color;

import org.lwjgl.util.vector.Vector3f;

import xueli.craftgame.renderer.model.CubeDrawer;
import xueli.craftgame.state.StateWorld;
import xueli.craftgame.world.Dimension;
import xueli.game.utils.FloatList;
import xueli.game.utils.texture.AtlasTextureHolder;

public class AbstractPlant extends AbstractBlock {
	
	private AtlasTextureHolder holder;

	public AbstractPlant(String namespace, String nameInternational, String textureName) {
		super(namespace, nameInternational, new String[] {
			textureName, textureName, textureName, textureName, textureName, textureName
		});
		
		isComplete = false;
		isAlpha = true;
		
		this.holder = StateWorld.getInstance().getBlocksTextureAtlas().getTextureHolder(textureName);
		
		
	}
	
	@Override
	public int getRenderCubeData(FloatList buffer, int x, int y, int z, byte face, Color color, Dimension dimension) {
		CubeDrawer.drawQuadFacingBackOrRight(buffer, 
				new Vector3f(x + 0.2f, y + 0.6f, z + 0.2f), holder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.8f, y + 0.6f, z + 0.8f), holder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.2f, y + 0, z + 0.2f), holder.p_left_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.8f, y + 0, z + 0.8f), holder.p_right_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
		CubeDrawer.drawQuadFacingBackOrRight(buffer, 
				new Vector3f(x + 0.8f, y + 0.6f, z + 0.2f), holder.p_left_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.2f, y + 0.6f, z + 0.8f), holder.p_right_top, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.8f, y + 0, z + 0.2f), holder.p_left_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f), 
				new Vector3f(x + 0.2f, y + 0, z + 0.8f), holder.p_right_down, new Vector3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f));
		return 12;
	}

}
