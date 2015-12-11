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

    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    private static Map<Integer,MitramTemp> RespodentList = new LinkedHashMap<Integer,MitramTemp>();

    public static Map<Integer, MitramSaveTie> selectedRespodentList = new LinkedHashMap<Integer, MitramSaveTie>();





    public static Map<Integer,MitramSaveTie> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramSaveTie getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramSaveTie) selectedRespodentList.get(in);
    }



    private static synchronized void parseResponseXML() {

        RespodentList = new LinkedHashMap<Integer, MitramTemp>();
        selectedRespodentList = new LinkedHashMap<Integer, MitramSaveTie>();
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


            MitramTempTie respTemp = null;
            Integer icount = 0;
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
                            System.out.println("Dr baby");
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
                            System.out.println("Logo"+tagValue);
                            respTemp.Logo = Integer.parseInt(tagValue);

                            GenrateList(respTemp);
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
boolean flag=false;
        MitramSaveTie resp;
        if (respTemp != null) {
            Integer i=1;
            resp = new MitramSaveTie();

if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==1){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("0.75 mm");
    resp.setLogo("No Logo");
    flag=true;
    Tie.type="1";
}
            else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="2";
            }
            else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="3";
            }
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==1){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("NoLogo");
    flag=true;
    Tie.type="4";
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==2){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="5";
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==3){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1 mm");
    resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="6";
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==1){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("NoLogo");
    flag=true;
    Tie.type="7";
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==2){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="8";
}
else if(respTemp.Type==1&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==3){

    resp.setType("Simple");
    resp.setSize("11 inch");
    resp.setFita("1.5 mm");
    resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="9";
}//hh
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==1){

                resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
    flag=true;
    Tie.type="10";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="11";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="12";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==1){

                resp.setType("Simple");
                resp.setSize("14 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
    flag=true;
    Tie.type="13";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="14";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="15";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==1){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");
    flag=true;
    Tie.type="16";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="17";
            }
            else if(respTemp.Type==1&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="18";
            }
         //for fancy
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
    flag=true;
    Tie.type="19";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="20";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==1&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="21";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
    flag=true;
    Tie.type="22";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="23";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==2&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="24";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");
    flag=true;
    Tie.type="25";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
    flag=true;
    Tie.type="26";
            }
            else if(respTemp.Type==2&&respTemp.Size==1&&respTemp.Fita==3&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
    flag=true;
    Tie.type="27";
            }//hh
            if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("No Logo");
                flag=true;
                Tie.type="28";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="29";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==1&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("0.75 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="30";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("14 inch");
                resp.setFita("1 mm");
                resp.setLogo("NoLogo");
                flag=true;
                Tie.type="31";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="32";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==2&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="33";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("NoLogo");

                Tie.type="34";
                flag=true;
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="35";
            }
            else if(respTemp.Type==2&&respTemp.Size==2&&respTemp.Fita==3&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("11 inch");
                resp.setFita("1.5 mm");
                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="36";
            }

//48&56
            else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==1){

                resp.setType("Simple");
                resp.setSize("48 inch");
                resp.setLogo("NO Logo");
                flag=true;
                Tie.type="37";
            }

            else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("48 inch");

                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="38";
            }
            else if(respTemp.Type==1&&respTemp.Size==3&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("48 inch");

                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="39";
            }
            else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==1){

                resp.setType("Simple");
                resp.setSize("48 inch");
                resp.setLogo("NO Logo");
                flag=true;
                Tie.type="40";
            }

            else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==2){

                resp.setType("Simple");
                resp.setSize("48 inch");

                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="41";
            }
            else if(respTemp.Type==1&&respTemp.Size==4&&respTemp.Logo==3){

                resp.setType("Simple");
                resp.setSize("48 inch");

                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="42";
            }
            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==1){

                resp.setType("Fancy");
                resp.setSize("48 inch");
                resp.setLogo("NO Logo");
                flag=true;
                Tie.type="43";
            }

            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("48 inch");

                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="44";
            }
            else if(respTemp.Type==2&&respTemp.Size==3&&respTemp.Logo==3){

                resp.setType("Fancy");
                resp.setSize("48 inch");

                resp.setLogo("Machine Embroidery Logo");
                flag=true;
                Tie.type="45";
            }
            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==1){

                resp.setType("fancy");
                resp.setSize("56 inch");
                resp.setLogo("NO Logo");
                flag=true;
                Tie.type="46";
            }

            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==2){

                resp.setType("Fancy");
                resp.setSize("56 inch");

                resp.setLogo("Hand Logo");
                flag=true;
                Tie.type="47";
            }
            else if(respTemp.Type==2&&respTemp.Size==4&&respTemp.Logo==3){

                resp.setType("fancy");
                resp.setSize("56 inch");

                resp.setLogo("Machine Embroidery Logo");

                Tie.type="48";
flag=true;
            }
if(flag)
            selectedRespodentList.put(i,resp);
            else{
Tie.type="Not possible";
            selectedRespodentList.put(i,resp);}



        }}}