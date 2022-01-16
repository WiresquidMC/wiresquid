package com.github.johnbanq.wiresquid.gui.styling;

import com.google.common.io.ByteStreams;
import imgui.*;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;


/**
 * central hub to hold all loaded fonts
 */
public class Fonts {

    public static ImFont REGULAR;
    public static ImFont ITALIC;
    public static ImFont BOLD;

    @SneakyThrows
    public static void loadFonts() {
        ImFontAtlas fonts = ImGui.getIO().getFonts();
        REGULAR = loadFontFromResouece(fonts, "Roboto-Regular.ttf");
        ITALIC = loadFontFromResouece(fonts, "Roboto-Italic.ttf");
        BOLD = loadFontFromResouece(fonts, "Roboto-Bold.ttf");
        fonts.build();
    }

    private static ImFont loadFontFromResouece(ImFontAtlas fonts, String filename) throws IOException {
        InputStream fontStream = Fonts.class.getClassLoader().getResourceAsStream(filename);
        assert fontStream != null;
        return fonts.addFontFromMemoryTTF(ByteStreams.toByteArray(fontStream), 18.0f);
    }

}
