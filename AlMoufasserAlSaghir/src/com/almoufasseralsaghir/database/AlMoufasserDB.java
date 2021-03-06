package com.almoufasseralsaghir.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.almoufasseralsaghir.entity.ActivityStats;
import com.almoufasseralsaghir.entity.Answer;
import com.almoufasseralsaghir.entity.PartFavourite;
import com.almoufasseralsaghir.entity.Question;
import com.almoufasseralsaghir.entity.QuizElementToAdd;
import com.almoufasseralsaghir.entity.Reminder;
import com.almoufasseralsaghir.entity.User;
import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.reminder.AlarmManagerBroadcastReceiver;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class AlMoufasserDB extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Almufassir_1.0.sqlite";
    private static final int DATABASE_VERSION = 1;
    
    private TafseerManager mTafseerManager;
    private AlarmManagerBroadcastReceiver alarmManager;
    private Context context;

    public AlMoufasserDB(Context context) {
    	super(context, DATABASE_NAME, context.getExternalFilesDir(null).getAbsolutePath(), null, DATABASE_VERSION);
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        
        mTafseerManager = TafseerManager.getInstance(context);
        alarmManager = new AlarmManagerBroadcastReceiver();
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
		
		c.close();
		return suraNb;

	}
    
    public String getSuraName(int suraId) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"c3name"}; 
		String sqlTables = "quran_suras_content";

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, "c0idx = '"+suraId+"'", null,
				null, null, null);

		String suraName = null;
		if(c.moveToFirst())
			suraName = c.getString(0);
		
		c.close();
		return suraName;
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
		
		c.close();
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
			
		c.close();
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
			
		c.close();
		return info.toString();
	}
    
    //**************************** PartFavorite
    public boolean isPartFavorite(int suraId, int partNb){    	
    	SQLiteDatabase db = getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTable = "PartFavorite";
		
		String whereClause = "SuraID = ? AND PartNumber = ? AND UserID = ?";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb), mTafseerManager.getLoggedInUser().getUid()};
		
		qb.setTables(sqlTable);
		Cursor c = qb.query(db, null, whereClause, whereArgs, null, null, null);
		
		if(c.moveToFirst()){
			c.close();
			return true;
		}
		
		c.close();
		return false;
    }
    
    public boolean addToPartFavorite(int suraId, int partNb){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "PartFavorite";
		
		ContentValues values = new ContentValues();
		values.put("SuraID", String.valueOf(suraId));
		values.put("PartNumber", String.valueOf(partNb));
		values.put("UserID", mTafseerManager.getLoggedInUser().getUid());
		
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		return insertedId != -1;
    }

    public boolean removeFromPartFavorite(int suraId, int partNb){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "PartFavorite";
		
		String whereClause = "SuraID = ? AND PartNumber = ? AND UserID = ?";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb), mTafseerManager.getLoggedInUser().getUid()};
		
		long insertedId = db.delete(sqlTable, whereClause, whereArgs);
		
		return insertedId > 0;
    }
    
    public ArrayList<PartFavourite> getPartFavorite(){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "PartFavorite";
		String whereClause = "UserID = ?";
		String[] whereArgs = {mTafseerManager.getLoggedInUser().getUid()};
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, whereClause, whereArgs, null, null, null);
		
		ArrayList<PartFavourite> result = new ArrayList<PartFavourite>();
		if(c.moveToFirst()){
			int counter = 0;
			do{
				PartFavourite fav = new PartFavourite();
				fav.setID(String.valueOf(counter++));
				
				int suraId = Integer.parseInt(c.getString(0));
				int partNb = Integer.parseInt(c.getString(1));
				
				fav.setSuraId(suraId);
				fav.setSuraName("سورة ".concat(getSuraName(suraId)));
				fav.setPartNb(partNb);
				fav.setPartName("المقطع ".concat(getPartName(partNb)));
				
				result.add(fav);
				
			}while (c.moveToNext());
		}
		
		c.close();
					
		return result;
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
            default:
            	str = "الأول";
                break;	

        }
        return str;//"المقطع ".concat(str)

    }
    
    //**************************** USERS
     
    public boolean setUserDefaultReciter(String reciterId, String userId){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "Users";
		
		ContentValues values = new ContentValues();
		values.put("DefaultReciter", reciterId);
		
		String whereClause = "uid = ?";
		String[] whereArgs = {userId};
		
		long insertedId = db.update(sqlTable, values, whereClause, whereArgs);
		
		return insertedId > 0;
    }
    
    public int getUserDefaultReciter(String userId){
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
		
		c.close();
		
		return reciter;
    }
    
    public boolean isUserExist(String email){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String[] sqlSelect = {"uid"};
		String sqlTables = "Users";
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, sqlSelect, "email ='"+email+"'", null,
				null, null, null);
			
		if(c.moveToFirst()){
			c.close();
			return true;
		}
		
		c.close();
		return false;
    }
    
    public User whoIsLoggedIn(){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "Users_content";
		
		String whereClause = "c12LoggedIn = ?";
		String[] whereArgs = {String.valueOf(1)};
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, null, whereClause, whereArgs,
				null, null, null);
			
		User user = new User();
		if(c.moveToFirst()){
			user.setUid(c.getString(1));
			user.setUdid(c.getString(2));
			user.setName(c.getString(3));
			user.setEmail(c.getString(4));
			user.setTwitter(c.getString(5));
			user.setFacebook(c.getString(6));
			user.setFollower1(c.getString(7));
			user.setType1(c.getString(8));
			user.setFollower2(c.getString(9));
			user.setType1(c.getString(10));
			user.setFollower3(c.getString(11));
			user.setType1(c.getString(12));
			user.setLoggedIn(c.getString(13).equals("1")?true:false);
			user.setDefaultReciter(c.getString(14));
			
		}
		
		c.close();
		
		return user;
    }

    public boolean login(String email){
    	SQLiteDatabase db = getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "Users";
		
		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, "email ='"+email+"'", null,
				null, null, null);
			
//		User user = new User();
		if(c.moveToFirst())
		{
			ContentValues values = new ContentValues();
			values.put("LoggedIn", "1");
			
			String whereClause = "email = ?";
			String[] whereArgs = {email};
			
			int updated = db.update(sqlTables, values, whereClause, whereArgs);
//			Log.i("", " LoggedIn: " + (updated > 0?"OK":"NOT"));
			
			c.close();
			return updated > 0;
		}
		
		c.close();
		return false;
    }
    
    public boolean logOut(String email){
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTables = "Users";
		
    	ContentValues values = new ContentValues();
		values.put("LoggedIn", "0");
		
		String whereClause = "email = ?";
		String[] whereArgs = {email};
		
		int updated = db.update(sqlTables, values, whereClause, whereArgs);
		
		return updated > 0;
    }
    
    public boolean insertUser(User user){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "Users";
		
		ContentValues values = new ContentValues();
		values.put("uid", user.getUid());
		values.put("udid", user.getUdid());
		values.put("name", user.getName());
		values.put("email", user.getEmail());
		values.put("twitter", user.getTwitter());
		values.put("facebook", user.getFacebook());
		values.put("follower1", user.getFollower1());
		values.put("typr1", user.getType1());
		values.put("follower2", user.getFollower2());
		values.put("typr2", user.getType2());
		values.put("follower3", user.getFollower3());
		values.put("typr3", user.getType3());
		values.put("DefaultReciter", user.getDefaultReciter());
		values.put("LoggedIn", user.isLoggedIn()?"1":"0");
		
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		if(insertedId != -1)
			initiateUserQuizelements(user.getUid());
		
		return insertedId != -1;
    }

    public boolean updateUser(User user){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "Users";
		
		ContentValues values = new ContentValues();
		values.put("uid", user.getUid());
		values.put("udid", user.getUdid());
		values.put("name", user.getName());
		values.put("email", user.getEmail());
		values.put("twitter", user.getTwitter());
		values.put("facebook", user.getFacebook());
		values.put("follower1", user.getFollower1());
		values.put("typr1", user.getType1());
		values.put("follower2", user.getFollower2());
		values.put("typr2", user.getType2());
		values.put("follower3", user.getFollower3());
		values.put("typr3", user.getType3());
		values.put("DefaultReciter", user.getDefaultReciter());
		values.put("LoggedIn", user.isLoggedIn()?"1":"0");
		
		String whereClause = "uid = ?";
		String[] whereArgs = {user.getUid()};
		
		long insertedId = db.update(sqlTable, values, whereClause, whereArgs);
		
		return insertedId > 0;
    }

  //**************************** REMINDER
    
    public int getNewReminderId(){
    	SQLiteDatabase db = getReadableDatabase();
    	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    	
		String sqlTable = "reminders";
		qb.setTables(sqlTable);
		String[] sqlSelect = {"ReminderID"};
    	Cursor c = qb.query(db, sqlSelect, null, null,
				null, null, null);
    	
//		String sql = "SELECT MAX(CAST(ReminderID AS Int)) FROM reminders";
//		Cursor c = db.rawQuery(sql, null);
    	
    	int max = -1;
		if(c.moveToFirst()){
			do{
				try{
					String qStr = c.getString(0);
					int value =  Integer.parseInt(qStr);
					if(value>max)
						max = value;
				}catch(Exception e){
					e.printStackTrace();
				}
			}while(c.moveToNext());
		}
		
		c.close();
		return max;
		
    }
    
    public boolean insertReminder(Reminder reminder, int suraId, int partNb){    	
    	SQLiteDatabase db = getWritableDatabase();
    	
    	String sqlTable = "reminders";
    	
    	int newID = reminder.getReminderID();
    	if(reminder.getReminderID() == -1)
    	{
    		newID = 0;
    		int qid = getNewReminderId();
    		if(qid != -1)
    			newID = qid + 1 ;
    	}else
    		alarmManager.cancelReminder(context, newID);
		
		ContentValues values = new ContentValues();
		values.put("ReminderID", newID);
		values.put("Part_number", String.valueOf(partNb));
		values.put("Sura", String.valueOf(suraId));
		values.put("RDate", reminder.getDate());
		values.put("RTime", reminder.getTime());
		values.put("Type", reminder.getType());
		values.put("Status", reminder.isStatus()?"1":"0");
		values.put("UserID", mTafseerManager.getLoggedInUser().getUid());
		
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		if(reminder.isStatus()){
			String notifMsg = prepareNotoficationText(suraId, partNb, reminder.getType());
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
			Date date;
	         try {
				date = formatter.parse(reminder.getDate()+" "+reminder.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				date = new Date();
			} 
			
			alarmManager.setReminder(context.getApplicationContext(), newID, suraId, partNb, notifMsg, date.getTime());
		}
		
		return insertedId != -1;
    }

    public String prepareNotoficationText(int suraId, int partNb, int type) {
		String typeStr;
		String suraName = getSuraName(suraId);
		String partName = getPartName(partNb);
		
		if(type == 1)
			typeStr = "حفظ";
		else
			typeStr = "مراجعة";
		
		StringBuilder sb = new StringBuilder();
		sb.append(" لاتنس ");
		sb.append(typeStr);
		sb.append(" المقطع ");
		sb.append(partName);
		sb.append(" من  سورة ");
		sb.append(suraName);
		
		
		return sb.toString();
	}
	
	public boolean deleteReminder(int suraId, int partNb){    	
    	SQLiteDatabase db = getWritableDatabase();

		int reminderID = getReminderID(suraId, partNb);
		String sqlTable = "reminders";
		
		String whereClause = "ReminderID LIKE ?";
		String[] whereArgs = {String.valueOf(reminderID)};
		
		//remove reminder from AlarmManager
		alarmManager.cancelReminder(context, reminderID);
		
		//remove reminder from DB
		long insertedId = db.delete(sqlTable, whereClause, whereArgs);
		
		return insertedId > 0;
    }
	
	
	public int getReminderSura(int reminderID){    	
    	SQLiteDatabase db = getReadableDatabase();
    	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTable = "reminders";
		String[] sqlSelect = {"Sura"};
		String whereClause = "ReminderID LIKE ?";
		String[] whereArgs = {String.valueOf(reminderID)};
		
		qb.setTables(sqlTable);
		
    	Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs,
				null, null, null);
    	
    	int suraId = 0;
    	if(c.moveToFirst())
    		suraId = Integer.valueOf(c.getString(0));
    	
    	c.close();
    	
		return suraId;
    }
	
	public int getReminderPart(int reminderID){    	
    	SQLiteDatabase db = getReadableDatabase();
    	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTable = "reminders";
		String[] sqlSelect = {"Part_number"};
		String whereClause = "ReminderID LIKE ?";
		String[] whereArgs = {String.valueOf(reminderID)};
		
		qb.setTables(sqlTable);
		
    	Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs,
				null, null, null);
    	
    	int partNb = 0;
    	if(c.moveToFirst())
    		partNb = Integer.valueOf(c.getString(0));
    	
    	c.close();
    	
		return partNb;
    }
	
//	public void getReminders() {
//		SQLiteDatabase db = getReadableDatabase();
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//		String sqlTables = "reminders";
//		
//		qb.setTables(sqlTables);
//		Cursor c = qb.query(db, null, null, null, null, null, null);
//
//		Reminder reminder = new Reminder();
//		if(c.moveToFirst()){
//			Log.i("Reminder Count", " " + c.getCount());
//			do{
//				reminder.setReminderID(Integer.parseInt(c.getString(0)));
//				reminder.setDate(c.getString(3));
//				reminder.setTime(c.getString(4));
//				reminder.setType(Integer.parseInt(c.getString(5)));
//				reminder.setStatus(c.getString(6).equals("1")?true:false);
//				Log.i("Reminder ", " ID " + reminder.getReminderID());
//			}while(c.moveToNext());
//		}
//		
//		c.close();		
//	}
	
	public int getReminderID(int suraId, int partNb) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"ReminderID"}; 
		String sqlTables = "reminders";
		
		String whereClause = "Sura = ? AND Part_number = ? AND UserID = ?";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb), mTafseerManager.getLoggedInUser().getUid()};
		

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs,
				null, null, null);

		int reminderID = -1;
		if(c.moveToFirst())
			reminderID = Integer.parseInt(c.getString(0));
		
		c.close();
		
		return reminderID;
	}

	public Reminder getReminder(int suraId, int partNb) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "reminders";
		
		String whereClause = "Sura = ? AND Part_number = ? AND UserID = ?";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb), mTafseerManager.getLoggedInUser().getUid()};

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, whereClause, whereArgs,
				null, null, null);

		Reminder reminder = new Reminder();
		if(c.moveToFirst()){
			reminder.setReminderID(Integer.parseInt(c.getString(0)));
			reminder.setDate(c.getString(3));
			reminder.setTime(c.getString(4));
			reminder.setType(Integer.parseInt(c.getString(5)));
			reminder.setStatus(c.getString(6).equals("1")?true:false);
		}
		
		c.close();
		
		return reminder;
	}
	
	//**************************** SURA PART AYA
	
	public void populatePartText (int suraId, int partNb, int font_size){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		mTafseerManager.setCurrentlyUsedFonts(new ArrayList<String>());
		mTafseerManager.setAyaAudioFileNames(new ArrayList<String>());
		mTafseerManager.setPartText("");
		String ayaFrom = "", ayaTo = "";

		String sqlTables = "parts";
		String[] sqlSelect = {"aya_from", "aya_to"};
		
		String whereClause = "sura = ? AND part_number = ?";
		String[] whereArgs = {String.valueOf(suraId), String.valueOf(partNb)};

		qb.setTables(sqlTables);
		Cursor c1 = qb.query(db, sqlSelect, whereClause, whereArgs,
				null, null, null);

		if(c1.moveToFirst()){
			ayaFrom = c1.getString(0);
			ayaTo = c1.getString(1);
		}
		c1.close();
		
		mTafseerManager.setPartText("<span style='text-align: left !important;  float: left; margin-left: 60px;'>");
		int i = 0;
		
		String sqlQuranTables = "quran_number";
		String[] sqlQuranSelect = {"page", "sura", "cast(aya as int)", "mushaf_chr"};
		
		String whereQuranClause = "sura = ? AND cast(aya as int) >= ? AND cast(aya as int) <= ?";
		String[] whereQuranArgs = {String.valueOf(suraId), ayaFrom, ayaTo};

		qb.setTables(sqlQuranTables);
		Cursor cQuran = qb.query(db, sqlQuranSelect, whereQuranClause, whereQuranArgs,
				null, null, "cast(aya as int)");

		if(cQuran.moveToFirst()){
			do{
				
				String pageID = cQuran.getString(0);
				String suraID = cQuran.getString(1);
				String ayaID = cQuran.getString(2);
				String ayaText = cQuran.getString(3);
				String anchorTag = "<a class="+i+" href=local?SuraID="+suraID+"&AyaID="+ayaID+"&TrackID="+i+" style='font-family: P"+pageID+";'>";
	            ayaText = anchorTag.concat(ayaText);
	            
				
	            
	            if (ayaID.equals("1")) {
	                ayaText = "<a id=0 href=local class='bsmla' style='text-align: center !important;  font-size:"+font_size+"px; width: 70%; '>ﭚﭛﭜﭝ</a>".concat(ayaText);
	            }
	            
	            ayaText = ayaText.substring(0, ayaText.length() - 1);
	            ayaText = ayaText.concat("</a>");
	            
	            String tempStr = "</a></span>\n<span>".concat(anchorTag);
	            ayaText = ayaText.replace("\n", tempStr);

	            mTafseerManager.setPartText(mTafseerManager.getPartText().concat(ayaText));
	            
	            if (!mTafseerManager.getCurrentlyUsedFonts().contains(pageID)) 
	            	mTafseerManager.getCurrentlyUsedFonts().add(pageID);
	            
	            suraID = "000".concat(suraID);
	            suraID = suraID.substring(suraID.length() - 3);
	            
	            ayaID = "000".concat(ayaID);
	            ayaID = ayaID.substring(ayaID.length() - 3);
	            
	            mTafseerManager.getAyaAudioFileNames().add(suraID.concat(ayaID));

	            i++;
	            
			}while(cQuran.moveToNext());
			
			cQuran.close();
			
			mTafseerManager.setNumberOfTracks(i);
			
			mTafseerManager.setPartText(mTafseerManager.getPartText().concat("</span>"));
		}
		
	}
		
	public String getAyaText(int suraId, int ayaId) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"text"}; 
		String sqlTables = "quran_text";
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, sqlSelect, "(sura ='"+suraId+"') AND (aya ='"+ayaId+"')", null,
				null, null, null);
		
		StringBuilder text = new StringBuilder();
		if(c.moveToFirst())
		{
			text.append(c.getString(0));	
		}
			
		c.close();
		return text.toString();
	}
	
	public String getAyaWordMeaning(int suraId, int ayaId) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"word_mean"}; 
		String sqlTables = "tafseer2";
		
		String ayaIndex = getAyaIndex(suraId, ayaId);
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, sqlSelect, "(aya_number ='"+ayaIndex+"')", null,
				null, null, null);
		
		StringBuilder info = new StringBuilder();
		if(c.moveToFirst())
		{
			info.append(c.getString(0));	
		}
			
		c.close();
		return info.toString();
	}
	
	public String getAyaTafseer(int suraId, int ayaId) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"tafseer"}; 
		String sqlTables = "tafseer2";
		
		String ayaIndex = getAyaIndex(suraId, ayaId);
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, sqlSelect, "(aya_number ='"+ayaIndex+"')", null,
				null, null, null);
		
		StringBuilder info = new StringBuilder();
		if(c.moveToFirst())
		{
			info.append(c.getString(0));	
		}
			
		c.close();
		return info.toString();
	}
	
	public String getAyaIndex(int suraId, int ayaId) {
    	
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"idx"}; 
		String sqlTables = "quran_text";
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, sqlSelect, "(sura ='"+suraId+"') AND (aya ='"+ayaId+"')", null,
				null, null, null);
		
		StringBuilder info = new StringBuilder();
		if(c.moveToFirst())
		{
			info.append(c.getString(0));	
		}
			
		c.close();
		return info.toString();
	}
	
	//**************************** QUESTIONS & ANSWERS
	
	public ArrayList<Question> populateQuestions(int suraId, int partNb) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "com_qa";
		
		String whereClause;
		String[] whereArgs;
		String limit;
		
		if(suraId == 114){
			whereClause = "cast(sura as int) >= ?";
			whereArgs = new String[]{String.valueOf(suraId)};
			limit = "1";
		}else{
			whereClause = "sura = ? AND part_number = ?";
			whereArgs = new String[]{String.valueOf(suraId), String.valueOf(partNb)};
			limit = "4";
			qb.setDistinct(true);
		}

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, null, whereClause, whereArgs, null, null, "RANDOM()", limit);

		ArrayList<Question> questions = new ArrayList<Question>();
		int i = 0;
		if(c.moveToFirst()){
			mTafseerManager.setAnswers(new LinkedHashMap<String, ArrayList<Answer>>());
			do{
				Question q = new Question();
				
				q.setID(i);
				q.setQuestionID(c.getString(0));
				q.setText(c.getString(3));
				q.setStatus(false);
				
				Log.i("Question " + q.getQuestionID(), q.getText());
				questions.add(q);
				
				populateAnswers(q.getQuestionID());
				
				i++;
				
			}while(c.moveToNext());
			
			mTafseerManager.setQuestions(questions);
		}
		
		c.close();
		
		return questions;
	}
	
	public ArrayList<Answer> populateAnswers(String questionID) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "com_an";
		
		String whereClause = "qid = ?";
		String[] whereArgs = {questionID};
		
		qb.setTables(sqlTables);

		Cursor c = qb.query(db, null, whereClause, whereArgs, null, null, null);

		ArrayList<Answer> answers = new ArrayList<Answer>();
		int i = 0;
		if(c.moveToFirst()){
			do{
				Answer a = new Answer();
				
				a.setID(i);
				a.setAnswerID(c.getString(1));
				a.setText(c.getString(3));
				a.setStatus(c.getString(4).equals("1") ? true:false);
				
				Log.e("Answers " + a.getAnswerID(), a.getText());
				answers.add(a);
				i++;
			}while(c.moveToNext());
			
			mTafseerManager.getAnswers().put(questionID, answers);
		}
		
		c.close();
		
		return answers;
	}
	
	public String getRandomAdviceMP3(int suraId, int partNb) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "advices";
		String[] sqlSelect = {"id"};
		
		String whereClause = "sura = ? AND part_number = ?";
		String[] whereArgs = new String[]{String.valueOf(suraId), String.valueOf(partNb)};
		String limit = "1";		

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, "RANDOM()", limit);

		String adviceMP3 = null;
		if(c.moveToFirst()){
			adviceMP3 = c.getString(0);
		}
		
		c.close();
		
		return adviceMP3;
	}
	
	public String getElementID(int suraId, int partNb) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "QuizElements";
		String[] sqlSelect = {"ElementID"};
		
		String whereClause = "sura = ? AND part_number = ?";
		String[] whereArgs = new String[]{String.valueOf(suraId), String.valueOf(partNb)};

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
		
		String elementID = null;
		if(c.moveToFirst()){
			elementID = c.getString(0);
		}
		
		c.close();
		
		return elementID;
	}
	
	public boolean setElementStatus(int suraId, int partNb, int status) {
		SQLiteDatabase db = getWritableDatabase();

		String sqlTables = "UserQuizElements";
		String elementID = getElementID(suraId, partNb);
		
		ContentValues values = new ContentValues();
		values.put("Status", String.valueOf(status));
		
		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{elementID, mTafseerManager.getLoggedInUser().getUid()};

		int updated = db.update(sqlTables, values, whereClause, whereArgs);
		return updated > 0;
	}
	
	public int getElementStatus(String elementId) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "UserQuizElements";
		String[] sqlSelect = {"Status"};
		
		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{elementId, mTafseerManager.getLoggedInUser().getUid()};

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
		
		int status = -1;
		if(c.moveToFirst()){
			status = Integer.valueOf(c.getString(0));
		}
		
		c.close();
		
		return status;
	}
	
	public boolean setElementLocatedStatus(int suraId, int partNb, boolean status) {
		SQLiteDatabase db = getReadableDatabase();

		String sqlTables = "UserQuizElements";
		String elementID = getElementID(suraId, partNb);
		
		ContentValues values = new ContentValues();
		values.put("Located", status?"1":"0");
		
		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{elementID, mTafseerManager.getLoggedInUser().getUid()};

		int updated = db.update(sqlTables, values, whereClause, whereArgs);
		return updated > 0;
	}
	
	public boolean getElementLocatedStatus(String elementId) {
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "UserQuizElements";
		String[] sqlSelect = {"Located"};
		
		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{elementId, mTafseerManager.getLoggedInUser().getUid()};

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
		
		boolean status = false;
		if(c.moveToFirst()){
			status = c.getString(0).equals("1")?true:false;
		}
		
		c.close();
		
		return status;
	}


	public boolean CheckQuizAvailability(int suraId, int partNb) {
		
		if(suraId == 114)
			return true;
		
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		String sqlTables = "UserQuizElements";
		qb.setTables(sqlTables);
		
		boolean result1 = true;
		boolean result2 = true;
		int suraID = suraId + 1;
//		String previousSuraStr = String.valueOf(suraID);
		String elementID = getElementID(suraID, partNb);
		
		if(getElementStatus(elementID) == 0){

			String[] sqlSelect = {"COUNT(*)"};

			String whereClause = "cast(Status as int) >'1' AND cast(ElementID as int) < ? AND UserID = ?";
			String[] whereArgs = new String[]{elementID, mTafseerManager.getLoggedInUser().getUid()};

			Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

			if(c.moveToFirst()){
				int count = Integer.valueOf(c.getString(0));
				if(count >= 3)
					result1 = false;
				else
					result1 = true;
			}

			c.close();
		}
		
		int element = Integer.valueOf(elementID) - 1;
		
		String[] sqlSelect = {"Located", "Status"};

		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{String.valueOf(element), mTafseerManager.getLoggedInUser().getUid()};

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		if(c.moveToFirst()){
			boolean located = (c.getString(0).equals("1") || c.getString(0).equals("2"))?true:false;
			boolean status = c.getString(1).equals("1")?true:false;
			
			if(status && located)
				result2 = true;
			else
				result2 = false;
			
		}
		
		c.close();

		return result1 && result2;
	}
	
	public void initiateUserQuizelements(String uid){
		SQLiteDatabase db = getWritableDatabase();

//		String uid = mTafseerManager.getLoggedInUser().getUid();
		db.execSQL("INSERT INTO UserQuizElements(ElementID,Part_numberStr,SuraStr,Status,Located,UserID) SELECT ElementID,Part_numberStr,SuraStr,'0','0','"+uid+"' FROM QuizElements");
	
	}
	
	public int getPartNumberByQuestionID(String QID){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "com_qa";
		String[] sqlSelect = {"part_number"};
		
		String whereClause = "id = ?";
		String[] whereArgs = new String[]{QID};

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
		
		int partNb = 0;
		if(c.moveToFirst()){
			partNb = Integer.valueOf(c.getString(0));
		}
		
		c.close();
		
		return partNb;
	}
	
	public int getSuraNumberByQuestionID(String QID){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "com_qa";
		String[] sqlSelect = {"sura"};
		
		String whereClause = "id = ?";
		String[] whereArgs = new String[]{QID};

		qb.setTables(sqlTables);

		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
		
		int suraNb = 0;
		if(c.moveToFirst()){
			suraNb = Integer.valueOf(c.getString(0));
		}
		
		c.close();
		
		return suraNb;
	}

//	public boolean CheckQuizAvailability2(int suraId, int partNb) {
//		
//		if(suraId == 114)
//			return true;
//		
//		SQLiteDatabase db = getReadableDatabase();
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		
//		String sqlTables = "UserQuizElements";
//		qb.setTables(sqlTables);
//		
//		boolean step2 = false;
//		boolean result = true;
//		int suraID = suraId + 1;
//		String SuraStr = String.valueOf(suraID);
//		String elementID = getElementID(suraID, partNb);
//		
//		if(getElementStatus(elementID)){
//
//			String[] sqlSelect = {"Located"};
//
//			String whereClause = "ElementID = ? AND UserID = ?";
//			String[] whereArgs = new String[]{elementID, mTafseerManager.getLoggedInUser().getUid()};
//
//
//			Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
//
//			if(c.moveToFirst()){
//				String located = c.getString(0);
//				if(located.equals("0"))
//					result = false;
//				else
//					step2 = true;
//			}
//
//			c.close();
//		}
//
//		if(step2){
//
//			int element = Integer.valueOf(elementID) - 1;
//
//			String[] sqlSelect = {"Status"};
//
//			String whereClause = "ElementID = ? AND UserID = ?";
//			String[] whereArgs = new String[]{String.valueOf(element), mTafseerManager.getLoggedInUser().getUid()};
//
//			Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);
//
//			if(c.moveToFirst()){
//				boolean status = c.getString(0).equals("1")?true:false;
//
//				if(status)
//					result = false;
//				else
//					result = true;
//
//			}
//		}
//
//		return result;
//	}

	public boolean insertQuizHistory(int suraId, int partNb, String historyDetails){
		
		SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "quiz_history";
		
		ContentValues values = new ContentValues();
		values.put("pat_number", String.valueOf(partNb));
		values.put("sura", String.valueOf(suraId));
		values.put("result", historyDetails);
		values.put("quiz_date", mTafseerManager.getDateTime());
		values.put("UserID", mTafseerManager.getLoggedInUser().getUid());
				
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		return insertedId != -1;
	}
	
	public void populateQuizElements(){
		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		mTafseerManager.setQuizElements(new ArrayList<QuizElementToAdd>());
		
		String sqlTables = "QuizElements";
		qb.setTables(sqlTables);
		
		Cursor c = qb.query(db, null, null, null, null, null, null);

		if(c.moveToFirst()){
			do{
				QuizElementToAdd qe = new QuizElementToAdd();
				qe.setQuizIdx(c.getString(0));
				qe.setQuizWidth(Float.valueOf(c.getString(3)) / 2);
				qe.setQuizHeight(Float.valueOf(c.getString(4)) / 2);
				qe.setQuizElementX(Float.valueOf(c.getString(5)) / 2);
				qe.setQuizElementY(Float.valueOf(c.getString(6)) / 2);
				
	            int i = Integer.valueOf(qe.getQuizIdx());
	            String idx = "";
	            if (i<10) {
	                idx = "00" + i;
	            }
	            if (i<100 && i> 9) {
	                idx= "0" + i;
	            }
	            if (i> 99) {
	                idx=""+i;
	            }
	            
	            String FileName = "/QuizElement" + idx + "@2x~ipad.png";
	            String GrayFileName = "/QuizElement" + idx + "-gray@2x~ipad.png";
	            
	            qe.setQuizFileName(FileName);
	            qe.setQuizGrayFileName(GrayFileName);
	            
	            
	            String f6 = "0";
	            String f7 = "0";
	   
				Cursor cQ = getQuizElementStatusLocated(qe.getQuizIdx());
				
				if(cQ.moveToFirst()){
					f6 = cQ.getString(0);
					f7 = cQ.getString(1);
				}
				
				cQ.close();
				
				qe.setQuizStatus(Integer.valueOf(f6));
				qe.setQuizLocated(f7.equals("1"));
				
				mTafseerManager.getQuizElements().add(qe);
//				Log.i("QuizElement", qe.toString());
	            				
			}while(c.moveToNext());
			
			c.close();
		}
	}
	
	public Cursor getQuizElementStatusLocated(String elementID) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String [] sqlSelect = {"Status", "Located"}; 
		String sqlTables = "UserQuizElements";

		String whereClause = "ElementID = ? AND UserID = ?";
		String[] whereArgs = new String[]{String.valueOf(elementID), mTafseerManager.getLoggedInUser().getUid()};

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		return c;

	}

	public QuizElementToAdd getQuizElementInfos(int suraId, int partNb) {

		SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "QuizElements";

		String whereClause = "sura = ? AND part_number = ?";
		String[] whereArgs = new String[]{String.valueOf(suraId), String.valueOf(partNb)};

		qb.setTables(sqlTables);
		Cursor c = qb.query(db, null, whereClause, whereArgs, null, null, null);

		QuizElementToAdd qe = null;
		if(c.moveToFirst()){
			qe = new QuizElementToAdd();
			qe.setQuizIdx(c.getString(0));
			qe.setQuizWidth(Float.valueOf(c.getString(3)) / 2);
			qe.setQuizHeight(Float.valueOf(c.getString(4)) / 2);
			qe.setQuizElementX(Float.valueOf(c.getString(5)) / 2);
			qe.setQuizElementY(Float.valueOf(c.getString(6)) / 2);

			int i = Integer.valueOf(qe.getQuizIdx());
			String idx = "";
			if (i<10) {
				idx = "00" + i;
			}
			if (i<100 && i> 9) {
				idx= "0" + i;
			}
			if (i> 99) {
				idx=""+i;
			}

			String FileName = "/QuizElement" + idx + "@2x~ipad.png";
			qe.setQuizFileName(FileName);
			
			String f6 = "0";
            String f7 = "0";
   
			Cursor cQ = getQuizElementStatusLocated(qe.getQuizIdx());
			
			if(cQ.moveToFirst()){
				f6 = cQ.getString(0);
				f7 = cQ.getString(1);
			}
			
			cQ.close();
			
			qe.setQuizStatus(Integer.valueOf(f6));
			qe.setQuizLocated(f7.equals("1"));
		}

		c.close();

		return qe;

	}
	 
	 
	 public String getElementPartName(String elementId) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "QuizElements";
		 String[] sqlSelect = {"Part_numberStr"};

		 String whereClause = "ElementID = ?";
		 String[] whereArgs = new String[]{elementId};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 String partName = null;
		 if(c.moveToFirst()){
			 partName = c.getString(0);
		 }
		 
		 c.close();
		 return partName;
	 }
	 
	 public String getElementSuraName(String elementId) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "QuizElements";
		 String[] sqlSelect = {"SuraStr"};

		 String whereClause = "ElementID = ?";
		 String[] whereArgs = new String[]{elementId};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 String suraName = null;
		 if(c.moveToFirst()){
			 suraName = c.getString(0);
		 }
		 
		 c.close();
		 return suraName;
	 }
	 
	 public int getElementPartNumber(String elementId) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "QuizElements";
		 String[] sqlSelect = {"Part_number"};

		 String whereClause = "ElementID = ?";
		 String[] whereArgs = new String[]{elementId};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int partNb = -1;
		 if(c.moveToFirst()){
			 partNb = Integer.valueOf(c.getString(0));
		 }
		 
		 c.close();
		 return partNb;
	 }
	 
	 public int getElementSuraId(String elementId) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "QuizElements";
		 String[] sqlSelect = {"Sura"};

		 String whereClause = "ElementID = ?";
		 String[] whereArgs = new String[]{elementId};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int suraId = -1;
		 if(c.moveToFirst()){
			 suraId = Integer.valueOf(c.getString(0));
		 }

		 c.close();
		 return suraId;
	 }
	 
	 public boolean checkIfGameFinished() {
		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "UserQuizElements";
		 qb.setTables(sqlTables);

		 String[] sqlSelect = {"COUNT(*)"};

		 String whereClause = "Status LIKE '1' AND Located LIKE '1' AND UserID = ?";
		 String[] whereArgs = new String[]{mTafseerManager.getLoggedInUser().getUid()};
		 
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 boolean isGameFinished = false;
		 if(c.moveToFirst()){
			 int count = Integer.valueOf(c.getString(0));
			 if(count == 168)
				 isGameFinished = true;
		 }

		 c.close();

		 return isGameFinished;
	 }
	 
	 public boolean getSavePoint() {
		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "UserQuizElements";
		 qb.setTables(sqlTables);

		 String[] sqlSelect = {"MAX(cast(ElementID as int))"};

		 String whereClause = "Status NOT LIKE '0' AND Located LIKE '1' AND UserID = ?";
		 String[] whereArgs = new String[]{mTafseerManager.getLoggedInUser().getUid()};

		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int MaxElementID = 0;
		 boolean isGameFinished = false;
		 if(c.moveToFirst()){
			 String mx = c.getString(0);
			 MaxElementID = mx == null ? 0 : Integer.valueOf(mx.toString());
		 }
		 
		 c.close();

		 if(MaxElementID != 168){
			 
			 Cursor cSave = getSaveSuraPart(MaxElementID+1);
			 if(cSave.moveToFirst()){
				 int partNb = Integer.valueOf(cSave.getString(0));
				 int suraId = Integer.valueOf(cSave.getString(1));
				 int juz = getJuzBySuraNumber(suraId);
				 
				 mTafseerManager.setCurrentSuraPart(partNb);
				 mTafseerManager.setCurrentSura(mTafseerManager.getSouraIdByJuz(juz, suraId));
				 mTafseerManager.setCurrentJuz2(juz);
			 }
			 
			 cSave.close();
			 
		 }else
			 isGameFinished = true;
		 
		 return isGameFinished;
	 }
	 
	 public Cursor getSaveSuraPart(int elementID) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String [] sqlSelect = {"Part_number", "Sura"}; 
		 String sqlTables = "QuizElements";

		 String whereClause = "ElementID = ?";
		 String[] whereArgs = new String[]{String.valueOf(elementID)};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 return c;
	 }

	 public int getJuzBySuraNumber(int suraId) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String sqlTables = "quran_number";
		 String[] sqlSelect = {"part"};

		 String whereClause = "sura = ?";
		 String[] whereArgs = new String[]{String.valueOf(suraId)};

		 qb.setTables(sqlTables);
		 qb.setDistinct(true);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int juzNumber = -1;
		 if(c.moveToFirst()){
			 juzNumber = Integer.valueOf(c.getString(0));
		 }

		 if(suraId <= 51)
			 juzNumber = 27;

		 switch (juzNumber) {
		 case 27:
			 juzNumber = 3;
			 break;

		 case 28:
			 juzNumber = 2;
			 break;

		 case 29:
			 juzNumber = 1;
			 break;

		 case 30:
			 juzNumber = 0;
			 break;

		 default:
			 break;
		 }

		 c.close();
		 return juzNumber;
	 }
	 
	 
	 public void getSendingInfo() {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 final ActivityStats statsObj = new ActivityStats();
		 int partNb;
		 int suraId;
		 
		 String [] sqlSelect = {"sura", "part_number"}; 
		 String sqlTables = "parts";
		 String orderBy = "cast(id as int)";

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, null, null, null, null, orderBy);
		 if(c.moveToFirst()){
			 do{
				 suraId = Integer.valueOf(c.getString(0));
				 partNb = Integer.valueOf(c.getString(1));
				 
				 int sendingRepeat = getQuizHistoryCount(suraId, partNb);
				 statsObj.setRepeat(String.valueOf(sendingRepeat));
				 
				 if(sendingRepeat != 0){
					 String result="", sendingDate = "";
					 Cursor cQ = getResultQuizDate(suraId, partNb);
					 if(cQ.moveToFirst()){
						 result = cQ.getString(0);
						 sendingDate = cQ.getString(1);
					 }
					 
//					 String[] dates = sendingDate.split(".");
					 statsObj.setDate(sendingDate);
					 
					 String[] results = result.split(",");
					 int counter = 0;
					 for (int i = 0; i < results.length; i++) {
						 String[] results_items = results[i].split(":");
						 if(results_items[2].equals("1"))
							 counter++;
					}
					 
					float percentage = ((float)counter/results.length)*100;
					cQ.close();
					
					statsObj.setUid(mTafseerManager.getLoggedInUser().getUid());
					statsObj.setSura(String.valueOf(suraId));
					statsObj.setPart(String.valueOf(partNb));
					statsObj.setPercentage(String.valueOf((int)percentage));
					
					Log.v("Statistics", statsObj.toString());
					
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							mTafseerManager.postActivityStats(statsObj);
						}
					}).start();
					
				 }
				 
			 }while(c.moveToNext());
		 }

		 c.close();
	 }
	 
	 public int getQuizHistoryCount(int suraId, int partNb) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String [] sqlSelect = {"COUNT(*)"}; 
		 String sqlTables = "quiz_history";

		 String whereClause = "pat_number = ? AND sura = ? AND UserID = ?";
		 String[] whereArgs = new String[]{String.valueOf(partNb), String.valueOf(suraId), mTafseerManager.getLoggedInUser().getUid()};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int sendingRepeat = 0;
		 if(c.moveToFirst())
			 sendingRepeat = Integer.valueOf(c.getString(0));
		 
		 c.close();
		 return sendingRepeat;
	 }
	 
	 public Cursor getResultQuizDate(int suraId, int partNb) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String [] sqlSelect = {"result", "quiz_date"}; 
		 String sqlTables = "quiz_history";

		 int maxRowId = getMaxRowIdHistory(suraId, partNb);
		 
		 String whereClause = "rowid = ?";
		 String[] whereArgs = new String[]{String.valueOf(maxRowId)};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 return c;
	 }
	 
	 public int getMaxRowIdHistory(int suraId, int partNb) {

		 SQLiteDatabase db = getReadableDatabase();
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		 String [] sqlSelect = {"MAX(rowid)"}; 
		 String sqlTables = "quiz_history";

		 String whereClause = "pat_number = ? AND sura = ? AND UserID = ?";
		 String[] whereArgs = new String[]{String.valueOf(partNb), String.valueOf(suraId), mTafseerManager.getLoggedInUser().getUid()};

		 qb.setTables(sqlTables);
		 Cursor c = qb.query(db, sqlSelect, whereClause, whereArgs, null, null, null);

		 int maxRowId = 0;
		 if(c.moveToFirst())
			 maxRowId = Integer.valueOf(c.getString(0));
		 
		 c.close();
		 return maxRowId;
	 }
}