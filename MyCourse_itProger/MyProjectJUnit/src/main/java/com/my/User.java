package com.my;

public class User {
    private String login;
    private String email;

    User() {
    }

    User(String login, String email) {
        this.login = validLogin(login);
        this.email = validEmail(email);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = validLogin(login);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = validEmail(email);
    }

    private String validLogin(String login) {
        String s = null;
        if (login.length() >= 5) s = login;
        return s;
    }

    private String validEmail(String email) {
        String s = null;
        int num1 = email.indexOf("@");
        int num2 = email.indexOf(".", num1);
        if ((num1 != -1 && num1 != 0) && num2 != -1) {
            s = email;
        }
        return s;
    }
}
