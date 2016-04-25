package org.ngophaweb.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Configuration configuration = getResources().getConfiguration();
        if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Sub1Fragment sub1Fragment = new Sub1Fragment();
            fragmentTransaction.replace(android.R.id.content, sub1Fragment);
        }else{
            BlankFragment blankFragment = new BlankFragment();
            fragmentTransaction.replace(android.R.id.content, blankFragment);
        }
        fragmentTransaction.commit();
    }
}
