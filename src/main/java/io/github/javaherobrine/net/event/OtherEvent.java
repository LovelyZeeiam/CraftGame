package io.github.javaherobrine.net.event;

import java.util.Map;

public abstract class OtherEvent extends EventContent {
    public Object content;

    public abstract Object initContent(Map map);
}
