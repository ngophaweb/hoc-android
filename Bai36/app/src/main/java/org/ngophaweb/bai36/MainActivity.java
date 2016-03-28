package org.ngophaweb.bai36;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity {
    private Button button1;
    private EditText editText1;
    private ListView listView1;
    private ArrayAdapter<Integer> arrayAdapter = null;
    private ArrayList<Integer> arr = null;
    private Handler handler = null;
    private AtomicBoolean atomicBoolean = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button1);
        editText1 = (EditText) findViewById(R.id.editText1);
        listView1 = (ListView) findViewById(R.id.listView1);

        arr = new ArrayList<Integer>();
        arrayAdapter = new ArrayAdapter<Integer>(MainActivity.this,
                android.R.layout.simple_list_item_1, arr);
        listView1.setAdapter(arrayAdapter);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doStart();
            }
        });
    }
    private void doStart(){
        handler = new Handler();
        atomicBoolean = new AtomicBoolean(false);
        final Random rd = new Random();
        final int so = Integer.parseInt(editText1.getText() + "");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<so && atomicBoolean.get(); i++){
                    SystemClock.sleep(150);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            arr.add(rd.nextInt(100));
                            arrayAdapter.notifyDataSetChanged();
                        }
                    });
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Da xong ...", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        atomicBoolean.set(true);
        th.start();

    }
}
