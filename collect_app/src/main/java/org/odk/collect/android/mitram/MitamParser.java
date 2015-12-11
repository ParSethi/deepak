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
public class MitamParser {

    private static final String t = MitamParser.class
            .getSimpleName();

    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;

    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    private static Map<Integer,MitramTemp> RespodentList = new LinkedHashMap<Integer,MitramTemp>();

    public static Map<Integer, MitamSave> selectedRespodentList = new LinkedHashMap<Integer, MitamSave>();





    public static Map<Integer,MitamSave> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitamSave getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitamSave) selectedRespodentList.get(in);
    }



    private static synchronized void parseResponseXML() {

        RespodentList = new LinkedHashMap<Integer, MitramTemp>();
        selectedRespodentList = new LinkedHashMap<Integer, MitamSave>();
       // motherList = new LinkedHashMap<Integer, String>();

        FormController formController = Collect.getInstance()
                .getFormController();
        // String formname = formController.getFormTitle();
        try {
            InputStream is = formController.getFilledInFormXml()
                    .getPayloadStream();

			/*
			 * StringWriter writer = new StringWriter(); IOUtils.copy(is,
			 * writer, "UTF-8"); String theString = writer.toString(); Log.d(t +
			 * " form log in xml : ", theString); //Log.d(t + " form name : ",
			 * formname); InputStream in = IOUtils.toInputStream(theString,
			 * "UTF-8");
			 */
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

            MitramTemp respTemp = null;
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
                        } else if (tagName.equalsIgnoreCase("q1")) {
respTemp=new MitramTemp();
                           parser.next();
                            tagValue = parser.getText();

                            System.out.println("buck   "+tagValue);
                            respTemp.Buck =Integer.parseInt(tagValue);

                            System.out.println("ynsi");
                        } else if (tagName.equalsIgnoreCase("q2")) {
                            parser.next();

                            tagValue = parser.getText();
                            System.out.println("nev"+tagValue);
                            respTemp.Nev = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q3")) {
                           parser.next();
                            tagValue = parser.getText();
                            System.out.println("pinting"+tagValue);
                            respTemp.Pint = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q4")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("coatimg"+tagValue);
                           respTemp.Coat = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q5")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("leminaaa"+tagValue);
                           respTemp.Lemina = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q6")) {
                           parser.next();
                            tagValue = parser.getText();
                            System.out.println("pesting"+tagValue);
                            respTemp.Pest = Integer
                                  .parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q7")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("colr"+tagValue);
                            respTemp.Clr =Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q8")) {
                            parser.next();
                            tagValue = parser.getText();
                            System.out.println("size"+tagValue);
                            respTemp.Sz=Integer.parseInt(tagValue);
                            GenrateList(respTemp);
                        }

                        Log.d(t + " tag name aayush:value", tagName + ":" + tagValue);

                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception ex) {

        }



    }
    private static void GenrateList(MitramTemp respTemp) {

        boolean flag=false;
        MitamSave resp;
        if (respTemp != null) {
            Integer i=1;
            resp = new MitamSave();
if(respTemp.Buck==1&&respTemp.Nev==1&&respTemp.Lemina==2&&respTemp.Clr==1){
    resp.setBUck("Iron");
    resp.setNev("plastic");
    resp.setClr("Single ");
    resp.setLemina("No");
    flag=true;
    Belt.type="1";

}
            else if(respTemp.Buck==1&&respTemp.Nev==1&&respTemp.Lemina==1&&respTemp.Clr==2){


        resp.setBUck("Iron");
    resp.setNev("plastic");
    resp.setClr("Double ");
        resp.setLemina("Yes");
    flag=true;
    Belt.type="2";
}
else if(respTemp.Buck==1&&respTemp.Nev==1&&respTemp.Lemina==1&&respTemp.Clr==3){


    resp.setBUck("Iron");
    resp.setNev("plastic");
    resp.setClr("Multi ");
    resp.setLemina("Yes");
    flag=true;
    Belt.type="3";
        }


else if(respTemp.Buck==2&&respTemp.Nev==1&&respTemp.Pint==1&&respTemp.Clr==1&&respTemp.Pest==2){


    resp.setBUck("Brass");
    resp.setNev("plastic");
    resp.setClr("Single ");
    resp.setPint("Yes");
    flag=true;
    Belt.type="4";
    }
else if(respTemp.Buck==2&&respTemp.Nev==1&&respTemp.Pint==2&&respTemp.Clr==2&&respTemp.Pest==1) {
    resp.setBUck("Brass");
    resp.setNev("plastic");
    resp.setClr("Double ");
    resp.setPint("NO");
    resp.setpest("Yes");
    flag=true;
    Belt.type="5";
}
else if(respTemp.Buck==4&&respTemp.Nev==1){
    resp.setBUck("Meena");
    resp.setNev("plastic");
    flag=true;
    Belt.type="6";
}
else if(respTemp.Buck==3&&respTemp.Nev==1&&respTemp.Clr==2){
    resp.setBUck("Fiber");
    resp.setNev("plastic");
    resp.setClr("Double");
    flag=true;
    BeltImg.type="7";
} else if(respTemp.Buck==3&&respTemp.Nev==1&&respTemp.Clr==2){
    resp.setBUck("Fiber");
    resp.setNev("plastic");
    resp.setClr("Multi");
    flag=true;
    Belt.type="8";
}
else if(respTemp.Buck==3&&respTemp.Nev==1&&respTemp.Clr==1){
    resp.setBUck("Fiber");
    resp.setNev("plastic");
    resp.setClr("Single");
    flag=true;
    Belt.type="9";
}



else if(respTemp.Buck==1&&respTemp.Nev==2&&respTemp.Clr==1&&respTemp.Lemina==2){
    resp.setBUck("Iron");
    resp.setNev("Cotton");
    resp.setClr("Single");
    resp.setLemina("No");
    flag=true;
    Belt.type="10";
}
else if(respTemp.Buck==1&&respTemp.Nev==2&&respTemp.Clr==2&&respTemp.Lemina==1){
    resp.setBUck("Iron");
    resp.setNev("Cotton");
    resp.setClr("Double");
    resp.setLemina("Yes");
    flag=true;
    Belt.type="11";
}
else if(respTemp.Buck==1&&respTemp.Nev==2&&respTemp.Clr==3&&respTemp.Lemina==1){
    resp.setBUck("Iron");
    resp.setNev("Cotton");
    resp.setClr("Multi");
    resp.setLemina("Yes");
    flag=true;
    Belt.type="12";
}
else if(respTemp.Buck==2&&respTemp.Nev==2&&respTemp.Clr==1&&respTemp.Pint==1&&respTemp.Pest==2){
    resp.setBUck("Brass");
    resp.setNev("Cotton");
    resp.setClr("Single");
    resp.setPint("Yes");
    resp.setpest("No");
    flag=true;
    Belt.type="13";
}
else if(respTemp.Buck==2&&respTemp.Nev==2&&respTemp.Clr==1&&respTemp.Pint==2&&respTemp.Pest==1){
    resp.setBUck("Brass");
    resp.setNev("Cotton");
    resp.setClr("Single");
    resp.setPint("No");
    resp.setpest("Yes");
    flag=true;
    Belt.type="14";
}
else if(respTemp.Buck==4&&respTemp.Nev==2){
    resp.setBUck("Meena");
    resp.setNev("Cotton");
    flag=true;
    Belt.type="15";
}
else if(respTemp.Buck==3&&respTemp.Nev==2&&respTemp.Clr==1){
    resp.setBUck("Fiber");
    resp.setNev("Cotton");
    resp.setClr("Single");
    flag=true;
    Belt.type="16";       }
else if(respTemp.Buck==3&&respTemp.Nev==2&&respTemp.Clr==2){
    resp.setBUck("Fiber");
    resp.setNev("Cotton");
    resp.setClr("Double");
    flag=true;
    Belt.type="17";
}
else if(respTemp.Buck==3&&respTemp.Nev==2&&respTemp.Clr==3){
    resp.setBUck("Fiber");
    resp.setNev("Cotton");
    flag=true;    resp.setClr("Multi");
    Belt.type="18";
}

else if(respTemp.Buck==3&&respTemp.Nev==2&&respTemp.Clr==3&&respTemp.Pest==1&&respTemp.Pint==2&&respTemp.Coat==2&&respTemp.Lemina==2){
    resp.setBUck("Fiber");
    resp.setNev("Cotton");
    resp.setClr("Multi");
    resp.setLemina("No");
    resp.setpest("Yes");
    resp.setCoat("NO");
    resp.setPint("No");
    flag=true;
    Belt.type="19";

}
            if(flag)
                selectedRespodentList.put(i,resp);
else{
                resp.setBUck("Wrong Combination");
                resp.setNev("Wrong Combination");
                resp.setClr("Wrong Combination");
                resp.setLemina("Wrong Combination");
                resp.setpest("Wrong Combination");
                resp.setCoat("Wrong Combination");
                resp.setPint("Wrong Combination");
                resp.setSz("Wrong Combination");
                Belt.type="not possible";
                selectedRespodentList.put(i, resp);

            }

}}}