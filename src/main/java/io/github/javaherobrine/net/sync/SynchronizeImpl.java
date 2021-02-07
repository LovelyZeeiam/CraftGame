package io.github.javaherobrine.net.sync;

public abstract class SynchronizeImpl {
    public abstract void offline() throws Exception;

    public abstract void online() throws Exception;
}
