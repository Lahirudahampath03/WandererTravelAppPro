package com.example.wanderertravelapp.Class;

public class Admin {
    private String email;
    private String username;
    private String password;
    private String type;

    public Admin()
    {

    }

    public Admin(String e, String u, String p, String s)
    {
        this.email = e;
        this.username = u;
        this.password = p;
        this.type = s;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
