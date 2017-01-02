package eleweigh.woxian.com.eleweight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidParameterException;

import eleweigh.woxian.com.eleweight.R;
import eleweigh.woxian.com.eleweight.android_serialport_api.SerialHelper;
import eleweigh.woxian.com.eleweight.android_serialport_api.SerialPortFinder;
import eleweigh.woxian.com.eleweight.application.EApplication;
import eleweigh.woxian.com.eleweight.bean.serial.ComBean;
import eleweigh.woxian.com.eleweight.presenter.WeightPresenter;

public class MainActivity extends BaseActivity implements WeightPresenter.CastWeightInterface {
    TextView tv_weight;
    TextView login;
    SerialPortFinder mSerialPortFinder;
    SerialControl ComC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initSerialPortCom();
    }

    private void initSerialPortCom() {
        mSerialPortFinder = new SerialPortFinder();
        String[] entryValues = mSerialPortFinder.getAllDevicesPath();

        ComC.setPort("8888");
        ComC.setBaudRate("200");
        OpenComPort(ComC);

    }

    protected void initView() {
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    protected void initData() {
        WeightPresenter.getInstance().registerCastWeightWatcher(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeightPresenter.getInstance().unRegisterCasrWeightWatcher(this);
    }

    @Override
    public void onWeightNumChanged(String weight) {

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
            DispQueue.AddQueue(ComRecData);//线程定时刷新显示(推荐)
            /*
            runOnUiThread(new Runnable()//直接刷新显示
			{
				public void run()
				{
					DispRecData(ComRecData);
				}
			});*/
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
            ComPort.open();
        } catch (SecurityException e) {
            ShowMessage("打开串口失败:没有串口读/写权限!");
        } catch (IOException e) {
            ShowMessage("打开串口失败:未知错误!");
        } catch (InvalidParameterException e) {
            ShowMessage("打开串口失败:参数错误!");
        }
    }

    //------------------------------------------显示消息
    private void ShowMessage(String sMsg) {
        Toast.makeText(this, sMsg, Toast.LENGTH_SHORT).show();
    }

}
