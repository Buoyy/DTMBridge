package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MCCommandSave implements CommandExecutor {
    private final CustomYAML saves;
    public MCCommandSave(CustomYAML saves) {
        this.saves = saves;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage(ChatColor.RED+"Player command ONLY.");
        if (args.length == 0) sender.sendMessage(ChatColor.RED+"No name specified for save location.");
        Player player = (Player) sender;
        saves.getConfig().addDefault(args[0]+".world", player.getWorld().getName());
        saves.getConfig().addDefault(args[0]+".x", player.getLocation().getBlockX());
        saves.getConfig().addDefault(args[0]+".y", player.getLocation().getBlockY());
        saves.getConfig().addDefault(args[0]+".z", player.getLocation().getBlockZ());
        player.sendMessage(ChatColor.GREEN+args[0]+ChatColor.AQUA+" has been saved as location!");
        saves.save();

        return true;
    }
}
