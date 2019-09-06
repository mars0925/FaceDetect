package m.tri.facedetectcamera;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;

import m.tri.facedetectcamera.activity.BloodPicActivity;
import m.tri.facedetectcamera.activity.FaceDetectRGBActivity;
import m.tri.facedetectcamera.activity.StartDetecActivity;
import m.tri.facedetectcamera.utils.ActionCallback;
import m.tri.facedetectcamera.utils.GlobalFunction;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;
import static m.tri.facedetectcamera.utils.GlobalFunction.READ_EXTERNAL_STORAGE_REQUEST_CODE;

/**
 * Created by Nguyen on 5/20/2016.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private final String[] permissions = new String[]{CAMERA,READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};
    private static final int REQUEST_IMAGE_CAPTURE_FULL = 1;
    public  String PICTURE_IMAGE_PATH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PICTURE_IMAGE_PATH = getFilesDir().getAbsolutePath() + "/" + "image.jpg";
        mContext = this;

        Button btnCameraRGB = (Button) findViewById(R.id.b_build);
        Button b_detect = (Button) findViewById(R.id.b_detect);
        Button b_blood =  findViewById(R.id.b_blood);


        if (btnCameraRGB != null) {
            btnCameraRGB.setOnClickListener(this);
        }
        if (b_detect != null) {
            b_detect.setOnClickListener(this);
        }

        b_blood.setOnClickListener(this);

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
            /*血壓辨識*/
                case R.id.b_blood:
                    Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File tmpFile = new File(PICTURE_IMAGE_PATH);
                    Uri uri = FileProvider.getUriForFile(MainActivity.this, "m.tri.fileprovider", tmpFile);
                    takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE_FULL);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE_FULL) {
            File imgFile = new File(PICTURE_IMAGE_PATH);

            if (imgFile.exists()){
                Uri uri = FileProvider.getUriForFile(MainActivity.this, "m.tri.fileprovider", imgFile);
                System.out.println("=========" + uri);

                Intent in = new Intent(this, BloodPicActivity.class);
                in.putExtra("Uri",uri.toString());
                startActivity(in);
            }
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
