package com.bjw.ComAssistant.presenter;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.IOException;

import com.bjw.ComAssistant.util.BaseCallback;
import com.bjw.ComAssistant.util.Contants;
import com.bjw.ComAssistant.util.Loger;
import com.bjw.ComAssistant.util.RequestCallback;

/**
 * Created by Yocn on 16.11.28.
 */

public class QuantityPresenter extends BasePresenter {
    RequestCallback callback;
    OkHttpClient mOkHttpClient;

    public QuantityPresenter(RequestCallback callback) {
        this.callback = callback;
        //创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();
    }

    /**
     * 商品分拣接口
     * 称重和不称重都是调用此接口，称重：读取智能秤返回重量赋值给“quantity_real”参数；
     * 不承重：将商品“quantity”的值赋值给“quantity_real”参数。
     *
     * @param order_id      分拣单id
     * @param goods_id      商品id
     * @param customer_id   采购商id
     * @param unit_id       单位id
     * @param quantity_real 商品实际重量
     * @param access_token  用户合法标识
     */
    public void quantity(String order_id, String goods_id, String customer_id, String unit_id,
                         String quantity_real, String access_token) {
        String url = Contants.BASE_URL + Contants.CHECK +
                "?order_id=" + order_id + "&goods_id=" + goods_id
                + "&customer_id=" + customer_id + "&unit_id=" + unit_id
                + "&quantity_real=" + quantity_real + "&access_token=" + access_token;
        Loger.d("url" + url);
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
                        String result = object.getString("data");
                        if (code == 200) {
                            callback.success(result);
                        } else {
                            callback.fail(code, result);
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
