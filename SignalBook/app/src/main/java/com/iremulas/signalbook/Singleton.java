package com.iremulas.signalbook;

import android.graphics.Bitmap;

public class Singleton {
    private Bitmap choseImage;
    private static Singleton singleton;

    public Bitmap getChoseImage() {
        return choseImage;
    }

    public void setChoseImage(Bitmap choseImage) {
        this.choseImage = choseImage;
    }

    public static Singleton getInstance(){
        if (singleton == null){
            singleton = new Singleton();
        }
        return singleton;

    }

}
