package com.example.almoufasseralsaghir;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.almoufasseralsaghir.mycalendar.CaldroidFragment;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.roomorama.caldroid.CaldroidListener;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends MySuperScaler {
	private ImageView herbes, cal_compl ;
//	private CalendarView myCalendar ;
	private boolean undo = false;
	private CaldroidFragment caldroidFragment;
	private CaldroidFragment dialogCaldroidFragment;
	
	
	private FontFitTextView myDay, myMonth, myYear ;
	private Button previous ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_activity);
		
		previous = (Button) findViewById(R.id.previous);

		
		herbes = (ImageView) findViewById(R.id.herbes);
		cal_compl = (ImageView) findViewById(R.id.calendar_completion);
		
		herbes.bringToFront();
		cal_compl.bringToFront();
		previous.bringToFront();
		
		previous = (Button) findViewById(R.id.previous);
//		myCalendar = (CalendarView) findViewById(R.id.my_calendar);
		myDay = (FontFitTextView) findViewById(R.id.selected_day);
		myMonth = (FontFitTextView) findViewById(R.id.selected_month);
		myYear = (FontFitTextView) findViewById(R.id.selected_year);
		
/////////////////////   CALDROID IMPLEMENTATION   ////////////////////////////////////////////////////////////////////
		
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

		// Setup caldroid fragment
		// **** If you want normal CaldroidFragment, use below line ****
		caldroidFragment = new CaldroidFragment();

		// //////////////////////////////////////////////////////////////////////
		// **** This is to show customized fragment. If you want customized
		// version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

		// Setup arguments

		// If Activity is created after rotation
		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState,
					"CALDROID_SAVED_STATE");
		}
		// If activity is created from fresh
		else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

			// Uncomment this to customize startDayOfWeek
			// args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
			// CaldroidFragment.TUESDAY); // Tuesday
			caldroidFragment.setArguments(args);
		}

		setCustomResourceForDates();

		// Attach to the activity
		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		MySuperScaler.scaled = false ;
		t.commit();

		// Setup listener
		final CaldroidListener listener = new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				Toast.makeText(getApplicationContext(), formatter.format(date),
						Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onChangeMonth(int month, int year) {
				String text = "month: " + month + " year: " + year;
				Toast.makeText(getApplicationContext(), text,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onLongClickDate(Date date, View view) {
				Toast.makeText(getApplicationContext(),
						"Long click " + formatter.format(date),
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCaldroidViewCreated() {
				if (caldroidFragment.getLeftArrowButton() != null) {
					Toast.makeText(getApplicationContext(),
							"Caldroid view is created", Toast.LENGTH_SHORT)
							.show();
				}
			}

		};

		// Setup Caldroid
		caldroidFragment.setCaldroidListener(listener);
		
		
		
		
		
		
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
//		customizeCalendarHeader();
//	Toast.makeText(CalendarActivity.this, String.valueOf(myCalendar.getDate()), Toast.LENGTH_LONG).show();
//		Log.i("DATE***************", String.valueOf(myCalendar.getDate()));
//		
//		myCalendar.setOnDateChangeListener(new OnDateChangeListener() {
//
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month,
//                    int dayOfMonth) {
//            	
//            	int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+(String.valueOf(month+1)), "string", CalendarActivity.this.getPackageName());
//            	String month_arabe = CalendarActivity.this.getResources().getString(resourceId);
//            	
//            	myDay.setText(String.valueOf(dayOfMonth));
//            	myYear.setText(String.valueOf(year));
//            	myMonth.setText(month_arabe);
//            	
//            }
//        });
		
		
		previous.setOnTouchListener(new OnTouchListener() {
			
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          break;
		      }
		      case MotionEvent.ACTION_UP: {
		    	// Your action here on button click
					finish();
		      }
		      case MotionEvent.ACTION_CANCEL: {
		          Button view = (Button) v;
		          view.getBackground().clearColorFilter();
		          view.invalidate();
		          break;
		      }
		      }
		      return true;
		    }
		});
		
	}
	
	
//	public void customizeCalendarHeader(){
//		
//		try
//	    {
//	        Class<?> cvClass = myCalendar.getClass();
//	        Field field = cvClass.getDeclaredField("mMonthName");
//	        field.setAccessible(true);
//
//	        try
//	        {
//	            TextView tv = (TextView) field.get(myCalendar);
//	            tv.setTextColor( Color.parseColor("#3f4e60"));
//	            tv.setTypeface(null, Typeface.BOLD);
//	       //    String cmltxt = toCamelCase(tv.getText().toString()) ;
//	        //    tv.setText(cmltxt);
//	        } 
//	        catch (IllegalArgumentException e)
//	        {
//	            e.printStackTrace();
//	        }
//	        catch (IllegalAccessException e)
//	        {
//	            e.printStackTrace();
//	        }
//	    }
//	    catch (NoSuchFieldException e)
//	    {
//	        e.printStackTrace();
//	    }
//	}
	
	
//	public String toCamelCase (String text){
//		
//		String[] words = text.split(" ");
//		StringBuilder sb = new StringBuilder();
//		if (words[0].length() > 0) {
//		    sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
//		    for (int i = 1; i < words.length; i++) {
//		        sb.append(" ");
//		        sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
//		    }
//		}
//		String titleCaseValue = sb.toString();
//		return titleCaseValue ;
//	}	
	
	/**
	 * Save current states of the Caldroid here
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}

		if (dialogCaldroidFragment != null) {
			dialogCaldroidFragment.saveStatesToKey(outState,
					"DIALOG_CALDROID_SAVED_STATE");
		}
	}
	
	private void setCustomResourceForDates() {
		Calendar cal = Calendar.getInstance();

		// Min date is last 7 days
		cal.add(Calendar.DATE, -18);
		Date blueDate = cal.getTime();

		// Max date is next 7 days
		cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 16);
		Date greenDate = cal.getTime();

		if (caldroidFragment != null) {
			caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_holo_blue_dark,
					blueDate);
			caldroidFragment.setBackgroundResourceForDate(R.color.caldroid_sky_blue,
					greenDate);
			caldroidFragment.setTextColorForDate(R.color.caldroid_white, blueDate);
			caldroidFragment.setTextColorForDate(R.color.caldroid_white, greenDate);
		}
	}
	
	public void onBackPressed() {
		 finish();
	}
	
	
}
