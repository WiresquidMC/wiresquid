package com.github.johnbanq.wiresquid.gui;

import com.github.johnbanq.wiresquid.gui.styling.Fonts;
import com.github.johnbanq.wiresquid.gui.styling.Style;
import imgui.ImGui;
import imgui.app.Application;
import imgui.app.Configuration;
import lombok.SneakyThrows;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.CompletableFuture;

import static com.github.johnbanq.wiresquid.gui.styling.Fonts.loadFonts;

/**
 * root GUI object, deals with GUI configuration, lifecycle and threading
 * note: user of this object must manually listen using onTryToCloseWindow and close() it to allow user to stop by closing the window
 */
public class WiresquidGUI extends Application {

    // gui state //

    private RootWindow root;

    private boolean closeWindowHandlerCalled = false;

    private final Runnable onTryToCloseWindow;

    // threading state //

    private volatile boolean closing = false;

    private CompletableFuture<Void> stopFuture = new CompletableFuture<>();

    private Thread thread;

    public WiresquidGUI(Runnable onTryToCloseWindow) {
        this.onTryToCloseWindow = onTryToCloseWindow;
    }

    // GUI logic //

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Wiresquid MC:BE Protocol Utility");
    }

    @Override
    @SneakyThrows
    protected void initImGui(Configuration config) {
        super.initImGui(config);
        loadFonts();
        Style.configureStyle(ImGui.getStyle());
        root = new RootWindow();
    }

    @Override
    public void process() {
        ImGui.pushFont(Fonts.REGULAR);
        root.render();
        ImGui.popFont();
    }

    // lifecycle & threading logic //

    protected void run() {
        while(!closing) {
            boolean shouldClose = GLFW.glfwWindowShouldClose(handle);
            if(shouldClose && !closeWindowHandlerCalled) {
                closeWindowHandlerCalled = true;
                onTryToCloseWindow.run();
            }
            runFrame();
        }
    }

    @Override
    protected void dispose() {
        super.dispose();
        stopFuture.complete(null);
    }

    /**
     * main function of GUI thread, returns either when user closes the window or stop() was called
     */
    public synchronized void start() {
        if(thread!=null) {
            throw new IllegalStateException("GUI already started!");
        }
        thread = new Thread(()->launch(this));
        thread.setName("Wiresquid GUI");
        thread.start();
    }

    /**
     * stop function of GUI logic, thread-safe and will make main() return after called
     */
    public void stop() {
        closing = true;
    }

    /**
     * get future to wait for the thread to stop gracefully
     * @return
     */
    public CompletableFuture<Void> getStopFuture() {
        return stopFuture;
    }

}