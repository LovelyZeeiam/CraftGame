package io.github.javaherobrine.net.event;

import java.io.Serializable;

import io.github.javaherobrine.net.Client;
import io.github.javaherobrine.net.Server;

public abstract class EventContent implements Serializable {
    public EventType type;
    public int index;
    public int eid;

    public Client getSourceClient() {
        return Server.thisServer.clients.get(index);
    }

    public abstract void sendExec(Client c);

    public abstract void recvExec();
}
