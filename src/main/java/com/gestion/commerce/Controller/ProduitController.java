package com.gestion.commerce.Controller;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.Historique;
import com.gestion.commerce.Model.Produit;
import com.gestion.commerce.Model.UploadProductForm;
import com.gestion.commerce.Service.CategorieService;
import com.gestion.commerce.Service.HistoService;
import com.gestion.commerce.Service.ProduitService;

@Controller
public class ProduitController {
   @Autowired
   private ProduitService pdtservice;  
   @Autowired
   private CategorieService catserv;
   @Autowired
   private HistoService histos;

   @GetMapping("/produits/{id}")
   public String displayProduct(@PathVariable("id") long id, Model model,HttpServletRequest request) {
	   Produit pdt=pdtservice.getProduct(id);
	   model.addAttribute("produit", pdt);
	   List<Categorie> listcat=catserv.ListerCategories();
	   model.addAttribute("menucategories", listcat); 
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   String page=(lang.equals("fr"))? "produit" : "product";	
       return page;
   }
   
   @RequestMapping("/admin/produits")  //@RequestMapping parce qu'en laissant @PostMapping, ça disait Méthode ou requête Get Non autorisée
	public String AdministrerProduits(Model model) {
		List<Produit> listepdts=pdtservice.ListerProduits();
		model.addAttribute("listeproduits", listepdts);
		return "adminproduits";
	}
   @PostMapping("/produits/search")  //si c'était : /produits/search/?nom={valeur}  ou HttpServletRequest req->req.getParameter("nom")
   public String ChercherProduits(@RequestParam("nom") String nom, Model model, HttpServletRequest request) {
	  List<Produit> pdts= pdtservice.FindProduitsByName(nom);
	  model.addAttribute("listeproduits", pdts);
	  List<Categorie> catpdt=new ArrayList<>();  //création d'une liste vierge pour stocker les catégories issues des produits cherchés
	  for (Produit pdt : pdts) {
		catpdt.add(pdt.getCategorie());
	  }
	  model.addAttribute("listecategories", catpdt);   
	  List<Categorie> listcat=catserv.ListerCategories();
	  model.addAttribute("menucategories", listcat); 
	  String lang=(String)request.getSession(true).getAttribute("LOCALE");
	  String page=(lang.equals("fr"))? "accueil" : "welcome";	
      return page;
   }
   @RequestMapping("/produits/new")
   public String NewProduct(Model model) {
	  model.addAttribute("uploadprodform", new UploadProductForm());
	  Collection<Categorie> lc=catserv.ListerCategories();
	  model.addAttribute("listecategories", lc);	  
	  return "newproduct";
   }
   @GetMapping("/produits/edit/{id}")
   public ModelAndView EditerProduit(@PathVariable("id") long id) {
	  ModelAndView mav=new ModelAndView("editproduct");
	  Produit pdt= pdtservice.getProduct(id);
	  UploadProductForm upf=new UploadProductForm();
	  upf.setDescription(pdt.getDescription());
	  upf.setDesignation(pdt.getDesignation());	  
	  upf.setIdcategorie(pdt.getCategorie().getIdcategorie());
	  upf.setCategorie(pdt.getCategorie());
	  upf.setQtestock(pdt.getQtestock());
	  upf.setPrix(pdt.getPrix());
	  upf.setIdproduit(pdt.getIdproduit());
	  mav.addObject("uploadprodform", upf); 	  
	  Collection<Categorie> lc=catserv.ListerCategories();
	  mav.addObject("listecategories", lc);	
	  return mav;
   }
   @RequestMapping("/produits/save") 
   public String EnregistrerProduit(HttpServletRequest request, @ModelAttribute("uploadprodform") @Valid UploadProductForm myform, BindingResult br,Model model)  { 
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   if (br.hasErrors()) {//@Valid permet de valider le formulaire myform et de mettre les résultats dans BindingResult
		   if (lang.equals("fr"))
			   model.addAttribute("message", "Erreur lors du transfert du formulaire.");
		   else model.addAttribute("message", "Error while transferring the form.");
	      return "adminproduits";
	  }
	 // Root Directory.	 
	  String uploadRootPath = request.getServletContext().getRealPath("images/produits");
	  File uploadRootDir = new File(uploadRootPath);
	  // Create directory if it not exists.
	  if (!uploadRootDir.exists()) {
	     uploadRootDir.mkdirs();
	  }
	  MultipartFile[] fileDatas = myform.getFileDatas();
	  
	  for (MultipartFile fileData : fileDatas) {
	      // Client File Name
	      String name = fileData.getOriginalFilename();
	      if (name != null && name.length() > 0) {
	         try {
	            // Create the file at server
	            File serverFile = new File(uploadRootDir.getAbsolutePath() +File.separator + name);
	            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	            stream.write(fileData.getBytes());
	            stream.close();
	            //Save the category to the database
	            Produit pdt=new Produit();
	            pdt.setDescription(myform.getDescription());
	            pdt.setDesignation(myform.getDesignation());
	            pdt.setQtestock(myform.getQtestock());
	            pdt.setPrix(myform.getPrix());
	            Categorie cat=catserv.getCategorie(myform.getIdcategorie());
	            pdt.setCategorie(cat);
	          	pdt.setPhoto(fileData.getBytes());
	          	pdt.setDelai(myform.getDelai());
	          	pdt.setNouveau(myform.isNouveau());
	          	pdtservice.saveProduct(pdt);
	          	String username=SecurityContextHolder.getContext().getAuthentication().getName();
	          	Historique h=new Historique(username,"Ajout produit : "+myform.getDesignation(),new Date());
	          	histos.saveHisto(h);
	         } catch (Exception e) {
	        	 if (lang.equals("fr"))
	            	 model.addAttribute("message", "Erreur lors de l'écriture du fichier " +name);
	             else model.addAttribute("message", "File writing error " +name);
	         }
	      }
	   }
	  
	  Iterable<Produit> lc=pdtservice.ListerProduits();//il faudra désormais récupérer les nouveaux produits d'une part et les anciens d'autre part
	  model.addAttribute("listeproduits",lc);          //mais le filtre se fera avec thymeleaf
	  return "redirect:/admin/produits";
  }
   @RequestMapping(value="/photoproduit/{id}", produces=MediaType.MULTIPART_MIXED_VALUE)
   @ResponseBody
   public byte[] photoProduit(@PathVariable("id") long id) throws IOException{
      Produit c=pdtservice.getProduct(id);
      ByteArrayInputStream bais=new ByteArrayInputStream(c.getPhoto());
	  return bais.readAllBytes();
   } 
   @PostMapping("/produits/update/{id}")
   public String ModifierProduit(HttpServletRequest request, @PathVariable("id") long id, @ModelAttribute("uploadprodform") UploadProductForm myform, BindingResult br, Model model) {
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   if (br.hasErrors()) {
		   if (lang.equals("fr"))
		   model.addAttribute("message", "Erreur lors du transfert du formulaire.");
		   else model.addAttribute("message", "Error while transferring the form.");
	       return "redirect:/admin/produits";
	   }
	   String uploadRootPath = request.getServletContext().getRealPath("images/produits");
		  File uploadRootDir = new File(uploadRootPath);
	      // Create directory if it not exists.
	      if (!uploadRootDir.exists()) {
	         uploadRootDir.mkdirs();
	      }
	      MultipartFile[] fileDatas = myform.getFileDatas();
	      
	      for (MultipartFile fileData : fileDatas) {
	          // Client File Name
	          String name = fileData.getOriginalFilename();
	          if (name != null && name.length() > 0) {
	             try {
	                // Create the file at server
	                File serverFile = new File(uploadRootDir.getAbsolutePath() +File.separator + name);
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
	                stream.write(fileData.getBytes());
	                stream.close();
	                //Save the category to the database	          	
		          	Produit pdt=pdtservice.getProduct(id);
		     	    pdt.setIdproduit(myform.getIdproduit());
		     	    pdt.setDescription(myform.getDescription());
		     	    pdt.setDesignation(myform.getDesignation());
		     	    pdt.setPrix(myform.getPrix());
		     	    pdt.setQtestock(myform.getQtestock());
		     	    Categorie cat=catserv.getCategorie(myform.getIdcategorie());//récupérer la catégorie dont l'id est connu et l'affecter
		     	    pdt.setCategorie(cat);
		     	    pdt.setPhoto(fileData.getBytes());//myform.getPhoto() est null
		     		pdt.setDelai(myform.getDelai());
		          	pdt.setNouveau(myform.isNouveau());
		     	    pdtservice.saveProduct(pdt);
		     	    String username=SecurityContextHolder.getContext().getAuthentication().getName();
		          	Historique h=new Historique(username,"Edition produit : "+myform.getDesignation(),new Date());
		          	histos.saveHisto(h);
	             } catch (Exception e) {
	            	 if (lang.equals("fr"))
	            	 model.addAttribute("message", "Erreur lors de l'écriture du fichier " +name);
	            	 else model.addAttribute("message", "File writing error " +name);
	             }
	          }
	      }
	   
	   Iterable<Produit> lc=pdtservice.ListerProduits();
	   model.addAttribute("listeproduits",lc);
	   return "redirect:/admin/produits";
   }     
   @RequestMapping("/produits/del/{id}")
   public String Supprimer(@PathVariable(name="id") long id) {  //@RequestParam(required=false, defaultValue="val") typeparam param -> /delprod?id=1 (paramètre GET), @PathVariable(name="id") -> {id}
	   pdtservice.deleteProduct(id);    
	   String username=SecurityContextHolder.getContext().getAuthentication().getName();
     	Historique h=new Historique(username,"Suppression produit : "+pdtservice.getProduct(id).getDesignation(),new Date());
     	histos.saveHisto(h);//par défaut, un paramètre est obligatoire
	   return "redirect:/admin/produits";
   }
   
}
