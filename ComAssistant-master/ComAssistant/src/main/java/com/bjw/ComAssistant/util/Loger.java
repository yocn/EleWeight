package com.bjw.ComAssistant.util;

import android.util.Log;

import com.bjw.ComAssistant.application.EApplication;

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
