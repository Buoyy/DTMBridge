package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MCCommandDelete implements MCSubCommand {
    private final CustomYAML config;
    public MCCommandDelete(CustomYAML saves) {
        this.config = saves;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED+"No location name provided.\nUsage: /dtm delete <save>");
        } else {
           if (config.getConfig().contains(args[1])) {
               config.getConfig().set(args[1], null);
               sender.sendMessage(ChatColor.GREEN + "Deleted save: " + ChatColor.DARK_RED + args[1]);
               config.save();
           } else {
               sender.sendMessage(ChatColor.AQUA + "No such save was found.");
           }
        }
        return true;
    }
}
