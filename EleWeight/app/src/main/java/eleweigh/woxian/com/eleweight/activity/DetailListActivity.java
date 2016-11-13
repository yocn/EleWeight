package eleweigh.woxian.com.eleweight.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eleweigh.woxian.com.eleweight.R;
import eleweigh.woxian.com.eleweight.bean.DetailBean;
import eleweigh.woxian.com.eleweight.view.DetailAdapter;

public class DetailListActivity extends BaseActivity implements View.OnClickListener {
    DetailAdapter mDetailAdapter;
    TextView tv_address;//地址
    TextView tv_left;//剩余商品数量
    Button btn_check;//查漏
    ImageView iv_user;//用户信息
    ImageView iv_logout;//登出
    ImageView iv_home;//home'
    ListView lv_content;//listview
    TextView tv_brief;//目前分拣日期
    TextView tv_date_picker_start;//开始日期
    TextView tv_date_picker_end;//结束日期
    Button btn_no;//不称重
    Button btn_ok;//确定

    RelativeLayout rl_full_input;//输入的时候的总界面
    LinearLayout ll_full_input;//白色的输入界面
    TextView tv_num_input;//输入界面的id
    TextView tv_name_input;//输入界面的名字
    TextView tv_weight_input;//输入界面的称重
    EditText et_input;//输入界面的输入框
    List<DetailBean> mDetailBeanList = new ArrayList<>();
    private int mCurrentPosition = 0;
    boolean isInputShow = false;//输入框是否【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【【9898989898989898989898989898989898989898989898989898989898989898989898989898989898989898333333333333333333333333333333333333333333***在现实

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        initView();
        initData();
    }

    protected void initView() {
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_left = (TextView) findViewById(R.id.tv_left);
        btn_check = (Button) findViewById(R.id.btn_check);
        iv_user = (ImageView) findViewById(R.id.iv_user);
        iv_logout = (ImageView) findViewById(R.id.iv_logout);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        lv_content = (ListView) findViewById(R.id.lv_content);
        tv_brief = (TextView) findViewById(R.id.tv_brief);
        tv_date_picker_start = (TextView) findViewById(R.id.tv_date_picker_start);
        tv_date_picker_end = (TextView) findViewById(R.id.tv_date_picker_end);
        btn_no = (Button) findViewById(R.id.btn_no);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        rl_full_input = (RelativeLayout) findViewById(R.id.rl_full_input);
        ll_full_input = (LinearLayout) findViewById(R.id.ll_full_input);
        tv_num_input = (TextView) findViewById(R.id.tv_num_input);
        tv_name_input = (TextView) findViewById(R.id.tv_name_input);
        tv_weight_input = (TextView) findViewById(R.id.tv_weight_input);
        et_input = (EditText) findViewById(R.id.et_input);
    }

    protected void initData() {
        simulation();
        mDetailAdapter = new DetailAdapter(this);
        lv_content.setAdapter(mDetailAdapter);
        mDetailAdapter.setData(mDetailBeanList);
        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DetailListActivity.this, position + "", Toast.LENGTH_SHORT).show();
                mCurrentPosition = position;
                showInput(position);
            }
        });
    }

    private void showInput(int position) {
        DetailBean detailBean = mDetailBeanList.get(position);
        tv_num_input.setText(detailBean.getNum());
        tv_name_input.setText(detailBean.getName());
        tv_weight_input.setText(detailBean.getWeight());
        tv_weight_input.setText(detailBean.getRead());
        rl_full_input.setVisibility(View.VISIBLE);
    }

    int i = 0;

    private void simulation() {
        for (int i = 0; i < 20; i++) {
            i++;
            DetailBean mDetailBean = new DetailBean();
            mDetailBean.setNum(i + "");
            mDetailBean.setName("大白菜");
            mDetailBean.setWeight((50 - i) + "");
            mDetailBean.setRead("");
            mDetailBeanList.add(mDetailBean);
        }
    }

    /**
     * 检测是否是数字
     *
     * @param s 需要检测的数字
     * @return true：是数字，false不是数字
     */
    private boolean checkIsNum(String s) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(s);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_no://不称重
                rl_full_input.setVisibility(View.GONE);
                break;
            case R.id.rl_full_input://不称重
                break;
            case R.id.btn_ok://确定
                String result = et_input.getText().toString().trim();
                if (checkIsNum(result)) {
                    mDetailBeanList.get(mCurrentPosition).setRead(result);
                    mDetailAdapter.setData(mDetailBeanList);
                }
                rl_full_input.setVisibility(View.GONE);
                break;
            case R.id.btn_check://查漏
                break;
            case R.id.iv_user://用户
                break;
            case R.id.iv_logout://登出
                break;
            case R.id.iv_home://home
                break;
            case R.id.tv_date_picker_start://开始日期
                break;
            case R.id.tv_date_picker_end://结束日期
                break;
        }
    }
}
