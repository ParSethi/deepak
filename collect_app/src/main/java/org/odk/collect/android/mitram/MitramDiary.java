package org.odk.collect.android.mitram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.odk.collect.android.R;

import java.io.File;
import java.util.Map;

public class MitramDiary extends Activity {
    public static String path="/pic/tie.jpg";
    public static String type="not possible";
    private static final String t = MitramDiary.class
            .getSimpleName();
    MitramParserDiaryTemp ti=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(t, "msgMSGMSGMSGMSG");
        setContentView(R.layout.activity_mitram_diary);
        img();

    }

    public void img(){
        File f;
        MitramParserDiary.parseResponseXML();
        ImageView iv=(ImageView) findViewById(R.id.imageview);
        String path="/odk/pic/diary/"+"d"+type+".jpg";
        f = new File(Environment.getExternalStorageDirectory()+path);

        Bitmap bMap = BitmapFactory.decodeFile(f.getAbsolutePath());
        iv.setImageBitmap(bMap);
        TextView tw=(TextView)findViewById(R.id.content);
        tw.setText("You Left some questions");
        for(int i=0;i<MitramParserDiary.RespodentList.size();i++){
            System.out.println("object NO"+i);
            ti=MitramParserDiary.RespodentList.get(i);
            ti.show();
            tw.setText("Diary Details:\n"+ti.get());
        }


        MitramParserDiary.icount=0;

    }
    public void can(View v){
       /* Map<Integer, MitramParserDiarySave> respodentList = MitramParserDiary
                .getSelectedRespondentList();

        Log.i("Repeat Index",  "" + respodentList.size());
*/

        Intent returnIntent = new Intent();

        /*MitramParserDiarySave resp = MitramParserDiary
                .getRespondantByIndex(0);

returnIntent.putExtra("dtype",type);
        returnIntent.putExtra("tydry",resp.getTydry());
        returnIntent.putExtra("cvrpdsn",resp.getCvrpdsn());
        returnIntent.putExtra("cvrpp",resp.getCvrpp());
        returnIntent.putExtra("lamintn",resp.getLamintn());
        returnIntent.putExtra("inner",resp.getInner());
        returnIntent.putExtra("pagen",resp.getPagen());
        returnIntent.putExtra("color",resp.getColor());
        returnIntent.putExtra("photo",resp.getPhoto());
        returnIntent.putExtra("cnt",resp.getCnt());
        returnIntent.putExtra("cntclr",resp.getCntclr());
        returnIntent.putExtra("pagentxt",resp.getPagentxt());
        returnIntent.putExtra("dryn",resp.getDryn());
        returnIntent.putExtra("pagen_oth",MitramParserDiary.page_of_inner);
        returnIntent.putExtra("cnt_dmd",MitramParserDiary.Aspaerdmd);*/
if(MitramParserDiary.RespodentList.size()!=0)
        returnIntent.putExtra("q_dry_det", ti.get());
else
    returnIntent.putExtra("q_dry_det","You left some questions");
         setResult(RESULT_OK, returnIntent);

        finish();
MitramParserDiary.RespodentList.remove(0);
        type="not possible";
    }
}
