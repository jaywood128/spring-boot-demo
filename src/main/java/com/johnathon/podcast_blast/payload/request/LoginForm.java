package com.johnathon.podcast_blast.payload.request;

public class LoginForm {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginForm (String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
