package cn.edu.buaa.soft.eureka.db;

import java.util.ArrayList;

import cn.edu.buaa.soft.eureka.VocabularyEntry;

public class EnglishVocabulary extends VocabularyEntry {
	/**
	 * ”¢”Ôµ•¥ ¿‡
	 */
	
	/**
	 * constructor
	 * @param ques
	 * @param answ
	 */
	public EnglishVocabulary(String ques, String answ) {
		super(ques, answ, "US");
		
	}

	@Override
	public boolean store_to_newWordBook() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VocabularyEntry getVocabularyEntry(String question) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getQuestions_withPrefix(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

}
