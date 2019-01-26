package com.bw.movie.main.movie.bean;

public class MessageBean {
    public Object meaaage;
    public  String flag;
    public MessageBean(Object meaaage, String flag) {
        this.meaaage = meaaage;
        this.flag = flag;
    }

    public Object getMeaaage() {
        return meaaage;
    }

    public void setMeaaage(Object meaaage) {
        this.meaaage = meaaage;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
