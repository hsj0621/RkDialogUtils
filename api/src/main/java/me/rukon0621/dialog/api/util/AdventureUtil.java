package me.rukon0621.dialog.api.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public final class AdventureUtil {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private AdventureUtil() {
    }

    public static Component toComponent(String miniMessage) {
        if (miniMessage == null || miniMessage.isEmpty()) {
            return Component.empty();
        }
        return MINI_MESSAGE.deserialize(miniMessage);
    }
}
