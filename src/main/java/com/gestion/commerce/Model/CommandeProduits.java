package com.gestion.commerce.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="commandeproduits")
public class CommandeProduits {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long idcommande;
	//@Temporal(TemporalType.DATE)
	private String datecde;
	private String designation;
	private int qtecdee;
	private long prix;
	//private String mois;
	public CommandeProduits() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CommandeProduits(String designation, int qtecdee, String datecde, long prix) {
		this.designation = designation;
		this.qtecdee = qtecdee;
		this.datecde = datecde;
		this.prix = prix;
		//this.mois=mois;
	}
	public long getIdcommande() {
		return idcommande;
	}
	public void setIdcommande(long idcommande) {
		this.idcommande = idcommande;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getQtecdee() {
		return qtecdee;
	}
	public void setQtecdee(int qtecdee) {
		this.qtecdee = qtecdee;
	}
	public String getDatecde() {
		return datecde;
	}
	public void setDatecde(String datecde) {
		this.datecde = datecde;
	}
	public long getPrix() {
		return prix;
	}
	public void setPrix(long prix) {
		this.prix = prix;
	}
	
}
