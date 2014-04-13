package ru.example.pilotproject;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class EditOV extends ListActivity {
	
	ArrayAdapter<String> kksListAdapter;
	ArrayList<String> kksList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_ov);
		
		kksList = new ArrayList<String>();
		kksListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text_list_item, kksList);
		setListAdapter(kksListAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_ov, menu);
		return true;
	}
	
    public void Save_Click(View v) {   	
    	BaseData db = new BaseData(this);
		final SQLiteDatabase dbh = db.getWritableDatabase();			
		String groupName = ((EditText)(findViewById(R.id.creation))).getText().toString();
		String query = "INSERT OR REPLACE INTO " + BaseData.GROUP_TABLE + " (GroupName) VALUES(\""+groupName+"\");";
		Log.i("PILOPRG", query);
		Cursor gn = dbh.rawQuery(query, null);
		gn.moveToFirst();
		int id = gn.getInt(gn.getColumnIndex("_id"));
		for(String kks : kksList){
			query = "INSERT OR REPLACE INTO " + BaseData.VIEW_TABLE + " (GroupID, KKS) VALUES(\""+id+","+kks+"\");";
			dbh.rawQuery(query, null);
		}
		super.onBackPressed();
		
//		startActivity(new Intent().setClassName("ru.example.pilotproject",
//												"ru.example.pilotproject.ManagerOV"));
	}
    
    public void Add_Click(View v) {     	
    	String gname = ((EditText)findViewById(R.id.creation)).getText().toString();
    	Intent intent = new Intent(this, TwoScreen.class);
    	startActivityForResult(intent.putExtra("KKS", kksList), 1);
    	//startActivity(new Intent().setClassName("ru.example.pilotproject",
    		//	"ru.example.pilotproject.TwoScreen"), par);
    	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if (data == null) {return;}
    	ArrayList<String> names = data.getStringArrayListExtra("KKS");
    	kksList.addAll(names);
    }
    
    public void Cancel_Click(View v) { 
    //	startActivity(new Intent().setClassName("ru.example.pilotproject",
    	//		"ru.example.pilotproject.ManagerOV"));
    	super.onBackPressed();
    	}

}
