package tranduythanh.com;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn1=(Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this,
						SubActivity1.class);
				startActivity(intent);
			}
		});
		Button btn2=(Button) findViewById(R.id.button2);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent=new Intent(MainActivity.this, 
						SubActivity2.class);
				startActivity(intent);
			}
		});
	}
	@Override
	protected void onResume() {
		super.onResume();
		Toast.makeText
				(this, "onResume", Toast.LENGTH_LONG)
				.show();
	}
	@Override
	protected void onPause() {
		super.onPause();
		Toast.makeText
				(this, "onPause", Toast.LENGTH_LONG)
				.show();
	}
	@Override
	protected void onStop() {
		super.onStop();
		Toast.makeText
				(this, "onStop", Toast.LENGTH_LONG)
				.show();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Toast.makeText
			(this, "onDestroy", Toast.LENGTH_LONG)
			.show();
	}
}
