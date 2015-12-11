package org.odk.collect.android.mitram;

/**
 * Created by tli-6 on 3/12/15.
 */

import android.annotation.SuppressLint;


import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class MitamSave {

private int Id;
    private String Buck;
    private String Nev;
    private String Pint;
    private String Coat;
    private String Lemina;
    private String Pest;
    private String Clr;
    private String Sz;

    public MitamSave() {

        this.Buck="Not available";
        this.Nev="Not available";
        this.Pint="Not available";
        this.Coat="Not available";
        this.Lemina="Not available";
        this.Pest="Not available";
        this.Clr="Not available";
        this.Sz="Not available";
    }

    public String getBuck() {
        return Buck;
    }

    public void setBUck(String Buck) {
        this.Buck = Buck;
    }

    public String getNev() {
        return Nev;
    }

    public void setNev(String Nev) {
        this.Nev = Nev;
    }

    public String getPint() {
        return Pint;
    }

    public void setPint(String Pint) {
        this.Pint = Pint;
    }

    public String getCoat() {
        return Coat;
    }

    public void setCoat(String Coat) {
        this.Coat = Coat;
    }
    public String getLemina() {
        return Lemina;
    }

    public void setLemina(String Lemina) {
        this.Lemina = Lemina;
    }
    public String getPest() {
        return Pest;
    }

    public void setpest(String Pest) {
        this.Pest = Pest;
    }
    public String getClr() {
        return Clr;
    }

    public void setClr(String Clr) {
        this.Clr = Clr;
    }
    public String getSz() {
        return Sz;
    }

    public void setSz(String Sz) {
        this.Sz = Sz;
    }



}

