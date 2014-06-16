package com.example.almoufasseralsaghir;

import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.ImageAdapter;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.database.DownloadNotifier;
import com.example.almoufasseralsaghir.database.ReceiterDownloadManager;
import com.example.almoufasseralsaghir.entity.User;


public class MainActivity extends MySuperScaler implements DownloadNotifier{

	private Button  register_enter, login_btn, new_user,
	deconnect, account_settings, settings;
	private RelativeLayout register_interface, logged_in_interface, principal_layout ;
	private EditText email_login ;
	private FontFitTextView name_logged_in ;
	private ListView listViewArticles;
	private ImageView herbes, email_hint, welcomer ;
	private MediaPlayer welcome_mp, welcome_mp2 ;
	
	private SeekBar popup_progress;
	private LinearLayout download_layout;
	
	int i = 0 ;
	public static boolean first_entry = true;
	private String currentReceiter = "1";
	
	private ReceiterDownloadManager downloadManager;
	private Dialog dialog;
	
	public static boolean play_welcomer = false ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		downloadManager = new ReceiterDownloadManager(MainActivity.this);

		herbes = (ImageView) findViewById(R.id.herbes);
		welcomer = (ImageView) findViewById(R.id.welcomer);

		register_enter = (Button) findViewById(R.id.register);

		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
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


		//	name_logged_in.setText("Sedki Trimech");
		//	name_logged_in.resizeText(600,200);

//		name_logged_in.setText(mTafseerManager.getSavedUser().getName());

		herbes.bringToFront();



		///////////////  SOUND ANIMATION : GIF ALIKE /////////////////////////////////////////////////////////

		if (first_entry) {

			register_enter.setVisibility(View.VISIBLE);
			register_interface.setVisibility(View.INVISIBLE);
			logged_in_interface.setVisibility(View.INVISIBLE);

			if (play_welcomer){

				play_welcomer= false;
				
				welcome_mp = new MediaPlayer();
				welcome_mp = MediaPlayer.create(this, R.raw.welcome_msg_1);
				welcome_mp.start();

				new CountDownTimer(4000, 60) {

					public void onTick(long millisUntilFinished) {

						if (i == 6) i = 0;

						switch (i)  {
						case 0 : welcomer.setBackgroundResource(R.drawable.welcomer_1_modified);
						break ;
						case 1 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
						break ;
						case 2 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
						break ;
						case 3 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
						break ;
						case 4 : welcomer.setBackgroundResource(R.drawable.welcomer_3_modified);
						break ;
						case 5 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
						break ;
						}
						i++ ;
					}
					public void onFinish() {
						welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
					}
				}.start();


				welcome_mp.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.release();
						welcome_mp = MediaPlayer.create(MainActivity.this, R.raw.welcome_msg_2);
						welcome_mp.start();
						i = 0 ;
						new CountDownTimer(4000, 60) {

							public void onTick(long millisUntilFinished) {

								if (i == 6) i = 0;

								switch (i)  {
								case 0 : welcomer.setBackgroundResource(R.drawable.welcomer_1_modified);
								break ;
								case 1 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
								break ;
								case 2 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
								break ;
								case 3 : welcomer.setBackgroundResource(R.drawable.welcomer_4_modified);
								break ;
								case 4 : welcomer.setBackgroundResource(R.drawable.welcomer_3_modified);
								break ;
								case 5 : welcomer.setBackgroundResource(R.drawable.welcomer_2_modified);
								break ;
								}
								i++ ;
							}

							public void onFinish() {
								welcomer.setBackgroundResource(R.drawable.welcomer);
							}
						}.start();

					}
				});
			}


		} else {
			welcomer.setBackgroundResource(R.drawable.welcomer);
			if(mTafseerManager.getLoggedInUser().isLoggedIn())
			{
				register_enter.setVisibility(View.INVISIBLE);
				register_interface.setVisibility(View.INVISIBLE);
				logged_in_interface.setVisibility(View.VISIBLE);

				name_logged_in.setText(mTafseerManager.getLoggedInUser().getName());
			}else
			{
				register_enter.setVisibility(View.VISIBLE);
				register_interface.setVisibility(View.INVISIBLE);
				logged_in_interface.setVisibility(View.INVISIBLE);
			}

		}

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
					
					hideKeyboard();
					
					// Your action here on button click
					final ProgressDialog pd = new ProgressDialog(MainActivity.this);

					if (Utils.isOnline(getApplicationContext())){
						new AsyncTask<Void, Boolean, Boolean>() {

							@Override
							protected void onPreExecute() {
								register_interface.setVisibility(View.GONE);
								pd.setCancelable(false);
								pd.setIndeterminate(true);
								pd.show();
							}

							@Override
							protected Boolean doInBackground(Void... params) {

								String email = email_login.getText().toString();
								JSONObject result = null;
								if(email != null){
									result = mTafseerManager.loginUser(email);
								}	
								
								if(result != null)
								{
									myDB.login(email);
									
									User userLoggedIn = mTafseerManager.parseUser(result);
									if(userLoggedIn != null)
									{
										userLoggedIn.setLoggedIn(true);
										mTafseerManager.setLoggedInUser(userLoggedIn);
										
										if(!myDB.isUserExist(email))
											myDB.insertUser(userLoggedIn);
										else
											myDB.updateUser(userLoggedIn);
									}
									
								}
								
								return result != null;
							}

							@Override
							protected void onPostExecute(Boolean result) {
								pd.dismiss();
								if(result)
								{	
//									User userLoggedIn = mTafseerManager.parseUser(result);
//									if(userLoggedIn != null){
////										mTafseerManager.saveUser(userLoggedIn);
									
										
										name_logged_in.setText(mTafseerManager.getLoggedInUser().getName());
										logged_in_interface.setVisibility(View.VISIBLE);
										logged_in_interface.bringToFront();
//									}

								}else{ 
									register_interface.setVisibility(View.VISIBLE);
									mTafseerManager.showPopUp(MainActivity.this, R.string.error_try_again);
								}
							}

						}.execute();
					} else {
						mTafseerManager.showPopUp(MainActivity.this, R.string.error_internet_connexion);
					}
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
					
					hideKeyboard();
					
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
					
					myDB.logOut(mTafseerManager.getLoggedInUser().getEmail());
					mTafseerManager.setLoggedInUser(new User());

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
					Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
					intent.putExtra("update", true);
					startActivity(intent);

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
					dialog = new Dialog(MainActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
					dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
					dialog.setContentView(R.layout.main_dialog_settings);
					dialog.setCancelable(true);

					RelativeLayout popup_view = (RelativeLayout) dialog.findViewById(R.id.popup_main);
					popup_view.getLayoutParams().height = 521;
					popup_view.getLayoutParams().width = 847;
					MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);

					download_layout = (LinearLayout) dialog.findViewById(R.id.download_layout);
					popup_progress = (SeekBar) dialog.findViewById(R.id.popup_progress);
					final Button popup_cancel = (Button) dialog.findViewById(R.id.popup_cancel);
					
					ShapeDrawable thumb = new ShapeDrawable(new RectShape());
				    thumb.getPaint().setColor(Color.rgb(0, 0, 0));
				    thumb.setIntrinsicHeight(-80);
				    thumb.setIntrinsicWidth(30);
				    popup_progress.setThumb(thumb);
				    popup_progress.setMax(100);
				    
					Button popup_confirm = (Button) dialog.findViewById(R.id.popup_confirm);
					final Button popup_reader1 = (Button) dialog.findViewById(R.id.popup_active_btn);
					final Button popup_reader2 = (Button) dialog.findViewById(R.id.popup_inactive_btn);
					
					currentReceiter = mTafseerManager.getLoggedInUser().getDefaultReciter();
					if(currentReceiter.equals("1"))
					{
						popup_reader1.setBackgroundResource(R.drawable.popup_active_btn);
						popup_reader2.setBackgroundResource(R.drawable.popup_inactive_btn);
					}
					else
					{
						popup_reader1.setBackgroundResource(R.drawable.popup_inactive_btn);
						popup_reader2.setBackgroundResource(R.drawable.popup_active_btn);
					}

					popup_reader1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							if(downloadManager.isDownloading() || downloadManager.isUnzipping())
								return;
							
							popup_reader1.setBackgroundResource(R.drawable.popup_active_btn);
							popup_reader2.setBackgroundResource(R.drawable.popup_inactive_btn);
							
							currentReceiter = "1";
							

							////////////////////// SET READER 2 IN MY APPLICATION //////////////////////////////////////////////////////////////////			

						}
					});

					popup_reader2.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {

							if(downloadManager.isDownloading() || downloadManager.isUnzipping())
								return;
								
							popup_reader1.setBackgroundResource(R.drawable.popup_inactive_btn);
							popup_reader2.setBackgroundResource(R.drawable.popup_active_btn);
														
							if(downloadManager.initializeDownload())
							{
								download_layout.setVisibility(View.VISIBLE);
								dialog.setCancelable(false);
							}else
								currentReceiter = "2";
							
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

								if(!downloadManager.isDownloading() || downloadManager.isUnzipping())
								{	
									mTafseerManager.getLoggedInUser().setDefaultReciter(currentReceiter);
									myDB.setUserDefaultReciter(currentReceiter, mTafseerManager.getLoggedInUser().getUid());
									dialog.dismiss(); 
								}
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
					
					popup_cancel.setOnTouchListener(new OnTouchListener() {

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

								if(downloadManager.isDownloading())
								{
									downloadManager.cancelDownload();
									download_layout.setVisibility(View.GONE);
									dialog.setCancelable(true);
								}
								else if(downloadManager.isUnzipping())
								{
									Toast.makeText(MainActivity.this, R.string.wait, Toast.LENGTH_LONG).show();
//									downloadManager.cancelUnzip();
								}
								
								
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

				int position2 = 0;
				
				switch (position) {
				case 0 : position2 = 3;
				 break;
				case 1 : position2 = 2;
				 break;
				case 2 : position2 = 1;
				 break;
				case 3 : position2 = 0;
				 break;
				}
				 
				mTafseerManager.setCurrentJuz2(position2);

				switch (position) {
				case 0:
					Intent i1 = new Intent(MainActivity.this, HomeLoggedIn.class);
					startActivity(i1);
					Utils.animateSlide(MainActivity.this);
					finish();
					break;
				case 1:
					Intent i2 = new Intent(MainActivity.this, HomeLoggedIn.class);
					startActivity(i2);
					Utils.animateSlide(MainActivity.this);
					finish();
					break;
				case 2:
					Intent i3 = new Intent(MainActivity.this, HomeLoggedIn.class);
					startActivity(i3);
					Utils.animateSlide(MainActivity.this);
					finish();
					break;
				case 3:
					Intent i4 = new Intent(MainActivity.this, HomeLoggedIn.class);
					startActivity(i4);
					Utils.animateSlide(MainActivity.this);
					finish();
					break;
				default:
					break;
				}
			}

		});

		/////////////////////////////////////////////////////////////////////////////////////////////////////////


	}
	
	protected void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(email_login.getWindowToken(), 0);
	}

	@Override
	public void onProgressDownload(int progress) {
		popup_progress.setProgress(progress);
	}

	@Override
	public void onDownloadComplete() {
		downloadManager.setUnzipping(false);
		dialog.setCancelable(true);
		currentReceiter = "2";
		download_layout.setVisibility(View.GONE);
	}
	
	@Override
	public void configureProgress(int maxSize) {
		popup_progress.setMax(maxSize);
		popup_progress.setProgress(0);
		
	}
	
	@Override
	public void onErrorDownload() {
		
		mTafseerManager.showPopUp(this, R.string.error_download);
		downloadManager.cancelDownload();
		dialog.setCancelable(true);
		download_layout.setVisibility(View.GONE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}
}
