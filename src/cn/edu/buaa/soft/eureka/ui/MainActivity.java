package cn.edu.buaa.soft.eureka.ui;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import cn.edu.buaa.soft.eureka.R;

public class MainActivity extends Activity {
    /** Called when the activity is first created. */
	
	private ImageButton btnSearch;//��ѯ
	private ImageButton btnLearn;//ѧϰ
	private ImageButton btnWords;//���ʱ�
	private ImageButton btnDict;//�ʵ�
	private ImageButton btnSetting;//ϵͳ����
	private ImageButton btnHelp;//����
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        this.init();
        this.bind();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,R.string.search_goto_words);//�ڶ������������item��id
		menu.add(0,2,2,R.string.search_return_main);				
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == 1) {
			Intent i = new Intent();
			i.setClass(this,WordsActivity.class);
			this.startActivity(i);
		}
		if(item.getItemId() == 2) {
			Intent i = new Intent();
			i.setClass(this,MainActivity.class);
			this.startActivity(i);
		}		
		
		return super.onOptionsItemSelected(item);
	}
    
    //Ϊ�ؼ����¼�
    private void bind(){
    	this.btnSearch.setOnClickListener(new SearchButtonListener());
    }
    
    //��ʼ���ؼ�
    private void init(){    	
    	this.btnSearch = (ImageButton)findViewById(R.id.main_btn_search);
    	this.btnLearn = (ImageButton)findViewById(R.id.main_btn_learn);
    	this.btnDict = (ImageButton)findViewById(R.id.main_btn_dict);
    	this.btnWords = (ImageButton)findViewById(R.id.main_btn_words);
    	this.btnSetting = (ImageButton)findViewById(R.id.main_btn_setting);
    	this.btnHelp = (ImageButton)findViewById(R.id.main_btn_help);
    }
    
    //��ѯ��ť����¼�������
    class SearchButtonListener implements OnClickListener {
    	@Override
		public void onClick(View v) {		
			Intent intent = new Intent();			
			intent.setClass(MainActivity.this, SearchActivity.class);
			//��ת����ʽ���
			MainActivity.this.startActivity(intent);
			
		}
	}
}