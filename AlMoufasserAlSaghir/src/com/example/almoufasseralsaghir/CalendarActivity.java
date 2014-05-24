package com.example.almoufasseralsaghir;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

@SuppressLint("SimpleDateFormat")
public class CalendarActivity extends MySuperScaler {
	private ImageView herbes, cal_compl ;
	private CalendarView myCalendar ;
	
	
	
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
		myCalendar = (CalendarView) findViewById(R.id.my_calendar);
		myDay = (FontFitTextView) findViewById(R.id.selected_day);
		myMonth = (FontFitTextView) findViewById(R.id.selected_month);
		myYear = (FontFitTextView) findViewById(R.id.selected_year);
		
		myCalendar.setMinDate(01/01/2013);
		
		customizeCalendarHeader();
		
		
//	Toast.makeText(CalendarActivity.this, String.valueOf(myCalendar.getDate()), Toast.LENGTH_LONG).show();
//		Log.i("DATE***************", String.valueOf(myCalendar.getDate()));
//		
		myCalendar.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                    int dayOfMonth) {
            	
            	int resourceId = CalendarActivity.this.getResources().getIdentifier("month_"+(String.valueOf(month+1)), "string", CalendarActivity.this.getPackageName());
            	String month_arabe = CalendarActivity.this.getResources().getString(resourceId);
            	
            	myDay.setText(String.valueOf(dayOfMonth));
            	myYear.setText(String.valueOf(year));
            	myMonth.setText(month_arabe);
            	
            }
            
            
        });
		
		
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
	
	
	public void customizeCalendarHeader(){
		
		try
	    {
	        Class<?> cvClass = myCalendar.getClass();
	        Field field = cvClass.getDeclaredField("mMonthName");
	        field.setAccessible(true);

	        try
	        {
	            TextView tv = (TextView) field.get(myCalendar);
	            tv.setTextColor( Color.parseColor("#3f4e60"));
	            tv.setTypeface(null, Typeface.BOLD);
	            tv.setTextSize(8);
	            
	       //    String cmltxt = toCamelCase(tv.getText().toString()) ;
	        //    tv.setText(cmltxt);
	        } 
	        catch (IllegalArgumentException e)
	        {
	            e.printStackTrace();
	        }
	        catch (IllegalAccessException e)
	        {
	            e.printStackTrace();
	        }
	    }
	    catch (NoSuchFieldException e)
	    {
	        e.printStackTrace();
	    }
	}
	
	
	public String toCamelCase (String text){
		
		String[] words = text.split(" ");
		StringBuilder sb = new StringBuilder();
		if (words[0].length() > 0) {
		    sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
		    for (int i = 1; i < words.length; i++) {
		        sb.append(" ");
		        sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
		    }
		}
		String titleCaseValue = sb.toString();
		return titleCaseValue ;
	}	
	
	
	
	
	
	public void onBackPressed() {
		 finish();
	}
	
	
}
