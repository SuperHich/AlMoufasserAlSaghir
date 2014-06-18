package com.almoufasseralsaghir;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.entity.Sura;
import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.almoufasseralsaghir.wheelview.AbstractWheelTextAdapter;
import com.almoufasseralsaghir.wheelview.OnWheelScrollListener;
import com.almoufasseralsaghir.wheelview.WheelView;

@SuppressLint("NewApi")
public class SouraActivity extends MySuperScaler {

	private ImageView herbes, soura_part_num ;
	private Button info, favourites, previous, home, soura_parts_btn ;
	private Button questions, calendar, mostafad, maana, player ;
	private ImageView soura_title ;
	private RelativeLayout parts_btn_layout, principal_layout;
	boolean dialog_enter = false ;  boolean scrolling = false;
	
	private Sura currentSura;
	private int partsNumber = 1;
	
	private Button mostafad_previous ;
	private WebView mostafad_content ;
	
	private Button maana_previous ;
	private WebView maana_content ;
	
	public static Activity soura_act ;
	
	int currentPart = 0;
	
	private static final int ALL_PARTS_DRAWABLE[] =
            new int[] {R.drawable.popup_soura_part1,R.drawable.popup_soura_part2,R.drawable.popup_soura_part3,R.drawable.popup_soura_part4 
				,R.drawable.popup_soura_part5 ,R.drawable.popup_soura_part6 ,R.drawable.popup_soura_part7 };
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soura_activity);
		
		soura_act = this ;
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
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
		
		
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			int remSuraId = extras.getInt(TafseerManager.SURA_ID);
			int remPartNb = extras.getInt(TafseerManager.PART_NB);
			
			currentSura = mTafseerManager.getSuraById(remSuraId);
			int drawableResourceId = SouraActivity.this.getResources().getIdentifier("e5_soura_part_"+remPartNb, "drawable", SouraActivity.this.getPackageName());
	        soura_part_num.setBackgroundResource(drawableResourceId);
			
			if(currentSura == null)
				finish();
		}else
		{
			int s_position = mTafseerManager.getCurrentSura();
			int quran_part_num = mTafseerManager.getCurrentJuz2();
			
			currentPart = mTafseerManager.getCurrentSuraPart() - 1;
			
			int drawableResourceId = SouraActivity.this.getResources().getIdentifier("e5_soura_part_"+(currentPart+1), "drawable", SouraActivity.this.getPackageName());
	        soura_part_num.setBackgroundResource(drawableResourceId);
			
			currentSura = mTafseerManager.getSouraLabel(quran_part_num, s_position) ;
		}
			
		int drawablepartId = SouraActivity.this.getResources().getIdentifier("e5_soura_part_1", "drawable", SouraActivity.this.getPackageName());
        soura_part_num.setBackgroundResource(drawablepartId);
		
		
        Bitmap soura_title_bitmap = BitmapFactory.decodeFile(TafseerManager.SuraPath+"e5_title_sourat_"+currentSura.getLabel()+ ".png");
        Drawable soura_title_drawable = new BitmapDrawable(getResources(),soura_title_bitmap);
        
        
	//	int drawableResourceId = this.getResources().getIdentifier("e5_title_sourat_"+currentSura.getLabel(), "drawable", this.getPackageName());
		soura_title.setBackgroundDrawable(soura_title_drawable);
		
		
		
		
		
		
		
		
		
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
				    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
				    	  
				    	  Button popup_confirm = (Button) dialog.findViewById(R.id.popup_soura_ok_btn);
				    	  
///////////////////////// WHEEL IMPLEMENTATION  ////////////////////////////////////////////////////////////////////				    	  
				    	  
				    	  final WheelView mySouraParts = (WheelView) dialog.findViewById(R.id.parts_wheel);
				    	  mySouraParts.setVisibleItems(3);
				    	  mySouraParts.setViewAdapter(new MyWheelAdapter(SouraActivity.this, getPartsDrawable(partsNumber)));  
				    	  
				    	  
				    	  mySouraParts.setCurrentItem(currentPart);
				    	  
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
				    		    	  currentPart = mySouraParts.getCurrentItem();
//				    		    	  mTafseerManager.setCurrentSuraPart(currentPart+1);
				    		    	  dialog.dismiss();
				    		    	  objectAnimator.reverse();
				    		    	  
				    		    	  checkQuestionAvailability();
				    		    	  
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
		    	  Intent in;
		    	  
		    	  String elementId = myDB.getElementID(currentSura.getSuraId(), currentPart+1);
		    	  if(myDB.getElementLocatedStatus(elementId) && myDB.getElementStatus(elementId)==1){
		    		  in = new Intent(SouraActivity.this, ElementBuilderActivity.class);
		    	  }else
		    		  in = new Intent(SouraActivity.this, QuestionsActivity.class);
		    	  
		    	  in.putExtra("suraId", currentSura.getSuraId());
		    	  in.putExtra("partNb", currentPart+1);
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
		    	  in.putExtra("suraId", currentSura.getSuraId());
		    	  in.putExtra("partNb", currentPart+1);
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
		    	  final Dialog dialog = new Dialog(SouraActivity.this);
		    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		    	  dialog.setContentView(R.layout.mostafad_screen);
		    	  
		    	  dialog.setCancelable(true);
		    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.mostafad_layout);
		    	  popup_view.getLayoutParams().width = 1422;
		    	  popup_view.getLayoutParams().height = 800;
		    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
		    	  
///////////////////   MOSTAFAD CONTENT //////////////////////////////////////////////////////////////////		    	  
		    	  mostafad_content = (WebView) dialog.findViewById(R.id.mostafad_content);
		    	  mostafad_content.setBackgroundColor(0x00000000);
		    	  
		    	  new AsyncTask<Void, String, String>() {

						@Override
						protected String doInBackground(Void... params) {
							
							String data = myDB.getPartsMoustafad(currentSura.getSuraId(), currentPart+1);

							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							
							String style = "<style type=\"text/css\">body {font-size: 27px;margin: 10px; direction: rtl;  text-align: justify; }</style>";
							
							String new_data = restructureData(data);
							
							Log.i("OLD mostafad_content", data);
							Log.i("mostafad_content", new_data);
							
							if(data != null)
							{	
								mostafad_content.loadData("<html><head>"+style+"</head><body>"+new_data+"</body></html>", "text/html; charset=UTF-8", null);

							}else{ 
								mTafseerManager.showPopUp(SouraActivity.this, R.string.error_try_again);
							}
						}

					}.execute();
///////////////////////////////////////////////////////////////////////////////////////////////////		    	  
		    	 
		    	  mostafad_previous = (Button) dialog.findViewById(R.id.mostafad_previous);
		    	  mostafad_previous.bringToFront();
		    	  mostafad_previous.setOnTouchListener(new OnTouchListener() {
		    			
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
		    	  final Dialog dialog = new Dialog(SouraActivity.this);
		    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		    	  dialog.setContentView(R.layout.soura_maana_screen);
		    	  
		    	  dialog.setCancelable(true);
		    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.maana_layout);
		    	  popup_view.getLayoutParams().width = 1422;
		    	  popup_view.getLayoutParams().height = 800;
		    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
		    	  
///////////////////   MAANA CONTENT //////////////////////////////////////////////////////////////////		    	  
		    	  maana_content = (WebView) dialog.findViewById(R.id.maana_content);
		    	  maana_content.setBackgroundColor(0x00000000);
		    	  
		    	  new AsyncTask<Void, String, String>() {

						@Override
						protected String doInBackground(Void... params) {

							String data = myDB.getPartsInfo(currentSura.getSuraId(), currentPart+1);
							Log.i("maana_content", data);

							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							String style = "<style type=\"text/css\">body {font-size: 27px;margin: 10px; direction: rtl;  text-align: justify; }</style>";
		
									
							if(data != null)
							{	
								 maana_content.loadData("<html><head>"+style+"</head><body>"+data+"</body></html>", "text/html; charset=UTF-8", null);

							}else{ 
								mTafseerManager.showPopUp(SouraActivity.this, R.string.error_try_again);
							}
						}

					}.execute();
		    	  
///////////////////////////////////////////////////////////////////////////////////////////////////		    	  
		    	 
		    	  maana_previous = (Button) dialog.findViewById(R.id.maana_previous);
		    	  maana_previous.bringToFront();
		    	  maana_previous.setOnTouchListener(new OnTouchListener() {
		    			
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
					
		    	  Intent intent = new Intent(SouraActivity.this, EyetPlayerActivity.class);
		    	  intent.putExtra("suraId", currentSura.getSuraId());
		    	  intent.putExtra("partNb", currentPart+1);
		    	  startActivity(intent);
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

		    	  startActivity(new Intent(SouraActivity.this, HomeLoggedIn.class));
					Utils.animateFad(SouraActivity.this);
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
		    	  if (myDB.whoIsLoggedIn().isLoggedIn()) MainActivity.first_entry = false ;
					startActivity(new Intent(SouraActivity.this, MainActivity.class));
					Utils.animateFad(SouraActivity.this);
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
		    	  final Dialog dialog = new FavouriteDialog(SouraActivity.this);
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
		
	}

	@Override
	protected void onStart() {
		super.onStart();

		if(isFirstStart){
			partsNumber = myDB.getPartNumber(currentSura.getSuraId());

			checkQuestionAvailability();

			isFirstStart = false;
		}
	}
	
	private void checkQuestionAvailability() {
		
		if(!myDB.whoIsLoggedIn().isLoggedIn()){
			questions.setEnabled(false);
			questions.getBackground().setColorFilter(0x77d0d0d0, PorterDuff.Mode.SRC_ATOP);
			return;
		}
		
		if(!myDB.CheckQuizAvailability(currentSura.getSuraId(), currentPart+1))
		{
			questions.setEnabled(false);
			questions.getBackground().setColorFilter(0x77d0d0d0, PorterDuff.Mode.SRC_ATOP);
		}
		else {
			questions.setEnabled(true);
			questions.getBackground().clearColorFilter();
		}
	}

	private class MyWheelAdapter extends AbstractWheelTextAdapter {
		
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
	
	public String restructureData (String data){
		String new_data = data;

		if (data.contains("<div>")){

			String data_parts[] = data.split("<div>");

			data_parts[1] = "<div> •  "+ data_parts[1] ;
			for (int i = 2; i < data_parts.length;i++){

				data_parts[i] = "<div><br><br> •  "+ data_parts[i] ;
			}


			StringBuilder result = new StringBuilder();
			for (int i = 0; i < data_parts.length; i++) {
				if(!data_parts[i].equals("<div><br><br> •  ")) result.append( data_parts[i] );
			}
			new_data = result.toString();


		} 
		if (data.contains("<br />")){

			String data_parts[] = data.split("<br />");

			String first[] = data_parts[0].split("p>");
			first[1] = "p> •  "+first[1]+"<br><br>";

			StringBuilder result0 = new StringBuilder();
			for (int i = 0; i < first.length; i++) {
				result0.append( first[i] );
			}
			data_parts[0] = result0.toString();

			//		data_parts[0] = "<p> •  "+ data_parts[0] ;

			for (int i = 1; i < data_parts.length;i++){

				data_parts[i] = " • "+ data_parts[i] +  "<br><br>";
			}

			StringBuilder result = new StringBuilder();
			for (int i = 0; i < data_parts.length; i++) {
				result.append( data_parts[i] );
			}
			new_data = result.toString();
		}

		if (data.contains("<br/>")){
			String data_parts[] = data.split("<br />");

			String first[] = data_parts[0].split("p>");
			first[1] = "p> •  "+first[1]+"<br><br>";

			StringBuilder result0 = new StringBuilder();
			for (int i = 0; i < first.length; i++) {
				result0.append( first[i] );
			}
			data_parts[0] = result0.toString();

			//		data_parts[0] = "<p> •  "+ data_parts[0] ;

			for (int i = 1; i < data_parts.length;i++){

				data_parts[i] = " • "+ data_parts[i] +  "<br><br>";
			}

			StringBuilder result = new StringBuilder();
			for (int i = 0; i < data_parts.length; i++) {
				result.append( data_parts[i] );
			}
			new_data = result.toString();
		}
		return new_data;
	}	
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}

}
