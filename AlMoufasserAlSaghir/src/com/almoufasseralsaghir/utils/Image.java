package com.almoufasseralsaghir.utils;

public class Image {

	public int rep_defaut, rep_arrive, rep_vrai, rep_faux;

	public Image(int rep_defaut, int rep_vrais, int rep_faux) {
		super();
		this.rep_defaut = rep_defaut;
		this.rep_vrai = rep_vrais;
		this.rep_faux = rep_faux;
	}
	
	public Image(int rep_defaut, int rep_arrive, int rep_vrais, int rep_faux) {
		super();
		this.rep_defaut = rep_defaut;
		this.rep_vrai = rep_vrais;
		this.rep_faux = rep_faux;
		this.rep_arrive = rep_arrive;
	}

	public int getRep_defaut() {
		return rep_defaut;
	}

	public void setRep_defaut(int rep_defaut) {
		this.rep_defaut = rep_defaut;
	}

	public int getRep_vrais() {
		return rep_vrai;
	}

	public void setRep_vrais(int rep_vrais) {
		this.rep_vrai = rep_vrais;
	}

	public int getRep_faux() {
		return rep_faux;
	}

	public void setRep_faux(int rep_faux) {
		this.rep_faux = rep_faux;
	} 
	
	public int getRep_arrive() {
		return rep_arrive;
	}

	public void setRep_arrive(int rep_faux) {
		this.rep_arrive = rep_faux;
	}
	
	
}
