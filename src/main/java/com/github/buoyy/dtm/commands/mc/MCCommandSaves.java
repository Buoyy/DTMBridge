package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class MCCommandSaves implements CommandExecutor {
    private final JavaPlugin plugin;
    public MCCommandSaves(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.GREEN + "Following saves are currently available: ");
            for (String i : plugin.getConfig().getKeys(false)) {
                sender.sendMessage(ChatColor.RED + i);
            }
        } else if (args[0] != null) {
            String msg = String.format("World: %s\nX: %s\nY: %s\nZ: %s",
                    plugin.getConfig().getString(args[0]+".world"),
                    plugin.getConfig().getString(args[0]+".x"),
                    plugin.getConfig().getString(args[0]+".y"),
                    plugin.getConfig().getString(args[0]+".z"));
            sender.sendMessage(msg);
        }
        return true;
    }
}
