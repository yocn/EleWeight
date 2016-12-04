package eleweigh.woxian.com.eleweight.application;

import android.app.Application;

import eleweigh.woxian.com.eleweight.bean.user.UserBean;
import eleweigh.woxian.com.eleweight.util.SharedPreferencesUtil;

/**
 * Created by Yocn on 16.11.28.
 */

public class EApplication extends Application {
    public static String tag = "woxain";
    public static boolean isShowLog = true;
    public static boolean isLoginSuccess = false;//是否记住密码
    public UserBean user = null;
    public static Application context;
    public static String LoginString = "isLoginSuccess";

    @Override
    public void onCreate() {
        super.onCreate();
        context = EApplication.this;
        isLoginSuccess = SharedPreferencesUtil.getInstance(this).getBoolean(LoginString);
        user = SharedPreferencesUtil.getInstance(this).getUser();
    }
}
