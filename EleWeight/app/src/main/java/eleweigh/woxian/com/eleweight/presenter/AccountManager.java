package eleweigh.woxian.com.eleweight.presenter;

import eleweigh.woxian.com.eleweight.bean.user.UserBean;

/**
 * Created by Hui on 2016/12/10.
 */

public class AccountManager {
    //私有的静态变量
    private static AccountManager instance;

    //私有的构造方法
    private AccountManager() {
    }

    //公有的同步静态方法
    public static synchronized AccountManager getInstance() {
        if (instance == null) {
            instance = new AccountManager();
        }
        return instance;
    }

}
