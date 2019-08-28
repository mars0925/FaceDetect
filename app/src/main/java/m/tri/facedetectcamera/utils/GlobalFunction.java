package m.tri.facedetectcamera.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;

import android.os.Environment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import m.tri.facedetectcamera.R;

public class GlobalFunction {
    public final static int READ_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    private static String fileName = "path.txt";//設定檔在的位置
    private static String fileDirPath = "/Download/24drs/";//設定檔在的位置
    public final static String GET_FACE_API = "http://192.168.5.153/api/Mobile/GetFace";//辨識人臉API
    public final static String SET_FACE_API = "http://192.168.5.153/api/Mobile/SetFace";//上傳建立人臉檔案API

    /**
     * Bitmap轉換成base64
     *
     * @param bm 圖片bitmap檔
     * @return 含有mimType的base64
     */
    public static String converToBase64(Bitmap bm) {
        /*bitmap to byte[]*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] input = baos.toByteArray();

        /*byte[] to base64編碼*/
        String strEncFile;//Base64編碼

        strEncFile = new String(android.util.Base64.encode(input, android.util.Base64.DEFAULT));

        StringBuilder base64_mimeType = new StringBuilder("data:").append("image/png").append(";base64,").append(strEncFile);//拼接成所要的base64_mimeType

        return base64_mimeType.toString();
    }

    //读取指定目录下的所有TXT文件的文件内容
    public static JSONObject getFileContent() throws Exception {
        File parentFile = Environment.getExternalStorageDirectory();//外部儲存空間路徑
        File fileDir = new File(parentFile, fileDirPath);//設定檔所在的資料夾
        File file = new File(parentFile,fileDirPath + fileName);//指定到所在檔案

        StringBuilder content = new StringBuilder();

        /*如果設定檔資料夾不在的話*/
        if (!fileDir.exists()){
            fileDir.mkdir();
        }

        /*如果檔案存在*/
        if (file.exists()) {
            InputStream instream = new FileInputStream(file);
            InputStreamReader inputreader = new InputStreamReader(instream, "UTF-8");
            BufferedReader buffreader = new BufferedReader(inputreader);

            String line;
            //分行读取
            while ((line = buffreader.readLine()) != null) {
                content.append(line).append("\n");
            }
            instream.close();//关闭输入流
        }

        return new JSONObject(content.toString());
    }

    public static void showAlerDialog(Context context, String meaasge, boolean haveNo, final ActionCallback.bool callback){
        AlertDialog.Builder alertbox = new AlertDialog.Builder(context);

        alertbox.setTitle(context.getString(R.string.app_name));
        alertbox.setMessage(meaasge);
        alertbox.setCancelable(false);
        alertbox.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null){
                    callback.onBooleanResult(true);
                }
            }
        });
        /*如果需要取消鍵*/
        if (haveNo){
            alertbox.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (callback != null){
                        callback.onBooleanResult(false);
                    }
                }
            });
        }

        AlertDialog alert = alertbox.create();
        alert.show();
    }
}