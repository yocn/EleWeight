package eleweigh.woxian.com.eleweight.presenter;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import eleweigh.woxian.com.eleweight.bean.product.ProductBean;
import eleweigh.woxian.com.eleweight.bean.product.ProductListBean;
import eleweigh.woxian.com.eleweight.util.BaseCallback;
import eleweigh.woxian.com.eleweight.util.Contants;
import eleweigh.woxian.com.eleweight.util.Loger;
import eleweigh.woxian.com.eleweight.util.RequestCallback;

/**
 * Created by Yocn on 16.11.28.
 */

public class GetListPresenter extends BasePresenter {
    RequestCallback callback;
    OkHttpClient mOkHttpClient;

    public GetListPresenter(RequestCallback callback) {
        this.callback = callback;
        //创建okHttpClient对象
        mOkHttpClient = new OkHttpClient();
    }

    private void getList() {

    }

    public void getList(String uid, String access_token) {
        String url = Contants.BASE_URL + Contants.GET_LIST + "?uid=" + uid + "&access_token=" + access_token;
        Loger.d(url);

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
                        if (code == 200) {
                            JSONObject o = object.getJSONObject("data");
                            ArrayList<ProductBean> goods_list = new ArrayList<ProductBean>();
                            String order_id = o.getString("order_id");
                            String start_date = o.getString("start_date");
                            String end_date = o.getString("end_date");
                            String goods_count = o.getString("goods_count");
                            JSONArray goodArray = o.getJSONArray("goods_list");
                            for (int i = 0; i < goodArray.length(); i++) {
                                JSONObject detailObject = goodArray.getJSONObject(i);
                                String line_num = detailObject.getString("line_num");
                                String goods_id = detailObject.getString("goods_id");
                                String goods_name = detailObject.getString("goods_name");
                                String customer_id = detailObject.getString("customer_id");
                                String customer_store_name = detailObject.getString("customer_store_name");
                                String unit_id = detailObject.getString("unit_id");
                                String quantity = detailObject.getString("quantity");
                                String quantity_unit = detailObject.getString("quantity_unit");
                                String quantity_real = detailObject.getString("quantity_real");
                                String quantity_real_unit = detailObject.getString("quantity_real_unit");
                                ProductBean mProductBean = new ProductBean(line_num, goods_id, goods_name, customer_id,
                                        customer_store_name, unit_id, quantity, quantity_unit, quantity_real, quantity_real_unit);
                                goods_list.add(mProductBean);
                            }
                            ProductListBean mProductListBean = new ProductListBean(order_id, start_date, end_date, goods_count, goods_list);
                            callback.success(mProductListBean);
                        } else {
                            String s = object.getString("data");
                            callback.fail(code, s.toString());
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
