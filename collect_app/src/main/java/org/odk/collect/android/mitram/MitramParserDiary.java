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
    public static String Aspaerdmd = "";

    private static final String t = MitamParser.class
            .getSimpleName();
    public static boolean flagas = false;
    public static boolean flaginner = false;
    public static String page_of_inner = "";
    private static XmlPullParserFactory pullparserfactory;
    private static XmlPullParser parser;
public  static  Integer icount = 0;
    private static int eventType;
    private static String tagName = null;
    private static String tagValue = null;

 public static Map<Integer, MitramParserDiaryTemp> RespodentList = new LinkedHashMap<Integer, MitramParserDiaryTemp>();

   /* public static Map<Integer, MitramParserDiarySave> selectedRespodentList = new LinkedHashMap<Integer, MitramParserDiarySave>();


    public static Map<Integer, MitramParserDiarySave> getSelectedRespondentList() {
        parseResponseXML();

        return selectedRespodentList;
    }

    public static MitramParserDiarySave getRespondantByIndex(int i) {

        List array = new ArrayList(selectedRespodentList.keySet());

        Integer in = (Integer) array.get(i);

        return (MitramParserDiarySave) selectedRespodentList.get(in);
    }*/


    public static synchronized void parseResponseXML() {

      //  RespodentList = new LinkedHashMap<Integer, MitramTemp>();
      //  selectedRespodentList = new LinkedHashMap<Integer, MitramParserDiarySave>();
        //motherList = new LinkedHashMap<Integer, String>();

        FormController formController = Collect.getInstance()
                .getFormController();
        // String formname = formController.getFormTitle();
        try {
            InputStream is = formController.getFilledInFormXml()
                    .getPayloadStream();

            MitramParserDiaryTemp respTemp = null;

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

                        } else if (tagName.equals("q_dry_1")) {
                            respTemp = new MitramParserDiaryTemp();

                            parser.next();
                            tagValue = parser.getText();


                            respTemp.tydry = Integer.parseInt(tagValue);


                        } else if (tagName.equals("q_dry_2a")) {
                            parser.next();

                            tagValue = parser.getText();

                            respTemp.cvrpdsn = Integer.parseInt(tagValue);
                        } else if (tagName.equals("q_dry_2b")) {
                            parser.next();

                            tagValue = parser.getText();

                            respTemp.cvrpdsn1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_3a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cvrpp = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_3b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cvrpp1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_3c")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cvrpp2 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_4a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.lamintn = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_4b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.lamintn1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_5a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.inner = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_5b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.inner1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_6a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagen = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_6_oth")) {
                            parser.next();
                            tagValue = parser.getText();
                            flaginner = true;
                            page_of_inner = tagValue;
                        } else if (tagName.equalsIgnoreCase(" q_dry_6b")) {
                            parser.next();
                            tagValue = parser.getText();
                            respTemp.pagen1 = Integer.parseInt(tagValue);

                        } else if (tagName.equalsIgnoreCase("q_dry_7a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.color = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_7b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.color1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_7c")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.color2 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_8")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.photo = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_9a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cnt = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_9_as")) {
                            parser.next();
                            tagValue = parser.getText();
                            flagas = true;
                            Aspaerdmd = tagValue;
                        } else if (tagName.equalsIgnoreCase("q_dry_9b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cnt1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_10a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cntclr = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_10b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cntclr1 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_10c")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.cntclr2 = Integer.parseInt(tagValue);
                        } else if (tagName.equalsIgnoreCase("q_dry_11a")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagentxt = Integer.parseInt(tagValue);


                        } else if (tagName.equalsIgnoreCase("q_dry_11b")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagentxt1 = Integer.parseInt(tagValue);


                        } else if (tagName.equalsIgnoreCase("q_dry_11c")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.pagentxt2 = Integer.parseInt(tagValue);


                        } else if (tagName.equalsIgnoreCase("q_dry_12")) {
                            parser.next();
                            tagValue = parser.getText();

                            respTemp.dryn = Integer.parseInt(tagValue);

if(respTemp!=null){
                            GenrateList(respTemp);
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

    private static void GenrateList(MitramParserDiaryTemp respTemp) {
       // String innerp = "";
         String dir_del = "Left some question";
        /*String phot,cvpp,lamin,cvrd,type,contnt, cntcol,pg,pgn, col="";
        boolean flag=false;
        MitramParserDiarySave resp;

        if(respTemp.cvrpp==1)
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
        else cvrd="Design Multicolor";*/

            // Integer i=1;
            //resp = new MitramParserDiarySave();
//7*9 premium
            if ((respTemp.tydry == 1 || respTemp.tydry == 2 || respTemp.tydry == 3 || respTemp.tydry == 4) && respTemp.cvrpdsn != 0 && respTemp.cvrpp != 0 && respTemp.lamintn != 0 && respTemp.inner != 0 && respTemp.pagen != 0 && respTemp.color != 0 && respTemp.photo != 0 && respTemp.cnt != 0 && respTemp.cntclr != 0 && respTemp.pagentxt != 0) {
                if (respTemp.tydry == 1)
                    dir_del = "Cover Type: 1 page 2 day 5.5*8.5(Premimum)";
                else if (respTemp.tydry == 2)
                    dir_del = "Cover Type: 1 page 1 day 5.5*8.5(Premimum)";
                else if (respTemp.tydry == 3)
                    dir_del = "Cover Type: 1 page 2 day 7*9(Premimum)";
                else dir_del = "Cover Type: 1 page 1 day 7*9(Premimum)";

                dir_del = dir_del + "\n\n" + "Cover page Design: Multocolor";
                if (respTemp.cvrpp == 1)
                    dir_del = dir_del + "\n\n" + "Cover page paper: 220 gsm";
                else dir_del = dir_del + "\n\n" + "Cover page paper: 250 gsm";
                dir_del = dir_del + "\n\n" + "Lamination: Fancy";
                if (respTemp.inner == 1)
                    dir_del = dir_del + "\n\n" + "Inner text page paper: 60 gsm";
                else dir_del = dir_del + "\n\n" + "Inner text page paper: 70 gsm";
                if (respTemp.pagen == 1)
                    dir_del = dir_del + "\n\n" + "No of inner pages: 96";
                else if (respTemp.pagen == 2)
                    dir_del = dir_del + "\n\n" + "No of inner pages: 192";
                else {
                    if (flaginner == true)
                        dir_del = dir_del + "\n\n" + "No of inner pages: " + page_of_inner;
                }
                if (respTemp.color == 1)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: B&W";
                else if (respTemp.color == 2)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: single";
                else if (respTemp.color == 3)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: 2 color";
                else
                    dir_del = dir_del + "\n\n" + "Color in inner pages: Multi";
                if (respTemp.photo == 1)
                    dir_del = dir_del + "\n\n" + "Child photo on personal page: No";
                else dir_del = dir_del + "\n\n" + "Child photo on personal page: Yes";
                if (respTemp.cnt == 1)
                    dir_del = dir_del + "\n\n" + "Text page content: Standard";
                else {
                    if (flagas)
                        dir_del = dir_del + "\n\n" + "Text page content as per demand:" + Aspaerdmd;
                }
                if (respTemp.cntclr == 1)
                    dir_del = dir_del + "\n\n" + "Text page content color: B&W";
                else if (respTemp.cntclr == 2)
                    dir_del = dir_del + "\n\n" + "Text page content color: Single";
                else if (respTemp.cntclr == 3)
                    dir_del = dir_del + "\n\n" + "Text page content color: Double";
                else
                    dir_del = dir_del + "\n\n" + "Text page content color: Multi";
                if (respTemp.pagentxt == 1)
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 8";
                else if (respTemp.pagentxt == 2)
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 16";
                else if (respTemp.pagentxt == 3)
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 20";
                else
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 24";
                dir_del = dir_del + "\n\n" + "No of diary: " + respTemp.dryn;
                respTemp.put(dir_del);
                    /*resp.setTydry(type);

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
                resp.setDryn(respTemp.dryn+"");*/
                MitramDiary.type = "1";
                //flag=true;

            }
            else if ((respTemp.tydry == 3 || respTemp.tydry == 4 || respTemp.tydry == 5 || respTemp.tydry == 6) && respTemp.cvrpdsn1 != 0 && respTemp.cvrpp1 != 0 && respTemp.lamintn1 != 0 && respTemp.inner1 != 0 && respTemp.pagen1 != 0 && respTemp.color1 != 0 && respTemp.photo != 0 && respTemp.cnt1 != 0 && respTemp.cntclr1 != 0 && respTemp.pagentxt1 != 0) {
                if (respTemp.tydry == 3)
                    dir_del = "Cover Type: 1 page 2 day 5.5*8.5(Standard)";
                else if (respTemp.tydry == 4)
                    dir_del = "Cover Type: 1 page 1 day 5.5*8.5(Standard)";
                else if (respTemp.tydry == 5)
                    dir_del = "Cover Type: 1 page 2 day 7*9(Standard)";
                else dir_del = "Cover Type: 1 page 1 day 7*9(Standard)";
                if (respTemp.cvrpdsn1 == 1)
                    dir_del = dir_del + "\n\n" + "Cover page Design: Standard Multicolor without screen printing";
                else
                    dir_del = dir_del + "\n\n" + "Cover page Design: Standard Multicolor with screen printing";
                dir_del = dir_del + "\n\n" + "Cover page paper: 220 gsm";
                dir_del = dir_del + "\n\n" + "Lamination: Simple";
                dir_del = dir_del + "\n\n" + "Inner text page paper: 60 gsm";
                dir_del = dir_del + "\n\n" + "No of inner pages: 96";
                if (respTemp.color == 1)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: B&W";
                else if (respTemp.color == 2)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: single";
                else
                    dir_del = dir_del + "\n\n" + "Color in inner pages: 2 color";

                if (respTemp.photo == 1)
                    dir_del = dir_del + "\n\n" + "Child photo on personal page: No";
                else dir_del = dir_del + "\n\n" + "Child photo on personal page: Yes";
                dir_del = dir_del + "\n\n" + "Text page content: Standard";
                if (respTemp.cntclr == 1)
                    dir_del = dir_del + "\n\n" + "Text page content color: B&W";
                else if (respTemp.cntclr == 2)
                    dir_del = dir_del + "\n\n" + "Text page content color: Single";
                else
                    dir_del = dir_del + "\n\n" + "Text page content color: Double";
                if (respTemp.pagentxt == 1)
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 16";
                else if (respTemp.pagentxt == 2)
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 20";
                else
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 24";
                dir_del = dir_del + "\n\n" + "No of diary: " + respTemp.dryn;
//5.5*8.5 premium
                MitramDiary.type="2";respTemp.put(dir_del);

            }
            else if (respTemp.tydry == 9 && respTemp.cvrpdsn1 != 0 && respTemp.cvrpp2 != 0 && respTemp.inner1 != 0 && respTemp.pagen1 != 0 && respTemp.color2 != 0&& respTemp.cnt1 != 0 && respTemp.cntclr2 != 0 && respTemp.pagentxt2 != 0) {
                    dir_del = "Cover Type: 1 page 2 day 5.5*8.5(First Class)";
                if (respTemp.cvrpdsn1 == 1)
                    dir_del = dir_del + "\n\n" + "Cover page Design: Standard Multicolor without screen printing";
                else
                    dir_del = dir_del + "\n\n" + "Cover page Design: Standard Multicolor with screen printing";
                dir_del = dir_del + "\n\n" + "Cover page paper: 180 gsm";
                dir_del = dir_del + "\n\n" + "Inner text page paper: 60 gsm";
                dir_del = dir_del + "\n\n" + "No of inner pages: 96";
                if (respTemp.color == 1)
                    dir_del = dir_del + "\n\n" + "Color in inner pages: B&W";
                else
                    dir_del = dir_del + "\n\n" + "Color in inner pages: single";
                dir_del = dir_del + "\n\n" + "Text page content: Standard";
                if (respTemp.cntclr == 1)
                    dir_del = dir_del + "\n\n" + "Text page content color: B&W";
               else
                    dir_del = dir_del + "\n\n" + "Text page content color: Single";
                    dir_del = dir_del + "\n\n" + "No of pages of text pages: 8";
                dir_del = dir_del + "\n\n" + "No of diary: " + respTemp.dryn;
//5.5*8.5 premium
                MitramDiary.type="3";respTemp.put(dir_del);

            }
/*else {dir_del="You did not choose some questions ";
            MitramDiary.type="not possible";
            }*/

    }
}