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
public class MitramParserCard {
    static String  get_oth=null;
    private static final String t = MitamParser.class
            .getSimpleName();

    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;

    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    private static Map<Integer,MitramTemp> RespodentList = new LinkedHashMap<Integer,MitramTemp>();

    public static Map<Integer, MitramSaveCard> selectedRespodentList = new LinkedHashMap<Integer, MitramSaveCard>();

    private static Map<Integer, String> motherList = new LinkedHashMap<Integer, String>();




    public static Map<Integer,MitramSaveCard> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramSaveCard getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramSaveCard) selectedRespodentList.get(in);
    }




    private static synchronized void parseResponseXML() {

        RespodentList = new LinkedHashMap<Integer, MitramTemp>();
        selectedRespodentList = new LinkedHashMap<Integer, MitramSaveCard>();


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
                System.out.println("bcsadasd");
            }


            MitramTempCard respTemp = null;
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
                        else if (tagName.equals("q1_icrd")) {
                            System.out.println("Dr baby");
                            respTemp=new MitramTempCard();
                            parser.next();
                            tagValue = parser.getText();


                            respTemp.typec =Integer.parseInt(tagValue);


                        } else if (tagName.equals("q2_icrd")) {
                            parser.next();

                            tagValue = parser.getText();

                            respTemp.orn = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q3_icrd")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.prnt = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hangta = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hangm = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_c")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hangps = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_d")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hangpp = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_e")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hangl = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_sa")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.szs = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_fa")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.szf = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_r")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.szr = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_o")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.szo = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q5_icrd")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.clp = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.hold = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd_a")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("insert  "+tagValue);
                            respTemp.holdi = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd_a_1")) {
                            parser.next();
                            tagValue = parser.getText();
System.out.println("crystal  "+tagValue);
                            respTemp.holdic= Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd_b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.holdp= Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd_b_1")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.holdps = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q7_icrd")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.card = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q7_icrd_a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cardp = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q7_icrd_b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cardpl = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q8_icrd")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.lamin = Integer.parseInt(tagValue);
                        }  else if (tagName.equalsIgnoreCase("q8_icrd_oth")) {
                            parser.next();
                            tagValue = parser.getText();

                            get_oth=tagValue;
                        }

                        else if (tagName.equalsIgnoreCase("q9_icrd")) {


                                parser.next();
                            tagValue = parser.getText();



                           respTemp.pro = Integer.parseInt(tagValue);

                            GenrateList(respTemp);
                        }



                        break;
                }

                eventType = parser.next();
            }
        } catch (Exception ex) {

        }



    }
    private static void GenrateList(MitramTempCard respTemp) {

        boolean flag=false;
        MitramSaveCard resp;
        if (respTemp != null) {
            Integer i=1;
            resp = new MitramSaveCard();
if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==1&&respTemp.clp==3){
resp.setType("Tall-wide");
    if(respTemp.orn==1)
    resp.setOrn("tall");
    else
    resp.setOrn("wide");
    resp.setHangta("satin");
    resp.setSzs("12 mm");
resp.setClp("No clip");
flag=true;
Card.type="1";

}
            else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==2&&respTemp.clp==3){
                resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("satin");
    resp.setSzs("16 mm");
                resp.setClp("No clip");
    flag=true;
    Card.type="2";

            }
          else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==3&&respTemp.clp==3){
                resp.setType("Tall-wide");
    if(respTemp.orn == 1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("satin");
    resp.setSzs("20 mm");
                resp.setClp("No clip");
                Card.type="3";
    flag=true;
            }

           else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==2&&respTemp.szf==1&&respTemp.clp == 1){
    resp.setType("Tall-wide");
    if(respTemp.orn == 1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("flat");
    resp.setSzf("12 mm");
                resp.setClp("palstic clip");
                Card.type="4";
    flag=true;

            }
else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==2&&respTemp.szf==2&&respTemp.clp == 1){
    resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("flat");
    resp.setSzf("14 mm");
    resp.setClp("palstic clip");
    Card.type="5";
    flag=true;

}
else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==3&&respTemp.szr==1){
    resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("Round");
    resp.setSzr("Local");
    flag=true;
    Card.type="6";

} else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==3&&respTemp.szr==2){
    resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("Round");
    resp.setSzr("Standard");
    Card.type="7";
    flag=true;
}
else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==4&&respTemp.szo==1){
    resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("Oval");
    resp.setSzr("Local");
    Card.type="8";
    flag=true;

}
else if(respTemp.typec==1&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==4&&respTemp.szo==2){
    resp.setType("Tall-wide");
    if(respTemp.orn==1)
        resp.setOrn("tall");
    else
        resp.setOrn("wide");
    resp.setHangta("Oval");
    resp.setSzr("Standard");
    Card.type="9";
    flag=true;

}

            //atm cardstart
            if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==1&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                if(respTemp.holdic==1)
                    resp.setHoldic("Single");
                    else
                    resp.setHoldic("Double");
                }

                resp.setHangta("satin");
                resp.setSzs("12 mm");
                resp.setClp("Silver clip");
                flag=true;
                Card.type="10";

            }
            else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==2&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setHangta("satin");
                resp.setSzs("16 mm");
                resp.setClp("Silver clip");
                flag=true;
                Card.type="11";

            }
            else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==1&&respTemp.szs==3&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHangta("satin");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setSzs("20 mm");
                resp.setClp("Silver clip");
                Card.type="12";
                flag=true;
            }


            else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==3&&respTemp.szr==1&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setClp("Silver clip");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setHangta("Round");
                resp.setSzr("Local");
                flag=true;
                Card.type="13";

            } else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==3&&respTemp.szr==2&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHangta("Round");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setClp("Silver clip");
                resp.setSzr("Standard");
                Card.type="14";
                flag=true;
            }
            else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==4&&respTemp.szo==1&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setClp("Silver clip");
                resp.setHangta("Oval");
                resp.setSzr("Local");
                Card.type="15";
                flag=true;

            }
            else if(respTemp.typec==2&&(respTemp.orn==1||respTemp.orn==2)&&respTemp.hangta==4&&respTemp.szo==2&&respTemp.clp==2&&respTemp.hold==1){
                resp.setType("Atm");
                if(respTemp.orn==1)
                    resp.setOrn("tall");
                else
                    resp.setOrn("wide");
                resp.setHold("insert");
                if(respTemp.holdi==1)
                    resp.setHoldi("Jhopdi");
                else if(respTemp.holdi==2)
                    resp.setHoldi("Box");
                else if(respTemp.holdi==3)
                    resp.setHoldi("thumb");
                else if(respTemp.holdi==5)
                    resp.setHoldi("pouch");
                else{
                    resp.setHoldi("Crystal");
                    if(respTemp.holdic==1)
                        resp.setHoldic("Single");
                    else
                        resp.setHoldic("Double");
                }
                resp.setHangta("Oval");
                resp.setSzr("Standard");
                Card.type="16";
                resp.setClp("Silver clip");

                flag=true;
            }

            //atn cars end


            if(respTemp.prnt==1)
                resp.setPrnt("Single");
           else
                resp.setPrnt("Double");

             if(respTemp.pro==1)
                    resp.setPro("Photo Scan");
                else if(respTemp.pro==2)
                 resp.setPro("Signature Scan");
             else if(respTemp.pro==3)
                 resp.setPro("Data Entry");

               else
           resp.setPro("Job/Crop");
            if(flag)
            selectedRespodentList.put(i, resp);
            else{
                Card.type="not possible";
                selectedRespodentList.put(i, resp);

            }
        }}}