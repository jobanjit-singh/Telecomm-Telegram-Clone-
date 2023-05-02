package com.example.telecomm;

public class MessageDataModel {
    String Message;
    String Type;
    public MessageDataModel(String Message,String Type){
        this.Message = Message;
        this.Type = Type;
    }

    public String getMessage() {
        return Message;
    }

    public String getType() {
        return Type;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public void setType(String type) {
        Type = type;
    }
}
