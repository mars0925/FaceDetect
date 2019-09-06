package m.tri.facedetectcamera.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import m.tri.facedetectcamera.MainActivity;
import m.tri.facedetectcamera.R;

public class BloodPicActivity extends AppCompatActivity {
    private ImageView i_bloodPic;
    private Button b_upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pic);
        i_bloodPic = findViewById(R.id.i_bloodPic);
        b_upload = findViewById(R.id.b_upload);

        final Intent intent = getIntent();

        String uriStr = (String) intent.getExtras().get("Uri");

        Uri imageUri = Uri.parse(uriStr);
        try {
            Bitmap cameraBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            i_bloodPic.setImageBitmap(cameraBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        b_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(BloodPicActivity.this,BloodResultActivity.class);
                startActivity(in);
            }
        });
    }
}
