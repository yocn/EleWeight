package eleweigh.woxian.com.eleweight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import eleweigh.woxian.com.eleweight.R;

public class LoginActivity extends BaseActivity {
    ImageView iv_close;
    EditText et_login;
    EditText et_psd;
    CheckBox checkBox;
    TextView tv_login;
    private boolean isRemeberPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    protected void initView() {
        iv_close = (ImageView) findViewById(R.id.iv_close);
        et_login = (EditText) findViewById(R.id.et_login);
        et_psd = (EditText) findViewById(R.id.et_psd);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        tv_login = (TextView) findViewById(R.id.tv_login);
    }

    protected void initData() {
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
                ;
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = et_login.getText().toString();
                String userPassword = et_psd.getText().toString();
                Toast.makeText(LoginActivity.this, "login--" + isRemeberPass, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, DetailListActivity.class));
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRemeberPass = isChecked;
            }
        });
    }
}