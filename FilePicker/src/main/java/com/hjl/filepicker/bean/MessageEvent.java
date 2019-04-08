package com.hjl.filepicker.bean;

public class MessageEvent {
    private int code;
    private String message;

    public MessageEvent(){

    }
    public MessageEvent(int code,String message){
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
