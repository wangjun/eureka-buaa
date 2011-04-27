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
	 * �������ݿ��ģ���� 1. ��һ���ַ������������ݿ��д˴����Ľ��� 2. ���Ը��ַ������������ݿ����Դ�Ϊǰ׺�Ĵ������б�
	 */
	public VocabularyDao(Context context) {
		helpers.add(new DictionaryOpenHelper(context));
	}

	public VocabularyDao(Context ctx, int version) {
		helpers.add(new DictionaryOpenHelper(ctx, version));
	}

	public VocabularyDao(Context ctx, String[] dbNames) {
		//helpers.add(new DictionaryOpenHelper(ctx, "customized")); // �û����ʱ����ݿ�
		for (int i = 0; i < dbNames.length; i++) {
			helpers.add(new DictionaryOpenHelper(ctx, dbNames[i]));
		}
	}

	public void insertVocabulary(VocabularyEntry ve) {
		SQLiteDatabase db = helpers.get(0).getWritableDatabase(); // ���뵽�û����ʱ����ݿ�
		String sql = "insert into dict_tbl(question,answer) values(?,?)";
		db.execSQL(sql, new Object[] { ve.question, ve.answer });
	}

	/**
	 * ����ָ�������Ľ��͵�ָ���ĵ����ݿ�
	 * 
	 * @param db
	 *            ��ǰ�򿪵����ݿ�
	 * @param ve
	 *            Ҫ����ĵ�����
	 * 
	 */
	public void updateVocabulary(SQLiteDatabase db, VocabularyEntry ve) {
		String sql = "update dict_tbl set answer = ? where question = ?";
		db.execSQL(sql, new Object[] { ve.answer, ve.question });
	}

	/**
	 * ��������ǰ׺��ѯ��ʿ��е��ʴ���
	 * @param quesPrefix
	 * @return ��������
	 */
	public VocabularyEntry[] findVocabularyByPrefix(String quesPrefix) {
		if (quesPrefix.length() <= 1) { // ǰ׺ֻ��һ����ĸʱ���ؿռ�
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
	 * �ر����ݿ�
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
