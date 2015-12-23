package org.odk.collect.android.mitram;

public class MitramTemp {

    public int Buck= 0;
    public  int Nev =0;
    public  int Pint = 0;

    public  int Coat = 0;
    public int Lemina = 0;

    public   int Pest = 0;
    public   int Clr= 0;
    public  int Clr1=0;
    public   int Sz = 0;

public  String belt_det="Left some question";




    public MitramTemp() {

    }
    public void show(){
        System.out.println("Buckle "+Buck+"Nevar"+Nev+"Pinting"+Pint+"Coating"+Coat+"Lemina"+Lemina+"Pesting"+Pest+"Color"+Clr+"Color1"+Clr1+"Size"+Sz);


    }
    public void put(String d){

       belt_det=d;

    }
    public String get(){
        return  belt_det;
    }
}