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

import com.example.almoufasseralsaghir.MainActivity;
import com.example.almoufasseralsaghir.R;

public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

	final public static String ONE_TIME = "onetime";
	public static final String ACTION_NOTIF_CLICK = "ACTION_CLICK";
	private static final String REMINDER_MESSAGE = "reminder";
	private static final String REMINDER_ID = "reminder_id";
	
	private NotificationManager mNotifyManager;
	private NotificationCompat.Builder mBuilder;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equals(ACTION_NOTIF_CLICK)){
			Intent reminderIntent = new Intent(context, MainActivity.class);
//			reminderIntent.putExtra("", value);
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
//         Format formatter = new SimpleDateFormat("hh:mm:ss a");
//         msgStr.append(formatter.format(new Date()));

         Toast.makeText(context, msgStr, Toast.LENGTH_LONG).show();

         startNotification(context, msgStr, reminderID);
         
         //Release the lock
         wl.release();
         
	}
//	public void SetAlarm(Context context)
//    {
//        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
//        intent.putExtra(ONE_TIME, Boolean.FALSE);
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//        //After after 30 seconds
//        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 5 , pi); 
//    }
//
//    public void CancelAlarm(Context context)
//    {
//        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(sender);
//    }
    public void setReminder(Context context, int reminderID, String notifMsg){
    	AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.setData(Uri.parse("custom://" + reminderID));
        intent.setAction(String.valueOf(reminderID));
        intent.putExtra(REMINDER_ID, reminderID);
        intent.putExtra(REMINDER_MESSAGE, notifMsg);
        
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
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
		    .setSmallIcon(R.drawable.ic_launcher);
		
		Intent resultIntent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		resultIntent.setAction(ACTION_NOTIF_CLICK);
		
		PendingIntent resultPendingIntent= PendingIntent.getBroadcast(context, 0, resultIntent, 0);
		
		mBuilder.setContentIntent(resultPendingIntent);
		
		mNotifyManager.notify(reminderID, mBuilder.build());
	}
}
