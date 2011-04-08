package cn.edu.buaa.soft.eureka;

import java.util.ArrayList;

public abstract class VocabularyEntry {
	/**
	 * �����Ľӿ�
	 */
	public String question = null;
	public String answer = null;
	public String language = "US";
	public VocabularyEntry(String ques,String answ, String lang){
		question = ques;
		language = lang;
		answer = answ;
	}
	abstract public boolean store_to_newWordBook();
	abstract public VocabularyEntry getVocabularyEntry(String question);
	abstract public ArrayList<String> getQuestions_withPrefix(String prefix);
}
