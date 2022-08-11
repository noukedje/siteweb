package com.gestion.commerce.Model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.commerce.Service.ProduitService;


@Entity
public class Panier {
	/*private Customer customer;
	private Address deliveryAddress;
	private Payment payment;*/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Temporal(TemporalType.DATE)
	private Date date;
	//@Transient
	//private double total;
	//@Transient
	//private int size;
	@JsonIgnore //pour éviter l'erreur : "Cannot call sendError after the response has been committed->items null"
	@OneToMany(mappedBy="panier",fetch = FetchType.EAGER, cascade=CascadeType.ALL)//ie qu'il remplit automatiquement Lignecommande dans Panier
	private List<LigneCommande> items;
	//@Transient
	//private String sessionToken;

	public Panier()
	{
		items = new ArrayList<LigneCommande>();
		/*customer = new Customer();
		deliveryAddress = new Address();
		payment = new Payment();*/
	}
	public void addItem(Produit produit, int qte)
	{  int i=0;
		for (LigneCommande lineitem : items) //dans le panier en ligne avec élément(s)
		{
			if (lineitem.getProduit().getIdproduit()==produit.getIdproduit()){//quand le produit existe déjà et est trouvé
				i+=1;
				lineitem.setQtecdee(lineitem.getQtecdee()+qte);//on augmente sa quantité. sa date existe déjà
			    this.items.set(i, lineitem); //on remplace l'ancienne valeur par la nouvelle de même rang
				return;  //il sort de la méthode ici
			}
		}
		LigneCommande item = new LigneCommande(produit,qte);
		SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String datecde=date.format(new Date());  
		item.setDate(datecde);
		item.setPanier(this);
		this.items.add(item);	
		this.setDate(Calendar.getInstance().getTime());//ou en paramètre new Date(), mais sans la maitrise de l'heure
	}
	
	public void updateItemQuantity(Produit produit, int quantity)
	{
		int i=0;
		for (LigneCommande lineitem : items) //dans le panier en ligne avec élément(s)
		{
			if (lineitem.getProduit().getIdproduit()==produit.getIdproduit()){//quand le produit existe déjà et est trouvé
				i+=1;
				lineitem.setQtecdee(quantity);//on définit sa nouvelle quantité
			    this.items.set(i, lineitem); //on remplace l'ancienne valeur par la nouvelle de meême rang
				return;  //il sort de la méthode ici
			}
		}
	}
	
	public void removeItem(long id)
	{
		LigneCommande  item = null;
		for (LigneCommande lineitem : items)
		{
			if(lineitem.getProduit().getIdproduit()==id){
				item = lineitem;
				break;
			}
		}
		if(item != null){
			items.remove(item);
		}
	}
	
	public void clearItems()
	{
		items = new ArrayList<LigneCommande>();
	}
	
	public int getItemCount()
	{
		int count = 0;
		for (LigneCommande lineitem : items) { //lineitem est null
			count +=  lineitem.getQtecdee();
		}
		return count;
	}
	
	public List<LigneCommande> getItems()
	{
		return items;
	}
	public void setItems(List<LigneCommande> items)
	{
		this.items = items;
	}
	public double getTotalAmount()
	{
		double amount=0.0;
		for (LigneCommande lineitem : items) //lineitem est null
		{
			amount = amount+lineitem.getSubTotal();
		}
		return amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
