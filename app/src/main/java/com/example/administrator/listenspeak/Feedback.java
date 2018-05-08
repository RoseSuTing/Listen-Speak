package com.example.administrator.listenspeak;

import android.os.Parcel;
import android.os.Parcelable;

class Feedback implements Parcelable{
    private String name;
    private String error_message;
    public final int SUCCESS = 1;
    public final int FAIL = 0;

    Feedback(){ }

    protected Feedback(Parcel in) {
        name = in.readString();
        error_message = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(error_message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Feedback> CREATOR = new Parcelable.Creator<Feedback>() {
        @Override
        public Feedback createFromParcel(Parcel in) {
            return new Feedback(in);
        }

        @Override
        public Feedback[] newArray(int size) {
            return new Feedback[size];
        }
    };



    //Getters and Setters
    public String getName() {
        return name;
    }

    public String getError_message() {
        return error_message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
