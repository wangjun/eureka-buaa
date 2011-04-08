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
	
	private ImageButton btnSearch;//查询
	private ImageButton btnLearn;//学习
	private ImageButton btnWords;//生词本
	private ImageButton btnDict;//词典
	private ImageButton btnSetting;//系统设置
	private ImageButton btnHelp;//帮助
	
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
		menu.add(0,1,1,R.string.search_goto_words);//第二个参数是添加item的id
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
    
    //为控件绑定事件
    private void bind(){
    	this.btnSearch.setOnClickListener(new SearchButtonListener());
    }
    
    //初始化控件
    private void init(){    	
    	this.btnSearch = (ImageButton)findViewById(R.id.main_btn_search);
    	this.btnLearn = (ImageButton)findViewById(R.id.main_btn_learn);
    	this.btnDict = (ImageButton)findViewById(R.id.main_btn_dict);
    	this.btnWords = (ImageButton)findViewById(R.id.main_btn_words);
    	this.btnSetting = (ImageButton)findViewById(R.id.main_btn_setting);
    	this.btnHelp = (ImageButton)findViewById(R.id.main_btn_help);
    }
    
    //查询按钮点击事件监听器
    class SearchButtonListener implements OnClickListener {
    	@Override
		public void onClick(View v) {		
			Intent intent = new Intent();			
			intent.setClass(MainActivity.this, SearchActivity.class);
			//跳转到查词界面
			MainActivity.this.startActivity(intent);
			
		}
	}
}