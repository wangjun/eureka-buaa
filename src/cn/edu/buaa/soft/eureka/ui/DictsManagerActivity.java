package cn.edu.buaa.soft.eureka.ui;


import cn.edu.buaa.soft.eureka.R;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class DictsManagerActivity extends TabActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dicts_manager_main);
		
		//得到TabHost对象，正对TabActivity的操作通常都有这个对象完成
		TabHost tabHost = getTabHost();
		//生成一个Intent对象，该对象指向一个Activity
		Intent remoteIntent = new Intent();
		remoteIntent.setClass(this, AllDictListActivity.class);
		//生成一个TabSpec对象，这个对象代表了一个页
		TabHost.TabSpec remoteSpec = tabHost.newTabSpec("Remote");
		Resources res = getResources();
		//设置该页的indicator
		remoteSpec.setIndicator("所有词典", res.getDrawable(android.R.drawable.stat_sys_download));
		//设置该页的内容
		remoteSpec.setContent(remoteIntent);
		//将设置好的TabSpec对象添加到TabHost当中
		tabHost.addTab(remoteSpec);
		
		Intent localIntent = new Intent();
		localIntent.setClass(this, LocalDictListActivity.class);
		TabHost.TabSpec localSpec = tabHost.newTabSpec("Local");
		localSpec.setIndicator("本地词典", res.getDrawable(android.R.drawable.stat_sys_upload));
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
	}
}

