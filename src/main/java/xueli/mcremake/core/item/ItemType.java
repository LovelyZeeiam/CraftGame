package xueli.mcremake.core.item;

import xueli.mcremake.client.renderer.item.ItemVertexGatherer;
import xueli.registry.Identifier;

public record ItemType(Identifier namespace, String name, ItemVertexGatherer renderer) {
}
