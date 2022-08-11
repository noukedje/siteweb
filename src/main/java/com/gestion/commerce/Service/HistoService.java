package com.gestion.commerce.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.Historique;
import com.gestion.commerce.Repository.HistoRepository;

@Service
public class HistoService {
	@Autowired
	HistoRepository hr;
   public void saveHisto(Historique histo) {
	   hr.save(histo);
   }
   public void delHisto(long id) {
	   hr.deleteById(id);
   }
   public Historique getHisto(long id) {
	  return hr.findById(id).get();
   }
   public Iterable<Historique> listhistorique(){
   	return hr.findAll();
   }
}
