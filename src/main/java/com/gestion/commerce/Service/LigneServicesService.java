package com.gestion.commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.LigneService;
import com.gestion.commerce.Repository.LigneServiceRepository;

@Service
public class LigneServicesService {
	 @Autowired
	   LigneServiceRepository ligservrepo;
	   public LigneService getLigneService(long id) {
		   return ligservrepo.getById(id);
	   }
	   public Iterable<LigneService> listLigneServices(){
		   return ligservrepo.findAll();
	   }
	   public void saveLigneService(LigneService lc) {
		   ligservrepo.save(lc);
	   }
	   public void delLigneService(long id) {
		   ligservrepo.deleteById(id);
	   }
}
