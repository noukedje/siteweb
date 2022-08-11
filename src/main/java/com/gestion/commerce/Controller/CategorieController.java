package com.gestion.commerce.Controller;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.Historique;
import com.gestion.commerce.Model.UploadCategoryForm;
import com.gestion.commerce.Service.CategorieService;
import com.gestion.commerce.Service.HistoService;
import com.gestion.commerce.Service.ProduitService;




@Controller
public class CategorieController {
    @Autowired
   CategorieService catserv;
   // @PersistenceContext
   // private EntityManager em;  
   @Autowired
   ProduitService pdtserv;
   @Autowired
   HistoService histos;

   
   @PostMapping("/categories/save")  
   public String EnregistrerCategorie(HttpServletRequest request, @ModelAttribute("uploadcatform") @Valid UploadCategoryForm myCatform, BindingResult br,Model model)  { 
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   if (br.hasErrors()) {
		   if (lang.equals("fr"))
			   model.addAttribute("message", "Erreur lors du transfert du formulaire.");
		   else model.addAttribute("message", "Error while transferring the form.");
          return "admincategories";
	  }
	 // Root Directory.	 
	 
      String uploadRootPath = request.getServletContext().getRealPath("images/produits");
	  File uploadRootDir = new File(uploadRootPath);
      // Create directory if it not exists.
      if (!uploadRootDir.exists()) {
         uploadRootDir.mkdirs();
      }
      MultipartFile[] fileDatas = myCatform.getFileDatas();
      
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
                Categorie cat=new Categorie();
	            cat.setDescription(myCatform.getDescription());
	            cat.setNomcategorie(myCatform.getNomcategorie());
	          	cat.setNomphoto(name);
	          	cat.setPhoto(fileData.getBytes());
	          	catserv.SaveCategorie(cat); 
	          	String username=SecurityContextHolder.getContext().getAuthentication().getName();
	          	Historique h=new Historique(username,"Ajout catégorie : "+myCatform.getNomcategorie(),new Date());
	          	histos.saveHisto(h);
             } catch (Exception e) {
            	 if (lang.equals("fr"))
	            	 model.addAttribute("message", "Erreur lors de l'écriture du fichier " +name);
	             else model.addAttribute("message", "File writing error " +name);
             }
          }
       }
	  
	  Iterable<Categorie> lc=catserv.ListerCategories();
	  model.addAttribute("listecategories",lc);
	  model.addAttribute("uploadcatform", new UploadCategoryForm());
	  return "admincategories";
   }
  
   @RequestMapping(value="/photocat/{id}", produces=MediaType.MULTIPART_MIXED_VALUE)
   @ResponseBody
   public byte[] photoCategorie(@PathVariable("id") long idcat) throws IOException{
      Categorie c=catserv.getCategorie(idcat);
      ByteArrayInputStream bais=new ByteArrayInputStream(c.getPhoto());
	  return bais.readAllBytes();
   } 
   @GetMapping("/categories/new")          
   public String CreerCategorie(Model model) {  //em.find(Categorie.class, idcat);  
	   model.addAttribute("uploadcatform", new UploadCategoryForm());
	   return "newcategory";
   }
   @GetMapping("/categories/all")  //côté front-end
   public String listerCategories(Model model) {
	   Iterable<Categorie> lc=catserv.ListerCategories();   //Query req=em.createQuery("select c from categorie c");
	   model.addAttribute("listecategories", lc);
	   return "redirect:/admin/categories";                           //return req.getResultList();
   }
   @RequestMapping("/admin/categories")
	public String AdministrerCategories(Model model) {
	   Iterable<Categorie> lc=catserv.ListerCategories();
	   model.addAttribute("listecategories", lc);
	   return "admincategories";
	}
   @RequestMapping("/categories/del/{id}")
   public String SupprimerCategorie(@PathVariable(name="id") long id, Model model) {
	   catserv.DelCategorie(id);    //em.remove(categorie);
	   String username=SecurityContextHolder.getContext().getAuthentication().getName();
     	Historique h=new Historique(username,"Suppression catégorie : "+catserv.getCategorie(id).getNomcategorie(),new Date());
     	histos.saveHisto(h);
	   model.addAttribute("listecategories", catserv.ListerCategories());
	   return "redirect:/admin/categories";
   }
   @RequestMapping("/categories/{id}")  //produits de la catégorie
   public String getCategoryProducts(@PathVariable(name="id") long id, Model model, HttpServletRequest request) {
	   Iterable<Categorie> lc=catserv.ListerCategories();
	   model.addAttribute("menucategories",lc); //pour le menu
	   model.addAttribute("listecategories",catserv.getCategorie(id)); //pour le contenu
	   model.addAttribute("listeproduits",catserv.getCategorie(id).getProduits());
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   String page=(lang.equals("fr"))? "accueil" : "welcome";	
       return page;
   }
   
   @GetMapping("/categorie/{id}")  //fiche de la catégorie
   public String displayCategory(@PathVariable("id") long id, Model model, HttpServletRequest request) {
	   Categorie serv=catserv.getCategorie(id);
	   model.addAttribute("categorie", serv);
	   List<Categorie> listcat=catserv.ListerCategories();
	   model.addAttribute("menucategories", listcat); 
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   String page=(lang.equals("fr"))? "categorie" : "category";	
       return page;
   }
   @GetMapping("/categories/edit/{id}") //on prend les données de la base et on les assigne à l'objet formulaire
	public String EditerCategorie(@PathVariable Long id, Model model) {
	   UploadCategoryForm myform=new UploadCategoryForm();
	   Categorie cat=catserv.getCategorie(id);
	   myform.setIdcategorie(id);
	   myform.setDescription(cat.getDescription());
	   myform.setNomcategorie(cat.getNomcategorie());
	   myform.setPhoto(cat.getPhoto()); 
	   model.addAttribute("uploadcatform", myform);
	   return "editcategory";
	}
   @RequestMapping("/categories/update/{id}") //on prend l'objet soumis et on stocke ses données dans la base
   public ModelAndView ModifierCategorie(HttpServletRequest request, @PathVariable("id") long id, @ModelAttribute("uploadcatform") UploadCategoryForm myform, BindingResult br, Model model) {
	   ModelAndView mav =new ModelAndView("admincategories");
	   String lang=(String)request.getSession(true).getAttribute("LOCALE");
	   if (br.hasErrors()) {
		   if (lang.equals("fr"))
		   model.addAttribute("message", "Erreur lors du transfert du formulaire.");
		   else model.addAttribute("message", "Error while transferring the form.");
	       return mav;
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
		          	Categorie cat=catserv.getCategorie(id);
		     	    cat.setIdcategorie(myform.getIdcategorie());
		     	    cat.setDescription(myform.getDescription());
		     	    cat.setNomcategorie(myform.getNomcategorie());
		     	    cat.setNomphoto(name);
		     	    cat.setPhoto(fileData.getBytes());//myform.getPhoto() est null
		     	    catserv.SaveCategorie(cat);
		     	    String username=SecurityContextHolder.getContext().getAuthentication().getName();
		          	Historique h=new Historique(username,"Edition catégorie : "+myform.getNomcategorie(),new Date());
		          	histos.saveHisto(h);
	             } catch (Exception e) {
	            	 if (lang.equals("fr"))
		            	 model.addAttribute("message", "Erreur lors de l'écriture du fichier " +name);
		           	 else model.addAttribute("message", "File writing error " +name);
	             }
	          }
	      }
	   
	   Iterable<Categorie> lc=catserv.ListerCategories();
	   mav.addObject("listecategories",lc);
	   return mav;
   }
   
}   
   