package xueli.game2.resource.manager;

import xueli.game2.resource.ResourceHolder;
import xueli.game2.resource.provider.ResourceProvider;

import java.io.Closeable;

public interface ResourceManager extends ResourceProvider, ResourceHolder, Closeable {
}
