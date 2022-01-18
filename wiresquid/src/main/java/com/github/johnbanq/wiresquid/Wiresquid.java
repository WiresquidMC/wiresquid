package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.api.ConnectionListener;
import com.github.johnbanq.wiresquid.gui.WiresquidGUI;
import com.github.johnbanq.wiresquid.logic.ConnectionDatabase;
import lombok.SneakyThrows;

import java.util.concurrent.Future;

/**
 * main object of the entire application
 */
public class Wiresquid {

    private final Runnable guiStopCallback;

    private ConnectionDatabase database;

    private WiresquidGUI gui;

    public Wiresquid() {
        this(()->{});
    }

    /**
     * @param guiStopCallback callback on wiresquid is stopped by closing the GUI window
     *                        wiresquid will stop after this callback is completed
     */
    public Wiresquid(Runnable guiStopCallback) {
        this.guiStopCallback = guiStopCallback;
    }

    // lifecycle //

    public void start() {
        database = new ConnectionDatabase();
        database.start();
        gui = new WiresquidGUI(database, this::stopFromGUI);
        gui.start();
    }

    public void stop() {
        gui.stop();
        database.stop();
    }

    public Future<Void> getStopFuture() {
        return gui.getStopFuture();
    }
    
    private void stopFromGUI() {
        guiStopCallback.run();
        stop();
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
