package ru.example.pilotproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class ManagerOV extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ov);
		
		
		BaseData db = new BaseData(this);
		final SQLiteDatabase dbh = db.getReadableDatabase();
		String[] from = {"GroupName"};
		int [] to = {R.id.textview5};
		
		String query = "SELECT * FROM " + BaseData.GROUP_TABLE + " ORDER BY GroupName;";
		
//		Cursor cursor = dbh.query(BaseData.BASE_TABLE, from, null, null, null, null, null);
		
		
		
		Cursor cursor = dbh.rawQuery(query , null);
		
		Log.i("PILOPRG", query);
		Log.i("PILOPRG", String.format("%s", cursor.getCount()));
		
		final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.text_list_item, cursor, from, to, 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manager_ov, menu);
		return true;
	}
	
	public void Add_Object(View v) { 
	startActivity(new Intent().setClassName("ru.example.pilotproject",
			"ru.example.pilotproject.EditOV"));
	}

}
