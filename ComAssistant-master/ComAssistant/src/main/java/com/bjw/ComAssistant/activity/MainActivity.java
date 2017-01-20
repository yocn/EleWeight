package com.bjw.ComAssistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bjw.ComAssistant.MyFunc;
import com.bjw.ComAssistant.R;
import com.bjw.ComAssistant.SerialHelper;
import com.bjw.ComAssistant.application.EApplication;
import com.bjw.ComAssistant.presenter.WeightPresenter;
import com.bjw.ComAssistant.util.Contants;
import com.bjw.ComAssistant.util.Loger;
import com.bjw.ComAssistant.util.Utils;
import com.bjw.bean.ComBean;

import java.io.IOException;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPortFinder;

public class MainActivity extends BaseActivity implements WeightPresenter.CastWeightInterface {
    TextView tv_weight;
    TextView tv_test;
    TextView login;
    SerialPortFinder mSerialPortFinder;
    SerialControl ComC;
    DispQueueThread mDispQueue;//刷新显示线程
    String tempWeight = "0.0";
    StringBuilder sbTemp = new StringBuilder();
    Button bt_clear;
    StringBuilder mTotalSB = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initSerialPortCom();
    }

    private void initSerialPortCom() {
        ComC = new SerialControl();
        mSerialPortFinder = new SerialPortFinder();

        ComC.setPort("/dev/ttyS1");
        ComC.setBaudRate("9600");
        OpenComPort(ComC);
    }

    protected void initView() {
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_test = (TextView) findViewById(R.id.tv_test);
        login = (TextView) findViewById(R.id.login);

        bt_clear = (Button) findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendPortData(ComC, mToZeros);
                Toast.makeText(MainActivity.this, "发送成功---", Toast.LENGTH_LONG).show();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OpenComPort(ComC);
                if (EApplication.isLoginSuccess) {
                    Intent i = new Intent(MainActivity.this, DetailListActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private byte[] mToZeros = new byte[6];

    protected void initData() {
        for (int i = 0; i < Contants.toZero.length; i++) {
            mToZeros[i] = MyFunc.HexToByte(Contants.toZero[i] + "");
            System.out.println(mToZeros[i]);
        }

        mDispQueue = new DispQueueThread();
        mDispQueue.start();
        WeightPresenter.getInstance().registerCastWeightWatcher(this);
    }

    @Override
    public void onWeightNumChanged(String weight) {

    }

    private String mPreShow = "";
    private StringBuilder testText = new StringBuilder();

    private void showWeight(String show) {
        if (testText.length() > 2000) {
            testText = new StringBuilder();
        }
        tv_test.setText(testText.toString());
        tv_weight.setText(show);
        sbTemp = new StringBuilder();
    }

    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                while (sbTemp.length() > 30) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String exe = Utils.exeData(sbTemp.toString());
                            mTotalSB.append(sbTemp.toString()).append("--").append(exe).append("********\n");
                            showWeight(Utils.exeData(sbTemp.toString()));
                        }
                    });
                    try {
                        Thread.sleep(Contants.TIME_SHOW);//显示性能高的话，可以把此数值调小。
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    //----------------------------------------------------串口控制类
    private class SerialControl extends SerialHelper {

        //		public SerialControl(String sPort, String sBaudRate){
//			super(sPort, sBaudRate);
//		}
        public SerialControl() {
        }

        @Override
        protected void onDataReceived(final ComBean ComRecData) {
            //数据接收量大或接收时弹出软键盘，界面会卡顿,可能和6410的显示性能有关
            //直接刷新显示，接收数据量大时，卡顿明显，但接收与显示同步。
            //用线程定时刷新显示可以获得较流畅的显示效果，但是接收数据速度快于显示速度时，显示会滞后。
            //最终效果差不多-_-，线程定时刷新稍好一些。
//            DispQueue.AddQueue(ComRecData);//线程定时刷新显示(推荐)
            tempWeight = new String(ComRecData.bRec);
            sbTemp.append(tempWeight);
            testText.append(tempWeight + "-----------------");
            WeightPresenter.getInstance().castWeight(sbTemp.toString());
            //直接刷新显示
        }
    }

    //----------------------------------------------------串口发送
    private void sendPortData(SerialHelper ComPort, byte[] sOut) {
        if (ComPort != null && ComPort.isOpen()) {
            ComPort.sendTxt(sOut);
        }
    }

    //----------------------------------------------------关闭串口
    private void CloseComPort(SerialHelper ComPort) {
        if (ComPort != null) {
            ComPort.stopSend();
            ComPort.close();
        }
    }

    //----------------------------------------------------打开串口
    private void OpenComPort(SerialHelper ComPort) {
        try {
            Loger.d("OpenComPort;---------");
            ComPort.open();
        } catch (SecurityException e) {
            Loger.d("SecurityException;---------");
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            Loger.d("IOException;---------");
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            Loger.d("InvalidParameterException;---------");
            ShowMessage("打开串口失败:参数错误!");
        }
    }

    @Override
    public void onDestroy() {
//        saveAssistData(AssistData);
        CloseComPort(ComC);
        WeightPresenter.getInstance().unRegisterCasrWeightWatcher(this);
        Utils.writeFileToSD(mTotalSB.toString());
        super.onDestroy();
    }

    //------------------------------------------显示消息
    private void ShowMessage(String sMsg) {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }

}
