package com.gestion.commerce.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Historique {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
   private long Id;
	@Size(max=50)
   private String Acteur;
   private String Action;
   private Date date;
   
   
public Historique(@Size(max = 50) String acteur, String action, Date date) {
	super();
	Acteur = acteur;
	Action = action;
	this.date = date;
}
public Historique() {
	super();
	// TODO Auto-generated constructor stub
}
public long getId() {
	return Id;
}
public void setId(long id) {
	Id = id;
}
public String getActeur() {
	return Acteur;
}
public void setActeur(String acteur) {
	Acteur = acteur;
}
public String getAction() {
	return Action;
}
public void setAction(String action) {
	Action = action;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
}
