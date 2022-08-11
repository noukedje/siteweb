package com.gestion.commerce.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="commandeservices")
public class CommandeServices {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
   private long id;	
   private String nomclient;//civilite+nom client + tel
   private String civilite;
   private String tel;
   private String nomservice;
   private String datecde;
   private String lieu;
public CommandeServices() {
	super();
	// TODO Auto-generated constructor stub
}

public CommandeServices(String nomclient, String civilite, String tel, String nomservice, String datecde, String lieu) {
	super();
	this.nomclient = nomclient;
	this.civilite = civilite;
	this.tel = tel;
	this.nomservice = nomservice;
	this.datecde = datecde;
	this.lieu = lieu;
}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getNomclient() {
	return nomclient;
}

public void setNomclient(String nomclient) {
	this.nomclient = nomclient;
}

public String getCivilite() {
	return civilite;
}

public void setCivilite(String civilite) {
	this.civilite = civilite;
}

public String getTel() {
	return tel;
}

public void setTel(String tel) {
	this.tel = tel;
}

public String getNomservice() {
	return nomservice;
}
public void setNomservice(String nomservice) {
	this.nomservice = nomservice;
}
public String getDatecde() {
	return datecde;
}
public void setDatecde(String datecde) {
	this.datecde = datecde;
}
public String getLieu() {
	return lieu;
}
public void setLieu(String lieu) {
	this.lieu = lieu;
}
   
}
