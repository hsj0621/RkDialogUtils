package me.rukon0621.dialog.core;

import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.RkDialogAPI;
import me.rukon0621.dialog.core.render.PaperDialogRenderer;
import org.bukkit.entity.Player;

public final class RkDialogAPIImpl implements RkDialogAPI {

    private final PaperDialogRenderer renderer = new PaperDialogRenderer();

    @Override
    public void open(Player player, Dialog dialog) {
        if (player == null || dialog == null) {
            return;
        }
        player.showDialog(renderer.render(dialog));
    }
}
