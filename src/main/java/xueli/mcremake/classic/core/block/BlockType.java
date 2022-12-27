package xueli.mcremake.classic.core.block;

import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.classic.client.renderer.world.BlockRenderer;

public record BlockType(ResourceLocation namespace, String name, BlockListener listener, BlockRenderer renderer) {
}
