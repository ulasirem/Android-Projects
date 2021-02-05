package com.iremulas.simpsonbook.Model;

import java.io.Serializable;

//Serializable ile simpsonList deki verileri seri halinde alabiliriz
public class Simpson implements Serializable {

    private String name;
    private String job;
    private  int pictureInteger;

    public Simpson(String name, String job, int pictureInteger) {
        this.name = name;
        this.job = job;
        this.pictureInteger = pictureInteger;   //android studyo drawballer ı bir sayı atayarak tutar
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public int getPictureInteger() {
        return pictureInteger;
    }
}
