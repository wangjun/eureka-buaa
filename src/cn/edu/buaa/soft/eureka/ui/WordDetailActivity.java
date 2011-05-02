package cn.edu.buaa.soft.eureka.ui;



import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.common.Constants;

public class WordDetailActivity extends Activity {
	  private TextToSpeech tts;
		 private ImageButton btnVoice;
		 private Button btnLwsy;
		 private TextView tvExplanation;
		 private TextView tvWord;

	
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.word_detail);
	        
	        init();
	        getData();
	    }
	 
	 private void init(){
		 btnVoice = (ImageButton)this.findViewById(R.id.wd_btn_voice);
		 
		 //voice布局还需要调整，暂时不显示
		 btnVoice.setVisibility(View.VISIBLE);
		 
		 tvExplanation = (TextView)this.findViewById(R.id.wd_js);
		 tvWord = (TextView)this.findViewById(R.id.wd_word);
		    /* 传入context及OnInitListener */
		    tts = new TextToSpeech(this, ttsInitListener);
			 btnVoice.setOnClickListener(new SpeechButtonListener());
	 }
	 
	 private void getData(){
		 Intent i = getIntent();
		 this.tvWord.setText(i.getStringExtra("word")+"\n");
		 this.tvExplanation.setText(this.tvExplanation.getText().toString()+"\n\n"+i.getStringExtra("explan"));
	 }

	 //发音按钮监听
	 class SpeechButtonListener implements OnClickListener {

		    @Override
		    public void onClick(View v)
		    {
		     Intent i = getIntent();
		      if (i.getStringExtra("word").length() > 0)
		      {
		        /* 传入要说的字符串 */
		        tts.speak(i.getStringExtra("word").toString(), TextToSpeech.QUEUE_FLUSH,
		            null);
		      } else
		      {
		        /* 无输入字符串时 */
		        tts.speak("Nothing to say", TextToSpeech.QUEUE_FLUSH, null);
		      }

		    }
				
		 }
	 private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener()
	  {

	    @Override
	    public void onInit(int status)
	    {
	      // TODO Auto-generated method stub
	      /* 使用美国时区目前不支持中文 */
	      Locale loc = new Locale("us", "", "");
	      /* 检查是否支持输入的时区 */
	      if (tts.isLanguageAvailable(loc) == TextToSpeech.LANG_AVAILABLE)
	      {
	        /* 设定语言 */
	        tts.setLanguage(loc);
	      }
	      tts.setOnUtteranceCompletedListener(ttsUtteranceCompletedListener);
	      Log.i(Constants.TAG, "TextToSpeech.OnInitListener");
	    }

	  };
	  private TextToSpeech.OnUtteranceCompletedListener ttsUtteranceCompletedListener = new TextToSpeech.OnUtteranceCompletedListener()
	  {
	    @Override
	    public void onUtteranceCompleted(String utteranceId)
	    {
	      // TODO Auto-generated method stub
	      Log.i(Constants.TAG, "TextToSpeech.OnUtteranceCompletedListener");
	    }
	  };

	  @Override
	  protected void onDestroy()
	  {
	    // TODO Auto-generated method stub
	    /* 释放TextToSpeech的资源 */
	    tts.shutdown();
	    Log.i(Constants.TAG, "tts.shutdown");
	    super.onDestroy();
	  }
}

