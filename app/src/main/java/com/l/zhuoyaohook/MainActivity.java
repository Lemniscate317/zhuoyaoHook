package com.l.zhuoyaohook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb = findViewById(R.id.cb);
        cb.setChecked(SPUtils.isSwitch());

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SPUtils.setSwitch(b);
            }
        });

        final EditText et = findViewById(R.id.et);
        et.setText(SPUtils.getNum());

        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et != null) {
                    String numStr = et.getText().toString();
                    try {
                        Integer integer = Integer.valueOf(numStr);
                        int i = integer.intValue();
                        SPUtils.setNumber(i);
                    } catch (Exception e) {
                        Toast.makeText(App.mContext, e.getMessage() + e.getCause(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
