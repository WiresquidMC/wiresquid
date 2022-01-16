package com.github.johnbanq.wiresquid.gui;

import imgui.ImGui;

/**
 * the "root" window that does the top-level rendering work
 */
public class RootWindow {

    private final WelcomeWindow welcomeWindow = new WelcomeWindow();

    private final SessionsWindow sessionsWindow = new SessionsWindow();

    public void render() {
        renderMainMenuBar();
        welcomeWindow.render();
        sessionsWindow.render();
    }

    private void renderMainMenuBar() {
        if (ImGui.beginMainMenuBar())
        {
            if (ImGui.beginMenu("Windows"))
            {
                if (ImGui.menuItem("Welcome", "", welcomeWindow.shouldShow())) {
                    welcomeWindow.toggleShouldShow();
                }
                if (ImGui.menuItem("Sessions", "", sessionsWindow.shouldShow())) {
                    sessionsWindow.toggleShouldShow();;
                }
                ImGui.endMenu();
            }
            ImGui.endMainMenuBar();
        }
    }

}
