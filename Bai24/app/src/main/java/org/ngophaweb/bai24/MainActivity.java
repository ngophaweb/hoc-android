package org.ngophaweb.bai24;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button btnopenactivity;
    private ListView lstView;
    private ArrayList<Integer> arr = null;
    private ArrayAdapter<Integer> adapter = null;
    public static final int REQUEST_CODE_INPUT=113;
    public static final int RESULT_CODE_SAVE1=115;
    public static final int RESULT_CODE_SAVE2=116;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnopenactivity = (Button) findViewById(R.id.btnopenactivity);
        lstView = (ListView) findViewById(R.id.lvdata);

        arr = new ArrayList<Integer>();
        adapter = new ArrayAdapter<Integer>(MainActivity.this,
                android.R.layout.simple_list_item_1, arr);
        lstView.setAdapter(adapter);

        btnopenactivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnopenactivity:
                Intent intent = new Intent(MainActivity.this, InputDataActivity.class);
                startActivityForResult(intent, REQUEST_CODE_INPUT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_INPUT){
            switch (resultCode){
                case MainActivity.RESULT_CODE_SAVE1:
                    int kq1 = data.getIntExtra("DATA", 0);
                    arr.add(kq1 * kq1);
                    adapter.notifyDataSetChanged();
                    break;
                case MainActivity.RESULT_CODE_SAVE2:
                    int kq2 = data.getIntExtra("DATA", 0);
                    arr.add(kq2);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
