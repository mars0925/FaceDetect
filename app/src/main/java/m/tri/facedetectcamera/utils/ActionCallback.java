package m.tri.facedetectcamera.utils;

import android.bluetooth.BluetoothDevice;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Eric on 2017/12/21.
 */

public interface ActionCallback extends Serializable {

    interface Int extends ActionCallback {
        void onIntResult(int result);
    }

    interface LongValue extends ActionCallback {
        void onLongResult(long result);
    }

    interface string extends ActionCallback {
        void onStringResult(String result);
    }

    interface bool extends ActionCallback {
        void onBooleanResult(boolean result);
    }

    interface data<E> extends ActionCallback {
        void onDataResult(E result);
    }

    interface arrayData<E> extends ActionCallback {
        void onArrayDataResult(ArrayList<E> result);
    }

    interface dataResult extends ActionCallback {
        /**
         * 回調函數
         * 傳入參數:json資料
         * */
        void onDataResult(JSONObject result);
    }

    interface webApiResult extends ActionCallback {
        /**
         * 回調函數
         * @param responseCode:Response狀態碼
         * @param result:json資料
         * */
        void onWebApiResult(int responseCode, JSONObject result);
    }

    interface bluetooth extends ActionCallback {
        /**
         * 回調函數
         * @param result 連線結果:true=成功 false=失敗
         * @param device 連接的藍芽設備
         * */
        void onBlueToothResult(boolean result, BluetoothDevice device);
    }

    /**回傳藍芽資料*/
    interface byteData extends ActionCallback {
        /**
         * @param buffer 如果為null值，表示沒取到資料，請求要重送
         * */
        void onByteResult(byte[] buffer);
    }
}
