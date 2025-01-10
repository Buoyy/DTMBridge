package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.Account;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class MCCommandLink implements CommandExecutor {
    Account account;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) sender.sendMessage("Command is player only!");
        // if (!Account.hasAccount(sender)) sender.sendMessage("You are already linked with account: name");
        if (label.equals("dtmlink")) {
            UUID pass = UUID.randomUUID();
            account = new Account((Player)sender, pass.toString());
            sender.sendMessage("Send this to the chat-bot as a DM: "+ pass.toString());
        }
        return true;
    }
}
