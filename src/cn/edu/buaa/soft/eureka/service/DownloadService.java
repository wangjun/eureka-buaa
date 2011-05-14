package cn.edu.buaa.soft.eureka.service;

import cn.edu.buaa.soft.eureka.net.DownloadHelper;
import cn.edu.buaa.soft.eureka.ui.AllDictListActivity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DownloadService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String name = intent.getStringExtra("name");
		DownloadThread dt = new DownloadThread(name);
		Thread t = new Thread(dt);
		t.start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	/**
	 * 下载线程类
	 * @author Administrator
	 *
	 */
	class DownloadThread implements Runnable {

		String name;
		public DownloadThread(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String dictUrl = AllDictListActivity.SERVER_IP + "/dict/"+this.name;
			System.out.println("chenxu:"+dictUrl);
			DownloadHelper httpDownloader = new DownloadHelper();
			httpDownloader.downFile(dictUrl, "/sdcard/Eureka/", this.name);
		}
		
	}

}
