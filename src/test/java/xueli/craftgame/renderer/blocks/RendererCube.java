package xueli.craftgame.renderer.blocks;

import java.nio.charset.StandardCharsets;

import xueli.craftgame.renderer.WorldRenderer;
import xueli.utils.io.Files;

public class RendererCube extends IBlockRenderer {

	public RendererCube(WorldRenderer manager) {
		super(manager);

		try {
			setShader(
					new String(Files.readResourcePackedInJar("/assets/shaders/world/world.vert"),
							StandardCharsets.UTF_8),
					new String(Files.readResourcePackedInJar("/assets/shaders/world/world.frag"),
							StandardCharsets.UTF_8));
		} catch (Exception e) {
			manager.getContext().announceCrash("RendererLoading", new Exception("Error when loading shader", e));
		}

	}

}
