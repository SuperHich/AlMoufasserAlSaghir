package com.almoufasseralsaghir.entity;

/**
 * AlMoufasserAlSaghir
 * @author HICHEM LAROUSSI - RAMI TRABELSI
 * Copyright (c) 2014 Zad Group. All rights reserved.
 */

public class ReminderListItem {
	private String label;
	private boolean isSelected = false;
	
	public ReminderListItem(String label, boolean isSelected) {
		this.label = label;
		this.isSelected = isSelected;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
}
