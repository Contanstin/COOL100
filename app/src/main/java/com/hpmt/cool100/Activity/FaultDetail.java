package com.hpmt.cool100.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hpmt.cool100.R;

public class FaultDetail extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_title;
    private ImageButton imgBtn_back;// 返回按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fault_detail);
        txt_title = (TextView) findViewById(R.id.center_txt);
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(R.string.failure_details);
        imgBtn_back = (ImageButton) findViewById(R.id.left_imgBtn);
        imgBtn_back.setVisibility(View.VISIBLE);
        imgBtn_back.setOnClickListener(this);

        TextView fault_code=(TextView)findViewById(R.id.detail_t1);
        TextView fault_reason=(TextView)findViewById(R.id.fault_reason);
        TextView fault_name=(TextView)findViewById(R.id.detail2_t2);
        TextView fault_method=(TextView)findViewById(R.id.falut_solution);
        Intent Mesage=getIntent();
        String faultcode=Mesage.getStringExtra("faultcode");
        String reason=Mesage.getStringExtra("reason");
        String name=Mesage.getStringExtra("name");
        String method=Mesage.getStringExtra("method");
        fault_code.setText(faultcode);
        fault_reason.setText(reason);
        fault_name.setText(name);
        fault_method.setText(method);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_imgBtn:
                finish();
                break;
            default:
                break;
        }
    }
}
