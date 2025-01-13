package com.github.buoyy.dtm.utils.accounts;

import com.github.buoyy.dtm.utils.YAMLLoader;
import org.bukkit.configuration.file.FileConfiguration;
import net.dv8tion.api.jda.JDA;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;

public class AccountManager {
    private final ArrayList<Account> accounts;
    private final YAMLLoader loader;
    private final JDA jda;
    private final FileConfiguration config;
    public AccountManager(JDA jda, YAMLLoader loader) {
        this.accounts = new ArrayList<>();
        this.loader = loader;
        this.config = loader.getConfig();
        this.jda = jda;
        loadAccounts(loader);
    }
    public void loadAccounts(YAMLLoader loader) {
        Set<String> yamlIndices = config.getKeys(false);
        for (String i : yamlIndices) {
            String mcId = config.getString(i+".mc-id");
            String discordId = config.getString(i+"discord-id");
            Account account = new Account();
            account.setPlayer(Bukkit.getPlayer(mcId));
            account.setUser(jda.getUserById(discordId));
            accounts.add(account);
        }
    }
    public void addAccount(String mcId, String discordId) {
      int next = yamlIndices.size();
      config.set(next+".mc-id", mcId);
      config.set(next+".discord-id", discordId);
      loader.saveConfig();
    }
    public YAMLLoader getLoader() {
        return loader;
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public static String genKey() {
        return UUID.randomUUID().toString();
    }
}
