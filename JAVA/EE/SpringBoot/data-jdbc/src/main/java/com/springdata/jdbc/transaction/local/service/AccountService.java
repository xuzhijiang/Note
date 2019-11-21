package com.springdata.jdbc.transaction.local.service;

public interface AccountService {
    void addAccount(String name, int initMoney);
    void getAllAccount();
}
