package com.github.johnbanq.wiresquid.api;

import com.nukkitx.protocol.bedrock.BedrockPacket;

/**
 * interface to represent a connection that can be provided to wiresquid
 */
public interface Connection {

    enum Receiver {
        CLIENT,
        SERVER
    }

    boolean isClosed();

    void sendPacket(Receiver receiver, BedrockPacket packet);

    void disconnect(String reason);

}
