package cn.edu.buaa.soft.eureka.ui;

import java.io.File;
import java.util.ArrayList;

import cn.edu.buaa.soft.eureka.common.Constants;
import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.common.Utils;
import cn.edu.buaa.soft.eureka.db.DictSearchUtil;
import cn.edu.buaa.soft.eureka.db.DictionaryOpenHelper;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Chen Xu
 * 历史记录
 */
public class HistoryActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.history);
	    
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
				   i.setClass(HistoryActivity.this, WordDetailActivity.class);				   
				   if(id >= 0){					   					  
					   final TextView tv =(TextView) view.findViewById(R.id.list_item_content);
					   String s = tv.getText().toString();
					   i.putExtra("word", tv.getText().toString());					   					  
					   i.putExtra("explan", dsu.searchTranslation(tv.getText().toString(), Constants.DICT_TB_NAME));
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
			dsu = new DictSearchUtil();
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
		/*ArrayAdapter listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list); 		
		lvWords.setAdapter(listAdapter);*/
		try{
		SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,R.layout.list_item_1,cursor,new String[]{"question"},new int[]{R.id.list_item_content});
		lvWords.setAdapter(listAdapter);
		}
		catch(Exception ex){
			System.out.println(ex.toString());
		}		
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
