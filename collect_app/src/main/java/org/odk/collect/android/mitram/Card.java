package org.odk.collect.android.mitram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;

import org.odk.collect.android.R;


import java.io.File;
import java.util.Map;

;
import org.odk.collect.android.application.Collect;
import org.odk.collect.android.logic.FormController;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Card extends Activity {
    public static String path="/pic/tie.jpg";
    public static String type="not possible";
    private static final String t = Card.class
            .getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(t, "msgMSGMSGMSGMSG");
        setContentView(R.layout.activity_card);
img();

    }

    public void img(){
        File f;

        ImageView iv=(ImageView) findViewById(R.id.imageview);
        f = new File(Environment.getExternalStorageDirectory()+path);
        TextView tx=(TextView)findViewById(R.id.main_menu_header);
        tx.setText("Card Image");
        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);




    }
    public void can(View v){
        Map<Integer, MitramSaveCard> respodentList = MitramParserCard
                .getSelectedRespondentList();

        Log.i("Repeat Index",  "" + respodentList.size());


        Intent returnIntent = new Intent();

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
        finish();

    }
}