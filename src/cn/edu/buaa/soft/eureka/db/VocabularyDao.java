/**
 * 
 */
package cn.edu.buaa.soft.eureka.db;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import cn.edu.buaa.soft.eureka.common.Constants;
import cn.edu.buaa.soft.eureka.common.VocabularyEntry;

/**
 * @author Chen Jin
 * 
 */
public class VocabularyDao {

	private List<DictionaryOpenHelper> helpers = new ArrayList<DictionaryOpenHelper>();

	/**
	 * 操作数据库的模型类 1. 给一个字符串，返回数据库中此词条的解释 2. 给以个字符串，返回数据库中以此为前缀的词条的列表
	 */
	public VocabularyDao(Context context) {
		helpers.add(new DictionaryOpenHelper(context));
	}

	public VocabularyDao(Context ctx, int version) {
		helpers.add(new DictionaryOpenHelper(ctx, version));
	}

	public VocabularyDao(Context ctx, String[] dbNames) {
		//helpers.add(new DictionaryOpenHelper(ctx, "customized")); // 用户单词本数据库
		for (int i = 0; i < dbNames.length; i++) {
			helpers.add(new DictionaryOpenHelper(ctx, dbNames[i]));
		}
	}

	public void insertVocabulary(VocabularyEntry ve) {
		SQLiteDatabase db = helpers.get(0).getWritableDatabase(); // 插入到用户单词本数据库
		String sql = "insert into dict_tbl(question,answer) values(?,?)";
		db.execSQL(sql, new Object[] { ve.question, ve.answer });
	}

	/**
	 * 更新指定词条的解释到指定的的数据库
	 * 
	 * @param db
	 *            当前打开的数据库
	 * @param ve
	 *            要插入的单词项
	 * 
	 */
	public void updateVocabulary(SQLiteDatabase db, VocabularyEntry ve) {
		String sql = "update dict_tbl set answer = ? where question = ?";
		db.execSQL(sql, new Object[] { ve.answer, ve.question });
	}

	/**
	 * 给定单词前缀查询多词库中单词词条
	 * @param quesPrefix
	 * @return 单词数组
	 */
	public VocabularyEntry[] findVocabularyByPrefix(String quesPrefix) {
		if (quesPrefix.length() <= 1) { // 前缀只有一个字母时返回空集
			return null;
		} 
		else {
			SortedSet<VocabularyEntry> vocabularySet = new TreeSet<VocabularyEntry>();
			for (int i = 0; i < helpers.size(); i++) {
				SQLiteDatabase db = helpers.get(i).getReadableDatabase();
				VocabularyEntry ve = null;
				//String sql = "select * from dict_tbl where question like ?%";
				String sql = "SELECT " + Constants.QUESTION + "," + Constants.ANSWER + " FROM "
				+ Constants.DICT_TB_NAME + " WHERE " + Constants.QUESTION
				+ " LIKE \'" + quesPrefix + "%\'";
				Cursor cur = null;
				try{
				 cur = db.rawQuery(sql, null);
				}
				catch(Exception ex){
					System.out.println(ex.toString());
				}
				
				int question_col_idx = cur.getColumnIndex("question");
				int answer_idx = cur.getColumnIndex("answer");
				while (cur.moveToNext()) {
					ve = new VocabularyEntry();
					ve.question = cur.getString(question_col_idx);
					ve.answer = cur.getString(answer_idx);
					vocabularySet.add(ve);
				}
				cur.close();				
			}
			Object[] objs = vocabularySet.toArray();
			VocabularyEntry[] ves = new VocabularyEntry[objs.length];
			for(int i=0; i<objs.length; i++){
				ves[i] = (VocabularyEntry)objs[i];
			}
			return ves;
		}
	}


	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		for (int i = 0; i < helpers.size(); i++) {
			SQLiteDatabase db = helpers.get(i).getWritableDatabase();
			if (db != null && db.isOpen()) {
				db.close();
			}
		}
	}

}
