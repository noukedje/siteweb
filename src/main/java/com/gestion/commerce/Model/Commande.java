package com.gestion.commerce.Model;
import javax.persistence.*;
import java.util.*;
import javax.validation.constraints.*;

@Entity
public class Commande{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long idcommande; 	
  @NotEmpty
  private Date datecommande;
  private double totalcde;
  public Commande(){
	  super();
  }
public long getIdcommande() {
	return idcommande;
}
public void setIdcommande(long idcommande) {
	this.idcommande = idcommande;
}
public Date getDatecommande() {
	return datecommande;
}
public void setDatecommande(Date datecommande) {
	this.datecommande = datecommande;
}
public double getTotalcde() {
	return totalcde;
}
public void setTotalcde(double totalcde) {
	this.totalcde = totalcde;
}

}