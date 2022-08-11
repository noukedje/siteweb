package com.gestion.commerce.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gestion.commerce.Model.LigneCommande;
import com.gestion.commerce.Service.LigneCommandeService;

@RequestMapping(value="comline")
@Controller
public class LigneCommandeController {
   @Autowired
   LigneCommandeService lcs;
   @GetMapping("/all")
   public String listLigneCommandes(Model model) {
	   Iterable<LigneCommande> lc=lcs.listLigneCommandes();
	   model.addAttribute("listecommandes", lc);
	   return "pagelistecommandes";
   }
   @GetMapping("/add")
   public String AjouterLigneCommande(Model model) {
	   model.addAttribute("listecommande", new LigneCommande());
	   model.addAttribute("listecdes", lcs.listLigneCommandes());
	   return "pagelistecommandes";
   }
   @PostMapping("/save")
   public String saveLigneCommande(@ModelAttribute(name="lignecommande") LigneCommande lc) {
	   lcs.saveLigneCommande(lc);
	   return "pagelistecommandes";
   }
   @DeleteMapping("/del/{id}")
   public String SupprimerLigneCommande(@PathVariable(name="id") long id) {
	   lcs.delLigneCommande(id);
	   return "redirect:/all";
   }
   @PutMapping("/edit/{id}")
   public ModelAndView ModifierLigneCommande(@PathVariable(name="id") long id) {
	   ModelAndView mav=new ModelAndView("pagelistecommandes");
	   LigneCommande lc=lcs.getLigneCommande(id);
	   mav.addObject("listecommandes", lc);
	   mav.addObject("listecdes", lcs.listLigneCommandes());
	   return mav;
   }
}
