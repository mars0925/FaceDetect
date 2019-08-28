package m.tri.facedetectcamera.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import m.tri.facedetectcamera.MainActivity;
import m.tri.facedetectcamera.R;
import m.tri.facedetectcamera.adapter.BuildPictureAdapter;
import m.tri.facedetectcamera.model.PictureData;
import m.tri.facedetectcamera.utils.ActionCallback;
import m.tri.facedetectcamera.utils.GlobalFunction;
import m.tri.facedetectcamera.utils.WebServiceUtil;

import static m.tri.facedetectcamera.utils.GlobalFunction.SET_FACE_API;

public class SentPictureActivity extends AppCompatActivity {
    private final WebServiceUtil webServiceUtil = WebServiceUtil.getWebServiceUtil();
    private ArrayList<Bitmap> bitmapList;//接收截圖列表
    private final PictureData pictureData = PictureData.getInstance();
    private RecyclerView r_piclist;//照片列表
    private EditText e_name;//照片的人名
    private Button b_send;//送出建檔
    private ProgressBar p_uplaod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_picture);
        r_piclist = (RecyclerView) findViewById(R.id.r_piclist);
        p_uplaod = (ProgressBar) findViewById(R.id.p_uplaod);
        e_name = (EditText) findViewById(R.id.e_name);
        b_send = (Button) findViewById(R.id.b_send);
        bitmapList = pictureData.getBitmapList();

        BuildPictureAdapter adapter = new BuildPictureAdapter(bitmapList);
        LinearLayoutManager LinearLayoutManager = new LinearLayoutManager(this);

        r_piclist.setLayoutManager(LinearLayoutManager);
        r_piclist.setAdapter(adapter);

        /*送出建檔按鈕*/
        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p_uplaod.setVisibility(View.VISIBLE);//可見ProgressBar
                String base64 = null;

                try {
                    if (bitmapList.size() != 0) {
                        base64 = GlobalFunction.converToBase64(bitmapList.get(0));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String name = e_name.getText().toString();
                pictureData.setName(name);
                uploadPicture(base64, name);
            }
        });
    }

    /**
     * 上傳建檔用的照片
     *
     * @param base64 照片
     */
    private void uploadPicture(String base64, final String name) {
        JSONObject jsonO = new JSONObject();

        try {
            jsonO.put("Name", name);//照片姓名
            jsonO.put("Picture", base64);//建檔照片
        } catch (JSONException e) {
            e.printStackTrace();
        }

        webServiceUtil.getResponse_POST(SET_FACE_API, jsonO, null, false, new ActionCallback.webApiResult() {
            @Override
            public void onWebApiResult(int responseCode, JSONObject result) {
                //TODO 接收結果且顯示
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p_uplaod.setVisibility(View.GONE);//
                    }
                });

                try {
//                    String buildResult = result.getString("Result");//建立的結果
                    String buildResult = "身分已經建立";
                    /*跳出視窗顯示*/
                    GlobalFunction.showAlerDialog(SentPictureActivity.this, buildResult, false, new bool() {
                        @Override
                        public void onBooleanResult(boolean result) {
                            /*按確認回首頁*/
                            if (result) {
                                Intent intent = new Intent(SentPictureActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
