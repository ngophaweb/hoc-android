package org.ngophaweb.sqliteexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends ActionBarActivity {
    private EditText txtId;
    private EditText txtName;
    private Button btnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        txtName = (EditText) findViewById(R.id.txtName);
        txtId = (EditText) findViewById(R.id.txtId);
        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                PeopleModel p = new PeopleModel();
                p.setId(Integer.parseInt(txtId.getText().toString()));
                p.setName(txtName.getText().toString());
                bundle.putSerializable("people", p);
                intent.putExtra("DATA", bundle);
                setResult(MainActivity.ADD_RESULT, intent);
                AddActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        final Bundle bundle = intent.getBundleExtra("DATA");
        if(bundle!=null){
            PeopleModel p = (PeopleModel)bundle.getSerializable("people");
            final int position = bundle.getInt("position");
            txtId.setText(String.valueOf(p.getId()));
            txtName.setText(p.getName());
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    PeopleModel p = new PeopleModel();
                    p.setId(Integer.parseInt(txtId.getText().toString()));
                    p.setName(txtName.getText().toString());
                    bundle.putSerializable("people", p);
                    bundle.putInt("position", position);
                    intent.putExtra("DATA", bundle);
                    setResult(MainActivity.EDIT_RESULT, intent);
                    AddActivity.this.finish();
                }
            });
        }

    }

}
