package com.bjw.ComAssistant.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.bjw.ComAssistant.R;

/**
 * 自定义过场动画，主要用户数据加载时，显示等待progress
 */
public class ProgressDialog extends AlertDialog {

    private ImageView progressImg;
    //帧动画
    private AnimationDrawable animation;

    public ProgressDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog_layout);

        //点击imageview外侧区域，动画不会消失
        setCanceledOnTouchOutside(false);

        progressImg = (ImageView) findViewById(R.id.refreshing_img);
        //加载动画资源
        animation = (AnimationDrawable) progressImg.getDrawable();
    }

    /**
     * 在AlertDialog的 onStart() 生命周期里面执行开始动画
     */
    @Override
    protected void onStart() {
        super.onStart();
        animation.start();
    }

    /**
     * 在AlertDialog的onStop()生命周期里面执行停止动画
     */
    @Override
    protected void onStop() {
        super.onStop();
        animation.stop();
    }
}
