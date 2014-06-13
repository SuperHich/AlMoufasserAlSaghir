package com.example.almoufasseralsaghir;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.almoufasseralsaghir.external.TafseerMediaPlayer;
import com.almoufasseralsaghir.utils.FontFitTextView;
import com.almoufasseralsaghir.utils.IMediaPlayerNotifier;
import com.almoufasseralsaghir.utils.MySuperScaler;
import com.almoufasseralsaghir.utils.Utils;
import com.example.almoufasseralsaghir.entity.Answer;
import com.example.almoufasseralsaghir.entity.Question;


public class QuestionsActivity extends MySuperScaler implements IMediaPlayerNotifier {
	
	private Button info, favourites, previous, home ;
	public  MediaPlayer mp;
	private ImageView  indication;
	private AnimationSet animation;
	Activity act;
	ClipData data;
	
	private TafseerMediaPlayer mPlayer;
	
	
	private RelativeLayout myQuestionsBackground, results_format_3, results_format_4 ;
	private FontFitTextView answer_1, answer_2, answer_3, question ;
	private ImageView result_3_format_3, result_2_format_3, result_1_format_3,
					   result_4_format_4, result_3_format_4, result_2_format_4, result_1_format_4 ;
	
	private int currentQuestionIndex = 0, correctCurrentAnswersCount = 0, correctAnswersCount = 0 ;
	private boolean format_4 = false;
	private boolean format_3 = false;
	private boolean answer ;
	
	private int suraId, partNb;
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	private LinkedHashMap<String, ArrayList<Answer>> answers;
	private Question currentQuestion;
	private ArrayList<Answer> currentAnswers = new ArrayList<Answer>();
	
	private String quizHistoryStr;
	private String currentElementStatus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.questions_screen);
		
		if(getIntent().getExtras() != null){
			suraId = getIntent().getExtras().getInt("suraId");
			partNb = getIntent().getExtras().getInt("partNb");
		}
		
		info = (Button) findViewById(R.id.questions_info);
		favourites = (Button) findViewById(R.id.questions_favourites);
		previous = (Button) findViewById(R.id.questions_previous);
		home = (Button) findViewById(R.id.questions_home);
		
		info.bringToFront();
		favourites.bringToFront();
		previous.bringToFront();
		home.bringToFront();
		
		myQuestionsBackground = (RelativeLayout) findViewById(R.id.my_questions_background);
		
//		myQuestionsBackground.getLayoutParams().width = screen_width ;
//		myQuestionsBackground.getLayoutParams().height = screen_height ;
		
		results_format_3 = (RelativeLayout) findViewById(R.id.results_format_3);
		results_format_4 = (RelativeLayout) findViewById(R.id.results_format_4);
		
		question = (FontFitTextView) findViewById(R.id.question);
		answer_1 = (FontFitTextView) findViewById(R.id.proposition_1);
		answer_2 = (FontFitTextView) findViewById(R.id.proposition_2);
		answer_3 = (FontFitTextView) findViewById(R.id.proposition_3);
		
		result_3_format_3 = (ImageView) findViewById(R.id.result_3_format_3);
		result_2_format_3 = (ImageView) findViewById(R.id.result_2_format_3);
		result_1_format_3 = (ImageView) findViewById(R.id.result_1_format_3);
		
		result_4_format_4 = (ImageView) findViewById(R.id.result_4_format_4);
		result_3_format_4 = (ImageView) findViewById(R.id.result_3_format_4);
		result_2_format_4 = (ImageView) findViewById(R.id.result_2_format_4);
		result_1_format_4 = (ImageView) findViewById(R.id.result_1_format_4);
		
		indication = (ImageView) findViewById(R.id.indication);
		
		Animation fadeIn = new AlphaAnimation(0, 1);
		fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
		fadeIn.setDuration(1000);

		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
		fadeOut.setStartOffset(2500);//3500
		fadeOut.setDuration(1000);

		animation = new AnimationSet(true); //change to false
		animation.addAnimation(fadeIn);
		animation.addAnimation(fadeOut);
		
		
		answer_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 if (format_3){
			    	  if (currentQuestionIndex <3) 
			    	  {
			    		  answerTreatment(0);
			    	  }
		    	  }
		    	  if (format_4){
			    	  if (currentQuestionIndex <4) 
			    	  {
			    		  answerTreatment(0);
			    	  }
			       }	
			}
		});
		answer_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				 if (format_3){
			    	  if (currentQuestionIndex <3) 
			    	  {
			    		  answerTreatment(1);
			    	  }
		    	  }
		    	  if (format_4){
			    	  if (currentQuestionIndex <4) 
			    	  {
			    		  answerTreatment(1);
			    	  }
			       }
			}
		});
		answer_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (format_3){
					if (currentQuestionIndex <3 ) 
					{
						answerTreatment(2);
					}
				}
				if (format_4){
					if (currentQuestionIndex <4) 
					{
						answerTreatment(2);
					}
				}	
				
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
		    	  startActivity(new Intent(QuestionsActivity.this, InfoActivity.class));
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
		    	  final Dialog dialog = new FavouriteDialog(QuestionsActivity.this);
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
		    	  SouraActivity.soura_act.finish();
					startActivity(new Intent(QuestionsActivity.this, MainActivity.class));
					Utils.animateFad(QuestionsActivity.this);
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
		
		
		mPlayer = new TafseerMediaPlayer(this);
				
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		myDB.populateQuestions(suraId, partNb);
		
		questions.addAll(mTafseerManager.getQuestions());
		answers = new LinkedHashMap<String, ArrayList<Answer>>(mTafseerManager.getAnswers());
		
		prepareQuestion();
		
		selectformat();
		
		String randomAdvice = myDB.getRandomAdviceMP3(suraId, partNb);
		mPlayer.playFromSdcard(mPlayer.shuffleAdviceSong(randomAdvice));
		
	}
	
	private void indicatePlay(boolean answer){
		
		if (format_4){
			switch (currentQuestionIndex){
			case 0 : if (answer) result_1_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_1_format_4.setBackgroundResource(R.drawable.answer_false);
				break;
			case 1 : if (answer) result_2_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_2_format_4.setBackgroundResource(R.drawable.answer_false);
				break;
			case 2 : if (answer) result_3_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_3_format_4.setBackgroundResource(R.drawable.answer_false);
				break ;
			case 3 : if (answer) result_4_format_4.setBackgroundResource(R.drawable.answer_correct);
						else  result_4_format_4.setBackgroundResource(R.drawable.answer_false);
				break ;
			
			}
		}
		if (format_3){
			switch (currentQuestionIndex){
			case 0 : if (answer) result_1_format_3.setBackgroundResource(R.drawable.answer_correct);
			else  result_1_format_3.setBackgroundResource(R.drawable.answer_false);
				break;
			case 1 : if (answer) result_2_format_3.setBackgroundResource(R.drawable.answer_correct);
						else  result_2_format_3.setBackgroundResource(R.drawable.answer_false);
				break;
			case 2 :if (answer) result_3_format_3.setBackgroundResource(R.drawable.answer_correct);
						else  result_3_format_3.setBackgroundResource(R.drawable.answer_false);
				break ;
		}
		}
		
	}

	private void selectformat(){

		if( format_3 && !format_4){
			myQuestionsBackground.setBackgroundResource(R.drawable.questions_bg_format_3);
			format_3 = true ; format_4 = false ;
			results_format_3.setVisibility(View.VISIBLE);
			results_format_4.setVisibility(View.GONE);
		}
		else	
		{
			myQuestionsBackground.setBackgroundResource(R.drawable.questions_bg_format_4);
			format_4 = true ; format_3 = false ;
			results_format_3.setVisibility(View.GONE);
			results_format_4.setVisibility(View.VISIBLE);
		}
	}
	
	private void prepareQuestion(){
		
		if(currentQuestionIndex == questions.size() )
		{	
			// Game finished ...
			goToElementBuilder();
			return;
		}
		
		if(questions.size()>3) { 
			format_4 = true ; format_3 = false ; 
		}
		else {
			format_4 = false ; format_3 = true ;
		}
		
		if (questions.get(currentQuestionIndex) != null) {

			currentQuestion = questions.get(currentQuestionIndex);
			currentAnswers.clear();
			currentAnswers.addAll(answers.get(currentQuestion.getQuestionID()));

			Log.i("FORMAT", String.valueOf(questions.size()));

			question.setText(currentQuestion.getText());

			answer_1.setText(currentAnswers.get(0).getText());
			answer_2.setText(currentAnswers.get(1).getText());
			if(currentAnswers.size() > 2)
				answer_3.setText(currentAnswers.get(2).getText());
		}
	
	}
	
	private void answerTreatment(int i){
		
		String correctAnswer;
		answer = currentAnswers.get(i).isStatus();
		indicatePlay(answer);
		
		if (answer){
			correctAnswersCount++;
			if(currentQuestionIndex + 1 == questions.size()){
				if(suraId == 114)
					correctCurrentAnswersCount++;
				else
					setElementStatus(1);
			}else
				correctCurrentAnswersCount++;
			
			if(mp != null) mp.release();
			mp = MediaPlayer
					.create(this, R.raw.success_answer);
			mp.start();	
			
			indication.setBackgroundResource(R.drawable.success);
			indication.setVisibility(View.VISIBLE);
			indication.startAnimation(animation);
			indication.setVisibility(View.INVISIBLE);
			
			mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {

					prepareQuestion() ;
				}
			});
			
			correctAnswer = "1";

		} else {

			if(currentQuestionIndex + 1 == questions.size() && suraId != 114)
				setElementStatus(2);
			
			
			if(mp != null) mp.release();
			mp = MediaPlayer
					.create(this, R.raw.fail_answer);
			mp.start();	
			indication.setBackgroundResource(R.drawable.fail);
			indication.setVisibility(View.VISIBLE);
			indication.startAnimation(animation);
			indication.setVisibility(View.INVISIBLE);
			
			mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer mp) {

					prepareQuestion() ;
				}
			});
			
			correctAnswer = "0";
		}
		
		prepareQuizHistory(i, correctAnswer);
		
		currentQuestionIndex++ ;
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		
		mPlayer.stop();
	}

	@Override
	public void onCompletion() {
		// TODO Auto-generated method stub
		
	}
	
	private void setElementStatus(int status){
		
		int partNB = myDB.getPartNumberByQuestionID(currentQuestion.getQuestionID());
		int suraNB = myDB.getSuraNumberByQuestionID(currentQuestion.getQuestionID());
		
		myDB.setElementStatus(suraNB, partNB, status);
		
	}
	
	private void prepareQuizHistory(int choosenAnswerIndex, String correctAnswer){
	
	    String questionID = currentQuestion.getQuestionID();
	    String answerID = currentAnswers.get(choosenAnswerIndex).getAnswerID();
	    String quizHistoryStep = questionID + answerID + correctAnswer;
	    
	    quizHistoryStr += quizHistoryStep;
	    
	}
	
	private void goToElementBuilder() {
		
		quizHistoryStr = quizHistoryStr.substring(0, quizHistoryStr.length() - 1);
		myDB.insertQuizHistory(suraId, partNb, quizHistoryStr);
		
		currentElementStatus = "3";
		
		int numberOfQuestionsBelogingToThisPart ;
		if (suraId == 114) {
			numberOfQuestionsBelogingToThisPart = questions.size();
		} else {
			numberOfQuestionsBelogingToThisPart = questions.size() - 1;
		}
		
		if (correctCurrentAnswersCount==numberOfQuestionsBelogingToThisPart) {
	        // all questions are answered correctly .. colored element
			myDB.setElementStatus(suraId, partNb, 1);
	        currentElementStatus = "1";
	        Intent intent = new Intent(this, ElementBuilderActivity.class);
	        intent.putExtra("suraId", suraId);
	        intent.putExtra("partNb", partNb);
	        startActivity(intent);
	        finish();
	    }
		
		else if (correctCurrentAnswersCount==numberOfQuestionsBelogingToThisPart-1) {
	        // all questions are answered partially .. colored element
			myDB.setElementStatus(suraId, partNb, 2);
	        currentElementStatus = "2";
	        Intent intent = new Intent(this, ElementBuilderActivity.class);
	        intent.putExtra("suraId", suraId);
	        intent.putExtra("partNb", partNb);
	        startActivity(intent);
	        finish();
	    }
		
//		else if (correctCurrentAnswersCount==numberOfQuestionsBelogingToThisPart-2) {
		else {
	        // fail
	        currentElementStatus = "3";
	        myDB.setElementStatus(suraId, partNb, 3);
	        
	        finish();
	    }
	}

	
	
}
