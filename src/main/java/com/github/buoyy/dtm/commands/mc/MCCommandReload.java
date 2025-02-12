package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MCCommandReload implements CommandExecutor {
    private final JavaPlugin plugin;
    private final CustomYAML saves;
    public MCCommandReload(JavaPlugin plugin, CustomYAML saves) {
        this.plugin = plugin;
        this.saves = saves;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        plugin.reloadConfig();
        saves.reload();
        commandSender.sendMessage("Reload complete.");
        return true;
    }
}
