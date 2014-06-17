package com.almoufasseralsaghir;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.ImageAdapter;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.R;


public class HomeLoggedIn extends MySuperScaler{
		
	private ListView part1_listView;
	private ListView part2_listView;
	private ListView part3_listView;
	private ListView part4_listView;
	
	private Button info, favourites, previous, home ;
	private RelativeLayout part1, part2, part3, part4, principal_layout ;
	private ImageView herbes ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_logged_in);
		
		info = (Button) findViewById(R.id.e4_info);
		favourites = (Button) findViewById(R.id.e4_favourites);
		previous = (Button) findViewById(R.id.e4_previous);
		home = (Button) findViewById(R.id.e4_home);
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
		
		part1 = (RelativeLayout) findViewById(R.id.e4_part1);
		part2 = (RelativeLayout) findViewById(R.id.e4_part2);
		part3 = (RelativeLayout) findViewById(R.id.e4_part3);
		part4 = (RelativeLayout) findViewById(R.id.e4_part4);
		
		
		part1_listView = (ListView) findViewById(R.id.listView1);
		part2_listView = (ListView) findViewById(R.id.listView2);
		part3_listView = (ListView) findViewById(R.id.listView3);
		part4_listView = (ListView) findViewById(R.id.listView4);
		
		herbes = (ImageView) findViewById(R.id.herbes);
		
		herbes.bringToFront();
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
//		Bundle extras = getIntent().getExtras();
//		String p =(String) extras.get("part");
		int p = mTafseerManager.getCurrentJuz2();
		
		if (p == 0) {
			part1.setVisibility(View.VISIBLE);
			part2.setVisibility(View.INVISIBLE);
			part3.setVisibility(View.INVISIBLE);
			part4.setVisibility(View.INVISIBLE);
			
			part1.bringToFront();
			
/////////////////////////////////////// LIST de PARTIE 1 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Integer[] part1_list ={R.drawable.p1_s1_nabaa , R.drawable.p1_s2_naziaat ,R.drawable.p1_s3_abas ,R.drawable.p1_s4_takwir ,R.drawable.p1_s5_infitar,
			R.drawable.p1_s6_moutaffifin,R.drawable.p1_s7_inchikak,R.drawable.p1_s8_bourouj,R.drawable.p1_s9_tarek, R.drawable.p1_s10_aala,
			R.drawable.p1_s11_ghachia,R.drawable.p1_s12_fajr, R.drawable.p1_s13_balad, R.drawable.p1_s14_shams, R.drawable.p1_s15_lail,
			R.drawable.p1_s16_dhouha, R.drawable.p1_s17_inchirah, R.drawable.p1_s18_tin,R.drawable.p1_s19_alaq, R.drawable.p1_s20_kadar,
			R.drawable.p1_s21_bayyina,R.drawable.p1_s22_zalzala, R.drawable.p1_s23_adyet, R.drawable.p1_s24_quariaa, R.drawable.p1_s25_takathor,
			R.drawable.p1_s26_asr, R.drawable.p1_s27_homaza, R.drawable.p1_s28_fil, R.drawable.p1_s29_quraich,R.drawable.p1_s30_maaaoun,
			R.drawable.p1_s31_kawthar, R.drawable.p1_s32_kafiroun, R.drawable.p1_s33_nasr, R.drawable.p1_s34_masad, R.drawable.p1_s35_ikhlas,
			R.drawable.p1_s36_falaq, R.drawable.p1_s37_ness
			};
			ArrayAdapter<Integer> adapter = new ImageAdapter(this, R.layout.rowlv_module, part1_list);
			
			part1_listView.setAdapter(adapter);
			part1_listView.setDivider(null);
			part1_listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				mTafseerManager.setCurrentSura(position);			
				Intent in = new Intent(HomeLoggedIn.this, SouraActivity.class);
				startActivity(in);
				Utils.animateFad(HomeLoggedIn.this);
				finish();
			
			}
			
			});
			
///////////////////////////////////////////////////////////////////////////////////////////////////////			
			
		}
		if (p == 1) {
			part1.setVisibility(View.INVISIBLE);
			part2.setVisibility(View.VISIBLE);
			part3.setVisibility(View.INVISIBLE);
			part4.setVisibility(View.INVISIBLE);
			
			part2.bringToFront();
			
/////////////////////////////////////// LIST de PARTIE 2 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Integer[] part2_list ={R.drawable.p2_s1_molk , R.drawable.p2_s2_kalam ,R.drawable.p2_s3_hakka ,R.drawable.p2_s4_miaraj ,R.drawable.p2_s5_nouh,
					R.drawable.p2_s6_jinn,R.drawable.p2_s7_mouzammil,R.drawable.p2_s8_modathir,R.drawable.p2_s9_qayimat, R.drawable.p2_s10_insan,
					R.drawable.p2_s11_morsilat
			};
			ArrayAdapter<Integer> adapter2 = new ImageAdapter(this, R.layout.rowlv_module, part2_list);
			
			part2_listView.setAdapter(adapter2);
			part2_listView.setDivider(null);
			part2_listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
					mTafseerManager.setCurrentSura(position);
					Intent in = new Intent(HomeLoggedIn.this, SouraActivity.class);
					startActivity(in);
					Utils.animateFad(HomeLoggedIn.this);
					finish();
				}
				
			});
			
///////////////////////////////////////////////////////////////////////////////////////////////////////			
			
		}
		if (p == 2) {
			part1.setVisibility(View.INVISIBLE);
			part2.setVisibility(View.INVISIBLE);
			part3.setVisibility(View.VISIBLE);
			part4.setVisibility(View.INVISIBLE);
			
			part3.bringToFront();
			
/////////////////////////////////////// LIST de PARTIE 3 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Integer[] part3_list ={R.drawable.p3_s1_moujedla , R.drawable.p3_s2_hachr ,R.drawable.p3_s3_momtahina ,R.drawable.p3_s4_saff ,R.drawable.p3_s5_jomoa,
					R.drawable.p3_s6_mounafiqoun,R.drawable.p3_s7_thaabin,R.drawable.p3_s8_talek,R.drawable.p3_s9_tahrim
					};
					ArrayAdapter<Integer> adapter3 = new ImageAdapter(this, R.layout.rowlv_module, part3_list);
					
					part3_listView.setAdapter(adapter3);
					part3_listView.setDivider(null);
					part3_listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						mTafseerManager.setCurrentSura(position);
						Intent in = new Intent(HomeLoggedIn.this, SouraActivity.class);
						startActivity(in);
						Utils.animateFad(HomeLoggedIn.this);
						finish();
					
					}
					
					});	
			
///////////////////////////////////////////////////////////////////////////////////////////////////////			
			
		}
		if (p == 3) {
			part1.setVisibility(View.INVISIBLE);
			part2.setVisibility(View.INVISIBLE);
			part3.setVisibility(View.INVISIBLE);
			part4.setVisibility(View.VISIBLE);
			
			part4.bringToFront();
			
/////////////////////////////////////// LIST de PARTIE 4 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			Integer[] part4_list ={R.drawable.p4_s1_qaf , R.drawable.p4_s2_dhariet ,R.drawable.p4_s3_tour ,R.drawable.p4_s4_najm ,R.drawable.p4_s5_qamar,
					R.drawable.p4_s6_rahmen,R.drawable.p4_s7_waqiaa,R.drawable.p4_s8_hadid
					};
					ArrayAdapter<Integer> adapter4 = new ImageAdapter(this, R.layout.rowlv_module, part4_list);
					
					part4_listView.setAdapter(adapter4);
					part4_listView.setDivider(null);
					part4_listView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						mTafseerManager.setCurrentSura(position);
						Intent in = new Intent(HomeLoggedIn.this, SouraActivity.class);
						startActivity(in);
						Utils.animateFad(HomeLoggedIn.this);
						finish();
					}
						});	
			
///////////////////////////////////////////////////////////////////////////////////////////////////////			
			
		}
		
		
//////////////////////////////////// UTIL BUTTONS	////////////////////////////////////////////////////////////////////////	
		

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
		    	  startActivity(new Intent(HomeLoggedIn.this, InfoActivity.class));
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
		    	  final Dialog dialog = new FavouriteDialog(HomeLoggedIn.this);
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
		    	  if (myDB.whoIsLoggedIn().isLoggedIn())MainActivity.first_entry = false ;
		    	  startActivity(new Intent(HomeLoggedIn.this, MainActivity.class));
		    	  Utils.animateFad(HomeLoggedIn.this);
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
		    	// Your action here on button click
		    	  if (myDB.whoIsLoggedIn().isLoggedIn())MainActivity.first_entry = false ;
		    	  startActivity(new Intent(HomeLoggedIn.this, MainActivity.class));
		    	  Utils.animateFad(HomeLoggedIn.this);
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
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}
}