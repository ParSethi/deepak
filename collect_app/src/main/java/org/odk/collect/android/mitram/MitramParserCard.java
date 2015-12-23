package org.odk.collect.android.mitram;

import java.io.InputStream;

import java.util.LinkedHashMap;

import java.util.Map;


import org.odk.collect.android.application.Collect;
import org.odk.collect.android.logic.FormController;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("NewApi")
public class MitramParserCard {

 public static  Integer icount = 0;

    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;

    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    public static Map<Integer,MitramTempCard> RespodentList = new LinkedHashMap<Integer,MitramTempCard>();

    //public static Map<Integer, MitramSaveCard> selectedRespodentList = new LinkedHashMap<Integer, MitramSaveCard>();

    //private static Map<Integer, String> motherList = new LinkedHashMap<Integer, String>();




    /*public static Map<Integer,MitramSaveCard> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramSaveCard getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramSaveCard) selectedRespodentList.get(in);
    }


*/

    public static synchronized void parseResponseXML() {

       /* RespodentList = new LinkedHashMap<Integer, MitramTemp>();
        selectedRespodentList = new LinkedHashMap<Integer, MitramSaveCard>();*/


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

                        if (tagName.equalsIgnoreCase("data")) {
                            form_id = parser.getAttributeValue(0);

                        }
                        break;
                }
                eventType = parser.next();

            }


            MitramTempCard respTemp = null;

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


                        }
                        else if (tagName.equals("q1_icrd")) {
                            respTemp=new MitramTempCard();
                            parser.next();
                            tagValue = parser.getText();

System.out.println("type "+tagValue);
                            respTemp.typec =Integer.parseInt(tagValue);


                        } else if (tagName.equals("q2_icrd")) {
                            parser.next();

                            tagValue = parser.getText();
                            System.out.println("orn "+tagValue);
                            respTemp.orn = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q3_icrd")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("prnt"+tagValue);
                            respTemp.prnt = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_1")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("hamgtm "+tagValue);
                            respTemp.hangtm = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_2")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("hamga "+tagValue);
                            respTemp.hanga = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_c")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("hangps "+tagValue);
                            respTemp.hangps = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_d")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("hangpp"+tagValue);
                            respTemp.hangpp = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q4_icrd_fa")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("szs "+tagValue);
                            respTemp.szs = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_sa")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("szf "+tagValue);
                            respTemp.szf = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q4_icrd_r")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("szr"+tagValue);
                            respTemp.szr = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q5_icrd_a")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("clp "+tagValue);
                            respTemp.clp = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q5_icrd_b")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("clpoth "+tagValue);
                            respTemp.clpoth = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("hold "+tagValue);
                            respTemp.hold = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q6_icrd_a_1")) {
                            parser.next();
                            tagValue = parser.getText();

                            System.out.println("holdm "+tagValue);
                            respTemp.holdm= Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q6_icrd_b")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("holdl "+tagValue);
                            respTemp.holdl= Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q6_icrd_a")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("insert "+tagValue);
                            respTemp.insert = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q7_icrd")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("card "+tagValue);
                            respTemp.card = Integer.parseInt(tagValue);
                        }



                        else if (tagName.equalsIgnoreCase("q9_icrd")) {


                                parser.next();
                            tagValue = parser.getText();
                            System.out.println("pro "+tagValue);
                            respTemp.pro = Integer.parseInt(tagValue);


if(respTemp!=null){

                            GenrateList(respTemp);
    RespodentList.put(icount, respTemp);
}
                        }



                        break;
                }

                eventType = parser.next();
            }
        } catch (Exception ex) {

        }



    }
    private static void GenrateList(MitramTempCard respTemp) {
String card_del="";
        String prnt="";
        String pro="";
        System.out.println("prvnn");
        String szs,szr,szf,insert="";
        if(respTemp.prnt==1)
            prnt="Single";
        else
            prnt="Double";
        if(respTemp.pro==1)
            pro="Photo Scan";
        else if(respTemp.pro==2)
            pro="Signature Scan";
        else if(respTemp.pro==3)
            pro="Data Entry";
        else
            pro="Job/Crop";
if(respTemp.szf==1)
    szf="12";
        else if(respTemp.szf==2)
    szf="16";
        else szf="20";
        if(respTemp.szs==1)
            szs="12";
        else
            szs="14";
        if(respTemp.szr==1)
            szr="Local";
        else szr="Standard";
        if(respTemp.insert==1)
            insert="Jhopdi";
        else if(respTemp.insert==2)
            insert="Box";
        else if(respTemp.insert==3)
            insert="Thumb";
        else
        insert="Crystal";
        String  orn="";
if(respTemp.orn==1)
    orn="Tall";
        else
orn="wide";
String hanger="";
    System.out.println(prnt+"\n"+pro+"\n"+szf+"\n"+orn);
if(respTemp.typec==1&&respTemp.orn!=0&respTemp.prnt==1&respTemp.hangtm==1&&respTemp.szf!=0&&respTemp.clp!=0&&respTemp.pro!=0){
System.out.println("inside genera");
card_del="Type: Tall-wide card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Plastic"+"\n\n"+"Processing: "+pro;
Card.type="1";
    respTemp.put(card_del);

}
       else if(respTemp.typec==1&&respTemp.orn!=0&respTemp.prnt==2&respTemp.hangtm==1&&respTemp.szf!=0&&respTemp.clp!=0&&respTemp.pro!=0){

            card_del="Type: Tall-wide card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Plastic"+"\n\n"+"Processing: "+pro;
            Card.type="2";
    respTemp.put(card_del);
        }
else if(respTemp.typec==1&&respTemp.orn!=0&respTemp.prnt==1&respTemp.hangtm==2&&respTemp.szs!=0&&respTemp.pro!=0){

    card_del="Type: Tall-wide card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Processing: "+pro;
    Card.type="3";
    respTemp.put(card_del);

}else if(respTemp.typec==1&&respTemp.orn!=0&respTemp.prnt==2&respTemp.hangtm==2&&respTemp.szs!=0&&respTemp.pro!=0){

    card_del="Type: Tall-wide card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Processing: "+pro;
    Card.type="4";
    respTemp.put(card_del);

}
       else if(respTemp.typec==2&&respTemp.orn!=0&respTemp.prnt==1&(respTemp.hanga==2||respTemp.hanga==3)&&respTemp.szr!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){
if(respTemp.hanga==2)
    hanger="Round";
    else
hanger="Oval";
            card_del="Type: Atm card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger:"+hanger+"\n\n"+"Size:"+szr+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"Processing: "+pro;
            Card.type="5";
    respTemp.put(card_del);

        }

else if(respTemp.typec==2&&respTemp.orn!=0&respTemp.prnt==2&(respTemp.hanga==2||respTemp.hanga==3)&&respTemp.szr!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){
    if(respTemp.hanga==2)
        hanger="Round";
    else
        hanger="Oval";
    card_del="Type: Atm card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger:"+hanger+"\n\n"+"Size:"+szr+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"Processing: "+pro;
    Card.type="6";
    respTemp.put(card_del);

}
else if(respTemp.typec==2&&respTemp.orn!=0&respTemp.prnt==1&respTemp.hanga==1&&respTemp.szs!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Atm card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"Processing: "+pro;
    Card.type="7";
    respTemp.put(card_del);

}
else if(respTemp.typec==2&&respTemp.orn!=0&respTemp.prnt==2&respTemp.hanga==1&&respTemp.szs!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Atm card"+"\n\n"+"Orientation: "+orn+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"Processing: "+pro;
    Card.type="8";
    respTemp.put(card_del);

}
        else if(respTemp.typec==3&&respTemp.prnt==1&&respTemp.szf!=0&&respTemp.hangtm==1&&respTemp.clpoth!=0&&respTemp.holdm!=0&&respTemp.pro!=0){

    card_del="Type: Mini card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder :Pesting"+"\n\n"+"Processing: "+pro;

    Card.type="9";
    respTemp.put(card_del);
}
else if(respTemp.typec==3&&respTemp.prnt==1&&respTemp.szs!=0&&respTemp.hangtm==2&&respTemp.clpoth!=0&&respTemp.holdm!=0&&respTemp.pro!=0){

    card_del="Type: Mini card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder :Pesting"+"\n\n"+"Processing: "+pro;

    Card.type="10";
    respTemp.put(card_del);
}
else if(respTemp.typec==3&&respTemp.prnt==2&&respTemp.szf!=0&&respTemp.hangtm==1&&respTemp.clpoth!=0&&respTemp.holdm!=0&&respTemp.pro!=0){

    card_del="Type: Mini card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder :Pesting"+"\n\n"+"Processing: "+pro;

    Card.type="11";
    respTemp.put(card_del);
}
else if(respTemp.typec==3&&respTemp.prnt==2&&respTemp.szs!=0&&respTemp.hangtm==2&&respTemp.clpoth!=0&&respTemp.holdm!=0&&respTemp.pro!=0){

    card_del="Type: Mini card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder :Pesting"+"\n\n"+"Processing: "+pro;

    Card.type="12";
    respTemp.put(card_del);
}
else if(respTemp.typec==4&&respTemp.prnt==1&&respTemp.card!=0&respTemp.hangps==1&&respTemp.szf!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Standard card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="13";
    respTemp.put(card_del);

}else if(respTemp.typec==4&&respTemp.prnt==2&&respTemp.card!=0&respTemp.hangps==1&&respTemp.szf!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Standard card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="14";
    respTemp.put(card_del);

}
else if(respTemp.typec==4&&respTemp.prnt==1&&respTemp.card!=0&respTemp.hangps==2&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Standard card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Dori"+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="15";
    respTemp.put(card_del);

}
else if(respTemp.typec==4&&respTemp.prnt==2&&respTemp.card!=0&respTemp.hangps==2&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Standard card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Dori"+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="16";
    respTemp.put(card_del);

}
else if(respTemp.typec==5&&respTemp.prnt==1&&respTemp.card!=0&respTemp.hangpp==1&&respTemp.szs!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Premium card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="17";
    respTemp.put(card_del);
}
else if(respTemp.typec==5&&respTemp.prnt==2&&respTemp.card!=0&respTemp.hangpp==1&&respTemp.szs!=0&&respTemp.clpoth!=0&&respTemp.hold!=0&&respTemp.insert!=0&&respTemp.pro!=0){

    card_del="Type: Pouch Premium card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Satin"+"\n\n"+"Size:"+szs+"\n\n"+"Clip: Silver"+"\n\n"+"card holder insert: "+insert+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="18";
    respTemp.put(card_del);

}

else if(respTemp.typec==6&&respTemp.prnt==1&&respTemp.card!=0&respTemp.hangps==1&&respTemp.szf!=0&&respTemp.clpoth!=0&&respTemp.holdl!=0&&respTemp.pro!=0){

    card_del="Type: Lamination card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder Laminated: "+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="19";
    respTemp.put(card_del);

}
else if(respTemp.typec==6&&respTemp.prnt==2&&respTemp.card!=0&respTemp.hangps==1&&respTemp.szf!=0&&respTemp.clpoth!=0&&respTemp.holdl!=0&&respTemp.pro!=0){

    card_del="Type: Lamination card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Flat"+"\n\n"+"Size:"+szf+"\n\n"+"Clip: Silver"+"\n\n"+"card holder Laminated: "+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="20";
    respTemp.put(card_del);

}
else if(respTemp.typec==6&&respTemp.prnt==1&&respTemp.card!=0&respTemp.hangps==2&&respTemp.clpoth!=0&&respTemp.holdl!=0&&respTemp.pro!=0){

    card_del="Type: Lamination card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Dori"+"\n\n"+"Clip: Silver"+"\n\n"+"card holder Laminated: "+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="21";
    respTemp.put(card_del);

}
else if(respTemp.typec==6&&respTemp.prnt==2&&respTemp.card!=0&respTemp.hangps==2&&respTemp.clpoth!=0&&respTemp.holdl!=0&&respTemp.pro!=0){

    card_del="Type: Lamination card"+"\n\n"+"printing side: "+prnt+"\n\n"+"Hanger: Dori"+"\n\n"+"Clip: Silver"+"\n\n"+"card holder Laminated: "+"\n\n"+"card :Board paper"+"\n\n"+"Processing: "+pro;
    Card.type="22";
    respTemp.put(card_del);

}

        }}