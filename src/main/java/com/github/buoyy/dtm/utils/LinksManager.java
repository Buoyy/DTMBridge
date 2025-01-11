package com.github.buoyy.dtm.utils;

import java.util.ArrayList;
import com.github.buoyy.dtm.utils.Account;
import com.github.buoyy.dtm.utils.TempAccount;
import org.bukkit.entity.Player;

//TODO: Keep genKey() method here instead
public class LinksManager {
    private ArrayList<TempAccount> temps;
    public LinksManager() {
        this.temps = new ArrayList<>();
    }
    public TempAccount createTemp(Player player) {
        TempAccount temp = new TempAccount(player);
        temps.add(temp);
        return temp;
    }
}