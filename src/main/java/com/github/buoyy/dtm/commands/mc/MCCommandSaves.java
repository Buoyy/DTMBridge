package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class MCCommandSaves implements CommandExecutor {
    private final FileConfiguration config;
    public MCCommandSaves(FileConfiguration config) {
        this.config = config;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            for (String i : config.getKeys(false)) {
                if (i.equals(args[0])) {
                    sender.sendMessage(ChatColor.AQUA+"Info for location "+ChatColor.GREEN+args[0]+':');
                    sender.sendMessage(ChatColor.DARK_GREEN+"World: "+ChatColor.BLUE+config.getString(args[0]+".world"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"X Co-ord: "+ChatColor.BLUE+config.getString(args[0]+".x"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"Y Co-ord: "+ChatColor.BLUE+config.getString(args[0]+".y"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"Z Co-ord: "+ChatColor.BLUE+config.getString(args[0]+".z"));
                    break;
                }
            }
        } else {
            sender.sendMessage(ChatColor.GREEN+"The following saves are currently available: ");
            for (String i : config.getKeys(false)) {
                sender.sendMessage(ChatColor.AQUA+i);
            }
        }

        return true;
    }
}
