package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.api.Connection;
import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.github.johnbanq.wiresquid.gui.WiresquidGUI;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import lombok.SneakyThrows;

import java.util.concurrent.Future;

/**
 * main object of the entire application
 */
public class Wiresquid {

    private WiresquidGUI gui;

    // lifecycle //

    public void start() {
        gui = new WiresquidGUI(this::stop);
        gui.start();
    }

    public void stop() {
        gui.stop();
    }

    public Future<Void> getStopFuture() {
        return gui.getStopFuture();
    }

    // connection listening //

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

    @SneakyThrows
    public static void main(String[] args) {
        Wiresquid wireSquid = new Wiresquid();
        wireSquid.start();
        wireSquid.getStopFuture().get();
    }

}
