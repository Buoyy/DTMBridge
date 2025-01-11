package com.github.buoyy.dtm.utils;

import net.dv8tion.jda.api.entities.Member;
import org.bukkit.entity.Player;

import java.util.UUID;

// Account will be linked between Discord and Minecraft.
// Players will even be able to send quick DMs through Minecraft or Discord.
public class Account {
    private final Player player;
    private final Member user;
    private boolean linked;
    
    // All accounts when generated shouldn't be linked. Will only be done when player tries to link it. 
    public Account(Player player, Member user) {
        this.player = player;
        this.user = user;
        this.linked = false;
    }
    
    public static String genKey() {
        return UUID.randomUUID().toString();
    }
    
    public void setLinked(boolean state) {
        linked = state;
    }
    
    public boolean isLinked() {
        return linked;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public Member getUser() {
        return user;
    }
}
