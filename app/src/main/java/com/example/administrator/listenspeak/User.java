package com.example.administrator.listenspeak;

class User {
    private String Username;
    private String Title;
    private int UserImg;
    private int TitleImg;

    //Setters
    public User(String Username, String Title, int UserImg,int TitleImg) {
        this.Username = Username;
        this.Title = Title;
        this.UserImg = UserImg;
        this.TitleImg = TitleImg;
    }

    //Getters
    public String getUsername() {
        return Username;
    }
    public String getTitle() {
        return Title;
    }

    public int getUserImg() {
        return UserImg;
    }

    public int getTitleImg(){return TitleImg;}
}
