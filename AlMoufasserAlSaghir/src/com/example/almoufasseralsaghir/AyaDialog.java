package com.example.almoufasseralsaghir;

import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.MySuperScaler;

public class AyaDialog extends Dialog{

	public AyaDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setContentView(R.layout.eya_dialog);

		RelativeLayout popup_view = (RelativeLayout) findViewById(R.id.eya_layout);
		popup_view.getLayoutParams().width = 1422;
		popup_view.getLayoutParams().height = 800;
		MySuperScaler.scaleViewAndChildren(popup_view, MySuperScaler.scale);

		Button eya_dialog_previous = (Button) findViewById(R.id.eya_dialog_previous);

		final Button eya_dialog_network = (Button) findViewById(R.id.eya_network);
		final Button eya_dialog_copy = (Button) findViewById(R.id.eya_copy);
		final Button eya_dialog_fav = (Button) findViewById(R.id.eya_favourite);
		final Button eya_dialog_listen = (Button) findViewById(R.id.eya_listen);
		final Button eya_dialog_maana = (Button) findViewById(R.id.eya_maana);
		final Button eya_dialog_mofradat = (Button) findViewById(R.id.eya_mofradat);

		final FontFitTextView eya_dialog_repetitions = (FontFitTextView) findViewById(R.id.eya_dialog_repetitions);

		eya_dialog_repetitions.setText(String.valueOf(EyetPlayerActivity.repetitions_aya_nbr));

		eya_dialog_network.setOnTouchListener(new OnTouchListener() {

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


		eya_dialog_copy.setOnTouchListener(new OnTouchListener() {

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

		eya_dialog_fav.getBackground().clearColorFilter();
		eya_dialog_fav.setOnTouchListener(new OnTouchListener() {

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
					eya_dialog_fav.setBackgroundResource(R.drawable.eya_dialog_favourite_active);

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


		eya_dialog_listen.setOnTouchListener(new OnTouchListener() {

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
					if (EyetPlayerActivity.repetitions_aya_nbr < 3) 
						EyetPlayerActivity.repetitions_aya_nbr++ ;
					else EyetPlayerActivity.repetitions_aya_nbr = 1 ;

					eya_dialog_repetitions.setText(String.valueOf(EyetPlayerActivity.repetitions_aya_nbr));

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


		eya_dialog_maana.setOnTouchListener(new OnTouchListener() {

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


		eya_dialog_mofradat.setOnTouchListener(new OnTouchListener() {

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

		eya_dialog_previous.setOnTouchListener(new OnTouchListener() {

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

					dismiss();
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
