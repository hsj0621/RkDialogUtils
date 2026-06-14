package me.rukon0621.dialog;

import lombok.Getter;
import me.rukon0621.dialog.api.RkDialogAPI;
import me.rukon0621.dialog.core.command.ExampleDialogCommand;
import me.rukon0621.dialog.core.RkDialogAPIImpl;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RkDialogPlugin extends JavaPlugin {

    @Getter
    private static RkDialogAPI API;

    @Override
    public void onEnable() {
        API = new RkDialogAPIImpl();
        Objects.requireNonNull(getCommand("rkdialogexample")).setExecutor(new ExampleDialogCommand());
    }

    @Override
    public void onDisable() {
        API = null;
    }
}
