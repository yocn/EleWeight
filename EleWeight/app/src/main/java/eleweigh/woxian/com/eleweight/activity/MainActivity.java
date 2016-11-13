package eleweigh.woxian.com.eleweight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import eleweigh.woxian.com.eleweight.R;

public class MainActivity extends BaseActivity {
    TextView tv_weight;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    protected void initView() {
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    protected void initData() {

    }
}
