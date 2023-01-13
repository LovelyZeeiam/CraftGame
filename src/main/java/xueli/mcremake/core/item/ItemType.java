package xueli.mcremake.core.item;

import xueli.game2.resource.ResourceLocation;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;

public record ItemType(ResourceLocation namespace, String name, ItemListener listener, ItemVertexGatherer renderer) {
}
