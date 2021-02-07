package io.github.javaherobrine.net.sync;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import io.github.javaherobrine.ioStream.JSONInputStream;
import io.github.javaherobrine.net.event.EventObject;
import io.github.javaherobrine.net.event.Events;

public class EventInputStream extends JSONInputStream implements EventInput {
    public EventInputStream(Reader source) {
        super(source);
    }

    @Override
    public EventObject readObject() throws IOException {
        try {
            return new EventObject(Events.EVENTS_BEAN.list.get((int) ((Map) super.readObject()).get("eid")).newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException(e);
        }
    }
}
