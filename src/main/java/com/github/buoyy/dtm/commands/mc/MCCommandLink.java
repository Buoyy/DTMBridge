package com.github.buoyy.dtm.commands.mc;

import com.github.buoyy.dtm.utils.YAMLLoader;
import com.github.buoyy.dtm.utils.accounts.Account;
import com.github.buoyy.dtm.utils.accounts.AccountManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.Objects;

public class MCCommandLink implements CommandExecutor {
    private final AccountManager manager;
    private final FileConfiguration config;
    public MCCommandLink(AccountManager manager) {
        this.manager = manager;
        this.config = manager.getLoader().getConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (manager.hasAccount(player.getUniqueId().toString()))
                player.sendMessage("Already linked with "+
                manager.getLinkedUser(player.getUniqueId().toString()).getEffectiveName());
            else {
                
            }
        } else {
          sender.sendMessage("This is a player-only command. Try running in-game.");
        }
        return true;
    }
}
