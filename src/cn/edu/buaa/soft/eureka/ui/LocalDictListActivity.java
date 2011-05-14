package cn.edu.buaa.soft.eureka.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.buaa.soft.eureka.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LocalDictListActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.locla_dict_list);
	    
	    lv = (ListView)findViewById(R.id.lv_localdict);
	    
	    String path = "/sdcard/eureka";
	    this.al = listAllLocalDB(path);
	    ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_list_item_1,this.al);
	    this.lv.setAdapter(aa);
	}
	
	private ArrayList listAllLocalDB(String path){
		ArrayList dbList = new ArrayList();
		File f = new File(path);
		File[] dbFiles = f.listFiles();
		for(int i=0;i<dbFiles.length;i++){
			dbList.add(dbFiles[i].getName());
		}
		return dbList;
	}
	
	private ListView lv;
	private ArrayList al;	
}
