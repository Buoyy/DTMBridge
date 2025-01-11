package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.Account;
import com.github.buoyy.dtm.utils.LinksManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MCCommandLink implements CommandExecutor {
    Account account; String key;
    LinksManager manager;
    public MCCommandLink(LinksManager manager) {
        this.manager = manager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ((sender instanceof Player player)){
        //TODO: if player is already linked, send an "already linked" kind of message
        if (label.equals("dtmlink")) {
            key = Account.genKey();
            player.sendMessage("DM this to the server's DTM bot: " + key);
            }
        }
        return true;
    }
    
    public String getKey() {
        return key;
    }
}
