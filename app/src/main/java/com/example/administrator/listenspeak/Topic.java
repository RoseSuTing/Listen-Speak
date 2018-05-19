package com.example.administrator.listenspeak;

class Topic {
    private String name;
    private String studentId;
    private int studentImg;

    //Setters
    public Topic(String name, String studentId, int studentImg) {
        this.name = name;
        this.studentId = studentId;
        this.studentImg = studentImg;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getImg(){return studentImg;}
}
