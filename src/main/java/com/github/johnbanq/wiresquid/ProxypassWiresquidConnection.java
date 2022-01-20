package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.api.Connection;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockServerSession;

public class ProxypassWiresquidConnection implements Connection {

    private final BedrockServerSession upstream;
    private final BedrockClientSession downstream;

    public ProxypassWiresquidConnection(BedrockServerSession upstream, BedrockClientSession downstream) {
        this.upstream = upstream;
        this.downstream = downstream;
    }

    @Override
    public boolean isClosed() {
        return upstream.isClosed() && downstream.isClosed();
    }

    @Override
    public void sendPacket(Receiver receiver, BedrockPacket packet) {
        switch (receiver) {
            case CLIENT:
                upstream.sendPacket(packet);
                break;
            case SERVER:
                downstream.sendPacket(packet);
                break;
            default:
                throw new AssertionError("unexpected receiver: "+receiver);
        }
    }

    @Override
    public void disconnect(String reason) {
        downstream.disconnect();
        upstream.disconnect(reason, true);
    }

}
