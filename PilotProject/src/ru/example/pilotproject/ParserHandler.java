package ru.example.pilotproject;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ParserHandler extends DefaultHandler {

	boolean bfkks;
	boolean blsignal;
	boolean bnname_r;
	boolean inf;
	String kks;
	String signal;
	String name_r;
	SQLiteDatabase dbh;
	
	public ParserHandler (){
		
		 bfkks = false;
		 blsignal = false;
		 bnname_r = false;
		 inf = false;
		 kks = "";
		 signal = "";
		 name_r = "";
		
	}
	
	public ParserHandler init(Context cx){
		try{
			BaseData db = new BaseData(cx);
			dbh = db.getReadableDatabase();	
		}catch(Exception e){
			Log.d("PILOPRG", "Got exception parser:" , e);
		}
		return this;
	}
    
    @Override
    	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
    			
    	
    				if (qName.equalsIgnoreCase("TBL_DOP_INF")) {
    					inf = true;
    				}
    				
    				if (inf) {
						
    					if (qName.equalsIgnoreCase("KKS")) {
    						bfkks = true;
    					}
 
    					if (qName.equalsIgnoreCase("SIGNAL")) {
    						blsignal = true;
    					}
 
    					if (qName.equalsIgnoreCase("NAME_R")) {
    						bnname_r = true;
    					}
					}
   
    }
    
    private void reset()
    {
		 bfkks = false;
		 blsignal = false;
		 bnname_r = false;
		 inf = false;
		 kks = "";
		 signal = "";
		 name_r = "";
    }
    @Override
    	public void endElement(String uri, String localName, String qName)
    		throws SAXException {
    		// TODO Auto-generated method stub
    			super.endElement(uri, localName, qName);
    			
    			if (qName.equalsIgnoreCase("TBL_DOP_INF")) {
					inf = false;
					
					if (kks.isEmpty())
					{
					reset();
					return;
					}
					
					if (signal.isEmpty() || signal.compareTo("-")==0)
					{
					reset();
					return;
					}
					
					if (name_r.isEmpty())
					{
					reset();
					return;
					}
					try{
						String st = "INSERT INTO " + BaseData.BASE_TABLE + "(KKS, Name) VALUES ( \""+kks+"_"+signal+"\", \""+name_r+"\");";
						Log.i("PILOPRG", st);
						dbh.execSQL(st);
					}catch(Exception e){
						Log.d("PILOPRG", "Got exception parser:" , e);
					}
					reset();
				}
				
				if (inf) {
					
					if (qName.equalsIgnoreCase("KKS") && bfkks == true) {
						bfkks = false;
					}

					if (qName.equalsIgnoreCase("SIGNAL") && blsignal == true) {
						blsignal = false;
					}

					if (qName.equalsIgnoreCase("NAME_R") && bnname_r == true) {
						bnname_r = false;
					}
				}
    			
    			
    		}
    
    @Override
    	public void characters(char[] ch, int start, int length)
    		throws SAXException {
    		// TODO Auto-generated method stub
    			super.characters(ch, start, length);
    			if (bfkks) {
    
    				kks += new String(ch, start, length);
    			}
 
    			if (blsignal) {

    				signal += new String(ch, start, length);
    			}
    			
    			if (bnname_r) {
    
    				name_r += new String(ch, start, length);
    			}
    		}
    
    @Override
    	public void endDocument() {
    		try {
    				super.endDocument();
    		} catch (SAXException e) {
            e.printStackTrace();
    		}
    }
}


