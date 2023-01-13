package xueli.mcremake.core.block;

import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.renderer.world.BlockVertexGatherer;

public record BlockType(ResourceLocation namespace, String name, BlockListener listener, BlockVertexGatherer renderer, BlockCollidable collidable) {
}
