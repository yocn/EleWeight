package eleweigh.woxian.com.eleweight.util;

import android.util.Log;

import eleweigh.woxian.com.eleweight.application.EApplication;

/**
 * Created by Yocn on 16.11.29.
 */

public class Loger {
    public static void d(String s) {
        if (EApplication.isShowLog) {
            Log.d(EApplication.tag, s);
        }
    }
}
