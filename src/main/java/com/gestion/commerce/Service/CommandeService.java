package com.gestion.commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.Commande;
import com.gestion.commerce.Repository.CommandeRepository;

@Service
public class CommandeService {
    @Autowired
    CommandeRepository cderepo;
    public Commande getCommande(long id) {
     return cderepo.findById(id).get();
    }
    public void delCommande(long id) {
    	cderepo.deleteById(id);
    }
    public Iterable<Commande> listCommandes(){
    	return cderepo.findAll();
    }
    public void saveCommande(Commande cde) {
    	cderepo.save(cde);
    }
}
