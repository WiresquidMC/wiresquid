package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.api.Connection;
import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.nukkitx.protocol.bedrock.BedrockPacket;

/**
 * main object of the entire application
 */
public class WireSquid {

    public void start() {

    }

    public void stop() {

    }

    public ConnectionListener getListener() {
        return new ConnectionListener() {
            @Override
            public void onNewConnection(Connection connection) {

            }

            @Override
            public void onPacketFromClient(Connection connection, BedrockPacket packet) {

            }

            @Override
            public void onPacketFromServer(Connection connection, BedrockPacket packet) {

            }

            @Override
            public void onConnectionClosed(Connection connection) {

            }
        };
    }

}
