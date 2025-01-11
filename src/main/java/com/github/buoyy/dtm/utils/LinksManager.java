package com.github.buoyy.dtm.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import com.github.buoyy.dtm.utils.Account;
import com.github.buoyy.dtm.utils.TempAccount;
import org.bukkit.entity.Player;
import net.dv8tion.jda.api.entities.Member;

//TODO: Keep genKey() method here instead
public class LinksManager {
    private ArrayList<TempAccount> temps;
    private HashMap<Player, String> idConnector;
    private ArrayList<Account> accounts;
    public LinksManager() {
        this.temps = new ArrayList<>();
        this.accounts = new ArrayList<>();
        this.idConnector = new HashMap<>();
    }
    public TempAccount createTemp(Player player) {
        TempAccount temp = new TempAccount(player);
        temps.add(temp);
        return temp;
    }
    public ArrayList<TempAccount> getTemps() {
        return temps;
    }
    public HashMap<Player, String> getConnector() {
        return idConnector;
    }
    public boolean verifyAndLink(Player player, Member user, String key) {
        Iterator<TempAccount> it = temps.iterator();
        while (it.hasNext()) {
            TempAccount temp = it.next();
            if (temp.getPlayer().equals(Player) && temp.matchesKey(key)) {
                // Verification successful. So remove temp
                it.remove();
                Account acc = new Account(player, user);
                acc.setLinked(true);
                accounts.add(acc);
            }
        }
    }
}