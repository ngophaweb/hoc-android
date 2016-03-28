package org.ngophaweb.bai38;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Administrator on 28/03/2016.
 */
public class MyAsyncTask extends AsyncTask<Integer, Integer, ArrayList<Integer>> {
    private Activity activity;
    private ListView listView1;
    private ArrayAdapter<Integer> adapter = null;
    private ArrayList<Integer> arr = new ArrayList<Integer>();

    public MyAsyncTask(Activity ac){
        this.activity = ac;
        listView1 = (ListView) activity.findViewById(R.id.listView1);
        adapter = new ArrayAdapter<Integer>(activity, android.R.layout.simple_list_item_1, arr);
        listView1.setAdapter(adapter);
    }

    public int fib(int n)
    {
        if(n<=2)return 1;
        return fib(n-1)+fib(n-2);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<Integer> doInBackground(Integer... params) {
        int so = params[0];
        ArrayList<Integer> arrTongCacSoFib=new ArrayList<Integer>();
        for(int i = 1; i<= so; i++){
            SystemClock.sleep(150);
            int f = fib(i);
            arrTongCacSoFib.add(f);
            publishProgress(f);
        }
        return arrTongCacSoFib;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        arr.add(values[0]);
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onPostExecute(ArrayList<Integer> integers) {
        super.onPostExecute(integers);
        int sum = 0;
        for(int i : integers){
            sum += i;
        }
        Toast.makeText(activity, "Tong la: " + String.valueOf(sum), Toast.LENGTH_LONG).show();
        TextView txt = (TextView) activity.findViewById(R.id.textView1);
        txt.setText(String.valueOf(sum));
    }


}
