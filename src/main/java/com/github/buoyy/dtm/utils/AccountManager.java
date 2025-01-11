package com.github.buoyy.dtm.utils;

import java.util.ArrayList;
import java.util.UUID;

public class AccountManager {
    private final ArrayList<Account> accounts;
    public AccountManager() {
        this.accounts = new ArrayList<>();
    }
    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    public static String genKey() {
        return UUID.randomUUID().toString();
    }
}
