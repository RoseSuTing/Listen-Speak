package com.example.administrator.listenspeak;

class Config {
    public String URL_LOGIN = "http://mpianatra.com/Courses/api/login.php";

    //Register
    public String URL_REGISTER = "http://mpianatra.com/Courses/api/register.php";

    public String getLoginUrl() {
        return URL_LOGIN;
    }

    public String getRegisterUrl() {
        return URL_REGISTER;
    }
}
