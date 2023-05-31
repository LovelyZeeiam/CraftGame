package xueli.mcremake.client.renderer.world.block;

import xueli.game2.ecs.ResourceListGeneric;
import xueli.mcremake.client.CraftGameClient;
import xueli.mcremake.client.renderer.world.ChunkRenderType;
import xueli.mcremake.client.renderer.world.RenderTypeAlpha;
import xueli.mcremake.client.renderer.world.RenderTypeSolid;

public class BlockRenderTypes extends ResourceListGeneric<ChunkRenderType> {

	public BlockRenderTypes(CraftGameClient ctx) {
		this.add(new RenderTypeSolid(ctx));
		this.add(new RenderTypeAlpha(ctx));
	}

}
