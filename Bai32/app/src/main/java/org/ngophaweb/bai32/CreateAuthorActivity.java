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

    }

}
