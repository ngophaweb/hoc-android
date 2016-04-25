package org.ngophaweb.bai32;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends Activity {
    private Button btnThemTacGia;
    private SQLiteDatabase database = null;
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
        //getDatabase();
    }
    private void createdAuthor(){
        Intent intent = new Intent(MainActivity.this, CreateAuthorActivity.class);
        startActivity(intent);
    }
    private boolean isTableExits(SQLiteDatabase database, String tableName){
        Cursor cursor = database.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor != null){
            if(cursor.getCount() > 0){
                cursor.close();
                return true;
            }
        }
        return false;
    }
    private SQLiteDatabase getDatabase(){
        database = SQLiteDatabase.openDatabase("mydata.db", null, SQLiteDatabase.CREATE_IF_NECESSARY);
        if(database != null){
            if(isTableExits(database, "tblAuthors"))
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
            //tao trigger
            String sqlTrigger="create trigger fk_insert_book before insert on tblBooks "
                    +" for each row "
                    +" begin "
                    +" select raise(rollback,'them du lieu tren bang tblBooks bi sai') "
                    +" where (select id from tblAuthors where id=new.authorid) is null ;"
                    +" end;";
            database.execSQL(sqlTrigger);
        }
        return database;
    }
}
