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

public class BeltImg extends Activity {
public static String heading="Belt Image";
    public static String path="/pic/life.jpg";
    public static String type="not possible";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_belt_img);
    img();

    }



    public void img(){
        File f;

        ImageView iv=(ImageView) findViewById(R.id.imageview);
        f = new File(Environment.getExternalStorageDirectory()+path);
        TextView tx=(TextView)findViewById(R.id.main_menu_header);
        tx.setText(heading);
        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);




    }
public void can(View v){
    Intent i=new Intent();
    i.putExtra("type",type);
    setResult(RESULT_OK, i);
    finish();


}
}
