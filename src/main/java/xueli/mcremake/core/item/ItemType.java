package xueli.mcremake.core.item;

import xueli.game2.resource.ResourceIdentifier;
import xueli.mcremake.client.renderer.item.ItemVertexGatherer;

public record ItemType(ResourceIdentifier namespace, String name, ItemVertexGatherer renderer) {
}
