package com.example.almoufasseralsaghir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.almoufasseralsaghir.entity.User;
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
    
    public int getSuraIdByName(String suraName) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"c0idx"}; 
		String sqlTables = "quran_suras_content";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "c4tname LIKE '%"+suraName+"%'", null,
				null, null, null);

		int suraNb = -1;
		if(c.moveToFirst())
			suraNb = Integer.valueOf(c.getString(0));
		
		return suraNb;

	}
    
    public int getPartNumber(int suraId) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"sura"}; 
		String sqlTables = "parts";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "sura ='"+suraId+"'", null,
				null, null, null);

		int partNb = 1;
		if(c.moveToFirst())
			partNb = c.getCount();
		
		return partNb;

	}
    
    public String getPartsInfo(int suraId, int partNb) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"subject", "reason", "advices", "about"}; 
		String sqlTables = "parts";
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "(sura ='"+suraId+"') AND (part_number ='"+partNb+"')", null,
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

    public String getPartsMoustafad(int suraId, int partNb) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"advices"}; 
		String sqlTables = "parts";
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "(sura ='"+suraId+"') AND (part_number ='"+partNb+"')", null,
				null, null, null);
		
		StringBuilder info = new StringBuilder();
		if(c.moveToFirst())
		{
			info.append(c.getString(0));	
		}
			
		return info.toString();
	}
    
    public boolean addToPartFavorite(int suraId, int partNb, int userId){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "PartFavorite";
		
		ContentValues values = new ContentValues();
		values.put("SuraID", String.valueOf(suraId));
		values.put("PartNumber", String.valueOf(partNb));
		values.put("UserID", String.valueOf(userId));
		
		long insertedId = db.insertOrThrow(sqlTable, null, values);
		
		return insertedId != -1;
    }

    public boolean removeFromPartFavorite(int suraId, int partNb, int userId){    	
    	SQLiteDatabase db = getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTable = "PartFavorite";
		
		String whereClause = "WHERE SuraID = '%@' AND PartNumber = '%@' AND UserID = '%@'";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb), String.valueOf(userId)};
		
		qb.setTables(sqlTable);
		long insertedId = db.delete(sqlTable, whereClause, whereArgs);
		
		return insertedId != -1;
    }
    
    public Cursor getPartFavorite(int userId){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "PartFavorite";
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, "UserID ='"+userId+"'", null,
				null, null, null);
					
		return c;
    }
    
    public String getPartName(int partNumber){
    	
    	String str = null;
        switch (partNumber) {
            case 1:
                str = "الأول";
                break;
            case 2:
                str = "الثاني";
                break;
            case 3:
                str = "الثالث";
                break;
            case 4:
                str = "الرابع";
                break;
            case 5:
                str = "الخامس";
                break;
            case 6:
                str = "السادس";
                break;
            case 7:
                str = "السابع";
                break;
            case 8:
                str = "الثامن";
                break;
            case 9:
                str = "التاسع";
                break;
            case 10:
                str = "العاشر";
                break;
            case 11:
                str = "الحادي عشر";
                break;
            case 12:
                str = "الثاني عشر";
                break;
            case 13:
                str = "الثالث عشر";
                break;
            case 14:
                str = "الرابع عشر";
                break;
            case 15:
                str = "الخامس عشر";
                break;

        }
        return str;//[NSString stringWithFormat:@"المقطع %@",str];

    }
    
    //**************************** USERS
     
    public boolean setUserDefaultReciter(int reciterId, int userId){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "Users";
		
		ContentValues values = new ContentValues();
		values.put("DefaultReciter", String.valueOf(reciterId));
		
		String whereClause = "WHERE uid = '%@'";
		String[] whereArgs = {String.valueOf(userId)};
		
		long insertedId = db.update(sqlTable, values, whereClause, whereArgs);
		
		return insertedId != -1;
    }
    
    public int getUserDefaultReciter(int userId){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = {"DefaultReciter"};
		String sqlTables = "Users";
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "uid ='"+userId+"'", null,
				null, null, null);
			
		int reciter = 0;
		if(c.moveToFirst())
			reciter = Integer.valueOf(c.getString(0));
		
		return reciter;
    }

//    public User login(String email){
//    	SQLiteDatabase db = getReadableDatabase();
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//		String sqlTables = "Users";
//		
//		qb.setTables(sqlTables);
//		Cursor c = qb.query(db, null, "email ='"+email+"'", null,
//				null, null, null);
//			
//		User user = new User();
//		if(c.moveToFirst())
//			reciter = Integer.valueOf(c.getString(0));
//		
//		return reciter;
//    }


}