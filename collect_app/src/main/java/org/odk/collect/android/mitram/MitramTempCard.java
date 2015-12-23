package org.odk.collect.android.mitram;

public class MitramTempCard {

    public  int typec= 0;
    public  int orn =0;
    public int prnt= 0;

    public  int hangtm=0;

    public  int hanga=0;
    public  int hangps=0;
    public  int hangpp=0;
    public  int szs=0;
    public  int szf=0;
    public  int szr=0;
public  int clp=0;//for tall card
    public  int clpoth=0;//for ll except tall card
    public  int hold=0;//for atm,poch st and pouch premiun
    public  int holdm=0;
    public  int holdl=0;
   public int insert=0;
    public int card=0;// for pouch std $preminu,lamination
    public  int pro=0;
public String card_del="You left some questions";

public void show(){


}
    public void put(String d){
        card_del=d;

    }

    public String get() {
        return card_del;
    }

    public MitramTempCard() {

    }
}