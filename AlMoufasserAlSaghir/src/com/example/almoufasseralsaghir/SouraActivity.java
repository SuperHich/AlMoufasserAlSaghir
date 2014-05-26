package com.example.almoufasseralsaghir;

import java.util.ArrayList;
import java.util.List;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.SwipeListView;
import com.almoufasseralsaghir.utils.SwipeListView.SwipeListViewCallback;
import com.almoufasseralsaghir.utils.Utils;
import com.almoufasseralsaghir.wheelview.AbstractWheelTextAdapter;
import com.almoufasseralsaghir.wheelview.OnWheelScrollListener;
import com.almoufasseralsaghir.wheelview.WheelView;
import com.example.almoufasseralsaghir.entity.Sura;

@SuppressLint("NewApi")
public class SouraActivity extends MySuperScaler {

	private ImageView herbes, soura_part_num ;
	private Button info, favourites, previous, home, soura_parts_btn ;
	private Button questions, calendar, mostafad, maana, player ;
	private ImageView soura_title ;
	private RelativeLayout parts_btn_layout ;
	boolean dialog_enter = false ;  boolean scrolling = false;
	
	private Sura currentSura;
	private int partsNumber = 1;
	
	private Button mostafad_previous ;
	private WebView mostafad_content ;
	
	private Button maana_previous ;
	private WebView maana_content ;
	
	private MyAdapter m_Adapter;
	
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
		
		partsNumber = myDB.getPartNumber(currentSura.getSuraId());
		
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
				    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
				    	  
				    	  Button popup_confirm = (Button) dialog.findViewById(R.id.popup_soura_ok_btn);
				    	  
///////////////////////// WHEEL IMPLEMENTATION  ////////////////////////////////////////////////////////////////////				    	  
				    	  
				    	  final WheelView mySouraParts = (WheelView) dialog.findViewById(R.id.parts_wheel);
				    	  mySouraParts.setVisibleItems(3);
				    	  mySouraParts.setViewAdapter(new MyWheelAdapter(SouraActivity.this, getPartsDrawable(partsNumber)));  
				    	  
//				    	  View inflatedView = dialog.getLayoutInflater().inflate(R.layout.s_part_layout, null);
//				    	  
//				    	  RelativeLayout soura_part_layout = (RelativeLayout) inflatedView.findViewById(R.id.soura_part_layout);
//				    	  SanabilActivity.scaleViewAndChildren( soura_part_layout, SanabilActivity.scale);
				    	  
//				    	  mySouraParts.setCurrentItem(mTafseerManager.getCurrentSuraPart());
				    	  
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
//		    	  mostafad_content.getSettings().setJavaScriptEnabled(true);
		    	 
		    	  new AsyncTask<Void, String, String>() {

						@Override
						protected String doInBackground(Void... params) {

							Log.i("mostafad_content", currentSura.getSuraId() + " ... " + (mTafseerManager.getCurrentSuraPart()+1));
							String data = myDB.getPartsMoustafad(currentSura.getSuraId(), (mTafseerManager.getCurrentSuraPart()+1));
							Log.i("mostafad_content", data);

							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							
							if(data != null)
							{	
								mostafad_content.loadData("<html><body>"+data+"</body></html>", "text/html; charset=UTF-8", null);

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

							Log.i("mostafad_content", currentSura.getSuraId() + " ... " + (mTafseerManager.getCurrentSuraPart()+1));
							
							String data = myDB.getPartsInfo(currentSura.getSuraId(), (mTafseerManager.getCurrentSuraPart()+1));
							Log.i("maana_content", data);

							return data;
						}

						@Override
						protected void onPostExecute(String data) {
							
							if(data != null)
							{	
								 maana_content.loadData("<html><body>"+data+"</body></html>", "text/html; charset=UTF-8", null);

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
		    	  final Dialog dialog = new Dialog(SouraActivity.this);
		    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		    	  dialog.setContentView(R.layout.favourites);
		    	  
		    	  dialog.setCancelable(true);
		    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.favourites_layout);
		    	  popup_view.getLayoutParams().width = 1155;
		    	  popup_view.getLayoutParams().height = 800;
		    	  MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);
		    	  
		    	  
		    	  Button set_fav = (Button) dialog.findViewById(R.id.set_favorite);
		    	  final ListView fav_list = (ListView) dialog.findViewById(R.id.favourites_listView);
		    	  fav_list.setDivider(null);
		    	  
		    	  SwipeListViewCallback mySwipeListViewCallback = new SwipeListViewCallback() {
		    		  
		    		  @Override
		    		  public void onSwipeItem(boolean isRight, int position) {
		    			  m_Adapter.onSwipeItem(isRight, position);
		    		  }
		    		  
		    		  @Override
		    		  public void onItemClickListener(ListAdapter adapter, int position) {
		    		  }
		    		  
		    		  @Override
		    		  public ListView getListView() {
		    		   return fav_list;
		    		  }
		    		 };
		    		 
		    	SwipeListView l = new SwipeListView(SouraActivity.this, mySwipeListViewCallback);
		    	l.exec();
				m_Adapter = new MyAdapter();
				fav_list.setAdapter(m_Adapter);
				
				set_fav.setOnTouchListener(new OnTouchListener() {
					
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

				    	  m_Adapter.addItem("Item Item Item Item Item ");
				      
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
	
////////////////////////////// Favourite SwipeToDelete  ADAPTER///////////////////////////////////////////////
	
	public class MyAdapter extends BaseAdapter {

		protected List<String> m_List;
		private final int INVALID = -1;
		protected int DELETE_POS = -1;

		public MyAdapter() {
			// TODO Auto-generated constructor stub
			m_List = new ArrayList<String>();
		}

		public void addItem(String item) {
			m_List.add(item);
			notifyDataSetChanged();
		}

		public void addItemAll(List<String> item) {
			//
			m_List.addAll(item);
			notifyDataSetChanged();
		}

		public void onSwipeItem(boolean isRight, int position) {
			// TODO Auto-generated method stub
			if (isRight == false) {
				DELETE_POS = position;
			} else if (DELETE_POS == position) {
				DELETE_POS = INVALID;
			}
			//
			notifyDataSetChanged();
		}

		public void deleteItem(int pos) {
			//
			m_List.remove(pos);
			DELETE_POS = INVALID;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return m_List.size();
		}

		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return m_List.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = LayoutInflater.from(SouraActivity.this).inflate(
						R.layout.favourite_list_row, null);
			}
			FontFitTextView text = ViewHolderPattern.get(convertView, R.id.reminder_time);
			Button delete = ViewHolderPattern.get(convertView, R.id.swipe_delete);
			if (DELETE_POS == position) {
				delete.setVisibility(View.VISIBLE);
			} else
				delete.setVisibility(View.GONE);
			delete.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteItem(position);
				}
			});

			text.setText(getItem(position));

			return convertView;
		}
	}

	public static class ViewHolderPattern {
		@SuppressWarnings("unchecked")
		public static <T extends View> T get(View view, int id) {
			SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
			if (viewHolder == null) {
				viewHolder = new SparseArray<View>();
				view.setTag(viewHolder);
			}
			View childView = viewHolder.get(id);
			if (childView == null) {
				childView = view.findViewById(id);
				viewHolder.put(id, childView);
			}
			return (T) childView;
		}
	}
	

}
