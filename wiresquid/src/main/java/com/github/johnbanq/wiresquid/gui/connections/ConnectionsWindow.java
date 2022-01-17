package com.github.johnbanq.wiresquid.gui.connections;

import com.github.johnbanq.wiresquid.gui.ClosableWindow;
import com.github.johnbanq.wiresquid.logic.*;
import imgui.ImGui;
import imgui.ImGuiListClipper;
import imgui.callback.ImListClipperCallback;
import imgui.flag.ImGuiInputTextFlags;
import imgui.flag.ImGuiTableColumnFlags;
import imgui.flag.ImGuiTableFlags;
import imgui.type.ImBoolean;
import imgui.type.ImInt;
import imgui.type.ImString;
import io.vavr.collection.Iterator;
import io.vavr.collection.SortedSet;

import javax.annotation.Nullable;

public class ConnectionsWindow implements ClosableWindow {

    private final ImBoolean show = new ImBoolean(true);

    // filter state //

    private final ImString playerName = new ImString("");

    private final ImString playerUUID = new ImString("");

    private final ImInt status = new ImInt(0);

    // subscription logic //

    private final ConnectionDatabase database;

    private ConnectionSubscription subscription;

    public ConnectionsWindow(ConnectionDatabase database) {
        this.database = database;
        this.subscription = database.subscribeConnections(new ConnectionFilter());
    }

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
        ImGui.inputText("##name", playerName, ImGuiInputTextFlags.EnterReturnsTrue);
        ImGui.text("UUID");
        ImGui.inputText("##uuid", playerUUID, ImGuiInputTextFlags.EnterReturnsTrue);
        ImGui.text("Status");
        ImGui.combo("##combo", status, stateComboString());
        if(ImGui.button("Apply")) {
            subscription.close();
            subscription = database.subscribeConnections(toFilter());
        }
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

            SortedSet<WiresquidConnection> set = subscription.getSet();
            ImGuiListClipper.forEach(set.length(), new ImListClipperCallback() {

                private long idx = 0;
                private Iterator<WiresquidConnection> iterator = set.iterator();

                @Override
                public void accept(int i) {
                    ImGui.tableNextRow();
                    if(idx > i) {
                        idx = 0;
                        iterator = set.iterator();
                    }
                    while(idx < i) {
                        idx++;
                        iterator.next();
                    }
                    WiresquidConnection connection = iterator.next();
                    ImGui.tableSetColumnIndex(0);
                    ImGui.text(Long.toString(connection.getId()));
                    ConnectionIdentifier identifier = connection.getIdentifier();
                    ImGui.tableSetColumnIndex(1);
                    ImGui.text(identifier == null ? "unknown" : identifier.getDisplayName());
                    ImGui.tableSetColumnIndex(2);
                    ImGui.text(identifier == null ? "unknown" : identifier.getUuid().toString());
                    ImGui.tableSetColumnIndex(3);
                    ImGui.text(connection.getState().toString());
                    ImGui.tableSetColumnIndex(4);
                    ImGui.button("Inspect");
                }
            });

            ImGui.endTable();
        }

        ImGui.endChild();
        ImGui.endGroup();
    }

    private ConnectionFilter toFilter() {
        return new ConnectionFilter(
                playerName.get().isEmpty() ? null : playerName.get(),
                playerUUID.get().isEmpty() ? null : playerUUID.get(),
                comboStringIndexToState(status.get())
        );
    }

    private String[] stateComboString() {
        return new String[]{"ALL", "ACTIVE", "CLOSED"};
    }

    private @Nullable ConnectionState comboStringIndexToState(int idx) {
        switch(idx) {
            case 0:
                return null;
            case 1:
                return ConnectionState.ACTIVE;
            case 2:
                return ConnectionState.CLOSED;
            default:
                throw new AssertionError("unexpected index: "+ idx);
        }
    }

}
