package m.tri.facedetectcamera.adapter;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.ArrayList;

import m.tri.facedetectcamera.R;
import m.tri.facedetectcamera.utils.RecyclerAdapter;

/**建立檔案頁面adapter*/
public class BuildPictureAdapter extends RecyclerAdapter<RecyclerAdapter.Holder>{
    private ArrayList<Bitmap> bitmapList;//接收截圖列表

    public BuildPictureAdapter(ArrayList<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);

        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.Holder holder, int position) {
        super.onBindViewHolder(holder, position);

        final ItemHolder list = (ItemHolder) holder;
        Bitmap bitmap = bitmapList.get(position);
        list.i_itme.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

    /*一般欄位Holder*/
    class ItemHolder extends Holder {
            ImageView i_itme;
        private ItemHolder(View itemView) {
            super(itemView);
            i_itme = (ImageView) itemView.findViewById(R.id.i_item);
        }
    }
}
