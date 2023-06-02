package xueli.mcremake.core.block;

import xueli.mcremake.client.renderer.world.block.BlockVertexGatherer;
import xueli.registry.Identifier;

public record BlockType(Identifier namespace, String name, BlockVertexGatherer renderer,
		BlockCollidable collidable) {
}
