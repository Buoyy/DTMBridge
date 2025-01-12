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

import java.util.Map;
import java.util.Objects;

public class MCCommandLink implements CommandExecutor {
    private final AccountManager manager;
    private final FileConfiguration config;
    public MCCommandLink(AccountManager manager, YAMLLoader yamlLoader) {
        this.manager = manager;
        this.config = manager.getLoader().getConfig();
    }

    private boolean hasAccount(String playerID) {
        Map<String, Object> linksSection = Objects.requireNonNull(config.getConfigurationSection("links"))
                .getValues(false);
        for (String x : linksSection.keySet()) {
            if (x.equals(playerID)) return true;
        }
        return false;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (hasAccount(player.getUniqueId().toString()))
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
