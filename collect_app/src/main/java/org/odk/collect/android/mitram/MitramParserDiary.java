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
public class MitramParserDiary {
public  static String Aspaerdmd="";
    private static final String t = MitamParser.class
            .getSimpleName();
public static String  page_of_inner="";
    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;

    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

    private static Map<Integer,MitramTemp> RespodentList = new LinkedHashMap<Integer,MitramTemp>();

    public static Map<Integer, MitramParserDiarySave> selectedRespodentList = new LinkedHashMap<Integer, MitramParserDiarySave>();







    public static Map<Integer,MitramParserDiarySave> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramParserDiarySave getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramParserDiarySave) selectedRespodentList.get(in);
    }



    private static synchronized void parseResponseXML() {

        RespodentList = new LinkedHashMap<Integer, MitramTemp>();
        selectedRespodentList = new LinkedHashMap<Integer, MitramParserDiarySave>();
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
                System.out.println("bcsadasd");
            }


            MitramParserDiaryTemp respTemp = null;
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
                        else if (tagName.equals("q_dry_1")) {

                            respTemp=new MitramParserDiaryTemp();
                            parser.next();
                            tagValue = parser.getText();


                            respTemp.tydry =Integer.parseInt(tagValue);


                        } else if (tagName.equals("q_dry_2")) {
                            parser.next();

                            tagValue = parser.getText();

                            respTemp.cvrpdsn= Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_3")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cvrpp = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q_dry_4")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.lamintn = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_5")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.inner = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_6")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagen=Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_6_oth")) {
                            parser.next();
                            tagValue = parser.getText();

                            page_of_inner=tagValue;
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_7")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.color = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_8")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.photo = Integer.parseInt(tagValue);
                        }

                        else if (tagName.equalsIgnoreCase("q_dry_9")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cnt = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_9_as")) {
                            parser.next();
                            tagValue = parser.getText();

                            Aspaerdmd=tagValue;
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_10")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cntclr = Integer.parseInt(tagValue);
                        }
                        else if (tagName.equalsIgnoreCase("q_dry_11")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagentxt = Integer.parseInt(tagValue);



                        }

                        else if (tagName.equalsIgnoreCase("q_dry_12")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.dryn = Integer.parseInt(tagValue);



                            GenrateList(respTemp);
                        }

                        break;
                }

                eventType = parser.next();
            }
        } catch (Exception ex) {

        }



    }
    private static void GenrateList(MitramParserDiaryTemp respTemp) {
String innerp="";
        String phot,cvpp,lamin,cvrd,type,contnt, cntcol,pg,pgn, col="";
        boolean flag=false;
        MitramParserDiarySave resp;
        if(respTemp.cvrpp==1)
            cvpp="Art Board 180 gsm";
        else if(respTemp.cvrpp==2)
        cvpp="Art Board 220 gsm";
        else
        cvpp="Art Board 250 gsm";
        if(respTemp.inner==1)
            innerp="60 gsm paper";
        else
            innerp="70 gsm paper";
        if(respTemp.color==1)
            col="B&W";
        else if(respTemp.color==2)
            col="Single";
        else if(respTemp.color==3)
col="2 color";
        else
        col="Multi color";
        if(respTemp.cntclr==1)
            cntcol="Single";
        else if (respTemp.cntclr==2)
        cntcol="2 color";
        else if (respTemp.cntclr==3)
            cntcol="Multi color";
        else
        cntcol="B&W";
        if(respTemp.pagentxt==1)
            pg="8";
        else if(respTemp.pagentxt==2)
            pg="16";
        else if(respTemp.pagentxt==3)
            pg="20";
        else
            pg="24";
        if(respTemp.pagen==1)
            pgn="96";
        else if(respTemp.pagen==2)
            pgn="192";
        else
        pgn="other";
        if(respTemp.cnt==1)
            contnt="Standard";
        else
        contnt="As per demamd";
        if(respTemp.tydry==1)
            type="1 page 2 day 5.5*8.5(Premimum)";
        else if(respTemp.tydry==2)
            type="1 page 1 day 5.5*8.5(Premimum)";
        else if(respTemp.tydry==5)
            type="1 page 2 day 5.5*8.5(standard)";
        else if(respTemp.tydry==6)
            type="1 page 1 day 5.5*8.5(standard)";
        else if(respTemp.tydry==9)
            type="1 page 2 day 5.5*8.5(First class)";
        else if(respTemp.tydry==3)
            type="1 page 2 day 7*9(Premimum)";
        else if(respTemp.tydry==4)
            type="1 page 1day 7*9(Premimum)";
        else if(respTemp.tydry==7)
            type="1 page 2 day 7*9(standard)";
         else
            type="1 page 1 day 7*9(standard)";
        if(respTemp.photo==1)
            phot="Yes";
        else phot="No";
        if(respTemp.lamintn==1)
            lamin="No";
        else if(respTemp.lamintn==2)
            lamin="Simple";
        else lamin="fancy";
        if(respTemp.cvrpdsn==1)
            cvrd="Standard MultiColor Without screen printing";
        else if(respTemp.cvrpdsn==2)
            cvrd="Standard MultiColor With screen printing";
        else cvrd="Design Multicolor";
        if (respTemp != null) {
            Integer i=1;
            resp = new MitramParserDiarySave();
//7*9 premium
            if((respTemp.tydry==3||respTemp.tydry==4)&&respTemp.cvrpdsn==3&&(respTemp.cvrpp==2||respTemp.cvrpp==3)&&(respTemp.lamintn==3||respTemp.lamintn==1)){

                    resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                    resp.setCvrpp(cvpp);
resp.setCnt(contnt);

    resp.setLamintn(lamin);
              resp.setCntclr(cntcol);
               resp.setInner(innerp);
resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


resp.setColor(col);
                resp.setDryn(respTemp.dryn+"");
                MitramDiary.type="1";
                flag=true;

            }
//5.5*8.5 premium
else if((respTemp.tydry==1||respTemp.tydry==2)&&respTemp.cvrpdsn==3&&respTemp.cvrpp==3&&(respTemp.lamintn==1||respTemp.lamintn==3)&&respTemp.inner==2&&(respTemp.pagen==1||respTemp.pagen==2)&&respTemp.cnt==1&&respTemp.cntclr==2&&respTemp.pagentxt==2){
                resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                resp.setCvrpp(cvpp);
                resp.setCnt(contnt);

                resp.setLamintn(lamin);
                resp.setCntclr(cntcol);
                resp.setInner(innerp);
                resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


                resp.setColor(col);
                resp.setDryn(respTemp.dryn + "");
                MitramDiary.type="2";
                flag=true;
            }//standard 5.5*8.5
            else if((respTemp.tydry==5||respTemp.tydry==6)&&(respTemp.cvrpdsn==1||respTemp.cvrpdsn==2)&&respTemp.cvrpp==2&&(respTemp.lamintn==2||respTemp.lamintn==1)&&respTemp.inner==1&&(respTemp.pagen==1||respTemp.pagen==2)&&(respTemp.color==1||respTemp.color==2)&&respTemp.photo==1&&respTemp.cnt==1&&respTemp.pagentxt==2){

                resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                resp.setCvrpp(cvpp);
                resp.setCnt(contnt);

                resp.setLamintn(lamin);
                resp.setCntclr(cntcol);
                resp.setInner(innerp);
                resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


                resp.setColor(col);
                resp.setDryn(respTemp.dryn+"");
                MitramDiary.type="3";
                flag=true;

            }// standard 7*9
            else if((respTemp.tydry==7||respTemp.tydry==8)&&(respTemp.cvrpdsn==1||respTemp.cvrpdsn==2)&&respTemp.cvrpp==2&&(respTemp.lamintn==2||respTemp.lamintn==1)&&respTemp.inner==1&&respTemp.pagen==1&&(respTemp.color==1||respTemp.color==2||respTemp.color==3)&&respTemp.cnt==1&&(respTemp.cntclr==1||respTemp.cntclr==4||respTemp.cntclr==2)&&respTemp.pagentxt!=1){

                resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                resp.setCvrpp(cvpp);
                resp.setCnt(contnt);

                resp.setLamintn(lamin);
                resp.setCntclr(cntcol);
                resp.setInner(innerp);
                resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


                resp.setColor(col);
                resp.setDryn(respTemp.dryn+"");
                MitramDiary.type="4";
                flag=true;

            }//standard 5.5*8.5 1 page 2 day multicolor
            else if(respTemp.tydry==5&&respTemp.cvrpdsn==3&&respTemp.cvrpp==1&&respTemp.lamintn==1&&respTemp.inner==1&&respTemp.pagen==1&&respTemp.color==2&&respTemp.cnt==1&&respTemp.photo==1&&respTemp.cntclr==4&&respTemp.pagentxt==1){

                resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                resp.setCvrpp(cvpp);
                resp.setCnt(contnt);

                resp.setLamintn(lamin);
                resp.setCntclr(cntcol);
                resp.setInner(innerp);
                resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


                resp.setColor(col);
                resp.setDryn(respTemp.dryn+"");
                MitramDiary.type="5";
                flag=true;

            }//first class
            else if(respTemp.tydry==9&&(respTemp.cvrpdsn==1||respTemp.cvrpdsn==2)&&respTemp.cvrpp==1&&respTemp.lamintn==1&&respTemp.inner==1&&respTemp.pagen==1&&(respTemp.color==2||respTemp.color==1)&&respTemp.cnt==1&&respTemp.photo==1&&(respTemp.cntclr==4||respTemp.cntclr==1)&&respTemp.pagentxt==1){

                resp.setTydry(type);

                resp.setCvrpdsn(cvrd);

                resp.setCvrpp(cvpp);
                resp.setCnt(contnt);

                resp.setLamintn(lamin);
                resp.setCntclr(cntcol);
                resp.setInner(innerp);
                resp.setPagentxt(pg);
                resp.setPagen(pgn);
                resp.setPhoto(phot);


                resp.setColor(col);
                resp.setDryn(respTemp.dryn+"");
                MitramDiary.type="6";
                flag=true;

            }
            if(flag)
                selectedRespodentList.put(i, resp);
            else{
                page_of_inner="";
                Aspaerdmd="";
                MitramDiary.type="not possible";
                selectedRespodentList.put(i, resp);

            }
        }}}
