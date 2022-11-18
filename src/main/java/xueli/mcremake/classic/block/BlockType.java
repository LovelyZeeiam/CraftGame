package xueli.mcremake.classic.block;

import xueli.game2.resource.ResourceLocation;
import xueli.game2.resource.submanager.lang.Translatable;

public record BlockType(ResourceLocation namespace, Translatable name, BlockListener listener) {
}
