/**
 * Date: 2011-04-01
 */
package cn.edu.buaa.soft.eureka.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import cn.edu.buaa.soft.eureka.Constants;
import cn.edu.buaa.soft.eureka.R;
import cn.edu.buaa.soft.eureka.Utils;
import cn.edu.buaa.soft.eureka.VocabularyEntry;

/**
 * @author Chen Jin
 * 
 */
public class DictSearchUtil extends Activity{

	/**
	 * 查词实用类
	 */

	DictionaryOpenHelper helper;
	SQLiteDatabase db;
	TextView display;
	String tempKey = "by and large";
	Cursor cursor = null;
	String tempPrefix = "a";

	public DictSearchUtil(DictionaryOpenHelper doh){
		this.helper = doh;		
	}
	
	/**
	 * 从res/raw导入数据库到sdCard/Eureka
	 * 
	 * @param dbfile
	 * @return true for normal otherwise, false
	 */
	public boolean importDatabase(String dbfile) {
		final int BUFFER_SIZE = 4 * 1024;  //4k 
		try {
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)
					&& !(new File(dbfile).exists())) {
				Log.d(Constants.TAG, "will import DB from raw/dbName");
				InputStream is = getResources().openRawResource(
						R.raw.cet4_phrases); // 欲导入的数据库
				FileOutputStream fos = new FileOutputStream(dbfile); // 写到SD
																		// card
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
				return true;
			}
		} catch (FileNotFoundException e) {
			Log.e("Database", "File not found");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			Log.e("Database", "IO exception");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 在CatLog中查看数据库的模式（元数据：包括建表sql等）
	 * 
	 * @param tableName
	 * @return
	 */
	public Cursor get_table_Model(String tableName) {
		Cursor cur = null;
		// 获得可读的SQLiteDatabase对象
		db = helper.getReadableDatabase();
		String args[] = { tableName };
		String statement = "SELECT * FROM " + tableName;
		cur = db.rawQuery(statement, null);
		int num_row = cur.getCount();
		Log.d(Constants.TAG, "row count: " + num_row);
		for (int i = 0; i < cur.getColumnCount(); i++)
			Log.d(Constants.TAG, cur.getColumnName(i));
		String STATEMENT = "select * from sqlite_master where tbl_name=?";
		cur = db.rawQuery(STATEMENT, args);
		String[] colName = cur.getColumnNames();
		Utils.LogMegs(colName);
		return cur;
	}

	/**
	 * 查询数据库，得到指定单词的翻译
	 * 
	 * @param word
	 *            要翻译的单词
	 * @param dbName
	 *            数据库名
	 * @return 单词记录
	 */
	public String searchTranslation(String word, String dbName) {
		StringBuffer explan = new StringBuffer("");
		Cursor c = null;
		try {			
			// 获得可读的SQLiteDatabase对象
			db = helper.getReadableDatabase();
			// 查询指定字符串
			String WHERECLAUSE = Constants.QUESTION + "=\'" + word.trim() + "\'";
			c = db.query(Constants.DICT_TB_NAME, null, WHERECLAUSE, null, null,
					null, null);
			
			int rowNum = c.getCount();
			int colNum = c.getColumnCount();
			if (c.moveToFirst()) {
				for (int i = 0; i < rowNum; i++) {
					for (int j = 0; j < colNum; j++) {
						if(j == 2){
							explan.append(c.getString(j) + "\t");
						}
					}
					explan.append("\n\n");
					c.moveToNext();
				}
			}
		} catch (Exception e) {
			Log.d(Constants.TAG, e.toString());
			e.printStackTrace();
		}
		return explan.toString();

	}

	/**
	 * 根据前缀查询数据库返回符合前缀的单词集合
	 * 
	 * @param prefix
	 * @return cursor
	 */
	public Cursor get_words_withPrefix(String prefix) {
		Cursor c = null;
		String sql = "SELECT " + Constants.QUESTION + " FROM "
				+ Constants.DICT_TB_NAME + " WHERE " + Constants.QUESTION
				+ " LIKE \'" + prefix + "%\'";
		db = helper.getReadableDatabase();
		c = db.rawQuery(sql, null);
		return c;
	}

	/**
	 * 用TextView显示翻译结果
	 * 
	 * @param c
	 */
	public String showResult(Cursor c) {
		StringBuilder sb = new StringBuilder();
		int rowNum = c.getCount();
		int colNum = c.getColumnCount();
		if (c.moveToFirst()) {
			for (int i = 0; i < rowNum; i++) {
				for (int j = 0; j < colNum; j++) {
					if (j == 0)
						sb.append(c.getInt(j) + "\t");
					else
						sb.append(c.getString(j) + "\t");
				}
				sb.append("\n");
				c.moveToNext();
			}
		}
		return sb.toString();
		/*display = (TextView) findViewById(R.id.tempTextView);
		display.setText(sb.toString());
		Utils.myLog(sb.toString());*/
	}

	/**
	 * 将根据前缀查询得到的单词结果转换为一个ArrayList
	 * 
	 * @param c
	 * @return ArrayList
	 */
	@SuppressWarnings("unused")
	public ArrayList<String> getWordList(Cursor c) {
		Cursor cur = c;
		if (cur != null) {
			int Num_Result = cur.getCount();
			ArrayList<String> resultList = new ArrayList<String>(Num_Result);
			int question_col_idx = cur.getColumnIndex("question");
			if (cur.moveToFirst()) {
				do {
					String question = cur.getString(question_col_idx);
					Log.d(Constants.TAG, "words: " + question);
					resultList.add(question);
				} while (cur.moveToNext());
			}
			return resultList;
		} else {
			return null;
		}
	}

	/*
	 * private ArrayList<VocabularyEntry> getRsultList(Cursor c) { Cursor cur =
	 * c; if (cur != null) { int Num_Result = cur.getCount();
	 * ArrayList<VocabularyEntry> resultList = new
	 * ArrayList<VocabularyEntry>(Num_Result); int id_col_idx =
	 * cur.getColumnIndex("_id"); int question_col_idx =
	 * cur.getColumnIndex("question"); int answer_col_idx =
	 * cur.getColumnIndex("answer"); if (cur.moveToFirst()) { do { int id =
	 * cur.getInt(id_col_idx); String question =
	 * cur.getString(question_col_idx); String answer =
	 * cur.getString(answer_col_idx); VocabularyEntry wordItem = new
	 * EnglishVocabulary(question,answer); Log.d(Constants.TAG,"_id: "+id);
	 * resultList.add(wordItem); } while (cur.moveToNext()); } return
	 * resultList; } else { return null; } }
	 */

}
