package cn.edu.buaa.soft.eureka.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.buaa.soft.eureka.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Chen Xu
 * ����һ���Զ���adapter���������ý���ʵ�ִ���ͼƬ��checkbox��ListView�ؼ�
 */
public class SettingListAdapter extends BaseAdapter {        
	private LayoutInflater mInflater;        
	private List<Map<String, Object>> mData;        
	public static Map<Integer, Boolean> isSelected;            
	public SettingListAdapter(Context context) {            
		mInflater = LayoutInflater.from(context);            
		init();        
	}  
	
	//��ʼ��      
	private void init() {
		File f = new File("/sdcard/Eureka");
		File[] dicts = f.listFiles();//�ʵ��б�
		mData=new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map;
		
		//�������дʵ�
		for (int i = 0; i < dicts.length; i++) {                
			map = new HashMap<String, Object>();                
			map.put("img", R.drawable.dict_icon);                
			map.put("title", dicts[i].getName());                
			mData.add(map);            
		}            
		
		map = new HashMap<String, Object>();
		map.put("img",R.drawable.voice);
		map.put("title","  ���÷���");
		mData.add(map);
		
		map = new HashMap<String, Object>();
		map.put("img",R.drawable.voice);
		map.put("title","  ����������");
		mData.add(map);
		
		//�������isSelected���map�Ǽ�¼ÿ��listitem��״̬����ʼ״̬ȫ��Ϊfalse��           
		isSelected = new HashMap<Integer, Boolean>();            
		for (int i = 0; i < mData.size(); i++) {                
			isSelected.put(i, false);            
		}        
	}            
	@Override        
	public int getCount() {            
		return mData.size();        
		}           
	@Override        
	public Object getItem(int position) {
		return null;       
	}           
	@Override        
	public long getItemId(int position) {            
		return 0;        
	}            
	@Override        
	public View getView(int position, View convertView, ViewGroup parent) {            
		ViewHolder holder = null;            //convertViewΪnull��ʱ���ʼ��convertView��           
		if (convertView == null) {                
			holder = new ViewHolder();                
			convertView = mInflater.inflate(R.layout.setting_list_dict, null);
			holder.img = (ImageView) convertView.findViewById(R.id.setting_list_img);
			holder.title = (TextView) convertView.findViewById(R.id.setting_list_name_dict);                
			holder.cBox = (CheckBox) convertView.findViewById(R.id.setting_list_title_cb);                
			convertView.setTag(holder);            
		} 
		else {                
			holder = (ViewHolder) convertView.getTag();            
		}           
		holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));
		holder.title.setText(mData.get(position).get("title").toString());            
		holder.cBox.setChecked(isSelected.get(position));            
		return convertView;        
	}            
	public final class ViewHolder { 
		public ImageView img;            
		public TextView title;            
		public CheckBox cBox;        
		}        
	}
