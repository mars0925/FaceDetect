package m.tri.facedetectcamera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import m.tri.facedetectcamera.activity.FaceDetectRGBActivity;
import m.tri.facedetectcamera.activity.StartDetecActivity;
import m.tri.facedetectcamera.utils.ActionCallback;
import m.tri.facedetectcamera.utils.GlobalFunction;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;
import static m.tri.facedetectcamera.utils.GlobalFunction.READ_EXTERNAL_STORAGE_REQUEST_CODE;

/**
 * Created by Nguyen on 5/20/2016.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private final String[] permissions = new String[]{CAMERA,READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        Button btnCameraRGB = (Button) findViewById(R.id.b_build);
        Button b_detect = (Button) findViewById(R.id.b_detect);

        if (btnCameraRGB != null) {
            btnCameraRGB.setOnClickListener(this);
        }
        if (b_detect != null) {
            b_detect.setOnClickListener(this);
        }

        /*請求權限*/
        if (ActivityCompat.checkSelfPermission(this,CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this,READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST_CODE);
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_build:
                Intent intent = new Intent(mContext, FaceDetectRGBActivity.class);
                startActivity(intent);
                break;
            case R.id.b_detect:
                Intent intent2 = new Intent(mContext, StartDetecActivity.class);
                startActivity(intent2);
                break;
        }
    }

    /**再按一次退出程式*/
    @Override
    public void onBackPressed() {
        GlobalFunction.showAlerDialog(MainActivity.this, "是否要退出程式", true, new ActionCallback.bool() {
            @Override
            public void onBooleanResult(boolean result) {
                if (result){
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);   //杀死当前进程\
                }
            }
        });
    }
}
