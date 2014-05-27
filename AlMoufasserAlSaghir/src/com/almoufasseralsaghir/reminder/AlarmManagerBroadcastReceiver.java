package com.almoufasseralsaghir.reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.example.almoufasseralsaghir.R;
import com.example.almoufasseralsaghir.SouraActivity;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	final public static String ONE_TIME = "onetime";
	public static final String ACTION_NOTIF_CLICK = "ACTION_CLICK";
	public static final String REMINDER_MESSAGE = "reminder";
	public static final String REMINDER_ID = "reminder_id";
	public static final String REMINDER_SURA_ID = "reminder_sura_id";
	public static final String REMINDER_PART_NB = "reminder_part_nb";
	
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals(ACTION_NOTIF_CLICK)){
			Intent reminderIntent = new Intent(context, SouraActivity.class);
			reminderIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			int suraId = intent.getExtras().getInt(REMINDER_SURA_ID);
			int partNb = intent.getExtras().getInt(REMINDER_PART_NB);
			reminderIntent.putExtra(REMINDER_SURA_ID, suraId);
			reminderIntent.putExtra(REMINDER_PART_NB, partNb);
			context.startActivity(reminderIntent);
			return;
		}
		
		 PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
         PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
         //Acquire the lock
         wl.acquire();

         //You can do the processing here update the widget/remote views.
         Bundle extras = intent.getExtras();
         String msgStr = null;
         int reminderID = -1;
         
         if(extras != null){
        	 msgStr = extras.getString(REMINDER_MESSAGE);
        	 reminderID = extras.getInt(REMINDER_ID);
         }

         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

         startNotification(context, msgStr, reminderID);
         
         //Release the lock
         wl.release();
         
	}

    public void setReminder(Context context, int reminderID, int suraId, int partNb, String notifMsg, long when){
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.setData(Uri.parse("custom://" + reminderID));
        intent.setAction(String.valueOf(reminderID));
        intent.putExtra(REMINDER_ID, reminderID);
        intent.putExtra(REMINDER_MESSAGE, notifMsg);
        intent.putExtra(REMINDER_SURA_ID, suraId);
        intent.putExtra(REMINDER_PART_NB, partNb);
        
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, when, pi);
    }
    	
    public void cancelReminder(Context context, int reminderID){
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.setData(Uri.parse("custom://" + reminderID));
        intent.setAction(String.valueOf(reminderID));
        
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.cancel(pi);
    }
    
	private void startNotification(Context context, String msg, int reminderID){
		mNotifyManager =
		        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		mBuilder = new NotificationCompat.Builder(context);
		mBuilder.setContentTitle(context.getResources().getString(R.string.app_name))
		    .setContentText(msg)
		    .setSmallIcon(R.drawable.logo);
		
		Intent resultIntent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		resultIntent.setAction(ACTION_NOTIF_CLICK);
		resultIntent.putExtra(REMINDER_ID, reminderID);
		
		PendingIntent resultPendingIntent= PendingIntent.getBroadcast(context, 0, resultIntent, 0);
		
		mBuilder.setContentIntent(resultPendingIntent);
		
		mNotifyManager.notify(reminderID, mBuilder.build());
	}
}
