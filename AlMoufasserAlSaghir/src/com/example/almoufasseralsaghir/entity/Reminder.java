package com.example.almoufasseralsaghir.entity;

public class Reminder {
	
	private int reminderID;
	private String date;
	private String time;
	private int type;
	private boolean status;
	
	public int getReminderID() {
		return reminderID;
	}
	public void setReminderID(int reminderID) {
		this.reminderID = reminderID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
