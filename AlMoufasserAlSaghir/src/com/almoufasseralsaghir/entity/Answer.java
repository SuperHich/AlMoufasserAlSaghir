package com.almoufasseralsaghir.entity;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class Answer {
	
	private int ID;
	private String answerID;
	private String text;
	private boolean status;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getAnswerID() {
		return answerID;
	}
	public void setAnswerID(String answerID) {
		this.answerID = answerID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
}
