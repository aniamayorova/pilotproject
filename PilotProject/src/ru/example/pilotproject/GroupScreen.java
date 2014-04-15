package ru.example.pilotproject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GroupScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.group_screen, menu);
		return true;
	}

}
