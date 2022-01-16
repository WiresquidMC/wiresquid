package com.github.johnbanq.wiresquid.api;

import com.nukkitx.protocol.bedrock.BedrockPacket;

/**
 * represents a listener that accepts new connection and packets from connection
 * @implNote listener methods are thread-safe
 */
public interface ConnectionListener {

    void onNewConnection(Connection connection);

    void onPacketFromClient(Connection connection, BedrockPacket packet);

    void onPacketFromServer(Connection connection, BedrockPacket packet);

    void onConnectionClosed(Connection connection);

}
