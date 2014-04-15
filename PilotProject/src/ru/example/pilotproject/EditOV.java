package ru.example.pilotproject;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.content.ContentValues;
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
	
	private long groupID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_ov);
		
		kksList = new ArrayList<String>();
		kksListAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.text_list_item, kksList);
		setListAdapter(kksListAdapter);
		
		groupID = getIntent().getLongExtra("group", -1);
		if(groupID != -1){
			SQLiteDatabase dbh = new BaseData(getApplicationContext()).getReadableDatabase();
			Cursor res = dbh.rawQuery("SELECT * FROM " + BaseData.GROUP_TABLE + " WHERE _id=" + groupID + ";", null);
			res.moveToFirst();
			Log.d("PILOPRG", "names > " + Arrays.toString(res.getColumnNames()));
			Log.d("PILOPRG", "idx > " + res.getColumnIndex("_id"));
			Log.d("PILOPRG", "idx > " + res.getColumnIndex("GroupName") +
					"\nstr >" + res.getString(res.getColumnIndex("GroupName")));
			((EditText)findViewById(R.id.creation)).setText(res.getString(res.getColumnIndex("GroupName")));
			
			res = dbh.rawQuery("SELECT * FROM " + BaseData.VIEW_TABLE + " WHERE GroupID=" + groupID + ";", null);
			for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()){
				kksList.add(res.getString(res.getColumnIndex("KKS")));
			}
		}
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
		
		ContentValues noe = new ContentValues();
		noe.put("GroupName", groupName);
		long id;
		if (groupID == -1) {
			id = dbh.insert(BaseData.GROUP_TABLE, null, noe);
		} else {
			id = dbh.update(BaseData.GROUP_TABLE, noe, "_id="+groupID, null);
		}
		
		noe.clear();
		noe.put("GroupID", id);
		dbh.execSQL("DELETE FROM " + BaseData.VIEW_TABLE + " WHERE GroupID=" + id + ";");
		for (String kks : kksList) {
			noe.put("KKS", kks);
			dbh.insert(BaseData.VIEW_TABLE, null, noe);
			noe.remove("KKS");
		}
		
		super.onBackPressed();
//		startActivity(new Intent().setClassName("ru.example.pilotproject",
//												"ru.example.pilotproject.ManagerOV"));
	}
    
    public void Add_Click(View v) {     	
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
