package com.bw.movie.login.bean;

public class RefurbishMessageBean {
    Object message;
    String flag;

    public RefurbishMessageBean(Object message, String flag) {
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
