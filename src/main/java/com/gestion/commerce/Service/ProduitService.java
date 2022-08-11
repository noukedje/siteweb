package com.gestion.commerce.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.commerce.Model.Produit;
import com.gestion.commerce.Repository.ProduitRepository;

@Service
@Transactional    //la classe utlise Query (requêtes personnalisées) du repository
public class ProduitService {
   @Autowired
   ProduitRepository pdtrepo;
   
	public void saveProduct(Produit pdt){
	pdtrepo.save(pdt);
	}
	public Produit getProduct(long id){
	return pdtrepo.findById(id).get();
	}
	public void deleteProduct(long id){
	pdtrepo.deleteById(id);
	} 
	public List<Produit> ListerProduits(){
	    return pdtrepo.findAll();
	}
	public List<Produit> FindProduitsByName(String nom){		
		return pdtrepo.searchproducts("%"+nom+"%");
	}
	public List<Produit> ListerNouveauxProduits(){
		return pdtrepo.getNewProducts();
	}
	
	public List<Produit> ListNewProductsByCategorie(long id){
		return pdtrepo.getNewProductsByCategory(id);
	}
}
