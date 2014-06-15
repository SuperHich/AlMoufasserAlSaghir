package com.example.almoufasseralsaghir;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.almoufasseralsaghir.external.TafseerManager;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.RightHorizontalScrollView;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.entity.QuizElementToAdd;

public class ElementBuilderActivity extends MySuperScaler{
	
	private RelativeLayout all_buildings_layout, all_builds_imgs, layout_scroll;
	private ImageView puller;
	private SeekBar seek_buildings;
	private RelativeLayout principal_layout; 
	private RightHorizontalScrollView horizontal_scroll;
	private Button back, all_buildings;
	private ImageView img_draggable;
	
//	private AlMoufasserDB myDB;
//	private TafseerManager mTafseerManager;
	
	private ObjectAnimator objectAnimator;
	
	private boolean isScrollEnabled = false;
	private boolean isAllBuildingsShown = false;
	private int suraId;
	private int partNb;
	
	private QuizElementToAdd currentQuizElement;
	private ImageView imgToDrag;
	private RelativeLayout viewToDragIn, viewToDragFrom;
	
	private boolean isDragOk = false;
	private float newX;
	
	private Bitmap bmToDrag;
	
	private ArrayList<QuizElementToAdd> defectiveElements;
	private Animation mFlashingAnimation;
	private ProgressDialog progressDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.buildings_layout);
		
		if(getIntent().getExtras() != null){
			suraId = getIntent().getExtras().getInt("suraId");
			partNb = getIntent().getExtras().getInt("partNb");
		}
		
//		mTafseerManager = new TafseerManager(this);
//		myDB = new AlMoufasserDB(this);
		
		mFlashingAnimation = AnimationUtils.loadAnimation(ElementBuilderActivity.this, R.anim.flashing);
		
		principal_layout = (RelativeLayout) findViewById(R.id.principal_layout);
		horizontal_scroll = (RightHorizontalScrollView) findViewById(R.id.horizontal_scroll);
		layout_scroll = (RelativeLayout) findViewById(R.id.layout_scroll);
		back = (Button) findViewById(R.id.back);
		all_buildings = (Button) findViewById(R.id.all_buildings);
		img_draggable = (ImageView) findViewById(R.id.img_draggable);
		
		///// Bottom Layout
		all_buildings_layout = (RelativeLayout) findViewById(R.id.all_buildings_layout);
		all_builds_imgs = (RelativeLayout) findViewById(R.id.all_builds_imgs);
		puller = (ImageView) findViewById(R.id.puller);
		seek_buildings = (SeekBar) findViewById(R.id.seek_buildings);
		seek_buildings.setMax(all_buildings_layout.getLayoutParams().width);
//		seek_buildings.setMax(800);
		puller.bringToFront();
		
		seek_buildings.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				
//				int real_progress = getRealProgress(seekBar.getProgress());
//				horizontal_scroll.smoothScrollTo(real_progress, 0);
//				Log.v(""," "+ seekBar.getProgress() + " ... " + real_progress + " ... " + horizontal_scroll.getMaxScrollAmount() + " ... " + horizontal_scroll.getMeasuredWidth());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		
		back.bringToFront();
		all_buildings.bringToFront();
		
		back.setOnTouchListener(new OnTouchListener() {
			
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
		    	  if(isScrollEnabled){
		    		  isScrollEnabled = false;
		    		  all_buildings_layout.setVisibility(View.VISIBLE);
		    		  all_buildings.setVisibility(View.VISIBLE);
		    	  }else
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
		
		all_buildings.setOnTouchListener(new OnTouchListener() {
			
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
					isScrollEnabled = true;
					all_buildings_layout.setVisibility(View.GONE);
					all_buildings.setVisibility(View.GONE);
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
		
		horizontal_scroll.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return !isScrollEnabled;
			}
		});
		
		  
		puller.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				 switch (event.getAction()) {
			      case MotionEvent.ACTION_DOWN: {
			          ImageView view = (ImageView) v;
			          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
			          v.invalidate();
			          break;
			      }
			      case MotionEvent.ACTION_UP: {
			    	  
			    	  if(!isAllBuildingsShown){
			    		  isAllBuildingsShown = true;
			    		  objectAnimator = ObjectAnimator.ofFloat(all_buildings_layout, "translationY", 0, -all_builds_imgs.getLayoutParams().height);
			    		  objectAnimator.setDuration(700);
			    		  objectAnimator.start();

			    		  objectAnimator.addListener(new AnimatorListener() {

			    			  @Override
			    			  public void onAnimationStart(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationRepeat(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationEnd(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }

			    			  @Override
			    			  public void onAnimationCancel(Animator arg0) {
			    				  // TODO Auto-generated method stub

			    			  }
			    		  });
			    	  }else{
			    		  isAllBuildingsShown = false;
			    		  objectAnimator.reverse();
			    	  }
					
			      }
			      case MotionEvent.ACTION_CANCEL: {
			    	  ImageView view = (ImageView) v;
			          view.getBackground().clearColorFilter();
			          view.invalidate();
			          break;
			      }
			      }
			      return true;
			}
		});
		
//		Bitmap bm = BitmapFactory.decodeFile(TafseerManager.QuizPNGPath + "buildings_black_white.png");
//		Drawable d = Drawable.createFromPath(TafseerManager.QuizPNGPath + "complete_colored.png");
//		img_draggable.setImageBitmap(bm);
//		img_draggable.setBackgroundDrawable(d);
		
		
		
		
	}
	

	@Override
	protected void onStart() {
		super.onStart();
		
		myDB.populateQuizElements();
		
		currentQuizElement = myDB.getQuizElementInfos(suraId, partNb);
		
		populateElements();
		prepareElementToAdd();
	}
	
	private void populateElements(){
		
		progressDialog = new ProgressDialog(this);
		progressDialog.setCancelable(false);
		progressDialog.show();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				defectiveElements = new ArrayList<QuizElementToAdd>();
				int i = 0;
				
				for(QuizElementToAdd elementToAdd : mTafseerManager.getQuizElements()){
					
					final ImageView imgInsideLoop = new ImageView(ElementBuilderActivity.this);
					imgInsideLoop.setTag(i+1);
					
					float x = (float) (elementToAdd.getQuizElementX()/1.5);
					float y = (float) (elementToAdd.getQuizElementY()/1.5);
					float width = (float) (elementToAdd.getQuizWidth()/1.5);
					float height = (float) (elementToAdd.getQuizHeight()/1.5);

					final Bitmap bm;
					if(!elementToAdd.isQuizLocated()){
						bm = originalResolution(TafseerManager.QuizPNGGrayPath + elementToAdd.getQuizGrayFileName(), (int)width, (int)height);

					}
					else {
						bm = originalResolution(TafseerManager.QuizPNGPath + elementToAdd.getQuizFileName(), (int)width, (int)height);

						if(elementToAdd.getQuizStatus() == 2){
							defectiveElements.add(elementToAdd);
							imgInsideLoop.startAnimation(mFlashingAnimation);
						}
					}


					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)width, (int)height);
					params.setMargins((int)x, (int)y, 0, 0);
					imgInsideLoop.setLayoutParams(params);

					ElementBuilderActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							imgInsideLoop.setImageBitmap(bm);
							layout_scroll.addView(imgInsideLoop);
						}
					});
					
					i++;
				}
				
				float x=900;
			    float gap = 20;
			    
			    for(QuizElementToAdd elementDef : defectiveElements){
			    	
			    	int idx = Integer.valueOf(elementDef.getQuizIdx());
			        
			        float realWidth  = (float) (elementDef.getQuizWidth()/1.5);
			        float realHeight = (float) (elementDef.getQuizHeight()/1.5);
			        float scale=(float) (realHeight/70.0);
			        float smallWidth=realWidth/scale;
			        float smallHeight=realHeight/scale;
			        
			        x=x-smallWidth-gap;

			        final ImageView imgDefectiveElement = new ImageView(ElementBuilderActivity.this);
			        imgDefectiveElement.setTag(idx);
					
			        final Bitmap bm = originalResolution(TafseerManager.QuizPNGPath + elementDef.getQuizFileName(), (int)smallWidth, (int)smallHeight);
			       
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)smallWidth, (int)smallHeight);
					params.setMargins((int)x, 20, 0, 0);
					imgDefectiveElement.setLayoutParams(params);
					imgDefectiveElement.setBackgroundColor(Color.TRANSPARENT);
					imgDefectiveElement.startAnimation(mFlashingAnimation);
					
					imgDefectiveElement.setOnTouchListener(new OnTouchListener() {
						
						@Override
					    public boolean onTouch(View v, MotionEvent event) {
					      switch (event.getAction()) {
					      case MotionEvent.ACTION_DOWN: {
					          ImageView view = (ImageView) v;
					          view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
					          v.invalidate();
					          break;
					      }
					      case MotionEvent.ACTION_UP: {
								
					    	  openDefectiveElement((Integer)v.getTag());
					    	  
					      }
					      case MotionEvent.ACTION_CANCEL: {
					    	  ImageView view = (ImageView) v;
					          view.getBackground().clearColorFilter();
					          view.invalidate();
					          break;
					      }
					      }
					      return true;
					    }
					});
					
					 ElementBuilderActivity.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								imgDefectiveElement.setImageBitmap(bm);
								layout_scroll.addView(imgDefectiveElement);
//								imgDefectiveElement = null;
							}
						});
					
			    }
			    
			    ElementBuilderActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						progressDialog.dismiss();
					}
				});
			}
		}).start();

		
		
	}

	private void prepareElementToAdd() {
		//==========================================================================================
	    //  scroll the view to a suitable position according to the new element position
	    //==========================================================================================
		
		newX = (float) (currentQuizElement.getQuizElementX()/1.5 - horizontal_scroll.getLayoutParams().width/3 + currentQuizElement.getQuizWidth()/3);
		Log.i("prepareElementToAdd", "newX 1  " + newX);
	
		int diff = img_draggable.getLayoutParams().width - horizontal_scroll.getLayoutParams().width;
		if (newX > diff) 
			newX = (float) diff;

		Log.i("prepareElementToAdd", "newX 2  " + newX);
		
		if (newX<0f) newX=0f;

		horizontal_scroll.postDelayed(new Runnable() {
			@Override
			public void run() {
				horizontal_scroll.smoothScrollTo((int)newX, 0);
				seek_buildings.setProgress(getSeekProgress((int)newX));
			}
		},300);

		 Log.i("prepareElementToAdd", "newX 3  " + newX);
		 
//		 horizontal_scroll.setLeft((int)newX/6);
		 
		 imgToDrag = new ImageView(this);
		 imgToDrag.setTag(Integer.valueOf(currentQuizElement.getQuizIdx()));
		 bmToDrag = BitmapFactory.decodeFile(TafseerManager.QuizPNGPath + currentQuizElement.getQuizFileName());
		 imgToDrag.setImageBitmap(bmToDrag);
		 imgToDrag.setOnTouchListener(new OnTouchListener() {
		 
			public boolean onTouch(View view, MotionEvent motionEvent) {

				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !isDragOk) {
					
					// create it from the object's tag
					
					ClipData.Item item = new ClipData.Item((CharSequence)String.valueOf(view.getTag()));
					
					String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };

					ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);

					DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);


					view.startDrag( data, //data to be dragged
							shadowBuilder, //drag shadow
							view, //local data about the drag and drop operation
							0   //no needed flags
							);

					view.setVisibility(View.INVISIBLE);

					return true;
				}

				else {
					imgToDrag.setVisibility(View.VISIBLE);
					return false;
				}
			}
		});
		 
		
		 float scale = currentQuizElement.getQuizHeight()/100;
		 float smallWidth = currentQuizElement.getQuizWidth()/scale;
		 float smallHeight = currentQuizElement.getQuizHeight()/scale;

		 viewToDragFrom = new RelativeLayout(this);
		 RelativeLayout.LayoutParams smallParams = new RelativeLayout.LayoutParams((int)smallWidth, (int)smallHeight);
		 viewToDragFrom.setLayoutParams(smallParams);
		 
		 viewToDragFrom.addView(imgToDrag);
		 
		 viewToDragIn = new RelativeLayout(this);
		 RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int)(currentQuizElement.getQuizWidth()/1.5), (int)(currentQuizElement.getQuizHeight()/1.5));
		 params.setMargins((int)(currentQuizElement.getQuizElementX()/1.5), (int)(currentQuizElement.getQuizElementY()/1.5), 0, 0);
		 viewToDragIn.setLayoutParams(params);
		 viewToDragIn.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
					break;
				case DragEvent.ACTION_DRAG_ENTERED:
					break;
				case DragEvent.ACTION_DRAG_EXITED:
					break;
				case DragEvent.ACTION_DROP:
					// if the view is the viewToDragIn, we accept the drag item
					if(v == viewToDragIn) {

						View view4 = (View) event.getLocalState();
						ViewGroup owner = (ViewGroup) view4.getParent();
						owner.removeView(view4);

//						RelativeLayout container = (RelativeLayout) v;
//						container.addView(view4);
//						view4.setVisibility(View.VISIBLE);

						isDragOk = true;
						
						ImageView imfDragged = (ImageView) view4;
						
						dragDoneWithSuccess(imfDragged);

					}
					break;
				case DragEvent.ACTION_DRAG_ENDED:
				default:
					break;
				}
				return true;
			}
		});
		 
		 principal_layout.addView(viewToDragFrom);
		 layout_scroll.addView(viewToDragIn);
	}
	
	private void dragDoneWithSuccess(ImageView imageDragged) {
		
		// Animate imageview : shake it
		
		currentQuizElement.setQuizLocated(true);
		
		myDB.setElementStatus(suraId, partNb, currentQuizElement.getQuizStatus());
		myDB.setElementLocatedStatus(suraId, partNb, true);
		
		for (int i = 0; i < layout_scroll.getChildCount(); i++) {
			View v = layout_scroll.getChildAt(i);
			if(v instanceof ImageView){
				if((Integer)v.getTag() == Integer.valueOf(currentQuizElement.getQuizIdx())){
					ImageView iv = (ImageView) v;
					
					float width = (float) (currentQuizElement.getQuizWidth()/1.5);
					float height = (float) (currentQuizElement.getQuizHeight()/1.5);
					Bitmap bm = originalResolution(TafseerManager.QuizPNGPath + currentQuizElement.getQuizFileName(), (int)width, (int)height);
					iv.setImageBitmap(bm);
					
					if(currentQuizElement.getQuizStatus() == 2)
						iv.startAnimation(mFlashingAnimation);
				}
			}
		}		
	}


	private int getScaledX(double x){
		
		double dx = Double.valueOf(x);
		
		double dPercent = (double) (dx * 100.0 / 6144.0);
		double f = (double) (dPercent * img_draggable.getLayoutParams().width / 100.0);
		
		return (int)f;
	}

	private int getScaledY(double y){
		
		double dy = Double.valueOf(y);
		
		double dPercent = (double) (dy * 100.0 / 768.0);
		double f = (double) (dPercent * img_draggable.getLayoutParams().height / 100.0);
		
		return (int)f;
	}
	
	private int getRealProgress(double progress){
		
		double dPercent = (double) (progress * 100.0 / 1365.0);
		double f = (double) (dPercent * img_draggable.getMeasuredWidth() / 100.0);
		
		return (int)f;
	}
	
	private int getSeekProgress(double scroll){
		
		double dPercent = (double) (scroll * 100.0 / img_draggable.getMeasuredWidth() );
		double f = (double) (dPercent * 1365.0 / 100.0);
		
		return (int)f;
	}

	private void openDefectiveElement(int tag) {
		
		QuizElementToAdd elementSelected = defectiveElements.get(tag);
		String elementId = elementSelected.getQuizIdx();
		
		if(elementSelected.isQuizLocated())
			if(elementSelected.getQuizStatus() == 2){
				
				String partName = myDB.getElementPartName(elementId);
				String suraName = myDB.getElementSuraName(elementId);
				
				String msg = suraName + getString(R.string.defective_msg2) + partName + getString(R.string.defective_msg1);
				
				int partNbToOpen = myDB.getElementPartNumber(elementId);
				int suraIdToOpen = myDB.getElementSuraId(elementId);
				
				showAlertDialog(msg, suraIdToOpen, partNbToOpen);
			}
		
		

	}

	private void showAlertDialog(String msg, final int suraId, final int partNb){
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set dialog message
		alertDialogBuilder
		.setMessage(msg)
		.setCancelable(false)
		.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(ElementBuilderActivity.this, SouraActivity.class);
				intent.putExtra(TafseerManager.SURA_ID, suraId);
				intent.putExtra(TafseerManager.PART_NB, partNb);
				startActivity(intent);
				finish();
				return;
			}
		})
		.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				return;
			}
		});
		// show it
		alertDialogBuilder.show();
		
	}

	private int getFixedX(int x){
		int diff = 6144 - x;
		return 1365 - diff;		
	}
	
	private int getFixedY(int y){
		int diff = 768 - y;
		return 800 - diff;		
	}
	
	public Bitmap originalResolution (String filePath, int width, int height)
	{   
		int reqHeight=width;
		int reqWidth=height;
		BitmapFactory.Options options = new BitmapFactory.Options();   

		// First decode with inJustDecodeBounds=true to check dimensions
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;        

		return BitmapFactory.decodeFile(filePath, options); 
	}

	private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		Utils.cleanViews(principal_layout);
	}
}
