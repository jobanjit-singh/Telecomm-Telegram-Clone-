package com.example.telecomm;

public class ChatContactListDataModel {
    String user_Name;
    String profile_Image;
    String user_Number;

    public ChatContactListDataModel(String user_Name, String profile_Image, String user_Number) {
        this.user_Name = user_Name;
        this.profile_Image = profile_Image;
        this.user_Number = user_Number;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getProfile_Image() {
        return profile_Image;
    }

    public String getUser_Number() {
        return user_Number;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public void setProfile_Image(String profile_Image) {
        this.profile_Image = profile_Image;
    }

    public void setUser_Number(String user_Number) {
        this.user_Number = user_Number;
    }
}
