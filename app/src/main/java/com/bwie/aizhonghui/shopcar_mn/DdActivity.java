package com.bwie.aizhonghui.shopcar_mn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DdActivity extends AppCompatActivity {

    @BindView(R.id.tv_we)
    TextView tvWe;
    @BindView(R.id.tv_sfj)
    TextView tvSfj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dd);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_we, R.id.tv_sfj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_we:
                break;
            case R.id.tv_sfj:
                break;
        }
    }
}
