package cn.edu.buaa.soft.eureka.ui;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.edu.buaa.soft.eureka.R;

public class WordDetailActivity extends Activity {
	
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
		 btnVoice.setVisibility(View.INVISIBLE);
		 
		 tvExplanation = (TextView)this.findViewById(R.id.wd_js);
		 tvWord = (TextView)this.findViewById(R.id.wd_word);
	 }
	 
	 private void getData(){
		 Intent i = getIntent();
		 this.tvWord.setText(i.getStringExtra("word")+"\n");
		 this.tvExplanation.setText(this.tvExplanation.getText().toString()+"\n\n"+i.getStringExtra("explan"));
	 }
	 
	 private ImageButton btnVoice;
	 private Button btnLwsy;
	 private TextView tvExplanation;
	 private TextView tvWord;
}
