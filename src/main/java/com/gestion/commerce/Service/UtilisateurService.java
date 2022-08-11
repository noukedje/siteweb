package com.gestion.commerce.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.commerce.Model.Utilisateur;
import com.gestion.commerce.Repository.UtilisateurRepository;


@Service 
public class UtilisateurService {
   @Autowired
   UtilisateurRepository userrepo;

   public void AttribuerRole(String rolename, long iduser) {
	   Utilisateur user=userrepo.getById(iduser);
	   user.setRole(rolename);
   }
   public void saveUtilisateur(Utilisateur user) {
	   userrepo.save(user);
   }
   public void delUtilisateur(long iduser) {
	   userrepo.deleteById(iduser);
   }
   public List<Utilisateur> listUtilisateurs(){
	   return userrepo.findAll();
   }
   public Utilisateur getUtilisateur(long iduser) {
	   return userrepo.getById(iduser);
   }
  
	
}
