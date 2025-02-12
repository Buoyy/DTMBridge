package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class MCCommandSaves implements ISubCommand {
    private final FileConfiguration config;
    public MCCommandSaves(FileConfiguration config) {
        this.config = config;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
                if (config.contains(args[1])) {
                    sender.sendMessage(ChatColor.AQUA+"Info for location "+ChatColor.GREEN+args[1]+':');
                    sender.sendMessage(ChatColor.DARK_GREEN+"World: "+ChatColor.BLUE+config.getString(args[1]+".world"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"X Co-ord: "+ChatColor.BLUE+config.getString(args[1]+".x"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"Y Co-ord: "+ChatColor.BLUE+config.getString(args[1]+".y"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"Z Co-ord: "+ChatColor.BLUE+config.getString(args[1]+".z"));
                }
        } else if (args.length == 1) {
            sender.sendMessage(ChatColor.GREEN+"The following saves are currently available: ");
            for (String i : config.getKeys(false)) {
                sender.sendMessage(ChatColor.AQUA+i);
            }
            sender.sendMessage(ChatColor.GREEN+"To see info on any one of these, do:\n"+ChatColor.AQUA+"/dtm saves <name>");
        }
        return true;
    }
}
