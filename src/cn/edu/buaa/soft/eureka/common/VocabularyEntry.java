package cn.edu.buaa.soft.eureka.common;

import java.util.ArrayList;

public class VocabularyEntry implements Comparable{
	/**
	 * 词条的接口
	 */
	public String question = null;
	public String answer = null;
	public String language = "US";
	public VocabularyEntry(){
		question = null;
		answer = null;
		language = "US";
	}
	public VocabularyEntry(String ques,String answ, String lang){
		question = ques;
		language = lang;
		answer = answ;
	}
	@Override
	public String toString() {
		return "VocabularyEntry("+language+","+ question+","+answer+")";
	}
	@Override
	public int compareTo(Object another) {    //排序方法
		VocabularyEntry anotherEntry = (VocabularyEntry)another;
		return this.question.compareTo(anotherEntry.question);   //升序排列
	}
	
	
	/*abstract public boolean store_to_newWordBook();
	abstract public VocabularyEntry getVocabularyEntry(String question);
	abstract public ArrayList<String> getQuestions_withPrefix(String prefix);*/
}
