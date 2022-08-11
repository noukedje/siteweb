package com.gestion.commerce.Controller;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import com.gestion.commerce.Model.Commande;
import com.gestion.commerce.Model.CommandeProduits;
import com.gestion.commerce.Model.CommandeServices;
import com.gestion.commerce.Service.CommandeProduitsService;
import com.gestion.commerce.Service.CommandeService;
import com.gestion.commerce.Service.CommandeServicesService;
import com.gestion.commerce.Service.ProduitService;
import com.gestion.commerce.Service.VentesService;

@Controller
public class CommandeController {
    @Autowired
	CommandeServicesService cdeserv;
    @Autowired
    CommandeService cdeservice;
    @Autowired
    CommandeProduitsService cps;
    @Autowired
    ProduitService pdtserv;
    @Autowired
    VentesService vs;
    
    @PersistenceContext
    private EntityManager em; 
       
    @GetMapping("/admin/commandes")
    public String listCommandes(Model model) {
    List<CommandeServices> listecdes=cdeserv.ListerCommandesServices();
    List<CommandeProduits> listecdepdts= cps.listCommandeProduits();
    model.addAttribute("commandeproduits",listecdepdts);
    model.addAttribute("commandeservices", listecdes);
    //Pour les données graphiques de l'histogramme
    List<Long> mont=new ArrayList<>();
    List<String> nom=new ArrayList<>();
	for (CommandeProduits cp:cps.listCommandeProduits()) {  
	  nom.add(cp.getDesignation());
	  mont.add(cp.getPrix()*cp.getQtecdee());
	}
	model.addAttribute("mont", mont);
	model.addAttribute("nom", nom);
	//Pour les données graphiques du cercle (Pie)
	model.addAttribute("piechartdata",vs.ListVentes());
		
    return "commandes";
    }
  
    @GetMapping("/commandes/add")
    public String AjouterCommande(Model model) {
    	model.addAttribute("commande",new Commande());
    	model.addAttribute("listecdes", cdeservice.listCommandes());
    	return "commandes";
    }
    
    @PostMapping("/commandes/save")
    public String EnregistrerCommande(@ModelAttribute("commande") Commande cde){
    	cdeservice.saveCommande(cde);
    	return "redirect:/all";
    }
    
    @DeleteMapping("/commandes/del/{id}")
    public String SupprimerCommande(@PathVariable(name="id") long id) {
    	cdeservice.delCommande(id);
    	return "redirect:/all";
    }
    
    @PutMapping("/commandes/edit/{id}")
    public ModelAndView ModifierCommande(@PathVariable(name="id") long id) {
    	ModelAndView mav=new ModelAndView("pagecommandes");
    	Commande cde=cdeservice.getCommande(id);
    	mav.addObject("commande", cde);
    	mav.addObject("listecdes", cdeservice.listCommandes());
    	return mav;
    }
}
