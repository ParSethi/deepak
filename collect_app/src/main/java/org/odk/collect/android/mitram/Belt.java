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
public class Belt extends Activity {
	public static String path="/pic/tie.jpg";
	public static String type="not possible";
    private static final String t = Belt.class
            .getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_belt);

img();

    }
	public void img(){
		File f;

		ImageView iv=(ImageView) findViewById(R.id.imageview);
		f = new File(Environment.getExternalStorageDirectory()+path);
		TextView tx=(TextView)findViewById(R.id.main_menu_header);
		tx.setText("Belt Image");
		Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
		iv.setImageBitmap(bMap);




	}
	public void can(View v){
		Map<Integer, MitamSave> respodentList = MitamParser
				.getSelectedRespondentList();

		Log.i("Repeat Index",  "" + respodentList.size());


		Intent returnIntent = new Intent();

		MitamSave resp = MitamParser
				.getRespondantByIndex(0);
		System.out.println("wapis");

		returnIntent.putExtra("Buck", resp.getBuck());
		returnIntent.putExtra("Nev", resp.getNev());

		returnIntent.putExtra("Pint",resp.getPint());
		returnIntent.putExtra("Coat",resp.getCoat());
		returnIntent.putExtra("Pest",resp.getPest());
		returnIntent.putExtra("Lemina",resp.getLemina());
		returnIntent.putExtra("Clr", resp.getClr());
		returnIntent.putExtra("Sz", resp.getSz());
		returnIntent.putExtra("type",type);

		setResult(RESULT_OK, returnIntent);

		finish();


	}
}