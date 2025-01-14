package com.github.buoyy.dtm.utils.accounts;

import com.github.buoyy.dtm.utils.YAMLLoader;
import org.bukkit.configuration.file.FileConfiguration;
import net.dv8tion.api.jda.JDA;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AccountManager {
    private final ArrayList<Account> accounts;
    private final JDA jda;
    private final FileConfiguration config;
    private final Set<String> yamlIndices;
    
    public AccountManager(JDA jda, YAMLLoader loader) {
        this.accounts = new ArrayList<>();
        this.config = loader.getConfig();
        this.jda = jda;
        this.yamlIndices = this.config.getKeys(false);
        loadSavedAccounts();
    }
    public void loadSavedAccounts() {
        for (String i : yamlIndices) {
            String mcId = config.getString(i+".mc-id");
            String discordId = config.getString(i+"discord-id");
            String key = config.getString(i+".key");
            Account account = new Account();
            account.setPlayer(Bukkit.getPlayer(mcId));
            account.setUser(jda.getUserById(discordId));
            account.setKey(key);
            accounts.add(account);
        }
    }
    public boolean hasAccount(String mcId) {
      for (String i : yamlIndices) {
        if (mcId.equals(config.getString(i+".mc-id"))) return true;
      }
      return false;
    }
    public String getLinkedUser(String mcId) {
      for (String i : yamlIndices) {
        if (hasAccount(mcId)) {
          return jda.getUserById(config.getString(i+".discord-id"));
        }
      }
    }
    public void addAccount(String mcId) {
      int next = yamlIndices.size();
      config.set(next+".mc-id", mcId);
      config.set(next+".discord-id", null);
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public static String genKey() {
        return UUID.randomUUID().toString();
    }
}
