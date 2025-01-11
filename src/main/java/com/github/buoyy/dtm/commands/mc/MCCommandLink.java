package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.Account;
import com.github.buoyy.dtm.utils.AccountManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MCCommandLink implements CommandExecutor {
    private final AccountManager manager;
    public MCCommandLink(AccountManager manager) {
        this.manager = manager;
    }
    private boolean hasAccount(Player player) {
        for (Account x : manager.getAccounts()) {
            if (x.getPlayer().equals(player)) return true;
        }
        return false;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (hasAccount(player))
                player.sendMessage("Already linked.");
            else {
                String key = AccountManager.genKey();
                Account account = new Account();
                account.setPlayer(player);
                account.setKey(key);
                manager.getAccounts().add(account);
                player.sendMessage("DM this to the bot to link account: "+key);
            }
        }
        return true;
    }
}
