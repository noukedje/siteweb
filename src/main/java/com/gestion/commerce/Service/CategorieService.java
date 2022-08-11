package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.Produit;
import com.gestion.commerce.Repository.CategorieRepository;

@Service
public class CategorieService {
    @Autowired 
    CategorieRepository catrepo;
    public void SaveCategorie(Categorie cat) {
    	catrepo.save(cat);
    }
    public void DelCategorie(long idcat) {
    	catrepo.deleteById(idcat);
    }
    public List<Categorie> ListerCategories (){
    	return catrepo.findAll();
    }
    public Categorie getCategorie(Long id) {
    	return catrepo.getById(id);
    }
    public Boolean CategorieExists(long id) {
    	return catrepo.existsById(id);
    }
    public List<Produit> getProductsByCategory(long id){
    	Categorie cat=catrepo.getById(id);
    	List<Produit> pdts=cat.getProduits();
    	return pdts;
    }
    public int getCatSize() {
    	List<Categorie> listcat=catrepo.findAll();
    	return listcat.size();
    }
}
