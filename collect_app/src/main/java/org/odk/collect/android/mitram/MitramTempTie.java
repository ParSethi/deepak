package org.odk.collect.android.mitram;

public class MitramTempTie {

    public  int Type= 0;
    public  int Size =0;
    public int Fita = 0;

    public  int Logo = 0;
    public String tie_response="You did not choose some questions";






    public MitramTempTie() {

    }
    public void put(String d){
tie_response=d;
    }
    public  String get(){
        return tie_response;
    }
    public void show(){
System.out.println("Type "+Type+" Size "+Size+" Fita "+Fita+" Logo "+Logo);



    }
}