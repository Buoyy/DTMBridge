package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.files.CustomYAML;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.jetbrains.annotations.NotNull;

public class MCCommandSave implements CommandExecutor {

    private final TextChannel channel;
    private final CustomYAML saves;
    public MCCommandSave(TextChannel channel, CustomYAML saves) {
        this.channel = channel;
        this.saves = saves;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage("Player command only.");
        if (args.length == 0) sender.sendMessage("Name of the location was not provided");
        assert sender instanceof Player;
        Player p = (Player) sender;
        saves.getConfig().set(args[0]+".world", p.getWorld().getName());
        saves.getConfig().set(args[0]+".x", p.getLocation().getBlockX());
        saves.getConfig().set(args[0]+".y", p.getLocation().getBlockY());
        saves.getConfig().set(args[0]+".z", p.getLocation().getBlockZ());
        saves.saveConfig();
        p.sendMessage(ChatColor.AQUA + "Location "+ ChatColor.GREEN + args[0] + ChatColor.AQUA + " was successfully set as a save.");
        channel.sendMessage("Location **"+ args[0] + "** was successfully set as a save.").queue();
        // Note: save the discord message by ID
        return true;
    }
}
