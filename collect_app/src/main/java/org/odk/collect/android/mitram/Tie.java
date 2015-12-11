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
    public static String path="/pic/tie.jpg";
    public static String type="not possible";
    private static final String t = Tie.class
            .getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(t, "msgMSGMSGMSGMSG");



        setContentView(R.layout.activity_tie);
        img();


    }
    public void img(){
        File f;

        ImageView iv=(ImageView) findViewById(R.id.imageview);
        f = new File(Environment.getExternalStorageDirectory()+path);
        TextView tx=(TextView)findViewById(R.id.main_menu_header);
        tx.setText("Tie Image");
        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);




    }
    public void can(View v){
        Map<Integer, MitramSaveTie> respodentList = MitramParserTie
                .getSelectedRespondentList();


        Log.i("Repeat Index",  "" + respodentList.size());





        Intent returnIntent = new Intent();

        MitramSaveTie resp = MitramParserTie
                .getRespondantByIndex(0);


        returnIntent.putExtra("Type1", resp.getType());
        returnIntent.putExtra("Size",resp.getSize());
        returnIntent.putExtra("Fita",resp.getFita());
        returnIntent.putExtra("Logo",resp.getLogo());
        returnIntent.putExtra("ty",type);

        setResult(RESULT_OK, returnIntent);
        finish();


    }
}