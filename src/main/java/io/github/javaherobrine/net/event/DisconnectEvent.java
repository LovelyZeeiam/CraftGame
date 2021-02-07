package io.github.javaherobrine.net.event;

import io.github.javaherobrine.net.Client;
import io.github.javaherobrine.net.Server;

public class DisconnectEvent extends EventContent {
    public static final DisconnectEvent DISCONNECT = new DisconnectEvent();

    {
        type = EventType.NETWORK_EVENT;
        eid = 0;
    }

    @Override
    public void sendExec(Client c) {
        System.exit(0);
    }

    @Override
    public void recvExec() {
        Server.thisServer.clients.remove(getSourceClient().msg.id);
    }
}
