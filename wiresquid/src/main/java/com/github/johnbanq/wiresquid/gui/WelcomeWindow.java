package com.github.johnbanq.wiresquid.gui;

import com.github.johnbanq.wiresquid.gui.styling.Fonts;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.type.ImBoolean;

public class WelcomeWindow implements ClosableWindow {

    private final ImBoolean show = new ImBoolean(true);

    public ImBoolean getShowProperty() {
        return show;
    }

    public void render() {
        if(shouldShow()) {
            ImGui.begin("Welcome", show);
            ImGui.text("MC:BE Protocol Inspection & Packet Generation Utility");
            ImGui.pushFont(Fonts.ITALIC);
            ImGui.text("\"All hail the flying squid!\"");
            ImGui.popFont();
            ImGui.end();
        }
    }

}
