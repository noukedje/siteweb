package com.gestion.commerce.Controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gestion.commerce.Service.HistoService;
import com.gestion.commerce.Service.UtilisateurService;
import com.gestion.commerce.Model.Historique;
import com.gestion.commerce.Model.Utilisateur;
import com.gestion.commerce.Security.UserDetailsServiceImpl;

@Controller
public class UtilisateurController {
	@Autowired
	UserDetailsServiceImpl userdetails;
	@Autowired
	UtilisateurService userserv;
	@Autowired
	HistoService histos;

	
	public boolean areUserPasswordsEqual(String rawpwd, String encodedpwd) {
		BCryptPasswordEncoder b = new BCryptPasswordEncoder();
		return b.matches(rawpwd, encodedpwd);
	}
	public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } 
	/*@PostMapping("/users/auth")
	public String ConnectUser(@ModelAttribute("utilisateur") Utilisateur user, Model model) {
		UserDetails userauth;
		
		try {
			userauth=userdetails.loadUserByUsername(user.getUsername());
			System.out.println("Account= " + userauth.getUsername()+" pass "+ userauth.getPassword());
			if (userauth.equals(null)) {
				model.addAttribute("message", "Le nom d'utilisateur est erroné.");
			    return "signin";
			}
		}
		catch(UsernameNotFoundException u) {
			model.addAttribute("message", u.getMessage());
		    return "signin";
		}
		if (!areUserPasswordsEqual(user.getPassword1(), userauth.getPassword())) {
			model.addAttribute("message", "Le mot de passe est erroné.");
			return "signin";
		}
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("welcome", "Bienvenue dans le dashboard "+userDetails.getUsername());
		return "/admin";  //pour changer le message de la barre d'adresse
	}*/
    @GetMapping("/login")
	public String gotoConnectionPage(Model model, HttpServletRequest request) {
    	String lang=(String)request.getSession(true).getAttribute("LOCALE");
    	String page=(lang.equals("fr"))? "entree" : "signin";	
        return page;
	}
	@PostMapping("/users/save")
	public String EnregistrerUtilisateur(@ModelAttribute("utilisateur") Utilisateur user, Model model) {
		if (!user.getPassword1().trim().equals(user.getPassword2().trim())) { //String est un objet et non un primitif, donc equals et non !=
			model.addAttribute("message", "Les mots de passe ne correspondent pas.");
		    return "redirect:/admin/users";  //Quand il y a erreur, on reste sur place
		}
		user.setPassword1(passwordEncoder().encode(user.getPassword1().trim()));//crypter le mot de passe avant de le stocker
		userserv.saveUtilisateur(user);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Ajout utilisateur : "+user.getUsername(),new Date());
      	histos.saveHisto(h);
	    return "redirect:/admin/users";
	}
	@GetMapping("/admin/users")
	public String Administrerutilisateurs(Model model) {
		Iterable<Utilisateur> listuser=userserv.listUtilisateurs();
		model.addAttribute("listeusers", listuser);
		return "adminusers";
	}
	@GetMapping("/users/histo")
	public String OuvrirHisto(Model model) {
		Iterable<Historique> histo =histos.listhistorique();
		model.addAttribute("historiques", histo);
		return "historiques";
	}
	@GetMapping("/users/new")
	public String Creerutilisateur(Model model) {
		model.addAttribute("utilisateur", new Utilisateur());
		return "newuser";
	}
	@GetMapping("/users/edit/{id}")
	public ModelAndView Editerutilisateur(@PathVariable(name="id") long id) {
		ModelAndView mav=new ModelAndView("edituser");
		Utilisateur user=userserv.getUtilisateur(id);
		mav.addObject("utilisateur", user);
		return mav;	
	}
	@RequestMapping("/users/update/{id}")
	public String Modifierutilisateur(@PathVariable(name="id") long id,@ModelAttribute("utilisateur") Utilisateur utilisateur, RedirectAttributes attributes, Model model) {
		if (!utilisateur.getPassword1().trim().equals(utilisateur.getPassword2().trim())) { //String est un objet et non un primitif, donc equals et non !=
			attributes.addFlashAttribute("message", "Les mots de passe ne correspondent pas.");
		    return "redirect:/users/edit/"+id;  //Quand il y a erreur, on reste sur place
		}
		Utilisateur user=userserv.getUtilisateur(id);
		//user.setEmail(utilisateur.getEmail());
		user.setPassword1(passwordEncoder().encode(utilisateur.getPassword1().trim()));//crypter le mot de passe avant de le stocker
		//user.setPassword2(utilisateur.getPassword2());
		user.setRole(utilisateur.getRole());
		user.setUsername(utilisateur.getUsername());
		user.setUserid(utilisateur.getUserid());
		user.setPermis(utilisateur.getPermis());
		userserv.saveUtilisateur(user);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Edition utilisateur : "+user.getUsername(),new Date());
      	histos.saveHisto(h);
		Iterable<Utilisateur> listuser=userserv.listUtilisateurs();
		model.addAttribute("listeusers", listuser);
		return "adminusers";	
	}
	
	@RequestMapping("/users/del/{id}")
	public String Supprimerutilisateur(@PathVariable(name="id") long iduser) {
		userserv.delUtilisateur(iduser);
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
      	Historique h=new Historique(username,"Suppression utilisateur : "+userserv.getUtilisateur(iduser).getUsername(),new Date());
      	histos.saveHisto(h);
		return "redirect:/admin/users";
	}
    
}
