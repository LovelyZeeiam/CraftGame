package xueli.craftgame.resource.manager;

import java.io.Closeable;

import xueli.craftgame.resource.provider.ResourceProvider;

public interface ResourceManager extends ResourceProvider, IReloadable, Closeable {
}
