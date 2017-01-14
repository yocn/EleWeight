package com.bjw.ComAssistant.util;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by Yocn on 16.11.28.
 */

public abstract class BaseCallback implements Callback {
    @Override
    public abstract void onFailure(Request request, IOException e);

    @Override
    public abstract void onResponse(Response response);
}
