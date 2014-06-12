package com.example.almoufasseralsaghir.entity;

public class QuizElementToAdd {
	
	private String QuizIdx;
	private String QuizFileName;
	private float QuizWidth;
	private float QuizHeight;
	private float QuizElementX;
	private float QuizElementY;
	
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
