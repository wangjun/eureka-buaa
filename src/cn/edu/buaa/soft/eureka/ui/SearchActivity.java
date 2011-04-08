package cn.edu.buaa.soft.eureka.ui;




import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import cn.edu.buaa.soft.eureka.Constants;
import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.Utils;
import cn.edu.buaa.soft.eureka.db.DictionaryOpenHelper;
import cn.edu.buaa.soft.eureka.db.DictSearchUtil;

public class SearchActivity extends Activity {
		
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.search);
	    
	    controlInit();
	    loadData();	    
	}
	
	
	/**
	 * 初始化控件	 
	 */
	private void controlInit(){
		this.lvWords = (ListView)findViewById(R.id.lv_wordlist);
		this.etWord = (EditText)findViewById(R.id.search_input);
		
		//监听输入框文本的变化
		this.etWord.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				System.out.println("aaaaa");
				fillListView();
				/*for(int i=0;i<list.size();i++){
				System.out.println(list.get((int)i));
				}*/
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		//监听单击listview某项的事件
		OnItemClickListener item_click = new OnItemClickListener(){
			  
			  /*parent	The AdapterView where the click happened.
				view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
				position	The position of the view in the adapter.
				id	    The row id of the item that was clicked.*/
				  @Override
				  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				  
				   Intent i = new Intent();
				   i.setClass(SearchActivity.this, WordDetailActivity.class);				   
				   if(id >= 0){
					   i.putExtra("word", list.get((int)id));
					   
					   //cursor = dsu.searchTranslation(list.get((int)id), Constants.DICT_TB_NAME);
					   i.putExtra("explan", dsu.searchTranslation(list.get((int)id), Constants.DICT_TB_NAME));
				   }
				   startActivity(i);
			  }
			  
		 };
		 
		 lvWords.setOnItemClickListener(item_click);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0,1,1,R.string.main_exit);
		menu.add(0,2,2,R.string.main_words);//第二个参数是添加item的id					
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == 1) {
			
		}
		else if(item.getItemId() == 2) {
			Intent i = new Intent();
			i.setClass(this,WordsActivity.class);
			this.startActivity(i);
		}		
		
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 加载词典数据
	 */
	private void loadData(){		
		try {
			// 初始化数据库辅助对象
			helper = new DictionaryOpenHelper(this);
			dsu = new DictSearchUtil(helper);
			// 创建/sdCard/Eureka文件夹
			String sdCardDir = Utils.getExternalStoragePath();
			Utils.createFolder(sdCardDir, "Eureka");
			// 导入数据库文件
			String dbfile = Utils.getEurekaPath() + File.separator
					+ Constants.DB_NAME;
			dsu.importDatabase(dbfile);
			Log.d(Constants.TAG, "imported database");			
		
			//dsu.showResult(cursor);										

		} finally {			
			
		}
	}
	
	/**
	 * 动态填充ListView
	 */
	private void fillListView() {
		cursor = dsu.get_words_withPrefix(this.etWord.getText().toString());
		list = dsu.getWordList(cursor);
		ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list); 		
		lvWords.setAdapter(listAdapter);
	}
	
	private DictionaryOpenHelper helper;
	DictSearchUtil dsu;
	private SQLiteDatabase db;	
	private String tempKey = "by and large";
	private Cursor cursor = null;	
	private ArrayList<String> list;
	private ListView lvWords; //显示单词列表
	private EditText etWord;  //单词输入框
	
	
}
