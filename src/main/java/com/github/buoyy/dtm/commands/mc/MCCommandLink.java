package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.TempAccount;
import com.github.buoyy.dtm.utils.LinksManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MCCommandLink implements CommandExecutor {
    private final LinksManager manager;
    public MCCommandLink(LinksManager manager) {
        this.manager = manager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((sender instanceof Player player)){
        //TODO: if player is already linked, send an "already linked" kind of message
            TempAccount temp = manager.createTemp(player);
            player.sendMessage("Send this to the bot's DM to link accounts: "+temp.getKey());
        }
        return true;
    }
}
