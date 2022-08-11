package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.CommandeProduits;
import com.gestion.commerce.Model.Ventes;
import com.gestion.commerce.Repository.CommandeProduitsRepository;

@Service
public class CommandeProduitsService {
   @Autowired
   CommandeProduitsRepository cpr;
   
   public void saveCommandeProduits(CommandeProduits cp) {
	   cpr.save(cp);
   }
   
   public List<CommandeProduits> listCommandeProduits(){
	   return cpr.findAll();
   }
   
}
