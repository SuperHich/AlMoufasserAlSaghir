package com.almoufasseralsaghir.entity;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class QuizElement {
	
	private String QuizIdx;
	private String QuizFileName;
	private String QuizGrayFileName;
	private String QuizWidth;
	private String QuizHeight;
	private String QuizLeft;
	private String QuizTop;
	private boolean QuizStatus;
	private boolean QuizLocated;
	
	public String getQuizIdx() {
		return QuizIdx;
	}
	public void setQuizIdx(String quizIdx) {
		QuizIdx = quizIdx;
	}
	public String getQuizFileName() {
		return QuizFileName;
	}
	public void setQuizFileName(String quizFileName) {
		QuizFileName = quizFileName;
	}
	public String getQuizGrayFileName() {
		return QuizGrayFileName;
	}
	public void setQuizGrayFileName(String quizGrayFileName) {
		QuizGrayFileName = quizGrayFileName;
	}
	public String getQuizWidth() {
		return QuizWidth;
	}
	public void setQuizWidth(String quizWidth) {
		QuizWidth = quizWidth;
	}
	public String getQuizHeight() {
		return QuizHeight;
	}
	public void setQuizHeight(String quizHeight) {
		QuizHeight = quizHeight;
	}
	public String getQuizLeft() {
		return QuizLeft;
	}
	public void setQuizLeft(String quizLeft) {
		QuizLeft = quizLeft;
	}
	public String getQuizTop() {
		return QuizTop;
	}
	public void setQuizTop(String quizTop) {
		QuizTop = quizTop;
	}
	public boolean isQuizStatus() {
		return QuizStatus;
	}
	public void setQuizStatus(boolean quizStatus) {
		QuizStatus = quizStatus;
	}
	public boolean isQuizLocated() {
		return QuizLocated;
	}
	public void setQuizLocated(boolean quizLocated) {
		QuizLocated = quizLocated;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(QuizIdx + "  ");
		sb.append(QuizFileName + "  ");
		sb.append(QuizGrayFileName + "  ");
		sb.append(QuizWidth + "  ");
		sb.append(QuizHeight + "  ");
		sb.append(QuizLeft + "  ");
		sb.append(QuizTop + "  ");
		sb.append(QuizStatus + "  ");
		sb.append(QuizLocated + "  ");
		return sb.toString();
	}


}
