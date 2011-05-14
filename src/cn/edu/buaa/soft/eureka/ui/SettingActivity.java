package cn.edu.buaa.soft.eureka.ui;

import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.ui.SettingListAdapter.ViewHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SettingActivity extends Activity {
	
	private ListView lvDict;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.setting);
	        
	        init();	        
	    }
	 private void init() {
		 lvDict=(ListView)findViewById(R.id.setting_dict_list);            
		 SettingListAdapter adapter=new SettingListAdapter(this);            
		 try{
		 lvDict.setAdapter(adapter);
		 }
		 catch(Exception ex){
			 System.out.println(ex.toString());
		 }
		 lvDict.setItemsCanFocus(false);            
		 lvDict.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); 
		 
		 lvDict.setOnItemClickListener(new OnItemClickListener(){                
			 @Override                
			 public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				 ViewHolder vHollder = (ViewHolder) view.getTag();    //在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。                   
				 vHollder.cBox.toggle();                   
				 SettingListAdapter.isSelected.put(position, vHollder.cBox.isChecked());                
				 }            
		});  			 
	}	 
}
