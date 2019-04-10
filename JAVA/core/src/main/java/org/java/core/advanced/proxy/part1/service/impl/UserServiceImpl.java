package org.java.core.advanced.proxy.part1.service.impl;

import org.java.core.advanced.proxy.part1.service.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void addUser() {
        System.out.println("add user");
    }

    @Override
    public void searchUser() {
        System.out.println("search User");
    }

    @Override
    public void removeUser() {
        System.out.println("remove User");
    }

}
