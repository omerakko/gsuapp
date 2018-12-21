package com.stork.root.gs.Yemekhane;

/**
 * Created by root on 12.04.2017.
 */

public class gün {

    static String yenigün = "0";

    public String getYenigün() {
        return yenigün;
    }

    public void gündegistir(String gelengün){
        yenigün = gelengün;
    }


}
