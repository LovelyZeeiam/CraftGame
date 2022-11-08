package xueli.game2.resource.manager;

import java.io.Closeable;

import xueli.game2.resource.provider.ResourceProvider;

public interface ResourceManager extends ResourceProvider, IReloadable, Closeable {
}
