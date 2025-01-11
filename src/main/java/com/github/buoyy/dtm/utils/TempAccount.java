package com.github.buoyy.dtm.utils;

import org.bukkit.entity.Player;
import com.github.buoyy.dtm.utils.Account;

public class TempAccount {
    private final Player player;
    private final String key;
    public TempAccount(Player player) {
        this.player = player;
        this.key = Account.genKey();
    }
    public String getKey() {
        return key;
    }
    public Player getPlayer() {
        return player;
    }
    public boolean matchesKey(String key) {
        return this.key.equals(key);
    }
}