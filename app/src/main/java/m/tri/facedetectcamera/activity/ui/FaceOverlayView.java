package m.tri.facedetectcamera.activity.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

import m.tri.facedetectcamera.model.FaceResult;

/**
 * Created by Nguyen on 5/20/2016.
 */

/**
 * This class is a simple View to display the faces.
 * 劃出臉部的框框的view
 */
public class FaceOverlayView extends View {

    private Paint mPaint;
    private Paint mTextPaint;
    private int mDisplayOrientation;
    private int mOrientation;
    private int previewWidth;
    private int previewHeight;
    private FaceResult[] mFaces;
    private double fps;
    private boolean isFront = false;

    public FaceOverlayView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        // We want a green box around the face:
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int stroke = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, metrics);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//是否抗锯齿
        mPaint.setDither(true);//设定是否使用图像抖动处理，会使绘制出来的图片颜色更加平滑和饱满，图像更加清晰
        mPaint.setColor(Color.BLUE);//設定顏色
        mPaint.setStrokeWidth(stroke);//設置畫筆的粗細度

        /*Paint.Style.STROKE：描边
        Paint.Style.FILL_AND_STROKE：描边并填充
        Paint.Style.FILL：填充*/
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, metrics);
        mTextPaint.setTextSize(size);
        mTextPaint.setColor(Color.GREEN);
        mTextPaint.setStyle(Paint.Style.FILL);
    }

    public void setFPS(double fps) {
        this.fps = fps;
    }

    /*設定新臉框 重繪*/
    public void setFaces(FaceResult[] faces) {
        mFaces = faces;
        invalidate();
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public void setDisplayOrientation(int displayOrientation) {
        mDisplayOrientation = displayOrientation;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFaces != null && mFaces.length > 0) {

            float scaleX = (float) getWidth() / (float) previewWidth;
            float scaleY = (float) getHeight() / (float) previewHeight;

            switch (mDisplayOrientation) {
                case 90:
                case 270:
                    scaleX = (float) getWidth() / (float) previewHeight;
                    scaleY = (float) getHeight() / (float) previewWidth;
                    break;
            }

            canvas.save();
            canvas.rotate(-mOrientation);
            RectF rectF = new RectF();
            for (FaceResult face : mFaces) {
                PointF mid = new PointF();
                face.getMidPoint(mid);

                if (mid.x != 0.0f && mid.y != 0.0f) {
                    float eyesDis = face.eyesDistance();
                        //調整方框的地方
                    rectF.set(new RectF(
                            (mid.x - eyesDis * 1.2f) * scaleX,
                            (mid.y - eyesDis * 1.2f) * scaleY,
                            (mid.x + eyesDis * 1.2f) * scaleX,
                            (mid.y + eyesDis * 1f) * scaleY));
                    if (isFront) {
                        float left = rectF.left;
                        float right = rectF.right;
                        rectF.left = getWidth() - right;
                        rectF.right = getWidth() - left;
                    }
                    canvas.drawRect(rectF, mPaint);
                    canvas.drawText("ID " + face.getId(), rectF.left, rectF.bottom + mTextPaint.getTextSize(), mTextPaint);
                    canvas.drawText("Confidence " + face.getConfidence(), rectF.left, rectF.bottom + mTextPaint.getTextSize() * 2, mTextPaint);
                    canvas.drawText("EyesDistance " + face.eyesDistance(), rectF.left, rectF.bottom + mTextPaint.getTextSize() * 3, mTextPaint);
                }
            }
            canvas.restore();
        }

        DecimalFormat df2 = new DecimalFormat(".##");
        canvas.drawText("Detected_Frame/s: " + df2.format(fps) + " @ " + previewWidth + "x" + previewHeight, mTextPaint.getTextSize(), mTextPaint.getTextSize(), mTextPaint);
    }

    public void setPreviewWidth(int previewWidth) {
        this.previewWidth = previewWidth;
    }

    public void setPreviewHeight(int previewHeight) {
        this.previewHeight = previewHeight;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}