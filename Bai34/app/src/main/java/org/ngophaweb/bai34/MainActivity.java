package org.ngophaweb.bai34;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {
    ProgressBar bar;
    //khai báo handler class để xử lý đa tiến trình
    Handler handler;
    //dùng AtomicBoolean để thay thế cho boolean
    AtomicBoolean isrunning=new AtomicBoolean(false);
    //boolean
    Button btnstart;
    TextView lblmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                bar.setProgress(msg.arg1);
                lblmsg.setText(msg.arg1 + "%");
            }
        };
    }
    private void init(){
        bar = (ProgressBar) findViewById(R.id.progressBar1);
        btnstart = (Button) findViewById(R.id.btnstart);
        lblmsg = (TextView) findViewById(R.id.textView1);
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });
    }
    private void doStart(){
        isrunning.set(false);
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 100 && isrunning.get(); i++){
                    SystemClock.sleep(100);
                    Message msg = handler.obtainMessage();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
            }
        });
        isrunning.set(true);
        th.start();

    }
}
