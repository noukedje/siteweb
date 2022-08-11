package com.gestion.commerce.Service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.CommandeProduits;
import com.gestion.commerce.Model.LigneCommande;
import com.gestion.commerce.Repository.LigneCommandeRepository;

@Service 
public class LigneCommandeService {
   @Autowired
   LigneCommandeRepository ligcderepo;
   @PersistenceContext
   private EntityManager em;
   
   public LigneCommande getLigneCommande(long id) {
	   return ligcderepo.getById(id);
   }
   public Iterable<LigneCommande> listLigneCommandes(){
	   return ligcderepo.findAll();
   }
   public void saveLigneCommande(LigneCommande lc) {
	   ligcderepo.save(lc);
   }
   public void delLigneCommande(long id) {
	   ligcderepo.deleteById(id);
   }
  
}
