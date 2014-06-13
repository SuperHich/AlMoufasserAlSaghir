package com.example.almoufasseralsaghir;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.almoufasseralsaghir.utils.CustomizedCalendarCells;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.example.almoufasseralsaghir.entity.Reminder;
import com.example.almoufasseralsaghir.entity.ReminderListItem;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends MySuperScaler implements OnClickListener {
	private ImageView herbes, cal_compl ;
	
	private String month_arabe ;
	
	public static String[] day_color ;
	
	private static final String tag = "MyCalendarActivity";

	private FontFitTextView currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private CalendarAdapter adapter;
	private Calendar _calendar;
	private int month, year;
	private static final String dateTemplate = "MMMM yyyy";

	public static final int REMINDER_LEARN = 1;
	public static final int REMINDER_REVISE = 2;
	
	private boolean rescale = false;
	private FontFitTextView myDay, myMonth, myYear ;
	private String myNumericMonth;
	private Button previous ;
	private String myhour, myminute ;
	
	private int suraId, partNb;
	private Reminder currentReminder = new Reminder();
	
	private ListView list1, list2;
	private CustomList adapter1, adapter2;
	
	private ArrayList<ReminderListItem> time1 = new ArrayList<ReminderListItem>();
	private ArrayList<ReminderListItem> time2 = new ArrayList<ReminderListItem>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_activity);
		
		if(getIntent().getExtras() != null){
			suraId = getIntent().getExtras().getInt("suraId");
			partNb = getIntent().getExtras().getInt("partNb");
		}
		
		previous = (Button) findViewById(R.id.previous);

		myDay = (FontFitTextView) findViewById(R.id.selected_day);
		myMonth = (FontFitTextView) findViewById(R.id.selected_month);
		myYear = (FontFitTextView) findViewById(R.id.selected_year);
		
		herbes = (ImageView) findViewById(R.id.herbes);
		cal_compl = (ImageView) findViewById(R.id.calendar_completion);
		
		herbes.bringToFront();
		cal_compl.bringToFront();
		previous.bringToFront();
		
		previous = (Button) findViewById(R.id.previous);

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
////////////////// NEW CALENDAR IMPLEMENTATION ////////////////////////////////////////////////////////////////		
		
		_calendar = Calendar.getInstance(Locale.getDefault());
		month = _calendar.get(Calendar.MONTH) +1 ;
		myNumericMonth = formatSingleUnit(month+"");
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

    	int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+(String.valueOf(month)), "string", CalendarActivity.this.getPackageName());
    	month_arabe = CalendarActivity.this.getResources().getString(resourceId);
		
		myDay.setText(String.valueOf(_calendar.get(Calendar.DAY_OF_MONTH)));
    	myYear.setText(String.valueOf(year));
    	myMonth.setText(month_arabe);

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (FontFitTextView) this.findViewById(R.id.currentMonth);
		
///////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		
		currentMonth.setText(month_arabe+" "+String.valueOf(year));

		Log.i("come here", (String) DateFormat.format(dateTemplate,_calendar.getTime()));
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////		
		
		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		// Initialised
		adapter = new CalendarAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);

		
//////////////////////////    ListViews   //////////////////////////////////////////////////////////////////////////////////////////////////		
				
		adapter1 = new CustomList(CalendarActivity.this, time1);

		list1 = (ListView) findViewById(R.id.calendar_listView1);
		list1.setAdapter(adapter1);
		list1.setDivider(null);
		list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				/////////////////////// Reminder DIALOG //////////////////////////////////////////////////////////////////////				 

				setReminderPopup(time1, position); 
				//////////////////////////////////////////////////////////////////////////////////////////////					 
			}
		});
		  
		adapter2 = new CustomList(CalendarActivity.this, time2);

		list2 = (ListView) findViewById(R.id.calendar_listView2);
		list2.setAdapter(adapter2);
		list2.setDivider(null);
		list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				/////////////////////// Reminder DIALOG //////////////////////////////////////////////////////////////////////				 
				setReminderPopup(time2, position); 
				//////////////////////////////////////////////////////////////////////////////////////////////				 
			}
		});
		
		

	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		currentReminder = myDB.getReminder(suraId, partNb);
		
		fillTimeList();
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
//	      //      tv.setTextAppearance(CalendarActivity.this, R.style.calendar_header);
//	            
//	           int density= getResources().getDisplayMetrics().densityDpi;
//	           if(density == DisplayMetrics.DENSITY_HIGH) tv.setTextSize(15 * MySuperScaler.scale);
//	           else tv.setTextSize(35 * MySuperScaler.scale);
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
//	
//	
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
	 * 
	 * @param month
	 * @param year
	 */
	private void setGridCellAdapterToDate(int month, int year) {
		adapter = new CalendarAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		_calendar.set(year, month - 1, _calendar.get(Calendar.DAY_OF_MONTH));
		
		int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+(String.valueOf(month)), "string", CalendarActivity.this.getPackageName());
    	String up_month_arabe = CalendarActivity.this.getResources().getString(resourceId);
		
		currentMonth.setText(up_month_arabe+" "+String.valueOf(year));
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		if (v == prevMonth) {
			rescale = true ;
			
			if (month <= 1) {
				month = 12;
				year--;
			} else {
				month--;
			}
			Log.d(tag, "Setting Prev Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}
		if (v == nextMonth) {
			rescale = true ;
			
			if (month > 11) {
				month = 1;
				year++;
			} else {
				month++;
			}
			Log.d(tag, "Setting Next Month in GridCellAdapter: " + "Month: "
					+ month + " Year: " + year);
			setGridCellAdapterToDate(month, year);
		}

	}

	@Override
	public void onDestroy() {
		Log.d(tag, "Destroying View ...");
		super.onDestroy();
	}
	
	
	public class CalendarAdapter extends BaseAdapter implements OnClickListener {
		private static final String tag = "GridCellAdapter";
		private final Context _context;

		private final List<String> list;
		private static final int DAY_OFFSET = 1;
		private final String[] weekdays = new String[] { "Sun", "Mon", "Tue",
				"Wed", "Thu", "Fri", "Sat" };
		
		
		private final String[] months = { "January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		private final int[] daysOfMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30,
				31, 30, 31 };
		private int daysInMonth;
		private int currentDayOfMonth;
		private int currentWeekDay;
		private CustomizedCalendarCells gridcell;
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"dd-MMM-yyyy");

		// Days in Current Month
		public CalendarAdapter(Context context, int textViewResourceId,
				int month, int year) {
			super();
			this._context = context;
			this.list = new ArrayList<String>();
			Log.d(tag, "==> Passed in Date FOR Month: " + month + " "
					+ "Year: " + year);
			Calendar calendar = Calendar.getInstance();
			setCurrentDayOfMonth(calendar.get(Calendar.DAY_OF_MONTH));
			setCurrentWeekDay(calendar.get(Calendar.DAY_OF_WEEK));
			Log.d(tag, "New Calendar:= " + calendar.getTime().toString());
			Log.d(tag, "CurrentDayOfWeek :" + getCurrentWeekDay());
			Log.d(tag, "CurrentDayOfMonth :" + getCurrentDayOfMonth());

			// Print Month
			printMonth(month, year);

		}

		private String getMonthAsString(int i) {
			return months[i];
		}

		private String getWeekDayAsString(int i) {
			return weekdays[i];
		}

		private int getNumberOfDaysOfMonth(int i) {
			return daysOfMonth[i];
		}

		public String getItem(int position) {
			return list.get(position);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		/**
		 * Prints Month
		 * 
		 * @param mm
		 * @param yy
		 */
		private void printMonth(int mm, int yy) {
			Log.d(tag, "==> printMonth: mm: " + mm + " " + "yy: " + yy);
			int trailingSpaces = 0;
			int daysInPrevMonth = 0;
			int prevMonth = 0;
			int prevYear = 0;
			int nextMonth = 0;
			int nextYear = 0;

			int currentMonth = mm - 1;
			String currentMonthName = getMonthAsString(currentMonth);
			daysInMonth = getNumberOfDaysOfMonth(currentMonth);

			Log.d(tag, "Current Month: " + " " + currentMonthName + " having "
					+ daysInMonth + " days.");

			GregorianCalendar cal = new GregorianCalendar(yy, currentMonth, 1);
			Log.d(tag, "Gregorian Calendar:= " + cal.getTime().toString());

			if (currentMonth == 11) {
				prevMonth = currentMonth - 1;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 0;
				prevYear = yy;
				nextYear = yy + 1;
				Log.d(tag, "*->PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else if (currentMonth == 0) {
				prevMonth = 11;
				prevYear = yy - 1;
				nextYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				nextMonth = 1;
				Log.d(tag, "**--> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			} else {
				prevMonth = currentMonth - 1;
				nextMonth = currentMonth + 1;
				nextYear = yy;
				prevYear = yy;
				daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
				Log.d(tag, "***---> PrevYear: " + prevYear + " PrevMonth:"
						+ prevMonth + " NextMonth: " + nextMonth
						+ " NextYear: " + nextYear);
			}

			int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
			trailingSpaces = currentWeekDay;

			Log.d(tag, "Week Day:" + currentWeekDay + " is "
					+ getWeekDayAsString(currentWeekDay));
			Log.d(tag, "No. Trailing space to Add: " + trailingSpaces);
			Log.d(tag, "No. of Days in Previous Month: " + daysInPrevMonth);

			if (cal.isLeapYear(cal.get(Calendar.YEAR)))
				if (mm == 2)
					++daysInMonth;
				else if (mm == 3)
					++daysInPrevMonth;

			// Trailing Month days
			for (int i = 0; i < trailingSpaces; i++) {
				Log.d(tag,
						"PREV MONTH:= "
								+ prevMonth
								+ " => "
								+ getMonthAsString(prevMonth)
								+ " "
								+ String.valueOf((daysInPrevMonth
										- trailingSpaces + DAY_OFFSET)
										+ i));
				list.add(String
						.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
								+ i)
						+ "-GREY"
						+ "-"
						+ getMonthAsString(prevMonth)
						+ "-"
						+ prevYear);
			}

			// Current Month Days
			for (int i = 1; i <= daysInMonth; i++) {
				Log.d(currentMonthName, String.valueOf(i) + " "
						+ getMonthAsString(currentMonth) + " " + yy);
				if (i == getCurrentDayOfMonth()) {
					list.add(String.valueOf(i) + "-BLUE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				} else {
					list.add(String.valueOf(i) + "-WHITE" + "-"
							+ getMonthAsString(currentMonth) + "-" + yy);
				}
			}

			// Leading Month days
			for (int i = 0; i < list.size() % 7; i++) {
				Log.d(tag, "NEXT MONTH:= " + getMonthAsString(nextMonth));
				list.add(String.valueOf(i + 1) + "-GREY" + "-"
						+ getMonthAsString(nextMonth) + "-" + nextYear);
			}
		}


		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row ;
			
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_gridcell, parent, false);
				if (rescale) MySuperScaler.scaleViewAndChildren(row, MySuperScaler.scale);
			} else {
				
			row	= convertView;
			}

			// Get a reference to the Day gridcell
			gridcell = (CustomizedCalendarCells) row.findViewById(R.id.calendar_day_gridcell);
			
			gridcell.bringToFront();
	//		gridcell.setId(position);
			gridcell.setOnClickListener(this);

			// ACCOUNT FOR SPACING

			Log.d(tag, "Current Day: " + getCurrentDayOfMonth());
			day_color = list.get(position).split("-");
			String theday = day_color[0];
			String themonth = day_color[2];
			String theyear = day_color[3];

			// Set the Day GridCell
			gridcell.setText(theday);
			gridcell.setTag(theday + "-" + themonth + "-" + theyear);
			Log.d(tag, "Setting GridCell " + theday + "-" + themonth + "-"
					+ theyear);

//			if (day_color[1].equals("GREY")) {
//				gridcell.setTextColor((Color.parseColor("#747a81"))); //// jours precedents  #747a81
//			}
//			if (day_color[1].equals("WHITE")) {
//				gridcell.setTextColor((Color.parseColor("#121e2a"))); ///jours normals  #121e2a
//			}
			if (day_color[1].equals("BLUE")) {
				gridcell.setBackgroundResource(R.drawable.calendar_cell_selected);
	//			gridcell.setTextColor(getResources().getColor(R.color.lightgray));
			}
			return row;
		}

		@Override
		public void onClick(View view) {
			
			String date_month_year = (String) view.getTag();
			Log.e("Selected date", date_month_year);
			try {
				Date parsedDate = dateFormatter.parse(date_month_year);
				Log.d(tag, "Parsed Date: " + parsedDate.toString());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			String[] date = date_month_year.split("-");
			
        	int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+date[1], "string", CalendarActivity.this.getPackageName());
        	String month_arabe = CalendarActivity.this.getResources().getString(resourceId);
			
			
			myDay.setText(date[0]);
        	myYear.setText(date[2]);
        	myMonth.setText(month_arabe);
        	myNumericMonth = formatSingleUnit(date[1]);
			
			
		}

		public int getCurrentDayOfMonth() {
			return currentDayOfMonth;
		}

		private void setCurrentDayOfMonth(int currentDayOfMonth) {
			this.currentDayOfMonth = currentDayOfMonth;
		}

		public void setCurrentWeekDay(int currentWeekDay) {
			this.currentWeekDay = currentWeekDay;
		}

		public int getCurrentWeekDay() {
			return currentWeekDay;
		}
	}
	
	public class CustomList extends ArrayAdapter<ReminderListItem>{
		private final Activity context;
		private final ArrayList<ReminderListItem> time;
		
		public CustomList(Activity context, ArrayList<ReminderListItem> web) {
			super(context, R.layout.calendar_list_row, web);
			this.context = context;
			this.time = web;
		}
		@Override
		public View getView(int position, View view, ViewGroup parent) {
			View row ;
			if(view == null)
			{
				LayoutInflater inflater = context.getLayoutInflater();
				row= inflater.inflate(R.layout.calendar_list_row, parent, false);
			}
			else row = view ;

			final ReminderListItem item = time.get(position);

			FontFitTextView txtTitle = (FontFitTextView) row.findViewById(R.id.reminder_time);
			ImageView imageView = (ImageView) row.findViewById(R.id.reminder_time_signal);
			txtTitle.setText(item.getLabel());
			if(!item.isSelected())
				imageView.setImageResource(R.drawable.calender_reminder_inactivated);
			else
				imageView.setImageResource(R.drawable.calender_reminder_activated);
			
			return row;
		}
		}
	
	public void setReminderPopup(final ArrayList<ReminderListItem> timeList, final int position){

		// Your action here on button click
			final Dialog dialog = new Dialog(CalendarActivity.this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
			dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
			dialog.setContentView(R.layout.reminder_view);
			dialog.setCancelable(true);

			RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.reminder_popup);
			popup_view.getLayoutParams().height = 517;
			popup_view.getLayoutParams().width = 847;
			MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);

/////////////////////// MY CUSTOM TIMEPICKER ////////////////////////////////////////////////////////			
			
			
			final Button hours_plus = (Button) dialog.findViewById(R.id.time_hours_plus);
			final Button hours_minus = (Button) dialog.findViewById(R.id.time_hours_minus);
			final FontFitTextView hours = (FontFitTextView) dialog.findViewById(R.id.time_hours);
			
			final Button minutes_plus = (Button) dialog.findViewById(R.id.time_minutes_plus);
			final Button minutes_minus = (Button) dialog.findViewById(R.id.time_minutes_minus);
			final FontFitTextView minutes = (FontFitTextView) dialog.findViewById(R.id.time_minutes);
			
			
			String time = timeList.get(position).getLabel();
			String time_parts[] = time.split(":");
			
			myhour = time_parts[0];
			myminute = time_parts[1];
			
			Log.i("*******  HOUR *************", myhour);
			Log.i("*******  MINUTE *************", myminute);
			
			hours.setText(myhour);
			minutes.setText(myminute);
			
			hours_plus.setOnTouchListener(new OnTouchListener() {

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
						
						int myhourValue = Integer.parseInt(myhour);
						if (myhourValue < 23) myhourValue++ ;
						else myhourValue = 0;
						myhour = String.valueOf(myhourValue);
						if (myhourValue < 10) myhour = "0"+myhour;
						hours.setText(myhour);
					
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
			hours_minus.setOnTouchListener(new OnTouchListener() {

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

						int myhourValue = Integer.parseInt(myhour);
						if (myhourValue > 0) myhourValue-- ;
						else myhourValue = 23;
						myhour = String.valueOf(myhourValue);
						if (myhourValue < 10) myhour = "0"+myhour;
						hours.setText(myhour);
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
			minutes_plus.setOnTouchListener(new OnTouchListener() {

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

						int myminutesValue = Integer.parseInt(myminute);
						if (myminutesValue < 59)
							myminutesValue++ ;
						else
							myminutesValue = 0;
						myminute = String.valueOf(myminutesValue);
						if (myminutesValue < 10) myminute = "0"+myminute;
						minutes.setText(myminute);
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
			minutes_minus.setOnTouchListener(new OnTouchListener() {

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

						int myminutesValue = Integer.parseInt(myminute);
						if (myminutesValue > 0) 
							myminutesValue-- ;
						else 
							myminutesValue = 59;
						myminute = String.valueOf(myminutesValue);
						if (myminutesValue < 10) myminute = "0"+myminute;
						minutes.setText(myminute);
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
			
			
			
			
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////			
			Button reminder_confirm = (Button) dialog.findViewById(R.id.reminder_confirm);
			Button reminder_cancel = (Button) dialog.findViewById(R.id.reminder_cancel);
			
			
			final Button reminder_start = (Button) dialog.findViewById(R.id.reminder_start);
			final Button reminder_stop = (Button) dialog.findViewById(R.id.reminder_stop);

/////////////////////// CHANGE TO GET REMINDER STATE
			final ImageView reminder_state = (ImageView) dialog.findViewById(R.id.reminder_state);
			
			
			//// GET
			if (currentReminder.isStatus()){
				reminder_state.setBackgroundResource(R.drawable.stop_reminder);
				reminder_start.setBackgroundResource(R.drawable.reminder_on_part1);
				reminder_stop.setBackgroundResource(R.drawable.reminder_on_part2);

				currentReminder.setStatus(true);
			} else {
				reminder_state.setBackgroundResource(R.drawable.start_reminder);
				reminder_start.setBackgroundResource(R.drawable.reminder_off_part1);
				reminder_stop.setBackgroundResource(R.drawable.reminder_off_part2);

				currentReminder.setStatus(false);
			}
			
			final Button reminder_learn = (Button) dialog.findViewById(R.id.reminder_learn);
			final Button reminder_revise = (Button) dialog.findViewById(R.id.reminder_revise);
			
			if(currentReminder.getType() == REMINDER_LEARN)
			{
				reminder_learn.setBackgroundResource(R.drawable.popup_active_btn);
				reminder_revise.setBackgroundResource(R.drawable.popup_inactive_btn);
			}else{
				reminder_learn.setBackgroundResource(R.drawable.popup_inactive_btn);
				reminder_revise.setBackgroundResource(R.drawable.popup_active_btn);
			}

			reminder_learn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					reminder_learn.setBackgroundResource(R.drawable.popup_active_btn);
					reminder_revise.setBackgroundResource(R.drawable.popup_inactive_btn);

////////////////////// SET REMINDER Learn //////////////////////////////////////////////////////////////////			
					currentReminder.setType(REMINDER_LEARN);
				}
			});

			reminder_revise.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					reminder_learn.setBackgroundResource(R.drawable.popup_inactive_btn);
					reminder_revise.setBackgroundResource(R.drawable.popup_active_btn);

////////////////////// SET REMINDER REVISE  //////////////////////////////////////////////////////////////////			
					currentReminder.setType(REMINDER_REVISE);
				}
			});

			reminder_confirm.setOnTouchListener(new OnTouchListener() {

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
						
						////////////////////// SET REMiDER //////////////////////////////////////////////////////////////////
						String time = myhour + ":" + myminute;
						time = formatDoubleUnit(time, ":");
						String date = myYear.getText().toString() +"-"+myNumericMonth+"-"+formatSingleUnit(myDay.getText().toString());
						
						currentReminder.setTime(time);
						currentReminder.setDate(date);
						
						boolean isOK = myDB.insertReminder(currentReminder, suraId, partNb);

						if (isOK)
							Toast.makeText(CalendarActivity.this, "Reminder set", Toast.LENGTH_SHORT).show();
						 else 
							Toast.makeText(CalendarActivity.this, "Reminder not set", Toast.LENGTH_SHORT).show();
						
						currentReminder = myDB.getReminder(suraId, partNb);
						refreshTimeStatus(timeList.size(), position, true);
						
						dialog.dismiss();
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
			
			reminder_cancel.setOnTouchListener(new OnTouchListener() {

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
						
						myDB.deleteReminder(suraId, partNb);
						
						refreshTimeStatus(timeList.size(), position, false);

						dialog.dismiss();
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
			
			reminder_start.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					reminder_state.setBackgroundResource(R.drawable.start_reminder);
					reminder_start.setBackgroundResource(R.drawable.reminder_on_part1);
					reminder_stop.setBackgroundResource(R.drawable.reminder_on_part2);
					
					currentReminder.setStatus(true);
					
				}
			});
			
			reminder_stop.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					reminder_state.setBackgroundResource(R.drawable.stop_reminder);
					reminder_start.setBackgroundResource(R.drawable.reminder_off_part1);
					reminder_stop.setBackgroundResource(R.drawable.reminder_off_part2);
					
					currentReminder.setStatus(false);
					
				}
			});
			
			
			dialog.show(); 
	}
	
	public void onBackPressed() {
		 finish();
	}
	
	private String formatDoubleUnit(String time, String delimiter){
		StringBuilder result = new StringBuilder();
		String[] timeSplit = time.split(delimiter);
		
		result.append(formatSingleUnit(timeSplit[0]));
		result.append(":");
		result.append(formatSingleUnit(timeSplit[1]));
		
		return result.toString();
	}
	
	private String formatSingleUnit(String unit){
		StringBuilder res = new StringBuilder();
		if(unit.length()==1)
			res.append("0"+unit);
		else
			res.append(unit);
		
		return res.toString();
	}
	
	private void refreshTimeStatus(int listSize, int position, boolean status){
		ArrayList<ReminderListItem> resultList = new ArrayList<ReminderListItem>();
		if(listSize == 8){
			for (int i = 0; i < time1.size(); i++) {
				ReminderListItem item = time1.get(i);
				if(i == position)
					item.setSelected(status);
				else
					item.setSelected(false);
				
				resultList.add(i, item);
			}
			
			time1.clear();
			time1.addAll(resultList);
			adapter1.notifyDataSetChanged();
			
			cleanTimeList(listSize);
		}else
		{
			for (int i = 0; i < time2.size(); i++) {
				ReminderListItem item = time2.get(i);
				if(i == position)
					item.setSelected(status);
				else
					item.setSelected(false);
				
				resultList.add(i, item);
			}
			
			time2.clear();
			time2.addAll(resultList);
			adapter2.notifyDataSetChanged();
			
			cleanTimeList(listSize);
		}
	}
	
	private void cleanTimeList(int listSize){
		ArrayList<ReminderListItem> resultList = new ArrayList<ReminderListItem>();
		if(listSize != 8){
			for (ReminderListItem item : time1) {
				item.setSelected(false);
				resultList.add(item);
			}
			
			time1.clear();
			time1.addAll(resultList);
			adapter1.notifyDataSetChanged();
		}else
		{
			for (ReminderListItem item : time2) {
				item.setSelected(false);
				resultList.add(item);
			}
			
			time2.clear();
			time2.addAll(resultList);
			adapter2.notifyDataSetChanged();
		}
	}
	
	private void fillTimeList(){
			  
			  time1.add(new ReminderListItem("00:00", isTimeSelected("00:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("01:00", isTimeSelected("01:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("02:00", isTimeSelected("02:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("03:00", isTimeSelected("03:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("04:00", isTimeSelected("04:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("05:00", isTimeSelected("05:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("06:00", isTimeSelected("06:00", currentReminder.getTime())));
			  time1.add(new ReminderListItem("07:00", isTimeSelected("07:00", currentReminder.getTime())));
			  
			  time2.add(new ReminderListItem("08:00", isTimeSelected("08:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("09:00", isTimeSelected("09:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("10:00", isTimeSelected("10:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("11:00", isTimeSelected("11:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("12:00", isTimeSelected("12:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("13:00", isTimeSelected("13:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("14:00", isTimeSelected("14:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("15:00", isTimeSelected("15:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("16:00", isTimeSelected("16:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("17:00", isTimeSelected("17:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("18:00", isTimeSelected("18:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("19:00", isTimeSelected("19:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("20:00", isTimeSelected("20:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("21:00", isTimeSelected("21:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("22:00", isTimeSelected("22:00", currentReminder.getTime())));
			  time2.add(new ReminderListItem("23:00", isTimeSelected("23:00", currentReminder.getTime())));
			  
	}
	
	private boolean isTimeSelected(String listTime, String reminderTime){
		boolean isEqual = false;
		if(reminderTime != null){
			String[] listTimeSplit = listTime.split(":");
			String[] reminderTimeSplit = reminderTime.split(":");
			isEqual = listTimeSplit[0].equals(reminderTimeSplit[0]);
		}
				
		return isEqual;
	}
}
