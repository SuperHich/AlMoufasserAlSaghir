package com.example.almoufasseralsaghir;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.SanabilActivity;

public class SouraActivity extends SanabilActivity implements IClickCustomListener {

	private ImageView herbes ;
	private Button info, favourites, previous, home ;
	private ConfirmationDialog exitDialog ;
	
	String soura_name ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soura_activity);
		
		info = (Button) findViewById(R.id.e4_info);
		favourites = (Button) findViewById(R.id.e4_favourites);
		previous = (Button) findViewById(R.id.e4_previous);
		home = (Button) findViewById(R.id.e4_home);	
		herbes = (ImageView) findViewById(R.id.herbes);
		
		herbes.bringToFront();
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		Bundle extras = getIntent().getExtras();
		String q_part_number =(String) extras.get("quran_part");
		String soura_position =(String) extras.get("soura_position");
		
		int s_position = Integer.parseInt(soura_position) ;
		int part_num = Integer.parseInt(q_part_number) ;
		
		soura_name = TafseerManager.getSouraName(part_num, s_position) ;
		
		Toast.makeText(getApplicationContext(), soura_name, Toast.LENGTH_LONG).show();
		
		
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
