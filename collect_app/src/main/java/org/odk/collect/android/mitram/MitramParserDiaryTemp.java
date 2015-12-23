package org.odk.collect.android.mitram;

public class MitramParserDiaryTemp {

    public int tydry = 0;
    public int cvrpdsn = 0;
    public int cvrpdsn1 = 0;
    public int cvrpp = 0;
    public int cvrpp1 = 0;
    public int cvrpp2 = 0;

    public int lamintn = 0;
    public int lamintn1 = 0;

    public int inner = 0;
    public int inner1 = 0;
    public int pagen = 0;
    public int pagen1 = 0;
    public int color = 0;
    public int color1 = 0;
    public int color2 = 0;
    public int photo = 0;
    public int cnt = 0;
    public int cnt1 = 0;
    public int cntclr = 0;
    public int cntclr1 = 0;
    public int cntclr2 = 0;
    public int pagentxt = 0;
    public int pagentxt1 = 0;
    public int pagentxt2 = 0;
public  int dryn=0;
    public static  String dir_del = "You did not choose some question";

    public MitramParserDiaryTemp() {

    }
    public void show(){
        System.out.println("tydry "+tydry+" cvrpdsn "+cvrpdsn+" cvrpdsn1 "+cvrpdsn1+" cvrpp "+cvrpp+" cvrpp1 "+cvrpp1+" cvrpp2 "+cvrpp2+" lamintn "+"\n"+lamintn+" lamintn1 "+lamintn1+" inner "+inner+" inner1 "+inner1+"\n"+" pagen "+pagen+" pagen1 "+pagen1+" color"+ color+" color1 "+color1+"\n"+" color2 "+color2+"photo "+photo+"content "+cnt+" cnt1"+cnt1+"\n"+" cntclor"+cntclr+" cnt color 1"+cntclr1+" cnt color 2"+cntclr2+"\n"+"pagetxt"+pagentxt+"pagetxt1"+pagentxt1+"pagetxt 2"+pagentxt2+"no of diary"+dryn);



    }
    public void put(String d){
        dir_del=d;
    }
    public String get(){
        return  dir_del;
    }
}
