package com.gestion.commerce.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.Historique;

import com.gestion.commerce.Model.Produit;
import com.gestion.commerce.Service.CategorieService;
import com.gestion.commerce.Service.HistoService;
import com.gestion.commerce.Service.ProduitService;
import com.gestion.commerce.Service.UtilisateurService;

@Controller      //Avec @RestController, les templates ne sortent pas
public class HomeController {
	@Autowired
	CategorieService catserv;
	@Autowired
	ProduitService pdt;
	@Autowired
	UtilisateurService user;
	@Autowired
	HistoService histos;
	
    @RequestMapping("/home/{lg}")
    public String SetAccueil(@PathVariable("lg") String lang, Model model, HttpServletRequest request) {  
       List<Categorie> listcat=catserv.ListerCategories();
       model.addAttribute("listecategories", listcat); //pour le contenu, une seule catégorie peut y apparaitre, selon l'internaute
       model.addAttribute("menucategories", listcat); //pour le menu (toutes les catégories doivent toujours y apparaitrent
       List<Produit> list =pdt.ListerProduits();
       model.addAttribute("listeproduits", list);
       List<Produit> newpdts =pdt.ListerNouveauxProduits();
       model.addAttribute("listenouveauxproduits", newpdts);
       request.getSession().setAttribute("LOCALE", lang);
       String page=(lang.equals("fr"))? "accueil" : "welcome";	
       return page;
    }
    @RequestMapping(value = { "/admin" }, method = RequestMethod.GET)
    public String OpenSession(Model model) {
       String username=SecurityContextHolder.getContext().getAuthentication().getName();
       model.addAttribute("welcome", "Bienvenue dans le dashboard "+username);
     	Historique h=new Historique(username,"Connexion utilisateur : "+username,new Date());
     	histos.saveHisto(h);
       return "admin";
    }
    @GetMapping("/logout1") //nécessaire pour contrôler la déconnexion
    public String CloseSession() {
    	String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Déconnexion utilisateur : "+username,new Date());
      	histos.saveHisto(h);
      	return "redirect:/logout";
    }
	@GetMapping("/about")
	public String HomePresentation(Model model, HttpServletRequest request) {
		List<Categorie> listcat=catserv.ListerCategories();
        model.addAttribute("listecategories", null); //pour le contenu, une seule catégorie peut y apparaitre, selon l'internaute
        model.addAttribute("menucategories", listcat); //pour le menu (toutes les catégories doivent toujours y apparaitrent
    	model.addAttribute("listeproduits", null);
    	model.addAttribute("listenouveauxproduits", null);
    	String lang=(String)request.getSession(true).getAttribute("LOCALE");
        String page=(lang.equals("fr"))? "apropos" : "about";	
        return page;
	}
   @RequestMapping("/403")
   public String accessDenied() {
      return "403";
   }

}
