package com.example.almoufasseralsaghir.entity;

public class Sura {

	private int suraId;
	private String db_name; 
	private String label;
	private int smallDrawableId ;
	private int bigDrawableId ;
	
	public Sura() {
		// TODO Auto-generated constructor stub
	}
	
	public Sura(int suraId, String db_name, String label, int smallDrawableId, int bigDrawableId) {
		this.setSuraId(suraId);
		this.db_name = db_name; 
		this.label = label;
		this.smallDrawableId = smallDrawableId;
		this.bigDrawableId = bigDrawableId;
		}
	
	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;		
	}
	public int getSmallDrawableId() {
		return smallDrawableId;
	}
	public void setSmallDrawableId(int smallDrawableId) {
		this.smallDrawableId = smallDrawableId;
	}
	public int getBigDrawableId() {
		return bigDrawableId;
	}
	public void setBigDrawableId(int bigDrawableId) {
		this.bigDrawableId = bigDrawableId;
	}

	public int getSuraId() {
		return suraId;
	}

	public void setSuraId(int suraId) {
		this.suraId = suraId;
	}
	
	
}
