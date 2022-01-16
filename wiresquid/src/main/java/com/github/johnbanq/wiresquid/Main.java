package com.github.johnbanq.wiresquid;

import com.github.johnbanq.wiresquid.gui.Styling;
import imgui.*;
import imgui.app.Application;
import imgui.app.Configuration;
import lombok.SneakyThrows;

import static com.github.johnbanq.wiresquid.gui.Styling.getFont;
import static com.github.johnbanq.wiresquid.gui.Styling.loadFonts;

public class Main extends Application {

    @Override
    protected void configure(Configuration config) {
        config.setTitle("Wiresquid MC:BE Protocol Utility");
    }

    @Override
    @SneakyThrows
    protected void initImGui(Configuration config) {
        super.initImGui(config);
        loadFonts();
        Styling.configureStyle(ImGui.getStyle());
    }

    @Override
    public void process() {
        ImGui.pushFont(getFont());
        ImGui.text("Hello, World!");
        ImGui.popFont();
    }

    public static void main(String[] args) {
        launch(new Main());
    }

}