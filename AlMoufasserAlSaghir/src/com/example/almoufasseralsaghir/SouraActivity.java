package com.example.almoufasseralsaghir;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.AlMoufasserActivity;
import com.almoufasseralsaghir.utils.Utils;
import com.almoufasseralsaghir.wheelview.AbstractWheelTextAdapter;
import com.almoufasseralsaghir.wheelview.OnWheelScrollListener;
import com.almoufasseralsaghir.wheelview.WheelView;
import com.example.almoufasseralsaghir.database.AlMoufasserDB;
import com.example.almoufasseralsaghir.entity.Sura;

@SuppressLint("NewApi")
public class SouraActivity extends AlMoufasserActivity {

	private ImageView herbes, soura_part_num ;
	private Button info, favourites, previous, home, soura_parts_btn ;
	private Button questions, calendar, mostafad, maana, player ;
	private ImageView soura_title ;
	private RelativeLayout parts_btn_layout ;
	boolean dialog_enter = false ;  boolean scrolling = false;
	
	private Sura currentSura;
	private int partsNumber = 1;
	
	private static final int ALL_PARTS_DRAWABLE[] =
            new int[] {R.drawable.popup_soura_part1,R.drawable.popup_soura_part2,R.drawable.popup_soura_part3,R.drawable.popup_soura_part4 
				,R.drawable.popup_soura_part5 ,R.drawable.popup_soura_part6 ,R.drawable.popup_soura_part7 };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soura_activity);
		
		soura_title = (ImageView) findViewById(R.id.soura_title);
		
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
		
		
		parts_btn_layout = (RelativeLayout) findViewById(R.id.parts_btn_layout);
		soura_parts_btn = (Button) findViewById(R.id.parts_btn);
		soura_part_num = (ImageView) findViewById(R.id.part_number);
		
		herbes.bringToFront();
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		
		
//		Bundle extras = getIntent().getExtras();
//		String q_part_number =(String) extras.get("quran_part");
//		String soura_position =(String) extras.get("soura_position");
		
//		int s_position = Integer.parseInt(soura_position) ;
//		int quran_part_num = Integer.parseInt(q_part_number) ;
		
		int s_position = mTafseerManager.getCurrentSura();
		int quran_part_num = mTafseerManager.getCurrentJuz2();
		
		currentSura = mTafseerManager.getSouraLabel(quran_part_num, s_position) ;
		
		AlMoufasserDB db = new AlMoufasserDB(this);
		partsNumber = db.getPartNumber(currentSura.getSuraId());
		
		int drawableResourceId = this.getResources().getIdentifier("e5_title_sourat_"+currentSura.getLabel(), "drawable", this.getPackageName());
		soura_title.setBackgroundResource(drawableResourceId);
		
		parts_btn_layout.bringToFront();
		
//////////////////////////////////////////////////////////////////////////////////////////////////////

		soura_parts_btn.setOnTouchListener(new OnTouchListener() {
			
			@Override
		    public boolean onTouch(View v, MotionEvent event) {
		      switch (event.getAction()) {
		      case MotionEvent.ACTION_DOWN: {
		          Button view = (Button) v;
		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
		          v.invalidate();
		          dialog_enter = true ;
		          break;
		      }
		      case MotionEvent.ACTION_UP: {
		    	// Your action here on button click
					
		    	  final ObjectAnimator objectAnimator= ObjectAnimator.ofFloat(parts_btn_layout, "translationY", 0, -150);
		    	  objectAnimator.setDuration(1000);
		    	  objectAnimator.start();
		    	 
		    	  
		    	  objectAnimator.addListener(new AnimatorListener() {
					@Override
					public void onAnimationStart(Animator animation) {
					}

					@Override
					public void onAnimationRepeat(Animator animation) {
					}
					
					@Override
					public void onAnimationCancel(Animator animation) {
						
					}
					
					@Override
					public void onAnimationEnd(Animator animation) {

///////////////////////////// WHEEL DIALOG ///////////////////////////////////////////////////////////		    	  
						
						if (dialog_enter){  
						
				    	  final Dialog dialog = new Dialog(SouraActivity.this);
				    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
				    	  dialog.setContentView(R.layout.dialog_soura_parts);
				    	  
				    	  dialog.setCancelable(true);
				    	  dialog.setOnCancelListener(new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								dialog.dismiss();
			    		    	objectAnimator.reverse();
			    		    	dialog_enter = false ;
							}
						});
				    	 
				    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.popup_soura_parts);
				    	  popup_view.getLayoutParams().width = 700;
				    	  popup_view.getLayoutParams().height = 429;
				    	  AlMoufasserActivity.scaleViewAndChildren(popup_view, AlMoufasserActivity.scale);
				    	  
				    	  Button popup_confirm = (Button) dialog.findViewById(R.id.popup_soura_ok_btn);
				    	  
///////////////////////// WHEEL IMPLEMENTATION  ////////////////////////////////////////////////////////////////////				    	  
				    	  
				    	  final WheelView mySouraParts = (WheelView) dialog.findViewById(R.id.parts_wheel);
				    	  mySouraParts.setVisibleItems(3);
				    	  mySouraParts.setViewAdapter(new MyWheelAdapter(SouraActivity.this, getPartsDrawable(partsNumber)));  
				    	  
//				    	  View inflatedView = dialog.getLayoutInflater().inflate(R.layout.s_part_layout, null);
//				    	  
//				    	  RelativeLayout soura_part_layout = (RelativeLayout) inflatedView.findViewById(R.id.soura_part_layout);
//				    	  SanabilActivity.scaleViewAndChildren( soura_part_layout, SanabilActivity.scale);
				    	  
				    	  mySouraParts.setCurrentItem(mTafseerManager.getCurrentSuraPart());
				    	  
				    	  mySouraParts.addScrollingListener( new OnWheelScrollListener() {
				              public void onScrollingStarted(WheelView wheel) {
				                  scrolling = true;
				              }
				              public void onScrollingFinished(WheelView wheel) {
				                  scrolling = false;
				                
		////ACTION//////
				          int drawableResourceId = SouraActivity.this.getResources().getIdentifier("e5_soura_part_"+(mySouraParts.getCurrentItem()+1), "drawable", SouraActivity.this.getPackageName());
				          soura_part_num.setBackgroundResource(drawableResourceId);
				          
				              }
				          });
				    	  
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				    	  
				    	  popup_confirm.setOnTouchListener(new OnTouchListener() {
				    			
				    			@Override
				    		    public boolean onTouch(View v, MotionEvent event) {
				    		      switch (event.getAction()) {
				    		      case MotionEvent.ACTION_DOWN: {
				    		          Button view = (Button) v;
				    		          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
				    		          v.invalidate();
				    		          dialog_enter = false ;
				    		          break;
				    		      }
				    		      case MotionEvent.ACTION_UP: {
				    		    	  
				    		    	  mTafseerManager.setCurrentSuraPart(mySouraParts.getCurrentItem());
				    		    	  dialog.dismiss();
				    		    	  objectAnimator.reverse();
				    		    	  
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
				    	  
				    	  dialog.show();
	    	  
///////////////////////////////////////////////////////////////////////////////////////////////////////	
						
					}
					}
					

				});
		    	  
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
		    	  Intent in = new Intent(SouraActivity.this, CalendarActivity.class);
					startActivity(in);
					Utils.animateFad(SouraActivity.this);
		    	  
		    	  
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
	
	private class MyWheelAdapter extends AbstractWheelTextAdapter {
		
	//	AlMoufasserDB db = new AlMoufasserDB(SouraActivity.this);
	//	String partInfo = db.getPartNumber(suraName);
		
		
		private int parts[];
        
        /**
         * Constructor
         */
        protected MyWheelAdapter(Context context, int[] parts) {
            super(context, R.layout.s_part_layout, NO_RESOURCE);
            this.parts = parts;
	    	
        }

        
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            ImageView img = (ImageView) view.findViewById(R.id.s_part);
            img.setImageResource(parts[index]);
            
            return view;
        }
        
        @Override
        public int getItemsCount() {
            return parts.length;
        }

		@Override
		protected CharSequence getItemText(int index) {
			return null;
		}
        
    }
	
	private int[] getPartsDrawable(int number){
		int[] parts = new int[number];
		for (int i = 0; i < number; i++) {
			parts[i] = ALL_PARTS_DRAWABLE[i];
		}
		
		return parts;
		
	}

}
