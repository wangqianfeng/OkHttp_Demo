package com.bwie.okhttp_demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String s = (String) msg.obj;
                textView.setText(s);
                textView2.setText("TextView2");
                textView3.setText("TextView3");
                textView4.setText("TextView4");

            } else if (msg.what == 1) {
                String s = (String) msg.obj;
                textView2.setText(s);
                textView.setText("TextView");
                textView3.setText("TextView3");
                textView4.setText("TextView4");

            } else if (msg.what == 2) {
                String s = (String) msg.obj;
                textView3.setText(s);
                textView2.setText("TextView2");
                textView.setText("TextView");
                textView4.setText("TextView4");

                Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
            }else if (msg.what == 3) {
                String s = (String) msg.obj;
                textView4.setText(s);
                textView3.setText("TextView3");
                textView2.setText("TextView2");
                textView.setText("TextView");
                Toast.makeText(MainActivity.this,"请求成功",Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
    }

    @Override
    public void onClick(View v) {
        HttpUtils mHttpUtils = new HttpUtils(mHandler);
        switch (v.getId()) {
            case R.id.button:
                mHttpUtils.byGetExecute();

                break;
            case R.id.button2:
                mHttpUtils.byGetEnqueue();
                break;
            case R.id.button3:
                mHttpUtils.byPostExecute();
                break;
            case R.id.button4:

                mHttpUtils.byPostEnqueue();

                break;
        }
    }
}
