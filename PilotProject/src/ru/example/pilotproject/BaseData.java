package ru.example.pilotproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BaseData extends SQLiteOpenHelper {
	
	private static final String BDNAME = "MyBD";
	 static final String BASE_TABLE = "BaseTable";
	 static final String ACTION_TABLE = "ActionTable";
	 static final String VIEW_TABLE = "ViewTable";
	 static final String GROUP_TABLE = "GroupTable";
	

	public BaseData(Context context) {
		super(context, BDNAME , null , 7);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

//		Log.d("PILOPRG", "CREATING");
//		db.execSQL("DROP TABLE *;");
		db.execSQL("CREATE TABLE " + BASE_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT,KKS TEXT,"
				+ " Name TEXT, Descr TEXT, ActId INTEGER);");
		db.execSQL("CREATE TABLE " + ACTION_TABLE + " (ActId INTEGER, Action TEXT);");
		db.execSQL("CREATE TABLE " + VIEW_TABLE + " (GroupID INTEGER, KKS INTEGER);");
		db.execSQL("CREATE TABLE " + GROUP_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT,GroupName TEXT);");
		
	//	for(int i = 0; i < 100; i++){
		//	db.execSQL("INSERT INTO " + BASE_TABLE + " VALUES ( "+i+", \"KKS_"+i+"\", \"Short name "+i+"\", \"Description "+i+"\", 0);");
	//	}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	//	Log.w("LOG_TAG", "ќбновление базы данных с версии " + oldVersion + " до версии " + newVersion + ", которое удалит все старые данные");
		//”дал€ем предыдущую таблицу при апгрейде
		Log.d("PILOPRG", "UPGADE");
		db.execSQL("DROP TABLE " + BASE_TABLE + ";");
		db.execSQL("DROP TABLE " + ACTION_TABLE + ";");
		db.execSQL("DROP TABLE " + VIEW_TABLE + ";");
		db.execSQL("DROP TABLE " + GROUP_TABLE + ";");
		//—оздаем новый экземпл€р таблицу
		onCreate(db);
	}
	

}
