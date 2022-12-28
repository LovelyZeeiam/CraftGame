package xueli.mcremake.core.block;

import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.renderer.world.BlockRenderer;

public record BlockType(ResourceLocation namespace, String name, BlockListener listener, BlockRenderer renderer, BlockCollidable collidable) {
}
