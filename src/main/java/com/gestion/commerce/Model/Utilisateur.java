package com.gestion.commerce.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.stereotype.Component;

@Component  //A cause du service d'envoi de mail
@Entity
public class Utilisateur {
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long userid;
  private String username;
  private String role;
  //private String emailAddress;
  private String permis; 
  @Column(name="password")
  private String password1; 
  private String password2;
  private boolean active;
  
public Utilisateur() {
	super();
	// TODO Auto-generated constructor stub
}
/*public String getEmailAddress() {
    return emailAddress;	
}
public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;	
}*/
public String getPermis() {
	return permis;
}

public String getPassword2() {
	return password2;
}

public void setPassword2(String password2) {
	this.password2 = password2;
}

public void setPermis(String permis) {
	this.permis = permis;
}

/*public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}*/
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword1() {
	return password1;
}
public void setPassword1(String password1) {
	this.password1 = password1;
}
public long getUserid() {
	return userid;
}
public void setUserid(long userid) {
	this.userid = userid;
}
public boolean isActive() {
	return active;
}
public void setActive(boolean actif) {
	this.active = actif;
} 

}
