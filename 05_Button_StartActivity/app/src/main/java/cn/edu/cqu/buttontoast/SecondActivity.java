package cn.edu.cqu.buttontoast;

import android.app.Activity;
import android.os.Bundle;

public class SecondActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Connect this java file with the xml layout
		setContentView(R.layout.second_layout);
		
		
	}

}
