package io.github.javaherobrine.net.sync;

import java.io.IOException;

import io.github.javaherobrine.net.event.EventObject;

public interface EventInput {
    EventObject readObject() throws IOException;
}
