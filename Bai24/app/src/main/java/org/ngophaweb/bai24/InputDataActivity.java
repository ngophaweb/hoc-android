package org.ngophaweb.bai24;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputDataActivity extends Activity implements View.OnClickListener{

    private EditText editNumber;
    private Button btnSave1;
    private Button btnSave2;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave1:
                sendToMain(MainActivity.RESULT_CODE_SAVE1);
                break;
            case R.id.btnSave2:
                sendToMain(MainActivity.RESULT_CODE_SAVE2);
                break;
        }
    }

    private void sendToMain(int requestCode){
        Intent intent = getIntent();
        int so = Integer.parseInt(editNumber.getText() + "");
        intent.putExtra("DATA", so);
        setResult(requestCode, intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        editNumber = (EditText) findViewById(R.id.editNumber);
        btnSave1 = (Button) findViewById(R.id.btnSave1);
        btnSave2 = (Button) findViewById(R.id.btnSave2);

        btnSave1.setOnClickListener(this);
        btnSave2.setOnClickListener(this);


    }

}
