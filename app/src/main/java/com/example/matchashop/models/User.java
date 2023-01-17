package com.example.matchashop.models;

public class User {
    public String Username;
    public String Password;
    public boolean savePassword;
    public boolean loggedIn;
    public User(String Username,String Password,boolean savePassword,boolean loggedIn){
        this.Username = Username;
        this.Password = Password;
        this.savePassword = savePassword;
        this.loggedIn = loggedIn;
    }
}
