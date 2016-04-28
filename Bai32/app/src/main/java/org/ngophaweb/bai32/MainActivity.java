package org.ngophaweb.bai32;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends Activity {
    private Button btnThemTacGia;
    private Button buttonShowAuthorList;
    private SQLiteDatabase database = null;
    public static final int OPEN_AUTHOR_DIALOG=1;
    public static final int SEND_DATA_FROM_AUTHOR_ACTIVITY=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnThemTacGia = (Button) findViewById(R.id.btnInsertAuthor);
        buttonShowAuthorList = (Button) findViewById(R.id.buttonShowAuthorList);
        btnThemTacGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createdAuthor();
            }
        });
        buttonShowAuthorList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAuthor();
            }
        });
        getDatabase();
    }
    private void showAuthor(){
        Intent intent = new Intent(MainActivity.this, ShowListAuthorActivity.class);
        startActivity(intent);
    }
    private void createdAuthor(){
        Intent intent = new Intent(MainActivity.this, CreateAuthorActivity.class);
        startActivityForResult(intent, OPEN_AUTHOR_DIALOG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SEND_DATA_FROM_AUTHOR_ACTIVITY) {
            Bundle bundle = data.getBundleExtra("DATA_AUTHOR");
            String firstname = bundle.getString("firstname");
            String lastname = bundle.getString("lastname");
            ContentValues content = new ContentValues();
            content.put("firstname", firstname);
            content.put("lastname", lastname);
            if (database != null) {
                long authorid = database.insert("tblAuthors", null, content);
                if (authorid == -1) {
                    Toast.makeText(MainActivity.this, authorid + " - " + firstname + " - " + lastname + " ==> insert error!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, authorid + " - " + firstname + " - " + lastname + " ==>insert OK!", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    public boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }
    public SQLiteDatabase getDatabase()
    {
        try
        {
            database=openOrCreateDatabase("mydata.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            if(database!=null)
            {
                if(isTableExists(database,"tblAuthors"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlAuthor="create table tblAuthors ("
                        +"id integer primary key autoincrement,"
                        +"firstname text, "
                        +"lastname text)";
                database.execSQL(sqlAuthor);
                String sqlBook="create table tblBooks ("
                        +"id integer primary key autoincrement,"
                        +"title text, "
                        +"dateadded date,"
                        +"authorid integer not null constraint authorid references tblAuthors(id) on delete cascade)";
                database.execSQL(sqlBook);
                //Cách tạo trigger khi nhập dữ liệu sai ràng buộc quan hệ
                String sqlTrigger="create trigger fk_insert_book before insert on tblBooks "
                        +" for each row "
                        +" begin "
                        +" select raise(rollback,'them du lieu tren bang tblBooks bi sai') "
                        +" where (select id from tblAuthors where id=new.authorid) is null ;"
                        +" end;";
                database.execSQL(sqlTrigger);
                Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }
}
