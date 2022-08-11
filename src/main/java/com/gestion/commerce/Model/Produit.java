package com.gestion.commerce.Model;
import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.*;
@Entity  
public class Produit implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
@Id
  @GeneratedValue(strategy=GenerationType.AUTO) 
  private long idproduit;
  @Size(min=3)
  private String designation;
  @Size(min=10)
  private String description;
  @Lob
  private byte[] photo;
  private long prix;
  private int qtestock;
  @ManyToOne
  @JoinColumn(name="idcateg")
  private Categorie categorie;
  private boolean nouveau;
  private String delai;
  public Produit(){
	  super();
  }
  @Transient
	public String getMainImagePath(){  //permet d'obtenir l'image dans le thymeleaf
	 return "/photoproduit/"+idproduit;
	}
public Produit(String nom,long prix) {
	designation=nom;
	this.prix=prix;
}
public String getDelai() {
	return delai;
}
public void setDelai(String delai) {
	this.delai = delai;
}
public boolean isNouveau() {
	return nouveau;
}
public void setNouveau(boolean nouveau) {
	this.nouveau = nouveau;
}
public void setIdproduit(long idproduit) {
	this.idproduit = idproduit;
}
public Long getIdproduit() {
	return idproduit;
}
public void setIdproduit(Long idproduit) {
	this.idproduit = idproduit;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public byte[] getPhoto() {
	return photo;
}
public void setPhoto(byte[] photo) {
	this.photo = photo;
}
public long getPrix() {
	return prix;
}
public void setPrix(long prix) {
	this.prix = prix;
}
public int getQtestock() {
	return qtestock;
}
public void setQtestock(int qtestock) {
	this.qtestock = qtestock;
}
public Categorie getCategorie() {
	return categorie;
}
public void setCategorie(Categorie categorie) {
	this.categorie = categorie;
}
}