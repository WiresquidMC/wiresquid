package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.api.Connection;
import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.github.johnbanq.wiresquid.gui.WiresquidGUI;
import com.github.johnbanq.wiresquid.logic.ConnectionDatabase;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.Future;

/**
 * main object of the entire application
 */
public class Wiresquid {

    private ConnectionDatabase database;

    private WiresquidGUI gui;

    // lifecycle //

    public void start() {
        database = new ConnectionDatabase();
        gui = new WiresquidGUI(database, this::stop);
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
        return database;
    }

    // main //

    @SneakyThrows
    public static void main(String[] args) {
        Wiresquid wireSquid = new Wiresquid();
        wireSquid.start();
        wireSquid.getStopFuture().get();
    }

}
