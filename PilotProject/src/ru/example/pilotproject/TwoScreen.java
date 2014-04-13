package ru.example.pilotproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FilterQueryProvider;
import android.widget.TextView;

public class TwoScreen extends ListActivity {
	
			
	@Override
	protected void onCreate(Bundle savedInstanceState){
		try{
	
			super.onCreate(savedInstanceState);
			setContentView(R.layout.view1);
		
			//Создание списка View для теста
			
			
	//		ArrayList<ErrorView> errorList = utils.GetErrorsArrayList();
	//		final ArrayAdapter<ErrorView> adapter = new ArrayAdapter<ErrorView>(this, R.layout.text_list_item,errorList);
			
			BaseData db = new BaseData(this);
			final SQLiteDatabase dbh = db.getReadableDatabase();
			String[] from = {"KKS"};
			int [] to = {R.id.textview5};
			ArrayList<String> ingr = getIntent().getStringArrayListExtra("KKS");
			String query;
			if(ingr.size() > 0){
				String lst = ingr.toString().replaceAll("(\\[)|(\\])", "\"").replaceAll(", ", "\",\"");//.replaceAll("  ", "_");
				query = "SELECT * FROM " + BaseData.BASE_TABLE + " WHERE KKS NOT IN (" + lst +") ORDER BY KKS;";
			}else{
				query= "SELECT * FROM " + BaseData.BASE_TABLE + " ORDER BY KKS;";
			}	
			
			Cursor cursor = dbh.rawQuery(query, null);			
			
//			final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.text_list_item, cursor, from, to, 0);
			final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.text_list_item, cursor, from, to, 0);
	
			adapter.setFilterQueryProvider(new FilterQueryProvider() {

			    @Override
			    public Cursor runQuery(CharSequence constraint) {
			        String partialValue = constraint.toString();
			        String queru = "SELECT * FROM " + BaseData.BASE_TABLE +" WHERE KKS LIKE '"+"%"+partialValue+"%" +"' ORDER BY KKS;" ;
			        return dbh.rawQuery( queru, null);
			        //return itemData.getAllSuggestedValues(partialValue);

			    }
			});
	
			
			
			
			TextView sb = (TextView)findViewById(R.id.searchbox);
			sb.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					// TODO Auto-generated method stub					
					adapter.getFilter().filter(s.toString());	
					adapter.notifyDataSetChanged();
					
					Log.d("PILOPRG", "ST="+start+", BE="+before+", COUNT="+count);
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	
				@Override
				public void afterTextChanged(Editable s) {}
				});
			
			setListAdapter(adapter);
			
			
			//Движения пальчиками!!!!
			getListView().setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Log.i("PILOPRG", "Checked num : " + getListView().getCheckedItemCount());
					// TODO Auto-generated method stub
					
				}
			});
			
			findViewById(R.id.b_cancel).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Back();
				}
			});
			findViewById(R.id.b_save).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Save_KKS();
				}
			});
		}catch(Exception e){
			Log.d("PILOPRG", "Got exception:" , e);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity1, menu);
		return true;
	}
	
	public void Back(){
		super.onBackPressed();
	}
	
	public void Save_KKS(){
		SparseBooleanArray checked = getListView().getCheckedItemPositions();
		
		ArrayList<String> aa = new ArrayList<String>();
		for(int i = 0; i < checked.size(); i++){
			if(checked.valueAt(i)){
				SQLiteCursor item = (SQLiteCursor) getListAdapter().getItem(checked.keyAt(i));
				Log.d(
					  "PILOPRG", "ADAPTER idx(" + i + ")" +
			          " pos(" + checked.keyAt(i) + ")" +
					  " val=" + item.getString(item.getColumnIndex("KKS"))
					  );
				aa.add(item.getString(item.getColumnIndex("KKS")));
			}
		}
	    long[] a  = getListView().getCheckedItemIds();
		Log.d("PILOPRG", "KKS:" + Arrays.toString(a));
		Log.d("PILOPRG", "HO " + getListAdapter().hasStableIds());
		
		Intent intent = new Intent();
		intent.putExtra("KKS", aa);
		setResult(RESULT_OK, intent);
		finish();
	}
}
