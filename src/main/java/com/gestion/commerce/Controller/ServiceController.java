package com.gestion.commerce.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.CommandeServices;
import com.gestion.commerce.Model.Historique;
import com.gestion.commerce.Model.Service;
import com.gestion.commerce.Model.Utilisateur;
import com.gestion.commerce.Service.CategorieService;
import com.gestion.commerce.Service.CommandeServicesService;
import com.gestion.commerce.Service.HistoService;
import com.gestion.commerce.Service.MailService;
import com.gestion.commerce.Service.ProduitService;
import com.gestion.commerce.Service.ServicesService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Controller

public class ServiceController {
	@Autowired
	ServicesService ss;
	@Autowired
	CategorieService catserv;
	@Autowired
	MailService notificationService;
	@Autowired
	Utilisateur user;
	@Autowired
	CommandeServicesService css;
	@Autowired
	ProduitService pdt;
    @Autowired
    HistoService histos;

	
	public String sendMail(String email,String mail) { 
		try {
			notificationService.sendEmail(email,mail);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Your mail has been send to the user.";
	}
	public ResponseEntity<String> sendSMS(String sms) {

        Twilio.init(System.getenv("TWILIO_ACCOUNT_SID"), System.getenv("TWILIO_AUTH_TOKEN"));

        Message.creator(new PhoneNumber("+237679931809"),
                        new PhoneNumber("+237694693057"), sms).create();

        return new ResponseEntity<String>("Message sent successfully", HttpStatus.OK);
    }
	@PostMapping("/services/save")
	public String EnregistrerService(@ModelAttribute("service") Service serv) {
		ss.saveService(serv);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Ajout service : "+serv.getServicename(),new Date());
      	histos.saveHisto(h);
		return "redirect:/admin/services";
	}
	@RequestMapping("/services/commandes")
	public String CommanderService(@ModelAttribute("commandeservices") CommandeServices serv, Model model, HttpServletRequest request) {
	css.saveCommandeServices(serv);	
	String trameservice=serv.getCivilite()+" "+serv.getNomclient()+" "+serv.getTel()+" voudrait le service nommé "+serv.getNomservice()+" pour le "+serv.getDatecde()+" à "+serv.getLieu();	
	//sendSMS(trameservice);
	//sendMail("referenceplus15@gmail.com",trameservice);
	String lang=(String)request.getSession(true).getAttribute("LOCALE");
	if (lang.equals("fr"))	
	    model.addAttribute("message", "Votre  commande de service a été enregitrée et notifiée.");
	else 
		model.addAttribute("message", "Your order of service has been registered and notified.");
	model.addAttribute("listecommandeservices",	css.ListerCommandesServices());
	model.addAttribute("listecategories", null); //lorsqu'on veut commander les services, le formulaire de commande sans les produits doit sortir
	model.addAttribute("listeproduits", null);
	model.addAttribute("about", null);
	SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
	String datecde=date.format(new Date());  
	CommandeServices cs=new CommandeServices(serv.getNomclient(),serv.getCivilite(),serv.getTel(),serv.getNomservice(),datecde,serv.getLieu());
	css.saveCommandeServices(cs);  //on sauvegarde la commande
	return "redirect:/services/all";
	}
	@RequestMapping("/services/new")
	public String CreerService(Model model) {
		model.addAttribute("service", new Service());
		return "newservice";
	}
	@GetMapping("/services/edit/{id}")
	public ModelAndView EditerService(@PathVariable(name="id") long id) {
		ModelAndView mav=new ModelAndView("editservice");
		Service serv=ss.getService(id);
		mav.addObject("service", serv);
		return mav;	
	}
	@RequestMapping("/services/update/{id}")
	public ModelAndView ModifierService(@PathVariable(name="id") long id,@ModelAttribute("service") Service service) {
		ModelAndView mav=new ModelAndView("adminservices");
		Service serv=ss.getService(id);
		serv.setContacts(service.getContacts());
		serv.setIdservice(service.getIdservice());
		serv.setServicename(service.getServicename());
		ss.saveService(serv);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Edition service : "+serv.getServicename(),new Date());
      	histos.saveHisto(h);
		Iterable<Service> listserv=ss.listServices();
		mav.addObject("listeservices", listserv);
		return mav;	
	}
	@GetMapping("/admin/services")
	public String AdministrerServices(Model model) {
		Iterable<Service> listserv=ss.listServices();
		model.addAttribute("listeservices", listserv);
		return "adminservices";
	}
	@GetMapping("/services/all")
	public String ListerServices(Model model, HttpServletRequest request) {
		List<Categorie> listcat=catserv.ListerCategories();
        model.addAttribute("listecategories", null); //pour le contenu, une seule catégorie peut y apparaitre, selon l'internaute
        model.addAttribute("menucategories", listcat); //pour le menu (toutes les catégories doivent toujours y apparaitrent
    	model.addAttribute("listeservices",	ss.listServices());//null, pour éviter que le formulaire de services apparaisse avec les produits
    	model.addAttribute("commandeservices", new CommandeServices());
    	String lang=(String)request.getSession(true).getAttribute("LOCALE");
    	String page=(lang.equals("fr"))? "services" : "services_en";	
        return page;
	}
	@RequestMapping("/services/del/{id}")
	public String SupprimerService(@PathVariable(name="id") long idserv) {
		ss.delService(idserv);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Ajout catégorie "+ss.getService(idserv),new Date());
      	histos.saveHisto(h);
		return "redirect:/admin/services";
	}
}
