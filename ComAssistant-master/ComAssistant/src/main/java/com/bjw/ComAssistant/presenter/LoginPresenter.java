package com.bjw.ComAssistant.presenter;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

import com.bjw.ComAssistant.bean.user.UserBean;
import com.bjw.ComAssistant.util.BaseCallback;
import com.bjw.ComAssistant.util.Contants;
import com.bjw.ComAssistant.util.RequestCallback;

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

                }
            }

            @Override
            public void onResponse(Response response) {
                if (callback != null) {
                    String data = null;
                    try {
                        data = response.body().string();
                        JSONObject object = new JSONObject(data);
                        int code = object.getInt("code");
                        JSONObject o = object.getJSONObject("data");
                        if (code == 200) {
                            String uid = o.getString("uid");
                            String name = o.getString("name");
                            String access_token = o.getString("access_token");
                            callback.success(new UserBean(uid, name, access_token));
                        } else {
                            callback.fail(code, data);
                        }
                    } catch (Exception e) {
                        callback.fail(201, "网络请求错误，请稍后重试");
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
