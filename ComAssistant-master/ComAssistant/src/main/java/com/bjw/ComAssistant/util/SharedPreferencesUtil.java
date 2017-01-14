package com.bjw.ComAssistant.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;

import java.util.HashMap;

import com.bjw.ComAssistant.application.EApplication;
import com.bjw.ComAssistant.bean.user.UserBean;

public class SharedPreferencesUtil {
    private static SharedPreferencesUtil instance;
    private SharedPreferences sp;
    private Editor editor;
    private static final String name = "setting";
    private static final int mode = Context.MODE_PRIVATE;
    public static final String ROOM_NAME = "room_name";

    /**
     * 这个构造函数默认名为"telecast_setting"
     *
     * @param context
     */
    public SharedPreferencesUtil(Context context) {
        this.sp = context.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    public SharedPreferencesUtil(Context context, String name) {
        this.sp = context.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    /**
     * 用这个方法默认为“telecast_setting”
     *
     * @param context
     * @return
     */
    public synchronized static SharedPreferencesUtil getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesUtil(context.getApplicationContext(), name);
        }
        return instance;
    }

    /**
     * @param context
     * @param name
     * @return
     */
    public synchronized static SharedPreferencesUtil getInstance(Context context, String name) {
        if (instance == null) {
            instance = new SharedPreferencesUtil(context.getApplicationContext(), name);
        }
        return instance;
    }

    /**
     * 创建一个工具类，默认打开名字为name的SharedPreferences实例
     *
     * @param context
     * @param name    唯一标识
     * @param mode    权限标识
     */
    public SharedPreferencesUtil(Context context, String name, int mode) {

        this.sp = context.getSharedPreferences(name, mode);
        this.editor = sp.edit();
    }

    public boolean contains(String paramString) {
        return this.sp.contains(paramString);
    }

    public void saveUser(UserBean user) {
        saveString("uid", user.getUid());
        saveString("name", user.getName());
        saveString("access_token", user.getAccess_token());
    }

    public UserBean getUser() {
        boolean isLoginsuccess = getBoolean(EApplication.LoginString);
        if (isLoginsuccess) {
            String uid = getString("uid");
            String name = getString("name");
            String access_token = getString("access_token");
            UserBean user = new UserBean(uid, name, access_token);
            return user;
        } else {
            return null;
        }
    }

    public boolean getBoolean(String paramString) {
        synchronized (this.sp) {
            boolean bool = this.sp.getBoolean(paramString, false);
            return bool;
        }
    }

    public boolean getBoolean(String paramString, boolean paramBoolean) {
        synchronized (this.sp) {
            boolean bool = this.sp.getBoolean(paramString, paramBoolean);
            return bool;
        }
    }

    public int getInt(String paramString, int paramInt) {
        synchronized (this.sp) {
            int i = this.sp.getInt(paramString, paramInt);
            return i;
        }
    }

    public long getLong(String paramString) {
        synchronized (this.sp) {
            long l = this.sp.getLong(paramString, -1L);
            return l;
        }
    }

    public String getString(String paramString) {
        synchronized (this.sp) {
            String str = this.sp.getString(paramString, "");
            return str;
        }
    }

    public void removeByKey(String paramString) {
        this.sp.edit().remove(paramString).commit();
    }

    public void saveBoolean(String paramString, boolean paramBoolean) {
        synchronized (this.sp) {
            this.sp.edit().putBoolean(paramString, paramBoolean).commit();
            return;
        }
    }

    public void saveHashMap(String paramString, HashMap<String, String> paramHashMap) {
        JSONObject localJSONObject = new JSONObject(paramHashMap);
        synchronized (this.sp) {
            this.sp.edit().putString(paramString, localJSONObject.toString()).commit();
            return;
        }
    }

    public void saveInt(String paramString, int paramInt) {
        synchronized (this.sp) {
            this.sp.edit().putInt(paramString, paramInt).commit();
            return;
        }
    }

    public void saveLong(String paramString, long paramLong) {
        synchronized (this.sp) {
            this.sp.edit().putLong(paramString, paramLong).commit();
            return;
        }
    }

    public void saveString(String key, String value) {
        synchronized (this.sp) {
            this.sp.edit().putString(key, value).commit();
            return;
        }
    }


}
