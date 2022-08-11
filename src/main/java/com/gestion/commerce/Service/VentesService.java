package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.Ventes;
import com.gestion.commerce.Repository.VentesRepository;

@Service
public class VentesService {
	@Autowired
	VentesRepository vr;
	
   public List<Ventes> ListVentes(){
	   return vr.findAll();
   }
   
   public void saveVentes(Ventes vte) {
	   vr.save(vte);
   }
}
