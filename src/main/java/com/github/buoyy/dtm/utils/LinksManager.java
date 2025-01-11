package com.github.buoyy.dtm.utils;

import java.util.ArrayList;
import com.github.buoyy.dtm.utils.Account;
public class LinksManager {
    private ArrayList<Account> accounts;
    public void initAccount() {
        Account account = accounts.add(new Account());
        return account;
    }
}