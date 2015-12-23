package org.odk.collect.android.mitram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;

import org.odk.collect.android.R;


import java.io.File;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.os.Environment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Card extends Activity {

    public static String type="not possible";

   // private static final String t = Card.class
          //  .getSimpleName();
    MitramTempCard t1=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_card);

        img();

    }
    public void img(){
        File f;
        MitramParserCard.parseResponseXML();
        String path="/odk/pic/card/"+"c"+type+".jpg";
        ImageView iv=(ImageView) findViewById(R.id.imageview);
        f = new File(Environment.getExternalStorageDirectory()+path);
        TextView tx=(TextView)findViewById(R.id.main_menu_header);
        tx.setText("Card Image");
        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);

        TextView tw=(TextView) findViewById(R.id.content);




        //MitamParser.belt_det="You did not choose some questions";
        tw.setText("You Left some questions");
        for(int i=0;i<MitramParserCard.RespodentList.size();i++){
            System.out.println("object NO"+i);
            t1=MitramParserCard.RespodentList.get(i);

            t1.show();
            tw.setText("Card details:\n\n"+t1.get());
        }

        MitramParserCard.icount=0;



    }
    public void can(View v){
        Intent returnIntent = new Intent();
        /*Map<Integer, MitramSaveCard> respodentList = MitramParserCard
                .getSelectedRespondentList();

        Log.i("Repeat Index",  "" + respodentList.size());




        MitramSaveCard resp = MitramParserCard
                .getRespondantByIndex(0);


        returnIntent.putExtra("typec", resp.getType());
        returnIntent.putExtra("orn", resp.getOrn());
        returnIntent.putExtra("prnt",resp.getPrnt());
        returnIntent.putExtra("hangta",resp.getHangta());
        returnIntent.putExtra("hangm",resp.getHangm());
        returnIntent.putExtra("hangps",resp.getHangps());
        returnIntent.putExtra("hangpp",resp.getHangpp());
        returnIntent.putExtra("hangl",resp.getHangl());
        returnIntent.putExtra("szs",resp.getSzs());
        returnIntent.putExtra("szf",resp.getSzf());
        returnIntent.putExtra("szr",resp.getSzr());
        returnIntent.putExtra("szo",resp.getSzo());
        returnIntent.putExtra("clp",resp.getClp());
        returnIntent.putExtra("hold",resp.getHold());
        returnIntent.putExtra("holdi",resp.getHoldi());
        returnIntent.putExtra("holdic",resp.getHoldic());
        returnIntent.putExtra("holdp",resp.getHoldp());
        returnIntent.putExtra("holdps",resp.getHoldps());
        returnIntent.putExtra("card",resp.getCard());
        returnIntent.putExtra("cardp",resp.getCardp());
        returnIntent.putExtra("cardpl",resp.getCardpl());
        if(MitramParserCard.get_oth!=null)
            returnIntent.putExtra("lamin",MitramParserCard.get_oth);
        else
            returnIntent.putExtra("lamin", resp.getLamin());
returnIntent.putExtra("Ty2",type);
        returnIntent.putExtra("pro",resp.getPro());
        setResult(RESULT_OK, returnIntent);
        finish();*/
        if(MitramParserCard.RespodentList.size()!=0)
            returnIntent.putExtra("q10_card_det", t1.get());
        else
            returnIntent.putExtra("q10_card_det","You left some questions");
        //MitamParser.icount=0;

        setResult(RESULT_OK, returnIntent);
        finish();
        type="not possible";
        MitramParserCard.RespodentList.remove(0);



    }
}