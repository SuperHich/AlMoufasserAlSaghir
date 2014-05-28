package com.example.almoufasseralsaghir;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

public class QuestionsActivity extends MySuperScaler {

	private Button info, favourites, previous, home ;
	
	private RelativeLayout myQuestionsBackground, results_format_3, results_format_4 ;
	private FontFitTextView answer_1, answer_2, answer_3, question ;
	private ImageView result_3_format_3, result_2_format_3, result_1_format_3,
					   result_4_format_4, result_3_format_4, result_2_format_4, result_1_format_4 ;
	
	private int answers_nbr = 0 ;
	private boolean format_4 = false;
	private boolean format_3 = false;
	private boolean answer ;
	
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
		
		
		selectformat();
		
		answer_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 if (format_3){
			    	  if (answers_nbr <3) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = false ;
			    	  indicatePlay(answer);
			    	  }
		    	  }
		    	  if (format_4){
			    	  if (answers_nbr <4) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = false ;
			    	  indicatePlay(answer);
			    	  }
			       }	
				
			}
		});
		answer_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 if (format_3){
			    	  if (answers_nbr <3) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = false ;
			    	  indicatePlay(answer);
			    	  }
		    	  }
		    	  if (format_4){
			    	  if (answers_nbr <4) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = false ;
			    	  indicatePlay(answer);
			    	  }
			       }
				
			}
		});
		answer_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (format_3){
			    	  if (answers_nbr <3) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = true ;
			    	  indicatePlay(answer);
			    	  }
		    	  }
		    	  if (format_4){
			    	  if (answers_nbr <4) 
			    	  {
			    	   answers_nbr++ ;
			    	  answer = true ;
			    	  indicatePlay(answer);
			    	  }
			       }	
				
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
	
	private void indicatePlay(boolean answer){
		
		if (format_4){
			switch (answers_nbr){
			case 1 : if (answer) result_1_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_1_format_4.setBackgroundResource(R.drawable.answer_false);
				break;
			case 2 : if (answer) result_2_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_2_format_4.setBackgroundResource(R.drawable.answer_false);
				break;
			case 3 : if (answer) result_3_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_3_format_4.setBackgroundResource(R.drawable.answer_false);
				break ;
			case 4 : if (answer) result_4_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_4_format_4.setBackgroundResource(R.drawable.answer_false);
				break ;
			
			}
		}
		if (format_3){
			switch (answers_nbr){
			case 1 : if (answer) result_1_format_3.setBackgroundResource(R.drawable.answer_correct);
			else  result_1_format_3.setBackgroundResource(R.drawable.answer_false);
				break;
			case 2 : if (answer) result_2_format_3.setBackgroundResource(R.drawable.answer_correct);
						else  result_2_format_3.setBackgroundResource(R.drawable.answer_false);
				break;
			case 3 :if (answer) result_3_format_3.setBackgroundResource(R.drawable.answer_correct);
						else  result_3_format_3.setBackgroundResource(R.drawable.answer_false);
				break ;
		}
		}
		
	}
	
	private void selectformat(){
		
	//	if( format 3)
		myQuestionsBackground.setBackgroundResource(R.drawable.questions_bg_format_3);
		format_3 = true ; format_4 = false ;
		results_format_3.setVisibility(View.VISIBLE);
		results_format_4.setVisibility(View.GONE);
	
		//	else	
		
//		myQuestionsBackground.setBackgroundResource(R.drawable.questions_bg_format_4);
//		format_4 = true ; format_3 = false ;
//		results_format_3.setVisibility(View.GONE);
//		results_format_4.setVisibility(View.VISIBLE);
	}
	
}
