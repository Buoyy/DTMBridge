package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MCCommandAddSave implements MCSubCommand {
    private final CustomYAML saves;
    public MCCommandAddSave(CustomYAML saves) {
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
        List<Integer> coords = Arrays.asList(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        saves.getConfig().addDefault(args[2]+".world", player.getWorld().getName());
        saves.getConfig().addDefault(args[2]+".coords", coords);
        player.sendMessage(ChatColor.GREEN+args[2]+ChatColor.AQUA+" has been saved as location!");
        saves.save();
        return true;
    }
}
