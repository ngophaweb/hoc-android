package org.ngophaweb.bai31;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private Button btnThem;
    private Button btnXoa;
    private Button btnSua;
    private ListView lstPerson;
    private ArrayList<String> arr = null;
    private ArrayAdapter<String> adapter = null;
    private SQLiteDatabase database = null;
    public static final int STATIC_REQUEST = 1;
    public static final int STATIC_RECODE = 2;
    public static final int EDIT_REQUEST = 3;
    public static final int EDIT_RESULT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnXoa = (Button) findViewById(R.id.btnXoa);
        btnSua = (Button) findViewById(R.id.btnSua);
        lstPerson = (ListView) findViewById(R.id.lvPerson);
        arr = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arr);
        lstPerson.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, STATIC_REQUEST);
            }
        });

        //Kiem tra csdl
        getDatabase();
        loadDatabase();
        lstPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                Bundle bundle = new Bundle();
                String value = arr.get(position);
                bundle.putString("name", value);
                intent.putExtra("DATA", bundle);
                startActivityForResult(intent, EDIT_REQUEST);
            }
        });
    }
    private void loadDatabase(){
        if (database != null) {
            Cursor cursor = database.query("tblPerson", null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(1);
                arr.add(name);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.STATIC_RECODE) {
            Bundle bundle = data.getBundleExtra("DATA");
            String name = bundle.getString("name");
            ContentValues content = new ContentValues();
            content.put("name", name);
            if (database != null) {
                long returnId = database.insert("tblPerson", null, content);
                if (returnId == -1) {
                    Toast.makeText(MainActivity.this, name + " ==> insert error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, name + " ==>insert OK!", Toast.LENGTH_LONG).show();
                }
            }
            arr.add(name);
            adapter.notifyDataSetChanged();
        }else if(resultCode == MainActivity.EDIT_RESULT){
            //Thuc hien update du lieu

        }
    }

    //Kiem tra va tao co so du lieu neu chua tao
    private SQLiteDatabase getDatabase() {
        try {
            database = openOrCreateDatabase("demo_sql.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            if (database != null) {
                if (isTableExists(database, "tblPerson"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlAuthor = "create table tblPerson ("
                        + "id integer primary key autoincrement,"
                        + "name text)";
                database.execSQL(sqlAuthor);
                Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }
}
