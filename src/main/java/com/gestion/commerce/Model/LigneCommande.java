package com.gestion.commerce.Model;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="lignecommande")
public class LigneCommande{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	/*@ManyToOne
	@JoinColumn(name="codecommande")
	@NotNull
	private Commande commande;*/
	@ManyToOne
	@JoinColumn(name="codeproduit")
	@NotNull
	private Produit produit;
	@NotNull
	private int qtecdee;
	//@Temporal(TemporalType.DATE)
	private String date;
	@ManyToOne
	@JoinColumn(name="codepanier")
	private Panier panier;
	public LigneCommande(){
		super();
	}
	public LigneCommande(Produit pdt,int qte) {
		produit=pdt;
		qtecdee=qte;
	}
	public long getSubTotal() {
		return qtecdee*produit.getPrix();
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit idpdt) {
		this.produit = idpdt;
	}
	public int getQtecdee() {
		return qtecdee;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setQtecdee(int qtecdee) {
		this.qtecdee = qtecdee;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Panier getPanier() {
		return panier;
	}
	public void setPanier(Panier panier) {
		this.panier = panier;
	}
	
	
}