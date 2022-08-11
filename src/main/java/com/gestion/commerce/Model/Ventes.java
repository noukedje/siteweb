package com.gestion.commerce.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ventes")
public class Ventes {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
   private long montant;
   private String mois;
public Ventes() {
	super();
	// TODO Auto-generated constructor stub
}
public Ventes(long mont, String mois) {
	montant=mont;
	this.mois=mois;
}
public long getMontant() {
	return montant;
}
public void setMontant(long montant) {
	this.montant = montant;
}
public String getMois() {
	return mois;
}
public void setMois(String mois) {
	this.mois = mois;
}
  
}
