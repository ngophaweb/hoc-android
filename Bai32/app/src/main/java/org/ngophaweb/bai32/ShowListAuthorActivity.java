package org.ngophaweb.bai32;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowListAuthorActivity extends Activity {
    private ListView listViewShowData;
    private ArrayAdapter<String> adapter = null;
    private ArrayList<String> arr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_author);
        listViewShowData = (ListView) findViewById(R.id.listViewShowData);
        arr = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(ShowListAuthorActivity.this, android.R.layout.simple_list_item_1, arr);
        listViewShowData.setAdapter(adapter);
        getData();
    }

    private void getData() {
        SQLiteDatabase database = null;
        database = openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        if (database != null) {

            Cursor cursor = database.query("tblAuthors", null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(2);
                arr.add(name);
                cursor.moveToNext();
            }
            cursor.close();
        }
        listViewShowData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder b = new AlertDialog.Builder(ShowListAuthorActivity.this);
                b.setTitle("Xóa tác giả");
                b.setMessage("Bạn muốn xóa " + arr.get(position) + " ra khỏi danh sách ?");
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                b.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                return false;
            }
        });
    }

}
