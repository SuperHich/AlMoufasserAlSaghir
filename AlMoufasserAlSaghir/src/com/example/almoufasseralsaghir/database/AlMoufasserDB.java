package com.example.almoufasseralsaghir.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.reminder.AlarmManagerBroadcastReceiver;
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
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
		
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		return insertedId != -1;
    }

    public boolean removeFromPartFavorite(int suraId, int partNb, int userId){    	
    	SQLiteDatabase db = getWritableDatabase();
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		String sqlTable = "PartFavorite";
		
		String whereClause = "SuraID = ? AND PartNumber = ? AND UserID = ?";
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
			
		if(c.moveToFirst())
			return true;
		
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
			
			return updated > 0;
		}
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
		
		return insertedId != -1;
    }

  //**************************** REMINDER
    
    public boolean insertReminder(int partNb, int suraId, String rDate, String rTime, int type, boolean status){    	
    	SQLiteDatabase db = getWritableDatabase();
    	SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
    	
		String sqlTable = "reminders";
		
		String[] sqlSelect = {"MAX(cast(ReminderID as int))"};
		
		qb.setTables(sqlTable);
		Cursor c = qb.query(db, sqlSelect, null, null,
				null, null, null);
		
		int newID = 0;
		if(c.moveToFirst()){
			try{
				int qid = Integer.parseInt(c.getString(0));
				if(qid != 0)
					newID = qid + 1 ;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ContentValues values = new ContentValues();
		values.put("ReminderID", newID);
		values.put("Part_number", String.valueOf(partNb));
		values.put("Sura", String.valueOf(suraId));
		values.put("RDate", rDate);
		values.put("RTime", rTime);
		values.put("Type", type);
		values.put("Status", status?"1":"0");
		values.put("UserID", mTafseerManager.getLoggedInUser().getUid());
		
		long insertedId = db.insertWithOnConflict(sqlTable, null, values, SQLiteDatabase.CONFLICT_REPLACE);
		
		if(status){
			String notifMsg = prepareNotoficationText(suraId, partNb, type);
			alarmManager.setReminder(context, newID, notifMsg);
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
		sb.append(" المقطع ");
		sb.append(partName);
		sb.append(" سورة ");
		sb.append(suraName);
		sb.append(" لاتنس ");
		sb.append(typeStr);
		
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

		Reminder reminder = null;
		if(c.moveToFirst()){
			reminder = new Reminder();
			reminder.setReminderID(Integer.parseInt(c.getString(0)));
			reminder.setDate(c.getString(3));
			reminder.setTime(c.getString(4));
			reminder.setType(Integer.parseInt(c.getString(5)));
			reminder.setStatus(c.getString(6).equals("1")?true:false);
		}
		
		return reminder;
	}

}