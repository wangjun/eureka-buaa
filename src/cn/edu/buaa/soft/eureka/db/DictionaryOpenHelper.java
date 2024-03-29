package cn.edu.buaa.soft.eureka.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.R.raw;
import cn.edu.buaa.soft.eureka.common.Constants;
import cn.edu.buaa.soft.eureka.common.Utils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DictionaryOpenHelper extends SDSQLiteOpenHelper {
	/**
	 * �Զ���ʿ������
	 */
	
	public static int DB_VERSION = 1;
	public static final String DB_PATH = Utils.getEurekaPath()+File.separator+Constants.DB_NAME;

	public DictionaryOpenHelper(Context context) {
		super(context, Constants.DB_NAME, null, DB_VERSION);
	}
	public DictionaryOpenHelper(Context context,int version) {
		super(context, Constants.DB_NAME, null, version);
	}
	public DictionaryOpenHelper(Context context,String dbName) {
		super(context,dbName, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}


}
