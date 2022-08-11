package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Repository.ServiceRepository;

@Service
public class ServicesService {
   @Autowired
   ServiceRepository servrepo;
   public List<com.gestion.commerce.Model.Service> listServices(){
	  return servrepo.findAll();
   }
   public void saveService(com.gestion.commerce.Model.Service serv) {
	   servrepo.save(serv);
   }
   public void delService(long idserv) {
	   servrepo.deleteById(idserv);
   }
   public com.gestion.commerce.Model.Service getService(long idserv){
	   return servrepo.getById(idserv);
   }
}
