package org.ngophaweb.sqliteexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
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
    private ListView lvPeople;
    private ArrayList<PeopleModel> arrLst = null;
    private ArrayAdapter<PeopleModel> adapter = null;
    public static final int ADD_REQUEST = 1;
    public static final int ADD_RESULT = 2;
    public static final int EDIT_REQUEST = 3;
    public static final int EDIT_RESULT = 4;
    private SQLiteDatabase database = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnThem = (Button) findViewById(R.id.btnThem);
        lvPeople = (ListView) findViewById(R.id.lvPeople);

        arrLst = new ArrayList<PeopleModel>();
        adapter = new ArrayAdapter<PeopleModel>(MainActivity.this,
                android.R.layout.simple_list_item_1, arrLst);
        lvPeople.setAdapter(adapter);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });
        getDatabase();

        //load Database
        loadDatabase();

        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                Bundle bundle = new Bundle();
                PeopleModel p = arrLst.get(position);
                bundle.putSerializable("people", p);
                bundle.putInt("position", position);
                intent.putExtra("DATA", bundle);
                startActivityForResult(intent, MainActivity.EDIT_REQUEST);
            }
        });
        lvPeople.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete Item");
                alert.setMessage("Delete item : " + arrLst.get(position).getId() + " - " + arrLst.get(position).getName() + " ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PeopleModel p = arrLst.get(position);
                        deletePeople(p);
                        arrLst.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.create().show();
                return false;
            }
        });

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
    private SQLiteDatabase getDatabase(){
        try{
            database = openOrCreateDatabase("android_people.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            if(database != null){
                if(isTableExists(database,"tblPeoples"))
                    return database;
                database.setLocale(Locale.getDefault());
                database.setVersion(1);
                String sqlPeople="create table tblPeoples ("
                        +"id integer primary key autoincrement,"
                        +"name text)";
                database.execSQL(sqlPeople);
                Toast.makeText(MainActivity.this, "OK OK", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return database;
    }

    private void loadDatabase(){
        if (database != null) {
            Cursor cursor = database.query("tblPeoples", null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PeopleModel p = new PeopleModel();
                p.setId(cursor.getInt(0));
                p.setName(cursor.getString(1));
                arrLst.add(p);
                cursor.moveToNext();
            }
            cursor.close();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(MainActivity.ADD_REQUEST == requestCode){
            if(resultCode == MainActivity.ADD_RESULT){
                Bundle bundle = data.getBundleExtra("DATA");
                PeopleModel p = (PeopleModel) bundle.getSerializable("people");
                //goi ham them
                ContentValues content = new ContentValues();
                content.put("id", p.getId());
                content.put("name", p.getName());
                if (database != null) {
                    long peopleId = database.insert("tblPeoples", null, content);
                    if (peopleId == -1) {
                        Toast.makeText(MainActivity.this, p.getId() + " - " + p.getName() + " ==> insert error!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, p.getId() + " - " + p.getName() + " ==>insert OK!", Toast.LENGTH_LONG).show();
                        arrLst.add(p);
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        }else if(MainActivity.EDIT_REQUEST == requestCode){
            if(MainActivity.EDIT_RESULT == resultCode){
                Bundle bundle = data.getBundleExtra("DATA");
                PeopleModel p = (PeopleModel) bundle.getSerializable("people");
                int position = bundle.getInt("position");
                if(MainActivity.this.updatePeople(p)) {
                    Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();
                    //loadDatabase();
                    arrLst.get(position).setName(p.getName());
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(MainActivity.this, "Thất bại", Toast.LENGTH_LONG).show();
            }
        }

    }
    private boolean updatePeople(PeopleModel p){
        ContentValues content = new ContentValues();
        content.put("name", p.getName());
        //int result = database.update("tblPeoples", content, "id=?", new String[]{p.getId()});
        int result =  database.update("tblPeoples", content, "id" + "=" + p.getId(), null);
        if(result == 0)
            return false;
        else
            return true;
    }
    private boolean deletePeople(PeopleModel p){
        if(database != null){
            int result =  database.delete("tblPeoples", "id" + "=" + p.getId(), null);
            if(result == 0)
                return false;
            else
                return true;
        }
        return false;
    }
}
