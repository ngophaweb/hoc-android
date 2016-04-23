package org.ngophaweb.bai32;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
    private Button btnThemTacGia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThemTacGia = (Button) findViewById(R.id.btnInsertAuthor);
        btnThemTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdAuthor();
            }
        });
    }
    private void createdAuthor(){
        Intent intent = new Intent(MainActivity.this, CreateAuthorActivity.class);
        startActivity(intent);
    }
}
