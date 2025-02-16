package com.github.buoyy.dtm.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

import com.github.buoyy.dtm.utils.files.CustomYAML;

public class MCCommandSavesInfo implements MCSubCommand {
    private final FileConfiguration config;
    public MCCommandSavesInfo(CustomYAML yaml) {
        this.config = yaml.getConfig();
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 3) {
            if (config.contains(args[2])) {
                List<Integer> coords = config.getIntegerList(args[2]+".coords");
                sender.sendMessage(ChatColor.AQUA+"Info for location "+ChatColor.GREEN+args[2]+':');
                sender.sendMessage(ChatColor.DARK_GREEN+"World: "+ChatColor.BLUE+config.getString(args[2]+".world"));
                sender.sendMessage(ChatColor.DARK_GREEN+"Coordinates: "+ChatColor.BLUE+coords.get(0)+", "+coords.get(1)+", "+coords.get(2));
            }
        } 
        return true;
    }

}
