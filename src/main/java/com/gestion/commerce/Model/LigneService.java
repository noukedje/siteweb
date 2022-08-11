package com.gestion.commerce.Model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name="ligneservice")
public class LigneService {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   @ManyToOne
   @JoinColumn(name="codeservice")
   private Service service;
   @Size(min=10,max=10)
   private Date date;  
   @Size(min=10,max=10)
   private Date delai;
public LigneService() {
	super();
	// TODO Auto-generated constructor stub
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

public Service getService() {
	return service;
}
public void setService(Service service) {
	this.service = service;
}
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public Date getDelai() {
	return delai;
}
public void setDelai(Date delai) {
	this.delai = delai;
}
}
