package com.project.nuguna.nuguna;

/**
 * Created by kwon on 2016-12-28.
 */

public class Singleitem {
    String name;
    String address;
    String talk;

    Singleitem() {}
    Singleitem(String name, String address, String talk){
        this.name = name;
        this.address = address;
        this.talk = talk;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getTalk() {
        return talk;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setTalk(String talk) {
        this.talk = talk;
    }
}
