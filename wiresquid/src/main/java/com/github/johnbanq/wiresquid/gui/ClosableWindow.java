package com.github.johnbanq.wiresquid.gui;

import imgui.type.ImBoolean;

public interface ClosableWindow {

    public ImBoolean getShowProperty();

    public default boolean shouldShow() {
        return getShowProperty().get();
    }

    public default void toggleShouldShow() {
        getShowProperty().set(!getShowProperty().get());
    }

}
