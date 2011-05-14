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
		
		//�õ�TabHost��������TabActivity�Ĳ���ͨ����������������
		TabHost tabHost = getTabHost();
		//����һ��Intent���󣬸ö���ָ��һ��Activity
		Intent remoteIntent = new Intent();
		remoteIntent.setClass(this, AllDictListActivity.class);
		//����һ��TabSpec����������������һ��ҳ
		TabHost.TabSpec remoteSpec = tabHost.newTabSpec("Remote");
		Resources res = getResources();
		//���ø�ҳ��indicator
		remoteSpec.setIndicator("���дʵ�", res.getDrawable(android.R.drawable.stat_sys_download));
		//���ø�ҳ������
		remoteSpec.setContent(remoteIntent);
		//�����úõ�TabSpec������ӵ�TabHost����
		tabHost.addTab(remoteSpec);
		
		Intent localIntent = new Intent();
		localIntent.setClass(this, LocalDictListActivity.class);
		TabHost.TabSpec localSpec = tabHost.newTabSpec("Local");
		localSpec.setIndicator("���شʵ�", res.getDrawable(android.R.drawable.stat_sys_upload));
		localSpec.setContent(localIntent);
		tabHost.addTab(localSpec);
	}
}

