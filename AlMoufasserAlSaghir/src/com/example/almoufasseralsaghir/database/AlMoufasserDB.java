package com.example.almoufasseralsaghir.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class AlMoufasserDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Almufassir_1.0.sqlite";
    private static final int DATABASE_VERSION = 1;

    public AlMoufasserDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    public Cursor getSuras() {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"0 _id", "docid", "c0idx", "c3name"}; 
		String sqlTables = "quran_suras_content";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, null, null,
				null, null, null);

		c.moveToFirst();
		return c;

	}
    
    public String getPartsInfo(int suraId, int partNb) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"subject", "reason", "advices", "about"}; 
		String sqlTables = "parts";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "(sura ='"+String.valueOf(suraId)+"') AND (part_number ='"+String.valueOf(partNb)+"')", null,
				null, null, null);
		
		StringBuilder info = new StringBuilder();
		if(c.moveToFirst())
		{
			if(partNb == 1)
				info.append("<h3>" + c.getString(3) + "</h3>");
			
			info.append(c.getString(0));
			
			String reason =  c.getString(1);
			if(reason.length() > 0)
				info.append("<h3>أسباب النزول</h3>" + reason); 		
		}
			
		return info.toString();
	}

}