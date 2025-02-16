package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MCCommandDeleteSave implements MCSubCommand {
    private final CustomYAML config;
    public MCCommandDeleteSave(CustomYAML saves) {
        this.config = saves;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED+"No location name provided.\nUsage: /dtm delete <save>");
        } else {
           if (config.getConfig().contains(args[2])) {
               config.getConfig().set(args[2], null);
               sender.sendMessage(ChatColor.GREEN + "Deleted save: " + ChatColor.DARK_RED + args[2]);
               config.save();
           } else {
               sender.sendMessage(ChatColor.AQUA + "No such save was found.");
           }
        }
        return true;
    }
}
