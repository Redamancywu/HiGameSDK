package com.hi.base.data;

public class HiEmailAccount {
    private String email;

    private String password;

    public HiEmailAccount(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public HiEmailAccount() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
