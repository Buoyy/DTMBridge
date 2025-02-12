package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCCommandSave implements ISubCommand {
    private final CustomYAML saves;
    public MCCommandSave(CustomYAML saves) {
        this.saves = saves;
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED+"Player command ONLY.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED+"No name specified for save location.\n"+ChatColor.AQUA+"Usage: /dtm save <name>");
            return true;
        }
        saves.getConfig().addDefault(args[1]+".world", player.getWorld().getName());
        saves.getConfig().addDefault(args[1]+".x", player.getLocation().getBlockX());
        saves.getConfig().addDefault(args[1]+".y", player.getLocation().getBlockY());
        saves.getConfig().addDefault(args[1]+".z", player.getLocation().getBlockZ());
        player.sendMessage(ChatColor.GREEN+args[1]+ChatColor.AQUA+" has been saved as location!");
        saves.save();
        return true;
    }
}
