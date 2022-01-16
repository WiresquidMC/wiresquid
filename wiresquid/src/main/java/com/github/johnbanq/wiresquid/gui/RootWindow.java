package com.github.johnbanq.wiresquid.gui;

import com.github.johnbanq.wiresquid.gui.connections.ConnectionsWindow;
import com.github.johnbanq.wiresquid.gui.welcome.WelcomeWindow;
import imgui.ImGui;

/**
 * the "root" window that does the top-level rendering work
 */
public class RootWindow {

    private final WelcomeWindow welcomeWindow = new WelcomeWindow();

    private final ConnectionsWindow connectionsWindow = new ConnectionsWindow();

    public void render() {
        renderMainMenuBar();
        welcomeWindow.render();
        connectionsWindow.render();
    }

    private void renderMainMenuBar() {
        if (ImGui.beginMainMenuBar())
        {
            if (ImGui.beginMenu("Windows"))
            {
                if (ImGui.menuItem("Welcome", "", welcomeWindow.shouldShow())) {
                    welcomeWindow.toggleShouldShow();
                }
                if (ImGui.menuItem("Sessions", "", connectionsWindow.shouldShow())) {
                    connectionsWindow.toggleShouldShow();;
                }
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
    }

}
