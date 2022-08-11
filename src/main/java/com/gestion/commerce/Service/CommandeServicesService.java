package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.CommandeServices;
import com.gestion.commerce.Repository.CommandeServicesRepository;

@Service
public class CommandeServicesService {
    @Autowired
	CommandeServicesRepository csr;
    
    public List<CommandeServices> ListerCommandesServices(){
    	return csr.findAll();
    }
    public void saveCommandeServices(CommandeServices commandeservices) {
    	csr.save(commandeservices);
    }
    public CommandeServices getCommandeServices(long id) {
    	return csr.getById(id);
    }
    public void delCommandeServicesById(long id) {
    	csr.deleteById(id);
    }
    public void delCommandeServicesByEntity(CommandeServices cs) {
    	csr.delete(cs);
    }
}
