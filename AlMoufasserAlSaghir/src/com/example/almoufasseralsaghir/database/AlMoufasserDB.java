package com.example.almoufasseralsaghir.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.reminder.AlarmManagerBroadcastReceiver;
import com.example.almoufasseralsaghir.entity.PartFavourite;
import com.example.almoufasseralsaghir.entity.Reminder;
import com.example.almoufasseralsaghir.entity.User;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

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
		
		return insertedId != -1;
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
     
    public boolean setUserDefaultReciter(int reciterId, int userId){    	
    	SQLiteDatabase db = getWritableDatabase();

		String sqlTable = "Users";
		
		ContentValues values = new ContentValues();
		values.put("DefaultReciter", String.valueOf(reciterId));
		
		String whereClause = "uid = ?";
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
		
		return false;
    }
    
    public boolean whoIsLoggedIn(){
    	SQLiteDatabase db = getReadableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTables = "Users";
		
		String whereClause = "LoggedIn = ?";
		String[] whereArgs = {String.valueOf(1)};
		
		qb.setTables(sqlTables);
		qb.setDistinct(true);
		Cursor c = qb.query(db, null, whereClause, whereArgs,
				null, null, null);
			
		if(c.moveToFirst()){
			c.close();
			return true;
		}
		
		return false;
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
//			user.setUid(c.getString(0));
//			user.setUdid(c.getString(1));
//			user.setName(c.getString(2));
//			user.setEmail(c.getString(3));
//			user.setTwitter(c.getString(4));
//			user.setFacebook(c.getString(5));
//			user.setFollower1(c.getString(6));
//			user.setType1(c.getString(7));
//			user.setFollower2(c.getString(8));
//			user.setType1(c.getString(9));
//			user.setFollower3(c.getString(10));
//			user.setType1(c.getString(11));
//			user.setLoggedIn(true);
//			user.setDefaultReciter(c.getString(13));
			
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
		String partName = getPartName(partNb+1);
		
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
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		int reminderID = getReminderID(suraId, partNb);
		String sqlTable = "reminders";
		
		String whereClause = "ReminderID = ?";
		String[] whereArgs = {String.valueOf(reminderID)};
		
		//remove reminder from AlarmManager
		alarmManager.cancelReminder(context, reminderID);
		
		//remove reminder from DB
		qb.setTables(sqlTable);
		long insertedId = db.delete(sqlTable, whereClause, whereArgs);
		
		return insertedId != -1;
    }
	
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

}