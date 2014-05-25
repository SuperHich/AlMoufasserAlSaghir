package com.example.almoufasseralsaghir;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.almoufasseralsaghir.utils.CustomizedCalendarCells;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends MySuperScaler implements OnClickListener {
	private ImageView herbes, cal_compl ;
//	private CalendarView myCalendar ;
	
	public static String[] day_color ;
	
	private static final String tag = "MyCalendarActivity";

	private FontFitTextView currentMonth;
	private ImageView prevMonth;
	private ImageView nextMonth;
	private GridView calendarView;
	private CalendarAdapter adapter;
	private Calendar _calendar;
	@SuppressLint("NewApi")
	private int month, year;
	@SuppressWarnings("unused")
	private final DateFormat dateFormatter = new DateFormat();
	private static final String dateTemplate = "MMMM yyyy";
	
	private boolean rescale = false;
	private FontFitTextView myDay, myMonth, myYear ;
	private Button previous ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_activity);
		
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
		month = _calendar.get(Calendar.MONTH) ;
		year = _calendar.get(Calendar.YEAR);
		Log.d(tag, "Calendar Instance:= " + "Month: " + month + " " + "Year: "
				+ year);

    	int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+(String.valueOf(month+1)), "string", CalendarActivity.this.getPackageName());
    	String month_arabe = CalendarActivity.this.getResources().getString(resourceId);
		
		myDay.setText(String.valueOf(_calendar.get(Calendar.DAY_OF_MONTH)));
    	myYear.setText(String.valueOf(year));
    	myMonth.setText(month_arabe);

		prevMonth = (ImageView) this.findViewById(R.id.prevMonth);
		prevMonth.setOnClickListener(this);

		currentMonth = (FontFitTextView) this.findViewById(R.id.currentMonth);
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));

		nextMonth = (ImageView) this.findViewById(R.id.nextMonth);
		nextMonth.setOnClickListener(this);

		calendarView = (GridView) this.findViewById(R.id.calendar);

		// Initialised
		adapter = new CalendarAdapter(getApplicationContext(),
				R.id.calendar_day_gridcell, month, year);
		adapter.notifyDataSetChanged();
		calendarView.setAdapter(adapter);
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
//		myCalendar = (CalendarView) findViewById(R.id.my_calendar);
//		myDay = (FontFitTextView) findViewById(R.id.selected_day);
//		myMonth = (FontFitTextView) findViewById(R.id.selected_month);
//		myYear = (FontFitTextView) findViewById(R.id.selected_year);
//		
//		myCalendar.setMinDate(01/01/2013);
//		
//		customizeCalendarHeader();
//		
//		
////	Toast.makeText(CalendarActivity.this, String.valueOf(myCalendar.getDate()), Toast.LENGTH_LONG).show();
////		Log.i("DATE***************", String.valueOf(myCalendar.getDate()));
////		
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
		currentMonth.setText(DateFormat.format(dateTemplate,
				_calendar.getTime()));
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
			View row = convertView;
			
			if (row == null) {
				LayoutInflater inflater = (LayoutInflater) _context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				row = inflater.inflate(R.layout.calendar_gridcell, parent, false);
				if (rescale) MySuperScaler.scaleViewAndChildren(row, MySuperScaler.scale);
			}

			// Get a reference to the Day gridcell
			gridcell = (CustomizedCalendarCells) row.findViewById(R.id.calendar_day_gridcell);
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
	
	public void onBackPressed() {
		 finish();
	}
	
	
}
