package me.rukon0621.dialog;

import me.rukon0621.dialog.api.Dialog;
import me.rukon0621.dialog.api.RkDialogAPI;
import me.rukon0621.dialog.core.command.ExampleDialogCommand;
import me.rukon0621.dialog.core.render.PaperDialogRenderer;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class RkDialogPlugin extends RkDialogAPI {


    public RkDialogPlugin() {
        inst = this;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getCommand("rkdialogexample")).setExecutor(new ExampleDialogCommand());
    }

    @Override
    public void onDisable() {
    }

    private final PaperDialogRenderer renderer = new PaperDialogRenderer();

    @Override
    public void open(Player player, Dialog dialog) {
        if (player == null || dialog == null) {
            return;
        }
        player.showDialog(renderer.render(dialog));
    }
}
