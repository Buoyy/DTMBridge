package com.github.buoyy.dtm.utils.accounts;

import com.github.buoyy.dtm.utils.YAMLLoader;

import java.util.ArrayList;
import java.util.UUID;

public class AccountManager {
    private final ArrayList<Account> accounts;
    private final YAMLLoader loader;
    public AccountManager(YAMLLoader loader) {
        this.loader = loader;
        this.accounts = new ArrayList<>();
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
