package com.example.almoufasseralsaghir;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.ConfirmationDialog;
import com.almoufasseralsaghir.utils.IClickCustomListener;
import com.almoufasseralsaghir.utils.ImageAdapter;
import com.almoufasseralsaghir.utils.SanabilActivity;


public class MainActivity extends SanabilActivity  implements IClickCustomListener{

	private Button  register_enter, login_btn, new_user,
					deconnect, account_settings, settings;
	private RelativeLayout register_interface, logged_in_interface ;
	private EditText email_login ;
	private FontFitTextView name_logged_in ;
	private ListView listViewArticles;
	ImageView herbes, email_hint ;
	private ConfirmationDialog exitDialog ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	herbes = (ImageView) findViewById(R.id.herbes);
	herbes.bringToFront();
	
	register_enter = (Button) findViewById(R.id.register);
	
	register_interface = (RelativeLayout) findViewById(R.id.register_interface);
	login_btn = (Button) findViewById(R.id.login_btn);
	new_user = (Button) findViewById(R.id.new_user_btn);
	
	email_login = (EditText) findViewById(R.id.mail_login);
	email_hint = (ImageView) findViewById(R.id.hint_login);
	
	logged_in_interface = (RelativeLayout) findViewById(R.id.logged_in);
	deconnect = (Button) findViewById(R.id.deconnect);
	account_settings = (Button) findViewById(R.id.account_settings);
	settings = (Button) findViewById(R.id.settings);
	
	name_logged_in = (FontFitTextView) findViewById(R.id.welcome_logged_name);
	

	name_logged_in.setText("Sedki Trimech");
//	name_logged_in.resizeText(600,200);
	
	
	register_enter.setVisibility(View.VISIBLE);
	register_interface.setVisibility(View.INVISIBLE);
	logged_in_interface.setVisibility(View.INVISIBLE);
	
	///////////////FIRST HEADER VIEW : ENTER/////////////////////////////////////////////////////////
	
	register_enter.setOnTouchListener(new OnTouchListener() {
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
	    	  register_enter.setVisibility(View.GONE);
				register_interface.setVisibility(View.VISIBLE);
				register_interface.bringToFront();
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////   SECOND HEADER VIEW : LOGIN    /////////////////////////////////////////////////////////
	
	
	email_login.setOnFocusChangeListener(new OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {

			if (hasFocus) email_hint.setVisibility(View.INVISIBLE);
			else 
				if(email_login.getText().toString().equals(""))
				email_hint.setVisibility(View.VISIBLE);
		}
	});
	
	
	login_btn.setOnTouchListener(new OnTouchListener() {
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
				register_interface.setVisibility(View.GONE);
				logged_in_interface.setVisibility(View.VISIBLE);
				logged_in_interface.bringToFront();
	    	  
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
	
	
	
	new_user.setOnTouchListener(new OnTouchListener() {
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
				startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
	    		//		Utils.animateFad(RegistrationActivity.this);
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
///////////////   THIRD HEADER VIEW : LOGGED IN    /////////////////////////////////////////////////////////
	
		deconnect.setOnTouchListener(new OnTouchListener() {
		
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
	//    	  name_logged_in.setText("");
	    	  logged_in_interface.setVisibility(View.GONE);
	    	  register_interface.setVisibility(View.GONE);
	    	  register_enter.setVisibility(View.VISIBLE);
	    	  register_enter.bringToFront();
	    	  
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
	
	
	account_settings.setOnTouchListener(new OnTouchListener() {
		
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
				startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
	
// SHOULD BE IMPLEMENTED WITH INTENT PUT EXTRA    		
				
				//		Utils.animateFad(RegistrationActivity.this);
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
	
		settings.setOnTouchListener(new OnTouchListener() {
		
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
	    	  final Dialog dialog = new Dialog(MainActivity.this);
	    	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
	    	  dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	    	  dialog.setContentView(R.layout.main_dialog_settings);
	    	  dialog.setCancelable(true);
	    	  
	    	  RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.popup_main);
	    	  popup_view.getLayoutParams().height = 521;
	    	  popup_view.getLayoutParams().width = 847;
	    	  SanabilActivity.scaleViewAndChildren(popup_view, SanabilActivity.scale);
	    	  
	    	  Button popup_confirm = (Button) dialog.findViewById(R.id.popup_confirm);
	    	  final Button popup_reader1 = (Button) dialog.findViewById(R.id.popup_active_btn);
	    	  final Button popup_reader2 = (Button) dialog.findViewById(R.id.popup_inactive_btn);
	    	  
	    	  popup_reader1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					popup_reader1.setBackgroundResource(R.drawable.popup_active_btn);
					popup_reader2.setBackgroundResource(R.drawable.popup_inactive_btn);
   
	////////////////////// SET READER 2 IN MY APPLICATION //////////////////////////////////////////////////////////////////			
	
				}
	    	  });
	    	  
	    	  popup_reader2.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						popup_reader1.setBackgroundResource(R.drawable.popup_inactive_btn);
						popup_reader2.setBackgroundResource(R.drawable.popup_active_btn);
	
	////////////////////// SET READER 2 IN MY APPLICATION //////////////////////////////////////////////////////////////////			
						
					}
		    	  });
	    	  
	    	  popup_confirm.setOnTouchListener(new OnTouchListener() {
	    			
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
	    
	   ////////////////////// SET READER IN MYAPPLICATION //////////////////////////////////////////////////////////////////
	    		    	  
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
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////

	
///////////////   LIST VIEW : HANDLING    /////////////////////////////////////////////////////////
	
	
	Integer[] lListIcone={R.drawable.list_1 , R.drawable.list_2 ,R.drawable.list_3 ,R.drawable.list_4 };
	ArrayAdapter<Integer> adapter = new ImageAdapter(this, R.layout.rowlv_module, lListIcone);

	listViewArticles = (ListView) findViewById(R.id.listView1);
	listViewArticles.setAdapter(adapter);
	listViewArticles.setDivider(null);

	listViewArticles.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			switch (position) {
			case 0:
//				startActivity(new Intent(ActivityModules.this, M8_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 1:
//				startActivity(new Intent(ActivityModules.this, M9_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 2:
//				startActivity(new Intent(ActivityModules.this, M10_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			case 3:
//				startActivity(new Intent(ActivityModules.this, M11_Home.class));
//				Utils.animateFad(ActivityModules.this);
//				finish();
				break;
			default:
				break;
			}
		}
		
	});
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
}
	public void correctWidth(TextView textView, int desiredWidth)
	{
	    Paint paint = new Paint();
	    Rect bounds = new Rect();

	    paint.setTypeface(textView.getTypeface());
	    float textSize = textView.getTextSize();
	    paint.setTextSize(textSize);
	    String text = textView.getText().toString();
	    paint.getTextBounds(text, 0, text.length(), bounds);

	    while (bounds.width() > desiredWidth)
	    {
	        textSize--;
	        paint.setTextSize(textSize);
	        paint.getTextBounds(text, 0, text.length(), bounds);
	    }

	    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
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
