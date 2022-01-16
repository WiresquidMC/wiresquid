package com.github.johnbanq.wiresquid.gui;

import imgui.ImGui;
import imgui.type.ImBoolean;

public class SessionsWindow implements ClosableWindow {

    private final ImBoolean show = new ImBoolean(true);

    public ImBoolean getShowProperty() {
        return show;
    }

    public void render() {
        if(shouldShow()) {
            ImGui.begin("Sessions", show);
            ImGui.end();
        }
    }

}
