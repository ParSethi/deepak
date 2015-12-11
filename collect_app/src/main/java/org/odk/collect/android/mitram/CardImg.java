package org.odk.collect.android.mitram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.odk.collect.android.R;

import java.io.File;

public class CardImg extends Activity {
static String type="";
    static String path="/pic/card";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_img);
        img();
    }
    public void img(){
        File f;

        //ImageView iv=(ImageView) findViewById(R.id.imageview1);
        f = new File(Environment.getExternalStorageDirectory()+path);

        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
       // iv.setImageBitmap(bMap);




    }
    public void can(View v){
        Intent i=new Intent();
        i.putExtra("type",type);
        setResult(RESULT_OK, i);
        finish();


    }

}
