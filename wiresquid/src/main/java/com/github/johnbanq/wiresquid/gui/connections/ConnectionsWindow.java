package com.github.johnbanq.wiresquid.gui.connections;

import com.github.johnbanq.wiresquid.gui.ClosableWindow;
import imgui.ImGui;
import imgui.ImGuiListClipper;
import imgui.callback.ImListClipperCallback;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;

public class ConnectionsWindow implements ClosableWindow {

    private final ImBoolean show = new ImBoolean(true);

    private final ImString playerName = new ImString("");

    private final ImString playerUUID = new ImString("");

    private final ImInt status = new ImInt(0);

    public ImBoolean getShowProperty() {
        return show;
    }

    public void render() {
        if (shouldShow()) {
            ImGui.begin("Connections", show);
            renderFilter();
            ImGui.sameLine();
            // table
            renderTable();
            ImGui.showDemoWindow();
            ImGui.end();
        }
    }

    private void renderFilter() {
        ImGui.beginGroup();
        ImGui.beginChild("Filters", 175, 0, true);
        ImGui.text("Display Name");
        ImGui.inputText("##name", playerName, 64);
        ImGui.text("UUID");
        ImGui.inputText("##uuid", playerUUID, 64);
        ImGui.text("Status");
        ImGui.combo("##combo", status, new String[]{"ALL", "ACTIVE", "DISCONNECTED"});
        ImGui.endChild();
        ImGui.endGroup();
    }

    private void renderTable() {
        ImGui.beginGroup();
        ImGui.beginChild("Connections", 0, 0, true);

        int flags = ImGuiTableFlags.ScrollY
                | ImGuiTableFlags.RowBg
                | ImGuiTableFlags.BordersOuter
                | ImGuiTableFlags.BordersV
                | ImGuiTableFlags.Resizable
                | ImGuiTableFlags.ScrollY
                | ImGuiTableFlags.SizingStretchSame;
        if (ImGui.beginTable("Connections", 5, flags)) {
            ImGui.tableSetupScrollFreeze(0, 1); // Make top row always visible
            ImGui.tableSetupColumn("#", ImGuiTableColumnFlags.None, 0.1f);
            ImGui.tableSetupColumn("Display Name", ImGuiTableColumnFlags.None, 0.3f);
            ImGui.tableSetupColumn("UUID", ImGuiTableColumnFlags.None, 0.3f);
            ImGui.tableSetupColumn("Status", ImGuiTableColumnFlags.None, 0.1f);
            ImGui.tableSetupColumn("Action", ImGuiTableColumnFlags.None, 0.2f);
            ImGui.tableHeadersRow();

            ImGuiListClipper.forEach(1000, new ImListClipperCallback() {
                @Override
                public void accept(int i) {
                    ImGui.tableNextRow();
                    for (int column = 0; column < 5; column++) {
                        ImGui.tableSetColumnIndex(column);
                        ImGui.text(String.format("Hello %d,%d", column, i));
                    }
                }
            });

            ImGui.endTable();
        }

        ImGui.endChild();
        ImGui.endGroup();
    }

}
