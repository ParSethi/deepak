package org.odk.collect.android.mitram;

/**
 * Created by tli-6 on 3/12/15.
 */

import android.annotation.SuppressLint;


import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class MitramSaveTie {

    private int Id;
    private String Type1;
    private String Size;

    private String Fita;
    private String Logo;


    public MitramSaveTie() {

       this.Type1="";
        this.Size="";
        this.Logo="";
        this.Fita="";
    }

    public String getType() {
        return Type1;
    }

    public void setType(String Type1) {
        this.Type1 = Type1;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getFita() {
        return Fita;
    }

    public void setFita(String Fita) {
        this.Fita = Fita;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String Logo) {
        this.Logo = Logo;
    }




}

