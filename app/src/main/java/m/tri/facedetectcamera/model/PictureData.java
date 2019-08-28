package m.tri.facedetectcamera.model;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;

public class PictureData extends Application {
    private static PictureData instance;
    private ArrayList<Bitmap> bitmapList;
    private String name;

    public static synchronized PictureData getInstance() {
        return instance;
    }

    public ArrayList<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(ArrayList<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
