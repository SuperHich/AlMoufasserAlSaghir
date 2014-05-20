package com.example.almoufasseralsaghir;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.SanabilActivity;

public class SouraActivity extends SanabilActivity implements IClickCustomListener {

	private ImageView herbes, soura_part_num ;
	private Button info, favourites, previous, home, parts_btn ;
	private Button questions, calendar, mostafad, maana, player ;
	private ConfirmationDialog exitDialog ;
	private ImageView soura_title ;
	String soura_name ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soura_activity);
		
		info = (Button) findViewById(R.id.info);
		favourites = (Button) findViewById(R.id.favourites);
		previous = (Button) findViewById(R.id.previous);
		home = (Button) findViewById(R.id.home);	
		herbes = (ImageView) findViewById(R.id.herbes);
		
		questions = (Button) findViewById(R.id.questions);
		calendar = (Button) findViewById(R.id.calendar);
		mostafad = (Button) findViewById(R.id.mostafad);
		maana = (Button) findViewById(R.id.maana);
		player = (Button) findViewById(R.id.player);
		
		parts_btn = (Button) findViewById(R.id.parts);
		soura_part_num = (ImageView) findViewById(R.id.part_number);
		
		herbes.bringToFront();
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		parts_btn.bringToFront();
		soura_part_num.bringToFront();
		
		Bundle extras = getIntent().getExtras();
		String q_part_number =(String) extras.get("quran_part");
		String soura_position =(String) extras.get("soura_position");
		
		int s_position = Integer.parseInt(soura_position) ;
		int quran_part_num = Integer.parseInt(q_part_number) ;
		
		soura_name = TafseerManager.getSouraName(quran_part_num, s_position) ;
		
		Toast.makeText(getApplicationContext(), soura_name, Toast.LENGTH_LONG).show();

//////////////////////////////////////////////////////////////////////////////////////////////////////

		parts_btn.setOnTouchListener(new OnTouchListener() {
			
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
					
		    	  Animation animation = new TranslateAnimation(0, 0,0, -150);
		    	  animation.setDuration(1000);
		    	  animation.setFillAfter(true);
		    	  parts_btn.startAnimation(animation);
		    	  soura_part_num.startAnimation(animation);
		    	  
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		questions.setOnTouchListener(new OnTouchListener() {
			
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////	

		calendar.setOnTouchListener(new OnTouchListener() {
			
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		mostafad.setOnTouchListener(new OnTouchListener() {
			
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

//////////////////////////////////////////////////////////////////////////////////////////////////////

		maana.setOnTouchListener(new OnTouchListener() {
			
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

//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		player.setOnTouchListener(new OnTouchListener() {
			
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
		
//////////////////////////////////////////////////////////////////////////////////////////////////////

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
		    	  	MainActivity.first_entry = false ;
					startActivity(new Intent(SouraActivity.this, MainActivity.class));
					
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
		    	  startActivity(new Intent(SouraActivity.this, InfoActivity.class));
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
	
	public void onBackPressed() {
		 exitDialog();
	}
	public  void exitDialog() {
			exitDialog = new ConfirmationDialog(this,
					R.style.CustomDialogTheme, 
					 this);
			exitDialog.setCancelable(false);
			exitDialog.show();
		}
	@Override
	public void onClickYes() {
		exitDialog.dismiss();
		finish();
	}
	@Override
	public void onClickNo() {
		exitDialog.dismiss();		
	}

}
