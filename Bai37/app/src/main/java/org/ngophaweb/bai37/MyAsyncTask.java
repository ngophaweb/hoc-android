package org.ngophaweb.bai37;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 28/03/2016.
 */
public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

    private Activity context;
    public MyAsyncTask(Activity ac){
        context = ac;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {

        for(int i = 0; i<= 100; i++){
            SystemClock.sleep(100);
            publishProgress(i);
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        ProgressBar progressBar = (ProgressBar) context.findViewById(R.id.progressBar1);
        TextView txt = (TextView) context.findViewById(R.id.textView1);
        progressBar.setProgress(values[0]);
        txt.setText(values[0] + "%");
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(context, "Da xong ...", Toast.LENGTH_LONG).show();
    }
}
