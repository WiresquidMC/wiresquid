package com.github.johnbanq.wiresquid.api;

import com.nukkitx.protocol.bedrock.BedrockPacket;

/**
 * interface to represent an connection that can be provided to wiresquid
 */
public interface Connection {

    boolean isClosed();

    void sendPacket(BedrockPacket packet);

    void disconnect(String reason);

}
