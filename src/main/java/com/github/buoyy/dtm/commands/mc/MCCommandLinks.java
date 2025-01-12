package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.accounts.Account;
import com.github.buoyy.dtm.utils.accounts.AccountManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class MCCommandLinks implements CommandExecutor {
    private final AccountManager manager;
    public MCCommandLinks(AccountManager accountManager) {
        this.manager = accountManager;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage("The following accounts are linked: ");
        for (Account x : manager.getAccounts()) {
            sender.sendMessage(String.format("Discord: %s, Minecraft: %s\n",
                    x.getUser().getEffectiveName(),
                    x.getPlayer().getDisplayName()));
        }
        return true;
    }
}
