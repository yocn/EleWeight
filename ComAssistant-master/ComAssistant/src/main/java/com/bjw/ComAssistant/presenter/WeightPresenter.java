package com.bjw.ComAssistant.presenter;

import java.util.ArrayList;

import com.bjw.ComAssistant.util.Loger;

/**
 * Created by Hui on 2016/12/4.
 */

public class WeightPresenter {
    private static WeightPresenter instance;
    private static ArrayList<CastWeightInterface> mInterfaceList = new ArrayList<CastWeightInterface>();

    public interface CastWeightInterface {
        void onWeightNumChanged(String weight);
    }

    private WeightPresenter() {
    }

    public static synchronized WeightPresenter getInstance() {
        if (instance == null) {
            instance = new WeightPresenter();
        }
        return instance;
    }

    public void registerCastWeightWatcher(CastWeightInterface mInterface) {
        mInterfaceList.add(mInterface);
        Loger.d("注册----" + mInterfaceList.toString());
    }

    public void unRegisterCasrWeightWatcher(CastWeightInterface mInterface) {
        mInterfaceList.remove(mInterface);
        Loger.d("注销-----" + mInterfaceList.toString());
    }

    public void castWeight(String weight) {
        for (CastWeightInterface mInterface : mInterfaceList) {
            mInterface.onWeightNumChanged(weight);
        }
    }

}
