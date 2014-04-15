package ru.example.pilotproject;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ManagerOV extends ListActivity {

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
		setListAdapter(adapter);
		getListView().setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent inte = new Intent().setClassName("ru.example.pilotproject",
						"ru.example.pilotproject.EditOV");
				inte.putExtra("group", arg3);
				startActivity(inte);
				
			}
		});
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
