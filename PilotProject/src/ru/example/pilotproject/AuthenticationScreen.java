package ru.example.pilotproject;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AuthenticationScreen extends Activity {

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentication);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onclk(View v) { 
    	
    	String str = ((EditText)(findViewById(R.id.editText1))).getText().toString();
    	Log.d("PILOPRG", "Got string: ["+str+"]");
    	
        if	(str.equals("1")) {
        	startActivity(new Intent().setClassName("ru.example.pilotproject",
        			"ru.example.pilotproject.ListGroupScreen"));
        return;	}
        Toast.makeText(this,"Неверный пароль", Toast.LENGTH_LONG).show();
    }
    public void upd(View v) {
    	
    	((Button)v).setActivated(false);
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
		    	try{
	//				BaseData db = new BaseData(getBaseContext());
	//				SQLiteDatabase dbh = db.getReadableDatabase();

		    	    AndroidHttpClient cl = AndroidHttpClient.newInstance("Android");
		    	    HttpResponse re = cl.execute(new HttpGet("http://188.226.130.23:8000/base.xml"));
		    	    
//		    	    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(re.getEntity().getContent());
		    	    
//		    	    NodeList signals = DocumentBuilderFactory.newInstance()
//		    	    		.newDocumentBuilder()
//		    	    		.parse(re.getEntity().getContent()).getElementsByTagName("TBL_DOP_INF");
		    	    
//		    	    XPathExpression kks1 = XPathFactory.newInstance().newXPath().compile("KKS");
//		    	    XPathExpression kks2 = XPathFactory.newInstance().newXPath().compile("SIGNAL");
//		    	    XPathExpression name = XPathFactory.newInstance().newXPath().compile("NAME_R");
		    	    
		    	    SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
//		    	    saxParser.parse("http://188.226.130.23:8000/base.xml", new ParserHandler().init(getBaseContext()));
		    	    saxParser.parse(re.getEntity().getContent(), new ParserHandler().init(getBaseContext()));
		    	    
//		    	    for (int i = 0; i < signals.getLength(); i++){
//		    	    	String kks1s = kks1.evaluate(signals.item(i));
//		    	    	Log.i("PILOPRG", "Got 1 -> " + kks1s);
//		    	    	
//		    	    	  for (int l = 0; l < signals.getLength(); l++){
//				    	    	String kks2s = kks2.evaluate(signals.item(l));
//				    	 //   	if (kks2s = "-")
//				    	    	Log.i("PILOPRG", "Got 2-> " + kks2s);
//				    	    	
//				    	    	for (int k = 0; k < signals.getLength(); k++){
//					    	    	String names = name.evaluate(signals.item(k));
//					    	    	Log.i("PILOPRG", "Got 3->" + names);  	
//
//		    	    	//db.execSQL("INSERT INTO " + BaseData.BASE_TABLE + "(KKS, Name) VALUES ( "+kks1s+"_"+kks2s+", "+names+");");
//					    	    	dbh.execSQL("INSERT INTO " + BaseData.BASE_TABLE + "(KKS, Name) VALUES ( "+kks1s+"_"+kks2s+", "+names+");");
//		    	    }
//		    	    	}
//		    	    		} 
		    	    } catch(Exception e){
					Log.d("PILOPRG", "Got exception firsct creen:" , e);
		    	}
			}
		}).start();
    }
}
