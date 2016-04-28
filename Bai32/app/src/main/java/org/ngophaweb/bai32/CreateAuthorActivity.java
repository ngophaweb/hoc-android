package org.ngophaweb.bai32;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateAuthorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_author);
        final Button btnInsert =(Button) findViewById(R.id.buttonInsert);
        final EditText txtFirstname=(EditText) findViewById(R.id.editTextFirstName);
        final EditText txtLastname=(EditText) findViewById(R.id.editTextLastName);
        final Intent intent= getIntent();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("firstname", txtFirstname.getText().toString());
                bundle.putString("lastname", txtLastname.getText().toString());
                intent.putExtra("DATA_AUTHOR", bundle);
                setResult(MainActivity.SEND_DATA_FROM_AUTHOR_ACTIVITY, intent);
                CreateAuthorActivity.this.finish();
            }
        });


    }

}
