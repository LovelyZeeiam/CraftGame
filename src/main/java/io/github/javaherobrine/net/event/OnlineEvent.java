package io.github.javaherobrine.net.event;

import io.github.javaherobrine.net.Client;
import io.github.javaherobrine.net.TransmissionStatus;

public class OnlineEvent extends EventContent {
    public static final EventObject ONLINE_EVENT = new EventObject(new OnlineEvent());

    {
        type = EventType.NETWORK_EVENT;
        eid = 1;
    }

    @Override
    public void sendExec(Client c) {
        c.msg.status = TransmissionStatus.ACCEPTED;
    }

    @Override
    public void recvExec() {
        sendExec(getSourceClient());
    }
}
