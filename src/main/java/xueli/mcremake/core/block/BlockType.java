package xueli.mcremake.core.block;

import xueli.game2.resource.ResourceIdentifier;
import xueli.mcremake.client.renderer.world.block.BlockVertexGatherer;

public record BlockType(ResourceIdentifier namespace, String name, BlockVertexGatherer renderer,
		BlockCollidable collidable) {
}
