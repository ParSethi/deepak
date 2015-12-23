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
public class Belt extends Activity {

	public static String type="not possible";

    private static final String t = Belt.class
            .getSimpleName();
	MitramTemp t1=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_belt);

img();

    }
	public void img(){
		File f;
		MitamParser.parseResponseXML();
		String path="/odk/pic/belt/"+"b"+type+".jpg";
		ImageView iv=(ImageView) findViewById(R.id.imageview);
		f = new File(Environment.getExternalStorageDirectory()+path);
		TextView tx=(TextView)findViewById(R.id.main_menu_header);
		tx.setText("Belt Image");
		Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());

		iv.setImageBitmap(bMap);

TextView tw=(TextView) findViewById(R.id.content);




		//MitamParser.belt_det="You did not choose some questions";
			tw.setText("You Left some questions");
		for(int i=0;i<MitamParser.RespodentList.size();i++){
			System.out.println("object NO"+i);
			t1=MitamParser.RespodentList.get(i);

			t1.show();
			tw.setText("Belt details:\n\n"+t1.get());
		}

		MitamParser.icount=0;



	}
	public void can(View v){
		/*Map<Integer, MitamSave> respodentList = MitamParser
				.getSelectedRespondentList();

		Log.i("Repeat Index",  "" + respodentList.size());*/


		Intent returnIntent = new Intent();

		/*MitamSave resp = MitamParser
				.getRespondantByIndex(0);
		System.ou1t.println("wapis");

		returnIntent.putExtra("Buck", resp.getBuck());
		returnIntent.putExtra("Nev", resp.getNev());

		returnIntent.putExtra("Pint",resp.getPint());
		returnIntent.putExtra("Coat",resp.getCoat());
		returnIntent.putExtra("Pest",resp.getPest());
		returnIntent.putExtra("Lemina",resp.getLemina());
		returnIntent.putExtra("Clr", resp.getClr());
		returnIntent.putExtra("Sz", resp.getSz());
		returnIntent.putExtra("type",type);*/
		

		//MitamParser.belt_det="You did not choose some questions";
		/*for(int i=0;i<MitamParser.RespodentList.size();i++){
			System.out.println("object NO"+i);
		t=MitamParser.RespodentList.get(i);
			t.show();
		}*/
		if(MitamParser.RespodentList.size()!=0)
		returnIntent.putExtra("q_belt_det", t1.get());
		else
			returnIntent.putExtra("q_belt_det","You left some questions");
		//MitamParser.icount=0;

				setResult(RESULT_OK, returnIntent);
		finish();
		type="not possible";
		MitamParser.RespodentList.remove(0);

	}
}