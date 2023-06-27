package org.springboot.routing.datasource.core.advanced.entity;

public class UserAccount {

    private Integer id;

    private String account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", account='" + account + '\'' +
                '}';
    }
}
