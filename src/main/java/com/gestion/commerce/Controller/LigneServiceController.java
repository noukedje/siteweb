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

import com.gestion.commerce.Model.LigneService;
import com.gestion.commerce.Service.LigneServicesService;

@RequestMapping(value="/servicesline")
@Controller
public class LigneServiceController {
   @Autowired
   LigneServicesService lss;
   
  /* public void EnvoyerMail(LigneService ls) {
	   
   }
   public void EnvoyerSMS(LigneService ls) {
	  String telexp="237679931809";
	  String teldest=ls.getClient().getTel();
	  String msg="Service:"+ls.getService().getServicename()+" | Lieu:"+ls.getClient().getLieu()+"| Délai:"+ ls.getDelai();
	  //envoi
   }*/
   @PostMapping("/save")
	public String EnregisterLigneService(@ModelAttribute(name="ligneservice") LigneService ls) { //Commander 1 service
	   lss.saveLigneService(ls);
	   //http://obitsms.com/api/bulksms?username=".$this->login."&password=".$this->pass."&sender=".$this->sender_name."&destination=237".$phone."&message=".$this->message;
	   return "ligneservices";
	}
	@GetMapping(value="/all")
	public String ListerServicesCommandes(Model model) {
		Iterable<LigneService> ls=lss.listLigneServices();
		model.addAttribute("listelignesservices", ls);
		return "ligneservices";
	}
	@PostMapping("/add")
	public String AjouterLigneService(Model model) {
	   model.addAttribute("ligneservice", new LigneService());
	   model.addAttribute("listeligneservices", lss.listLigneServices());
	   return "ligneservice";
	}
	@DeleteMapping("/del/{id}")
	public String SupprimerLigneService(@PathVariable(name="id") long id) {
		lss.delLigneService(id);
		return "redirect:/all";
	}
	@PutMapping("/edit/{id}")
	public ModelAndView ModifierLigneService(@PathVariable(name="id") long id) {
		ModelAndView mav=new ModelAndView("ligneservices");
		LigneService ls=lss.getLigneService(id);
		mav.addObject("lignservice", ls);
		mav.addObject("listeligneservices", lss.listLigneServices());
		return mav;
	}
}
