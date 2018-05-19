package com.example.administrator.listenspeak;

class Diary {
    private String Month;
    private String Week;
    private String Time;
    private String Title;
    private int TitleImg;

    //Setters
    public Diary(String Username, String Week, String Time,String Title,int TitleImg) {
        this.Month = Month;
        this.Title = Title;
        this.Week = Week;
        this.Time=Time;
        this.TitleImg = TitleImg;
    }

    //Getters
    public String getMonth() { return Month; }
    public String getTitle() {
        return Title;
    }
    public String getWeek() {
        return Week;
    }
    public String getTime() {
        return Time;
    }
    public int getTitleImg(){return TitleImg;}
}
