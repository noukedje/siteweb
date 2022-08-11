package com.gestion.commerce.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Service {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long idservice;
  private String servicename;
  private String contacts;
  
public Service() {
	  super();
  }
public String getContacts() {
	return contacts;
}
public void setContacts(String contacts) {
	this.contacts = contacts;
}
public long getIdservice() {
	return idservice;
}
public void setIdservice(long idservice) {
	this.idservice = idservice;
}
public String getServicename() {
	return servicename;
}
public void setServicename(String servicename) {
	this.servicename = servicename;
}
  
}
