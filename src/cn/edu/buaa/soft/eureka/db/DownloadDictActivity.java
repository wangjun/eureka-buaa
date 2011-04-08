package cn.edu.buaa.soft.eureka.db;


import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.R.drawable;
import cn.edu.buaa.soft.eureka.R.id;
import cn.edu.buaa.soft.eureka.R.layout;
import cn.edu.buaa.soft.eureka.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

public class DownloadDictActivity extends Activity {

	private WebView mWebView1;
	private ImageButton mImageButton1;
	private String TAG = "myActivity";
	//String uriStr = "http://127.0.0.1:80/download.html";
	String uriStr = "10.0.2.2/download.html";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		Log.i(TAG, "onCreate!!");

		
		mImageButton1 = (ImageButton) findViewById(R.id.myImageButton1);

		// URL url = new URL(uriStr);
		mWebView1 = (WebView) findViewById(R.id.myWebView1);
		mWebView1.setWebViewClient(new WebViewClient() {
		});

		/* 当按下箭头时的事件 */
		mImageButton1.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View arg0) {
				{
					mImageButton1.setImageResource(R.drawable.go_2);
					System.out.println("Button1 onClicked!!!");
					/* WebView里面显示网页数据 */
					mWebView1.loadUrl(uriStr);
					Log.i(TAG, "loadUrl");
					Toast.makeText(DownloadDictActivity.this,
							getString(R.string.load) + uriStr,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		/*
		 * HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 * conn.setDoInput(true); conn.connect(); InputStream is =
		 * conn.getInputStream();
		 * 
		 * 
		 * //创建本地保存流的文件 File musicFile = new File("/sdcard/", decode);
		 * 
		 * FileOutputStream fos = new FileOutputStream(musicFile);
		 * 
		 * byte[] bt = new byte[1024]; int i = 0;
		 * 
		 * while ( (i = is.read(bt)) > 0 ) { fos.write(bt, 0, i); } Log.d(TAG,
		 * "stop write");
		 * 
		 * fos.close(); is.close();
		 */

	}
}