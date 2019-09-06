package m.tri.facedetectcamera.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import m.tri.facedetectcamera.MainActivity;
import m.tri.facedetectcamera.R;
import m.tri.facedetectcamera.utils.ActionCallback;

public class BloodResultActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView title;
    private TextView systolic;//收縮壓
    private TextView diastolic ;//舒張壓
    private TextView rate ;//心率
    private Button upload ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_result);
        progressBar = findViewById(R.id.progressBar);
        title = findViewById(R.id.text);
        systolic = findViewById(R.id.textView5);
        diastolic = findViewById(R.id.textView4);
        rate = findViewById(R.id.textView6);
        upload = findViewById(R.id.button);


        new Thread(new Runnable() {
            public void run() {
                //sleep设置的是时长
                try {
                    Thread.sleep(4000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.GONE);
                            title.setVisibility(View.VISIBLE);
                            systolic.setVisibility(View.VISIBLE);
                            diastolic.setVisibility(View.VISIBLE);
                            rate.setVisibility(View.VISIBLE);
                            upload.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.bringToFront();
                progressBar.setVisibility(View.VISIBLE);

                new Thread(new Runnable() {
                    public void run() {
                        //sleep设置的是时长
                        try {
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);

                                    new AlertDialog.Builder(BloodResultActivity.this)
                                            .setTitle("血壓辨識")
                                            .setMessage("上傳成功")
                                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent i=new Intent(BloodResultActivity.this, MainActivity.class);
                                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    startActivity(i);
                                                }
                                            }).show();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
}
