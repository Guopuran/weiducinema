package com.bw.movie.main.bean;

public class LocationMessageBean {
    Object message;
    String flag;

    public LocationMessageBean(Object message, String flag) {
        this.message = message;
        this.flag = flag;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
