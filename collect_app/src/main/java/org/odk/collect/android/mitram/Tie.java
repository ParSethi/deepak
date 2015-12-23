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
public class Tie extends Activity {

    public static String type="not possible";
    private static final String t = Tie.class
            .getSimpleName();
    MitramTempTie ti=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(t, "msgMSGMSGMSGMSG");



        setContentView(R.layout.activity_tie);
        img();


    }
    public void img(){
        File f;
        MitramParserTie.parseResponseXML();
        ImageView iv=(ImageView) findViewById(R.id.imageview);
        String path="/odk/pic/tie/"+"t"+type+".jpg";
        f = new File(Environment.getExternalStorageDirectory()+path);
        TextView tx=(TextView)findViewById(R.id.main_menu_header);
        tx.setText("Tie Image");
        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);
        TextView tw=(TextView)findViewById(R.id.content);
        tw.setText("You Left some questions");
        for(int i=0;i<MitramParserTie.RespodentList.size();i++){
            System.out.println("object NO"+i);
            ti=MitramParserTie.RespodentList.get(i);
            ti.show();
            tw.setText("Tie Details:\n" + ti.get());
        }


        MitramParserTie.icount=0;

    }
    public void can(View v){
        //Map<Integer, MitramSaveTie> respodentList = MitramParserTie
              //  .getSelectedRespondentList();


       // Log.i("Repeat Index",  "" + respodentList.size());





        Intent returnIntent = new Intent();

      //  MitramSaveTie resp = MitramParserTie
              //  .getRespondantByIndex(0);

//resp.getLogo()
        //returnIntent.putExtra("q7_tie", resp.getType());
        //returnIntent.putExtra("q8_tie",resp.getSize());
        //returnIntent.putExtra("q9_tie",resp.getFita());
if(MitramParserTie.RespodentList.size()!=0)
        returnIntent.putExtra("q5_tie_det", ti.get());
        else
    returnIntent.putExtra("q5_tie_det","You Left some questions");
        //returnIntent.putExtra("q6_tie",type);

        setResult(RESULT_OK, returnIntent);

        finish();
        type="not possible";
        MitramParserTie.RespodentList.remove(0);
       // MitramParserTie.RespodentList.remove(0);

    }
}