package cn.edu.buaa.soft.eureka.ui;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import cn.edu.buaa.soft.eureka.common.Constants;
import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.net.DownloadHelper;
import cn.edu.buaa.soft.eureka.service.DownloadService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class AllDictListActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.all_dict_list);	 
	    
	    lv = (ListView)findViewById(R.id.lv_alldict);
	    ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,this.downloadList(SERVER_IP+XML_FILE_ADDRESS));
	    this.lv.setAdapter(aa);
	    
		//监听单击listview某项的事件
		OnItemClickListener item_click = new OnItemClickListener(){
			  
			  /*parent	The AdapterView where the click happened.
				view	The view within the AdapterView that was clicked (this will be a view provided by the adapter)
				position	The position of the view in the adapter.
				id	    The row id of the item that was clicked.*/
				  @Override
				  public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					  if(id >= 0){					   					  
						   String s = al.get((int)id).toString();
						   
						   Intent i = new Intent();
						   i.putExtra("name", s.substring(0, s.indexOf("("))+".db");
						   i.setClass(AllDictListActivity.this, DownloadService.class);
						   AllDictListActivity.this.startService(i);
					   }	
			  }
			  
		 };
		 
		 lv.setOnItemClickListener(item_click);	    
	}
	
	/**
	 * 下载指定URL的词典列表文件
	 * @param urlStr
	 * @return 下载到词典列表文件的内容，以ArrayList形式返回
	 */
	private ArrayList downloadList(String urlStr) {
		ArrayList<String> al = new ArrayList<String>();
		String result = "";
		DownloadHelper httpDownloader = new  DownloadHelper();
		result = httpDownloader.download(urlStr);
		String[] tmp = result.split("NEW");
		for(String s : tmp){
			al.add(s);
		}
		this.al = al;
		return al;
	}	
	
	
	

	
	private ListView lv;
	private ArrayList al;
	public static final String SERVER_IP = "http://219.225.96.33:8080"; //服务器的地址,不能用127.0.0.1
	private static final String XML_FILE_ADDRESS = "/dict/resources"; //词典列表文件
}
