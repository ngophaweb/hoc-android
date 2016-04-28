package org.ngophaweb.bai31;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {

    private EditText txtName;
    private Button btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        txtName = (EditText) findViewById(R.id.txtName);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("name", txtName.getText().toString());
                intent.putExtra("DATA", bundle);
                setResult(MainActivity.STATIC_RECODE, intent);
                AddActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("DATA");
        if(bundle!=null){
            String name = bundle.getString("name");
            txtName.setText(name);
            btnThem.setText("Sửa");
            btnThem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", txtName.getText().toString());
                    intent.putExtra("DATA", bundle);
                    setResult(MainActivity.EDIT_RESULT, intent);
                    AddActivity.this.finish();
                }
            });
        }

    }

}
