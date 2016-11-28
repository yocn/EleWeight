package eleweigh.woxian.com.eleweight.presenter;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import eleweigh.woxian.com.eleweight.util.BaseCallback;
import eleweigh.woxian.com.eleweight.util.Contants;
import eleweigh.woxian.com.eleweight.util.RequestCallback;

/**
 * Created by Yocn on 16.11.28.
 */

public class LoginPresenter extends BasePresenter {
    RequestCallback callback;
    OkHttpClient mOkHttpClient;

    public LoginPresenter(RequestCallback callback) {
        this.callback = callback;
        //创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();
    }

    public void doLogin(String account, String password) {
        String url = Contants.BASE_URL + Contants.LOGIN + "?account=" + account + "&password=" + password;
        //创建一个Request
        final Request request = new Request.Builder().url(url).build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //请求加入调度
        call.enqueue(new BaseCallback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (callback != null) {
                    callback.fail(404, "");
                }
            }

            @Override
            public void onResponse(Response response) {
                if (callback != null) {
                    callback.success(response);
                }
            }
        });
    }

}
