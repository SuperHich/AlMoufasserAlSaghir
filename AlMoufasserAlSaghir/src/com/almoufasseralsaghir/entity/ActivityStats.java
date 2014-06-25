package com.almoufasseralsaghir.entity;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class ActivityStats {
	
	private String uid;
	private String sura;
	private String part;
	private String repeat;
	private String percentage;
	private String date;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSura() {
		return sura;
	}
	public void setSura(String sura) {
		this.sura = sura;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getRepeat() {
		return repeat;
	}
	public void setRepeat(String repeat) {
		this.repeat = repeat;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("uid " + uid);
		sb.append(" sura " + sura);
		sb.append(" part " + part);
		sb.append(" repeat " + repeat);
		sb.append(" percentage " + percentage);
		sb.append(" date " + date);
		return sb.toString();
	}

}
