package m.tri.facedetectcamera.activity;

import android.content.Intent;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import m.tri.facedetectcamera.MainActivity;
import m.tri.facedetectcamera.R;
import m.tri.facedetectcamera.model.PictureData;
import m.tri.facedetectcamera.utils.ActionCallback;
import m.tri.facedetectcamera.utils.GlobalFunction;
import m.tri.facedetectcamera.utils.WebServiceUtil;

import static m.tri.facedetectcamera.utils.GlobalFunction.GET_FACE_API;

public class ResultActivity extends AppCompatActivity {
    private final WebServiceUtil webServiceUtil = WebServiceUtil.getWebServiceUtil();
    private TextView t_result;//辨識結果
    private final PictureData pictureData = PictureData.getInstance();
    private ProgressBar p_progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        t_result = (TextView) findViewById(R.id.t_result);
        p_progress = (ProgressBar) findViewById(R.id.p_progress);

        ArrayList<Bitmap> bitmapArrayList = pictureData.getBitmapList();//取得傳過來的照片

        String base64 = null;
        if (bitmapArrayList.size() != 0){
            base64 = GlobalFunction.converToBase64(bitmapArrayList.get(0));//轉base64
        }
        uploadPicture(base64);//上傳API
        p_progress.setVisibility(View.VISIBLE);
    }


    /**
     * 上傳辨識用的照片
     * @param base64  照片
     */
    private void uploadPicture(String base64) {
        JSONObject jsonO = new JSONObject();

        try {
            jsonO.put("Pictrue", base64);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        webServiceUtil.getResponse_POST(GET_FACE_API, jsonO, null, false, new ActionCallback.webApiResult() {
            @Override
            public void onWebApiResult(int responseCode, JSONObject result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p_progress.setVisibility(View.GONE);//關掉progressBar
                    }
                });

                try {
//                    String buildResult = result.getString("Result");//建立的結果
                    t_result.setText(pictureData.getName());//顯示結果
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
