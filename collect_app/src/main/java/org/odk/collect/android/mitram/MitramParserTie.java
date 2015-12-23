package org.odk.collect.android.mitram;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.odk.collect.android.mitram.Belt;
import org.odk.collect.android.mitram.MitamSave;
import org.odk.collect.android.mitram.MitramTemp;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.logic.FormController;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("NewApi")
public class MitramParserTie {


    private static final String t = MitamParser.class
            .getSimpleName();

    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;
public  static  Integer icount = 0;
    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    public static Map<Integer,MitramTempTie> RespodentList = new LinkedHashMap<Integer,MitramTempTie>();

   /* public static Map<Integer, MitramSaveTie> selectedRespodentList = new LinkedHashMap<Integer, MitramSaveTie>();





    public static Map<Integer,MitramSaveTie> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramSaveTie getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramSaveTie) selectedRespodentList.get(in);
    }

*/

    public static synchronized void parseResponseXML() {

      //  RespodentList = new LinkedHashMap<Integer, MitramTemp>();
       // selectedRespodentList = new LinkedHashMap<Integer, MitramSaveTie>();
        //motherList = new LinkedHashMap<Integer, String>();

        FormController formController = Collect.getInstance()
                .getFormController();
        // String formname = formController.getFormTitle();
        try {
            InputStream is = formController.getFilledInFormXml()
                    .getPayloadStream();


            pullparserfactory = XmlPullParserFactory.newInstance();

            parser = pullparserfactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);
            MitramTempTie respTemp = null;

            eventType = parser.getEventType();

            String form_id = "";
            while (form_id.isEmpty()) {

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d("parsing1 ", "start of document1");
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        Log.d(t + " tag name1:", tagName);
                        if (tagName.equalsIgnoreCase("data")) {
                            form_id = parser.getAttributeValue(0);
                            Log.d(t + " form_id1:", form_id);
                        }
                        break;
                }
                eventType = parser.next();

            }




            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_DOCUMENT:
                        Log.d("parsing ", "start of document");
                        break;
                    case XmlPullParser.START_TAG:
                        tagName = parser.getName();
                        tagValue = "";

                        if (tagName.equalsIgnoreCase("data")) {
                            form_id = parser.getAttributeValue(0);
                            Log.d(t + " form_id:", form_id);

                        }
                       else if (tagName.equals("q1_tie")) {

                            respTemp=new MitramTempTie();
                            parser.next();
                            tagValue = parser.getText();

                            System.out.println("type bbb  "+tagValue);
                            respTemp.Type =Integer.parseInt(tagValue);


                        } else if (tagName.equals("q2_tie")) {
                            parser.next();

                            tagValue = parser.getText();
                            System.out.println("Size"+tagValue);
                            respTemp.Size = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q3_tie")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("Fita"+tagValue);
                            respTemp.Fita = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q4_tie")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("Logo" + tagValue);
                            respTemp.Logo = Integer.parseInt(tagValue);
System.out.println("befor generate");
                            if(respTemp!=null){
                            GenrateList(respTemp);
                            System.out.println("aftre generate");
                            RespodentList.put(icount, respTemp);}
                            icount++;
                        }



                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception ex) {

        }



    }
    private static void GenrateList(MitramTempTie respTemp) {
        BeltImg.heading="Tie Image";
        BeltImg.path="/pic/tie.jpg";
        String tie_response="a question is left";
boolean flag=false;
       // MitramSaveTie resp;
System.out.println("america");
            Integer i=1;
           // resp = new MitramSaveTie();

if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==1){
tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75mm"+"\n\n"+"Logo:no logo";
    System.out.println("inside if");
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("0.75 mm");
    resp.setLogo("No Logo");*/
    //flag=true;
    Tie.type="1";
    respTemp.put(tie_response);
}
            else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75mm"+"\n\n"+"Logo:Hand logo";
                /*resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="2";
    respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75mm"+"\n\n"+"Logo:Machine Embroiderylogo";
                /*resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="3";
    respTemp.put(tie_response);
            }
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:no logo";
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("NoLogo");
    flag=true;*/
    Tie.type="4";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Hand logo";
   /* resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="5";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Machine Embroiderylogo";
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="6";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:no logo";
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("NoLogo");
    flag=true*/;
    Tie.type="7";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Hand logo";
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="8";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Machine Embroiderylogo";
    /*resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="9";respTemp.put(tie_response);
}//hh
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:no logo";
                /*resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
    flag=true;*/
    Tie.type="10";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Hand logo";
                /*resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="11";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Machine Embroiderylogo";
               /* resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="12";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:no logo";
                /*resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
    flag=true;*/
    Tie.type="13";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Hand logo";
                /*resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="14";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Machine Embroiderylogo";
               /* resp.setType("Simple");
                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    ;*/respTemp.put(tie_response);
    Tie.type="15";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==1){
        tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:no logo";
                /*resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");
    flag=true;*/
    Tie.type="16";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==2){
        tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Hand logo";
               /* resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="17";respTemp.put(tie_response);
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==3){
        tie_response="Type:Simple"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Machine Embroiderylogo";
                /*resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="18";respTemp.put(tie_response);
            }

else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:no logo";
    /*resp.setType("Simple");
    resp.setSize("48 inch");
    resp.setLogo("NO Logo");
    flag=true;*/
    Tie.type="19";respTemp.put(tie_response);
}

else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:Hand logo";
   /* resp.setType("Simple");
    resp.setSize("48 inch");

    resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="20";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:Machine Embroiderylogo";
    /*resp.setType("Simple");
    resp.setSize("48 inch");

    resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="21";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==1){
    tie_response="Type:Simple"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:no logo";
   /* resp.setType("Simple");
    resp.setSize("48 inch");
    resp.setLogo("NO Logo");
    flag=true;*/
    Tie.type="22";respTemp.put(tie_response);
}

else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==2){
    tie_response="Type:Simple"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:Hand logo";
    /*resp.setType("Simple");
    resp.setSize("48 inch");

    resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="23";respTemp.put(tie_response);
}
else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==3){
    tie_response="Type:Simple"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:Machine Embroiderylogo";
//    resp.setType("Simple");
//    resp.setSize("48 inch");
//
//    resp.setLogo("Machine Embroidery Logo");
//    flag=true;
    Tie.type="24";respTemp.put(tie_response);
}


         //for fancy
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==1){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:No logo";
                /*resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
    flag=true;*/
    Tie.type="25";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==2){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Hand logo";
                /*resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="26";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==3){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Machine Embroidery logo";
                /*resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="27";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==1){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:No logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
    flag=true;*/
    Tie.type="28";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==2){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Hand logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="29";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==3){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Machine Embroidery logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="30";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==1){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:No logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");
    flag=true;*/
    Tie.type="31";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==2){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Hand logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
    flag=true;*/
    Tie.type="32";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==3){
    tie_response="Type:Fancy"+"\n\n"+"Size:11 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Machine Embroidery logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
    Tie.type="33";respTemp.put(tie_response);
            }//hh
            if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==1){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:No logo";
               /* resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
                flag=true;*/
                Tie.type="34";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==2){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Hand logo";
                /*resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
                flag=true;*/
                Tie.type="35";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==3){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:0.75 mm"+"\n\n"+"Logo:Machine Embroidery logo";
                /*resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true;*/
                Tie.type="36";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==1){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:No logo";
               /* resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
                flag=true*/;
                Tie.type="37";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==2){
                tie_response="Type:Fancy"+"\n\n" + "Size:14 inch"+"\n\n"+"Fita:1 mm" + "\n\n" + "Logo:Hand logo";
                /*resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
                flag=true;*/
                Tie.type="38";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==3){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1 mm"+"\n\n"+"Logo:Machine Embroidery logo";
               /* resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true;*/
                Tie.type="39";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==1){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:No logo";
              /*  resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");*/

                Tie.type="40";respTemp.put(tie_response);
               // flag=true;
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==2){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Hand logo";
               /* resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
                flag=true;*/
                Tie.type="41";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==3){
                tie_response="Type:Fancy"+"\n\n"+"Size:14 inch"+"\n\n"+"Fita:1.5 mm"+"\n\n"+"Logo:Machine Embroidery logo";
                /*resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true*/;
                Tie.type="42";respTemp.put(tie_response);
            }
//here
            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==1){
                tie_response="Type:Fancy"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:no logo";
    /*resp.setType("Fancy");
    resp.setSize("48 inch");
    resp.setLogo("NO Logo");
    flag=true;*/
                Tie.type="43";respTemp.put(tie_response);
            }

            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==2){
                tie_response="Type:fancy"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:Hand logo";
    /*resp.setType("Fancy");
    resp.setSize("48 inch");

    resp.setLogo("Hand Logo");
    flag=true;*/
                Tie.type="44";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==3){
                tie_response="Type:fancy"+"\n\n"+"Size:48 inch"+"\n\n"+"Logo:Machine Embroiderylogo";
   /* resp.setType("Fancy");
    resp.setSize("48 inch");

    resp.setLogo("Machine Embroidery Logo");
    flag=true;*/
                Tie.type="45";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==1){
                tie_response="Type:Fancy"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:no logo";
   /* resp.setType("fancy");
    resp.setSize("56 inch");
    resp.setLogo("NO Logo");*/
                // flag=true;
                Tie.type="46";respTemp.put(tie_response);
            }

            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==2){
                tie_response="Type:Fancy"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:Hand logo";
   /* resp.setType("Fancy");
    resp.setSize("56 inch");

    resp.setLogo("Hand Logo");
    flag=true;*/
                Tie.type="47";respTemp.put(tie_response);
            }
            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==3){
                tie_response="Type:Fancy"+"\n\n"+"Size:56 inch"+"\n\n"+"Logo:Machine Embroiderylogo";
   /* resp.setType("fancy");
    resp.setSize("56 inch");

    resp.setLogo("Machine Embroidery Logo");*/

                Tie.type="48";respTemp.put(tie_response);
                // flag=true;
            }

            //else {tie_response="You did not choose some questions ";
//Tie.type="Not possible";}




        }}