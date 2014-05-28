package com.example.almoufasseralsaghir;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class QuestionsActivity extends MySuperScaler {

	private Button info, favourites, previous, home ;
	
	private RelativeLayout myQuestionsBackground, results_format_3, results_format_4 ;
	private FontFitTextView answer_1, answer_2, answer_3, question ;
	private ImageView result_3_format_3, result_2_format_3, result_1_format_3,
					   result_4_format_4, result_3_format_4, result_2_format_4, result_1_format_4 ;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_screen);
		
		info = (Button) findViewById(R.id.questions_info);
		favourites = (Button) findViewById(R.id.questions_favourites);
		previous = (Button) findViewById(R.id.questions_previous);
		home = (Button) findViewById(R.id.questions_home);
		
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		myQuestionsBackground = (RelativeLayout) findViewById(R.id.my_questions_background);
		results_format_3 = (RelativeLayout) findViewById(R.id.results_format_3);
		results_format_4 = (RelativeLayout) findViewById(R.id.results_format_4);
		
		question = (FontFitTextView) findViewById(R.id.question);
		answer_1 = (FontFitTextView) findViewById(R.id.proposition_1);
		answer_2 = (FontFitTextView) findViewById(R.id.proposition_2);
		answer_3 = (FontFitTextView) findViewById(R.id.proposition_3);
		
		result_3_format_3 = (ImageView) findViewById(R.id.result_3_format_3);
		result_2_format_3 = (ImageView) findViewById(R.id.result_2_format_3);
		result_1_format_3 = (ImageView) findViewById(R.id.result_1_format_3);
		
		result_4_format_4 = (ImageView) findViewById(R.id.result_4_format_4);
		result_3_format_4 = (ImageView) findViewById(R.id.result_3_format_4);
		result_2_format_4 = (ImageView) findViewById(R.id.result_2_format_4);
		result_1_format_4 = (ImageView) findViewById(R.id.result_1_format_4);
		
		
		
		
		
		
		
		
		
		
		
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

		info.setOnTouchListener(new OnTouchListener() {
			
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
		    	  startActivity(new Intent(QuestionsActivity.this, InfoActivity.class));
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
		
		favourites.setOnTouchListener(new OnTouchListener() {
			
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
		    	  final Dialog dialog = new FavouriteDialog(QuestionsActivity.this);
		    	  dialog.show();
				
		     
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
		
		home.setOnTouchListener(new OnTouchListener() {
			
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
	
}
