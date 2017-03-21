package com.bjw.ComAssistant.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bjw.ComAssistant.R;
import com.bjw.ComAssistant.activity.print.BluetoothService;
import com.bjw.ComAssistant.activity.print.DeviceListActivity;
import com.bjw.ComAssistant.activity.print.PrintTicket;
import com.bjw.ComAssistant.application.EApplication;
import com.bjw.ComAssistant.bean.product.ProductBean;
import com.bjw.ComAssistant.bean.product.ProductListBean;
import com.bjw.ComAssistant.presenter.GetListPresenter;
import com.bjw.ComAssistant.presenter.QuantityPresenter;
import com.bjw.ComAssistant.presenter.WeightPresenter;
import com.bjw.ComAssistant.util.Contants;
import com.bjw.ComAssistant.util.Loger;
import com.bjw.ComAssistant.util.RequestCallback;
import com.bjw.ComAssistant.util.SharedPreferencesUtil;
import com.bjw.ComAssistant.util.Utils;
import com.bjw.ComAssistant.view.DetailAdapter;
import com.bjw.ComAssistant.view.DoubleDatePickerDialog;
import com.bjw.ComAssistant.view.listview.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DetailListActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener, WeightPresenter.CastWeightInterface {
    DetailAdapter mDetailAdapter;
    TextView tv_address;//地址
    TextView tv_remark;//商品留言
    View view_divider;
    TextView tv_left;//剩余商品数量
    Button btn_check;//查漏
    ImageView iv_user;//用户信息
    ImageView iv_logout;//登出
    ImageView iv_home;//home'
    XListView lv_content;//listview
    TextView tv_brief;//目前分拣日期
    TextView tv_date_picker_start;//开始日期
    TextView tv_date_picker_end;//结束日期
    Button btn_no;//不称重
    Button btn_ok;//确定

    RelativeLayout rl_full_input;//输入的时候的总界面
    LinearLayout ll_full_input;//白色的输入界面
    RelativeLayout ll_full_info;//白色的登录信息界面
    TextView tv_login_info;//登录信息
    TextView tv_num_input;//输入界面的id
    TextView tv_name_input;//输入界面的名字
    TextView tv_weight_input;//输入界面的称重
    TextView tv_connect;//输入界面的称重
    EditText et_input;//输入界面的输入框
    Button btn_logout;//个人信息页的登出按钮
    ImageView iv_close;//个人信息页的关闭按钮
    ImageView iv_print;//个人信息页的打印开关
    private int mCurrentPosition = 0;
    private String mCurrentUnit = "";
    private String mCurrentOrderId = "";
    private int mUnCheckPosition = 0;//查漏的时候要用到的未称重的item
    private int mOldUnCheckPosition = 0;//查漏的时候要用到的上一个未称重的item，用来检测是不是到了最后一个漏掉的item
    boolean isInputShow = false;//输入框是否*在显示
    Activity mContext;
    QuantityPresenter mQuantityPresenter;
    GetListPresenter mGetListPresenter;
    ProductListBean mProductListBean = null;
    ArrayList<ProductBean> mProductBeanList = new ArrayList<>();
    String tempWeight = "0.0";
    DispQueueThread mDispThread;
    private boolean isCheckNoWeightOption = false;//是否是点击查漏的操作，如果是点击的查漏，称重后跳到下一个没有重量的item，否则跳到下一个
    private boolean isPrintOpen = false;//打印开关是否开启
    TextView tv_test;
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothService mService = null;
    private String mConnectedDeviceName = null;
    private PrintTicket printer;
    ProgressBar pb_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        initView();
        initData();
    }

    protected void initView() {
        tv_test = (TextView) findViewById(R.id.tv_test);
        iv_close = (ImageView) findViewById(R.id.iv_close);
        iv_print = (ImageView) findViewById(R.id.iv_print);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        tv_address = (TextView) findViewById(R.id.tv_address);
        view_divider = findViewById(R.id.view_divider);
        tv_remark = (TextView) findViewById(R.id.tv_remark);
        tv_left = (TextView) findViewById(R.id.tv_left);
        btn_check = (Button) findViewById(R.id.btn_check);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        lv_content = (XListView) findViewById(R.id.lv_content);
        tv_brief = (TextView) findViewById(R.id.tv_brief);
        tv_date_picker_start = (TextView) findViewById(R.id.tv_date_picker_start);
        tv_date_picker_end = (TextView) findViewById(R.id.tv_date_picker_end);
        btn_no = (Button) findViewById(R.id.btn_no);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        rl_full_input = (RelativeLayout) findViewById(R.id.rl_full_input);
        ll_full_input = (LinearLayout) findViewById(R.id.ll_full_input);
        ll_full_info = (RelativeLayout) findViewById(R.id.ll_full_info);
        tv_login_info = (TextView) findViewById(R.id.tv_login_info);
        tv_num_input = (TextView) findViewById(R.id.tv_num_input);
        tv_name_input = (TextView) findViewById(R.id.tv_name_input);
        tv_weight_input = (TextView) findViewById(R.id.tv_weight_input);
        et_input = (EditText) findViewById(R.id.et_input);

        pb_connect = (ProgressBar) findViewById(R.id.pb_connect);
        tv_connect = (TextView) findViewById(R.id.tv_connect);
    }


    protected void initData() {
        mContext = this;
//        isPrintOpen = SharedPreferencesUtil.getInstance(mContext).getBoolean("print", false);
        if (isPrintOpen) {
            iv_print.setImageResource(R.drawable.on);
        } else {
            iv_print.setImageResource(R.drawable.off);
        }

        mDispThread = new DispQueueThread();
        mDispThread.start();
        WeightPresenter.getInstance().registerCastWeightWatcher(this);
        mGetListPresenter = new GetListPresenter(mGetListCallback);
        mQuantityPresenter = new QuantityPresenter(mQuantityCallback);
        mDetailAdapter = new DetailAdapter(this);
        lv_content.setAdapter(mDetailAdapter);
        lv_content.setPullLoadEnable(false);
        lv_content.setXListViewListener(this);
        mDetailAdapter.setData(mProductBeanList);
        rl_full_input.setVisibility(View.GONE);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPreHeight = 0;
                isCheckNoWeightOption = false;
                mCurrentPosition = position - 1;
                mUnCheckPosition = mCurrentPosition;
                mOldUnCheckPosition = 0;
                mCurrentUnit = mProductBeanList.get(mCurrentPosition).getQuantity_unit();
                showInput(mCurrentPosition);
                tv_address.setText(mProductBeanList.get(mCurrentPosition).getCustomer_store_name());
            }
        });

        setListener();
        loadData();
        initPrint();
    }

    private void initPrint() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mService = new BluetoothService(this, mHandler);
        printer = new PrintTicket(mService);
    }

    private void loadData() {
        if (EApplication.user == null) return;
        tv_login_info.setText(EApplication.user.getName() + " (" + EApplication.user.getUid() + ")");
        mGetListPresenter.getList(EApplication.user.getUid(), EApplication.user.getAccess_token());
    }


    /**
     * 称重
     */
    RequestCallback mQuantityCallback = new RequestCallback() {
        @Override
        public void success(Object o) {
            Loger.d(o.toString());
        }

        @Override
        public void fail(int code, String msg) {
            Loger.d(code + "----" + msg);
        }
    };

    /**
     * 获取列表
     */
    RequestCallback mGetListCallback = new RequestCallback() {
        @Override
        public void success(final Object o) {
            Loger.d(o.toString());
            if (o != null && o instanceof ProductListBean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductListBean bean = (ProductListBean) o;
                        notifyAllView(bean);
                        tv_date_picker_start.setText(bean.getStart_date());
                        tv_date_picker_end.setText(bean.getEnd_date());
                    }
                });
            }
        }

        @Override
        public void fail(int code, String msg) {
            Loger.d("code" + code + "----msg---" + msg);
        }
    };

    private void notifyAllView(ProductListBean bean) {
        mProductListBean = bean;
        mCurrentOrderId = mProductListBean.getOrder_id();
        mProductBeanList = bean.getGoods_list();
        mDetailAdapter.setData(mProductBeanList);
        lv_content.stopRefresh(true);
        notifyListCount();
    }

    private void notifyListCount() {
        tv_left.setText("剩余商品数量：" + getListCount(mProductBeanList));
    }

    /**
     * 获取当前查漏的list的size
     *
     * @param mProductBeanList
     * @return
     */
    public int getListCount(ArrayList<ProductBean> mProductBeanList) {
        int n = 0;
        for (int i = 0; i < mProductBeanList.size(); i++) {
            String read = mProductBeanList.get(i).getQuantity_real();
            if (read == null || "".equals(read)) {
                n++;
//                mOldUnCheckPosition = i;
            }
        }
        return n;
    }

    /**
     * 检测是否是数字
     *
     * @param s 需要检测的数字
     * @return true：是数字，false不是数字
     */
    private boolean checkIsNum(String s) {
        Pattern p = Pattern.compile("-?[0-9]+.?[0-9]+");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    private void setListener() {
        ll_full_input.setOnClickListener(this);
        rl_full_input.setOnClickListener(this);
        iv_logout.setOnClickListener(this);
        iv_home.setOnClickListener(this);
        btn_no.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        btn_check.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        tv_date_picker_start.setOnClickListener(this);
        tv_date_picker_end.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        iv_print.setOnClickListener(this);
    }

    boolean hasNoWeight = false;

    /**
     * 找没有还称重的item
     *
     * @param isCheck 是否是查漏操作
     */
    private void checkNoWeight(boolean isCheck) {
        hideInput();
        mPreHeight = 0;
        hasNoWeight = false;
        isCheckNoWeightOption = true;
        if (!isCheck && mOldUnCheckPosition == mProductBeanList.size() - 1) {
            Toast.makeText(DetailListActivity.this, "已到底部", Toast.LENGTH_SHORT).show();
            return;
        }
        label:
        for (int i = mUnCheckPosition; i < mProductBeanList.size(); i++) {
            String real = mProductBeanList.get(i).getQuantity_real();
            if ("".equals(real)) {
                /**读数是空的*/
                mUnCheckPosition = i;
                hasNoWeight = true;
                mCurrentPosition = i;
                break label;
            }
        }

        mOldUnCheckPosition = mCurrentPosition;
        Loger.d("mCurrentPosition----" + mCurrentPosition);
        Loger.d("mOldUnCheckPosition----" + mOldUnCheckPosition);
        if (hasNoWeight) {
            lv_content.smoothScrollToPositionFromTop(mUnCheckPosition - 1, 0);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showInput(mUnCheckPosition);
                }
            }, 500);
        } else {
            mUnCheckPosition = 0;
            isInputShow = false;
            if (!hasEmptyItem()) {
                Toast.makeText(DetailListActivity.this, "没有遗漏的条目", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 还有没有空白的条目,只要有一个是空的，就不弹出 没有遗漏的条目
     *
     * @return
     */
    private boolean hasEmptyItem() {
        boolean empty = false;
        for (int i = 0; i < mProductBeanList.size(); i++) {
            String read = mProductBeanList.get(i).getQuantity_real();
            if (read == null || "".equals(read)) {
                empty = true;
            }
        }
        Loger.d("empty---" + empty);
        return empty;
    }

    private void showInput(int position) {
        if (position >= mProductBeanList.size()) {
            isInputShow = false;
            return;
        }
        if (position > 2) {
            lv_content.smoothScrollToPositionFromTop(mUnCheckPosition - 1, 0);
        }
        isInputShow = true;
        ProductBean detailBean = mProductBeanList.get(position);
        tv_num_input.setText(detailBean.getLine_num());
        tv_name_input.setText(detailBean.getGoods_name());
        tv_weight_input.setText(detailBean.getQuantity() + detailBean.getQuantity_unit());
        if (detailBean.getQuantity_real() != null && !"".equals(detailBean.getQuantity_real())) {
            /**数量不为空的情况下*/
            et_input.setText(detailBean.getQuantity_real());
        } else {
            et_input.setText("0.0");
        }
//        Loger.d("detailBean---" + detailBean.toString());
        if (detailBean.getRemark() != null && !"".equals(detailBean.getRemark())) {
            tv_remark.setVisibility(View.VISIBLE);
            view_divider.setVisibility(View.VISIBLE);
            tv_remark.setText(detailBean.getRemark());
        } else {
            tv_remark.setVisibility(View.GONE);
            view_divider.setVisibility(View.GONE);
        }

        et_input.setSelection(et_input.getText().toString().length());
        et_input.setFocusable(true);
        et_input.setFocusableInTouchMode(true);
        et_input.requestFocus();
        rl_full_input.setVisibility(View.VISIBLE);
        ll_full_input.setVisibility(View.VISIBLE);
        mUnCheckPosition++;
    }

    private void hideInput() {
        rl_full_input.setVisibility(View.GONE);
        ll_full_input.setVisibility(View.GONE);
        ll_full_info.setVisibility(View.GONE);
        isInputShow = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_full_input://点击不显示
                hideInput();
                break;
            case R.id.ll_full_input://称重的界面
                rl_full_input.setVisibility(View.VISIBLE);
                ll_full_input.setVisibility(View.VISIBLE);
                isInputShow = true;
                break;
            case R.id.btn_no://不称重
                rl_full_input.setVisibility(View.GONE);
                ll_full_input.setVisibility(View.GONE);
                if (mCurrentPosition >= mProductBeanList.size()) {
                    isInputShow = false;
                    return;
                }
                if (!isInputShow) {
                    break;
                }
                ProductBean productBean = mProductBeanList.get(mCurrentPosition);
                String read = productBean.getQuantity();
                productBean.setQuantity_real(read);
                productBean.setQuantity_real_unit(productBean.getQuantity_unit());
                printProduct(productBean);
                mDetailAdapter.setData(mProductBeanList);
                mQuantityPresenter.quantity(mCurrentOrderId, productBean.getGoods_id(), productBean.getCustomer_id(), productBean.getUnit_id(), productBean.getQuantity(), EApplication.user.getAccess_token());
                notifyListCount();
//                if (isCheckNoWeightOption) {
//                    /**如果是查漏操作，跳到下一个查漏*/
                checkNoWeight(false);
//                } else {
//                    /**如果不是是查漏操作，跳到下一个*/
//                    mCurrentPosition++;
//                    showInput(mCurrentPosition);
//                }
                break;
            case R.id.btn_ok://确定
                if (mCurrentPosition >= mProductBeanList.size()) {
                    isInputShow = false;
                    return;
                }
                if (!isInputShow) {
                    break;
                }
                String result = et_input.getText().toString().trim();
                if (checkIsNum(result)) {
                    float f = Float.parseFloat(result);
                    if (f < 0.01) {
                        /**认为是0,Do Nothing*/
                    } else {
                        ProductBean product = mProductBeanList.get(mCurrentPosition);
                        product.setQuantity_real(result);
                        product.setQuantity_real_unit("斤");
                        mDetailAdapter.setData(mProductBeanList);
                        mQuantityPresenter.quantity(mCurrentOrderId, product.getGoods_id(), product.getCustomer_id(), product.getUnit_id(), result, EApplication.user.getAccess_token());
                        printProduct(product);
                    }
                } else {
                    Toast.makeText(this, "请输入数字", Toast.LENGTH_SHORT).show();
                }
                rl_full_input.setVisibility(View.GONE);
                ll_full_input.setVisibility(View.GONE);
                et_input.setText("");
                notifyListCount();
//                if (isCheckNoWeightOption) {
//                    /**如果是查漏操作，跳到下一个查漏*/
                checkNoWeight(false);
//                } else {
//                    /**如果不是是查漏操作，跳到下一个*/
//                    mCurrentPosition++;
//                    showInput(mCurrentPosition);
//                }


                break;
            case R.id.btn_check://查漏
                mUnCheckPosition = 0;
                checkNoWeight(true);
                break;
            case R.id.iv_user://用户
                rl_full_input.setVisibility(View.VISIBLE);
                ll_full_info.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_home://home
                break;
            case R.id.iv_print:
                if (isPrintOpen) {
                    /**开启状态，需要关掉*/
                    isPrintOpen = false;
                    iv_print.setImageResource(R.drawable.off);
//                    SharedPreferencesUtil.getInstance(mContext).saveBoolean("print", isPrintOpen);
                    mService.stop();
                } else {
                    /**关闭状态，需要开启蓝牙*/
                    isPrintOpen = true;
                    iv_print.setImageResource(R.drawable.on);
//                    SharedPreferencesUtil.getInstance(mContext).saveBoolean("print", isPrintOpen);
                    connect();
                }
                break;
            case R.id.iv_close://我的信息，关闭
                rl_full_input.setVisibility(View.GONE);
                ll_full_info.setVisibility(View.GONE);
                break;
            case R.id.btn_logout://登出
                EApplication.isLoginSuccess = false;
                SharedPreferencesUtil.getInstance(mContext).saveBoolean(EApplication.LoginString, false);
                finish();
                break;
            case R.id.tv_date_picker_start://开始日期
//                showCalendarDialog();
                break;
            case R.id.tv_date_picker_end://结束日期
//                showCalendarDialog();
                break;
        }
    }

    private void showCalendarDialog() {
        Calendar c = Calendar.getInstance();
        new DoubleDatePickerDialog(DetailListActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                  int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                  int endDayOfMonth, TimePicker startTimePicker, int startHour, int startMin,
                                  TimePicker endTimePicker, int endHour, int endMin) {
                String start = String.format("%d-%d-%d %d:%d", startYear, startMonthOfYear + 1, startDayOfMonth, startHour, startMin);
                String end = String.format("%d-%d-%d %d:%d", endYear, endMonthOfYear + 1, endDayOfMonth, endHour, endMin);
                tv_date_picker_start.setText(start);
                tv_date_picker_end.setText(end);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
    }

    /**
     * 打印
     *
     * @param product 产品
     */
    private void printProduct(ProductBean product) {
        if (printer != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String time = format.format(new Date());
            printer.printGoodsInfo(product.getCustomer_store_name(), "商品：" + product.getGoods_name(), "数量：" + product.getQuantity_real() + product.getQuantity_real_unit(), time);
        }
    }


    private void connect() {
        if (mBluetoothAdapter != null && !mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 2);
        } else {
            Intent serverIntent = new Intent(DetailListActivity.this, DeviceListActivity.class);
            startActivityForResult(serverIntent, 1);
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onLoadMore() {
//        lv_content.stopLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WeightPresenter.getInstance().unRegisterCasrWeightWatcher(this);
    }

    StringBuilder sb = new StringBuilder();
    private float mPreHeight = 0;

    //----------------------------------------------------刷新显示线程
    private class DispQueueThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                while (tempWeight != null && !"".equals(tempWeight)) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String s = Utils.exeData(tempWeight) + "";
//                            Loger.d("weight--------------s----------" + s);
                            sb.append("  " + tempWeight + "----" + s + "+++");
                            if (sb.length() > 1000) {
                                sb = new StringBuilder();
                            }
                            float f = Float.parseFloat(s);
                            if (mPreHeight > f) {
                                /**如果是变小的，说明放上货品了，可以变成0*/
                                mPreHeight = f;
                                tv_test.setText(sb.toString());
                                et_input.setText(s);
                                et_input.setSelection(s.length());
                            } else {
                                mPreHeight = f;
                                tv_test.setText(sb.toString());
                                if (f < 0.01) {
                                    /**认为是0,Do Nothing*/
                                } else {
                                    et_input.setText(s);
                                    et_input.setSelection(s.length());
                                }
                            }
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

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            pb_connect.setVisibility(View.GONE);
                            tv_connect.setText("连接成功");
                            tv_connect.setVisibility(View.VISIBLE);
                            isPrintOpen = true;
                            iv_print.setImageResource(R.drawable.on);
//                            mtitle.setText("已经连接：");
//                            mtitle.append(mConnectedDeviceName);
//                            connectbutton.setEnabled(false);
//                            disconnectbutton.setEnabled(true);
                            break;
                        case BluetoothService.STATE_CONNECTING:
//                            mtitle.setText("正在连接");
                            pb_connect.setVisibility(View.VISIBLE);
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                            pb_connect.setVisibility(View.GONE);
                            tv_connect.setText("连接未成功");
                            tv_connect.setVisibility(View.VISIBLE);
                            isPrintOpen = false;
                            iv_print.setImageResource(R.drawable.off);
//                            connectbutton.setEnabled(true);
//                            disconnectbutton.setEnabled(false);
//                            mtitle.setText("没有连接");
                            break;
                    }
                    break;
                case BluetoothService.MESSAGE_WRITE:
                    // byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    // String writeMessage = new String(writeBuf);
                    break;
                case BluetoothService.MESSAGE_READ:
                    // byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    // String readMessage = new String(readBuf, 0, msg.arg1);
                    break;
                case BluetoothService.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(BluetoothService.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "成功连接到打印机" + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BluetoothService.TOAST), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onWeightNumChanged(String weight) {
//        Loger.d("weight--------------" + weight);
        tempWeight = weight;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                if (BluetoothAdapter.checkBluetoothAddress(address)) {
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    mService.connect(device);
                }
            }
        }
        Toast.makeText(this, "正在连接中，请稍候...", Toast.LENGTH_LONG).show();
    }
}
