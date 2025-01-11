package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.User;
import org.bukkit.entity.Player;

public class Account {

    private Player player;
    private User user;
    private boolean linked;
    private String key;

    public void setKey(String key) {
        this.key = key;
    }
    public String getKey() {
        return key;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public boolean isLinked() {
        return linked;
    }
    public void setLinked(boolean linked) {
        this.linked = linked;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
