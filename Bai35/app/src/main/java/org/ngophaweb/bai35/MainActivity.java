package org.ngophaweb.bai35;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {
    private Button btnDraw;
    private EditText txtDraw;
    private LinearLayout layout;
    Handler handler = null;
    AtomicBoolean atomicBoolean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDraw = (Button) findViewById(R.id.btnDraw);
        txtDraw = (EditText) findViewById(R.id.txtNumber);
        layout = (LinearLayout) findViewById(R.id.layout);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Button btn = new Button(MainActivity.this);
                btn.setText(msg.obj.toString());
                btn.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
                layout.addView(btn);
            }
        };
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });
    }
    private void doStart(){
        atomicBoolean = new AtomicBoolean(false);
        final int sl = Integer.parseInt(txtDraw.getText() + "");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<sl && atomicBoolean.get(); i++){
                    SystemClock.sleep(200);
                    Message msg = handler.obtainMessage();
                    msg.obj = "Button thu " + i;
                    handler.sendMessage(msg);
                }
            }
        });
        atomicBoolean.set(true);
        th.start();
    }
}
