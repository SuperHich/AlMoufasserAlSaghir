package com.almoufasseralsaghir.entity;

public class QuizElementToAdd {
	
	private String QuizIdx;
	private String QuizFileName;
	private String QuizGrayFileName;
	private float QuizWidth;
	private float QuizHeight;
	private float QuizElementX;
	private float QuizElementY;
	private int QuizStatus;
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
	public float getQuizWidth() {
		return QuizWidth;
	}
	public void setQuizWidth(float quizWidth) {
		QuizWidth = quizWidth;
	}
	public float getQuizHeight() {
		return QuizHeight;
	}
	public void setQuizHeight(float quizHeight) {
		QuizHeight = quizHeight;
	}
	public float getQuizElementX() {
		return QuizElementX;
	}
	public void setQuizElementX(float quizElementX) {
		QuizElementX = quizElementX;
	}
	public float getQuizElementY() {
		return QuizElementY;
	}
	public void setQuizElementY(float quizElementY) {
		QuizElementY = quizElementY;
	}

	public int getQuizStatus() {
		return QuizStatus;
	}
	public void setQuizStatus(int quizStatus) {
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
		sb.append(QuizWidth + "  ");
		sb.append(QuizHeight + "  ");
		sb.append(QuizElementX + "  ");
		sb.append(QuizElementY + "  ");
		return sb.toString();
	}


}
