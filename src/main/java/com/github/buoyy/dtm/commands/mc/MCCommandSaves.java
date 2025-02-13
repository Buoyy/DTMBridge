package com.github.buoyy.dtm.commands.mc;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import com.github.buoyy.dtm.utils.files.CustomYAML;

public class MCCommandSaves implements MCSubCommand {
    private final FileConfiguration config;
    public MCCommandSaves(CustomYAML yaml) {
        this.config = yaml.getConfig();
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 2) {
                if (config.contains(args[1])) {
                    List<Integer> coords = config.getIntegerList(args[1]+".coords");
                    sender.sendMessage(ChatColor.AQUA+"Info for location "+ChatColor.GREEN+args[1]+':');
                    sender.sendMessage(ChatColor.DARK_GREEN+"World: "+ChatColor.BLUE+config.getString(args[1]+".world"));
                    sender.sendMessage(ChatColor.DARK_GREEN+"Coordinates: "+ChatColor.BLUE+coords.get(0)+','+coords.get(1)+','+coords.get(2));
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
