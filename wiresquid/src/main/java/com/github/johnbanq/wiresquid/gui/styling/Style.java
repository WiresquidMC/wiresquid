package com.github.johnbanq.wiresquid.gui.styling;

import imgui.ImGui;
import imgui.ImGuiStyle;
import imgui.ImVec4;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;


public class Style {

    /**
     * configure style of the gui, based on https://github.com/ocornut/imgui/issues/707#issuecomment-468798935
     */
    public static void configureStyle(ImGuiStyle style) {
        /// 0 = FLAT APPEARENCE
        /// 1 = MORE "3D" LOOK
        int is3D = 0;

        style.setColor(ImGuiCol.Text, 1.00f, 1.00f, 1.00f, 1.00f);
        style.setColor(ImGuiCol.TextDisabled, 0.40f, 0.40f, 0.40f, 1.00f);
        style.setColor(ImGuiCol.ChildBg, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.WindowBg, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.PopupBg, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.Border, 0.12f, 0.12f, 0.12f, 0.71f);
        style.setColor(ImGuiCol.BorderShadow, 1.00f, 1.00f, 1.00f, 0.06f);
        style.setColor(ImGuiCol.FrameBg, 0.42f, 0.42f, 0.42f, 0.54f);
        style.setColor(ImGuiCol.FrameBgHovered, 0.42f, 0.42f, 0.42f, 0.40f);
        style.setColor(ImGuiCol.FrameBgActive, 0.56f, 0.56f, 0.56f, 0.67f);
        style.setColor(ImGuiCol.TitleBg, 0.19f, 0.19f, 0.19f, 1.00f);
        style.setColor(ImGuiCol.TitleBgActive, 0.22f, 0.22f, 0.22f, 1.00f);
        style.setColor(ImGuiCol.TitleBgCollapsed, 0.17f, 0.17f, 0.17f, 0.90f);
        style.setColor(ImGuiCol.MenuBarBg, 0.335f, 0.335f, 0.335f, 1.000f);
        style.setColor(ImGuiCol.ScrollbarBg, 0.24f, 0.24f, 0.24f, 0.53f);
        style.setColor(ImGuiCol.ScrollbarGrab, 0.41f, 0.41f, 0.41f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabHovered, 0.52f, 0.52f, 0.52f, 1.00f);
        style.setColor(ImGuiCol.ScrollbarGrabActive, 0.76f, 0.76f, 0.76f, 1.00f);
        style.setColor(ImGuiCol.CheckMark, 0.65f, 0.65f, 0.65f, 1.00f);
        style.setColor(ImGuiCol.SliderGrab, 0.52f, 0.52f, 0.52f, 1.00f);
        style.setColor(ImGuiCol.SliderGrabActive, 0.64f, 0.64f, 0.64f, 1.00f);
        style.setColor(ImGuiCol.Button, 0.54f, 0.54f, 0.54f, 0.35f);
        style.setColor(ImGuiCol.ButtonHovered, 0.52f, 0.52f, 0.52f, 0.59f);
        style.setColor(ImGuiCol.ButtonActive, 0.76f, 0.76f, 0.76f, 1.00f);
        style.setColor(ImGuiCol.Header, 0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.HeaderHovered, 0.47f, 0.47f, 0.47f, 1.00f);
        style.setColor(ImGuiCol.HeaderActive, 0.76f, 0.76f, 0.76f, 0.77f);
        style.setColor(ImGuiCol.Separator, 0.000f, 0.000f, 0.000f, 0.137f);
        style.setColor(ImGuiCol.SeparatorHovered, 0.700f, 0.671f, 0.600f, 0.290f);
        style.setColor(ImGuiCol.SeparatorActive, 0.702f, 0.671f, 0.600f, 0.674f);
        style.setColor(ImGuiCol.ResizeGrip, 0.26f, 0.59f, 0.98f, 0.25f);
        style.setColor(ImGuiCol.ResizeGripHovered, 0.26f, 0.59f, 0.98f, 0.67f);
        style.setColor(ImGuiCol.ResizeGripActive, 0.26f, 0.59f, 0.98f, 0.95f);
        style.setColor(ImGuiCol.PlotLines, 0.61f, 0.61f, 0.61f, 1.00f);
        style.setColor(ImGuiCol.PlotLinesHovered, 1.00f, 0.43f, 0.35f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogram, 0.90f, 0.70f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.PlotHistogramHovered, 1.00f, 0.60f, 0.00f, 1.00f);
        style.setColor(ImGuiCol.TextSelectedBg, 0.73f, 0.73f, 0.73f, 0.35f);
        style.setColor(ImGuiCol.ModalWindowDimBg, 0.80f, 0.80f, 0.80f, 0.35f);
        style.setColor(ImGuiCol.DragDropTarget, 1.00f, 1.00f, 0.00f, 0.90f);
        style.setColor(ImGuiCol.NavHighlight, 0.26f, 0.59f, 0.98f, 1.00f);
        style.setColor(ImGuiCol.NavWindowingHighlight, 1.00f, 1.00f, 1.00f, 0.70f);
        style.setColor(ImGuiCol.NavWindowingDimBg, 0.80f, 0.80f, 0.80f, 0.20f);

        style.setPopupRounding(3);

        style.setWindowPadding(4, 4);
        style.setFramePadding(6, 4);
        style.setItemSpacing(6, 2);

        style.setScrollbarSize(18);

        style.setWindowBorderSize(1);
        style.setChildBorderSize(1);
        style.setPopupBorderSize(1);
        style.setFrameBorderSize(is3D);

        style.setWindowRounding(3);
        style.setChildRounding(3);
        style.setFrameRounding(3);
        style.setScrollbarRounding(2);
        style.setGrabRounding(3);

        style.setTabBorderSize(is3D);
        style.setTabRounding(3);
        style.setColor(ImGuiCol.DockingEmptyBg, 0.38f, 0.38f, 0.38f, 1.00f);
        style.setColor(ImGuiCol.Tab, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.TabHovered, 0.40f, 0.40f, 0.40f, 1.00f);
        style.setColor(ImGuiCol.TabActive, 0.33f, 0.33f, 0.33f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocused, 0.25f, 0.25f, 0.25f, 1.00f);
        style.setColor(ImGuiCol.TabUnfocusedActive, 0.33f, 0.33f, 0.33f, 1.00f);
        style.setColor(ImGuiCol.DockingPreview, 0.85f, 0.85f, 0.85f, 0.28f);

        if ((ImGui.getIO().getConfigFlags() & ImGuiConfigFlags.ViewportsEnable) != 0)
        {
            style.setWindowRounding(0.0f);
            ImVec4 color = style.getColor(ImGuiCol.WindowBg);
            color.w = 1.0f;
            style.setColor(ImGuiCol.WindowBg, color.x, color.y, color.w, color.z);
        }
    }
}
