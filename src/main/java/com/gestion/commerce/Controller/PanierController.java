package com.gestion.commerce.Controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gestion.commerce.Model.CommandeProduits;
import com.gestion.commerce.Model.Panier;
import com.gestion.commerce.Model.Ventes;
import com.gestion.commerce.Repository.PanierRepository;
import com.gestion.commerce.Service.CommandeProduitsService;
import com.gestion.commerce.Service.PanierService;
import com.gestion.commerce.Service.ProduitService;
import com.gestion.commerce.Service.VentesService;

@Controller
public class PanierController {
	@Autowired
	ProduitService pdtserv;
	@Autowired
	PanierService panserv;
	@Autowired
	PanierRepository cartrepo;
	@Autowired
	CommandeProduitsService cps;
	@Autowired
	VentesService vs;

	
	public String getMonthByName() {
		int lemois=Calendar.getInstance().get(Calendar.MONTH)+1;
		String mois="";
		 // SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
		 // Date d=date.parse(cp.getDatecde());
		  switch (lemois) {
	   	  case 1:mois="Janvier";break;
	      case 2:mois="Février";break;
	      case 3:mois="Mars";break;
	      case 4:mois="Avril";break;
	      case 5:mois="Mai";break;
	      case 6:mois="Juin";break;
	      case 7:mois="Juillet";break;
	      case 8:mois="Août";break;
	      case 9:mois="Septembre";break;
	      case 10:mois="Octobre";break;
	      case 11:mois="Novembre";break;
	      case 12:mois="Décembre";break;
	   	  }
		  return mois;
	}
	
	@RequestMapping("/cart/items/{id}/{qty}")
	public void addToCart(@PathVariable("id") long id, @PathVariable("qty") int qtepdt,HttpServletRequest request){	 
		Panier cart=panserv.getCartSession(request);
		cart.addItem(pdtserv.getProduct(id), qtepdt);
		cartrepo.save(cart);
		
		SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
		String datecde=date.format(new Date());  
				
		CommandeProduits cp=new CommandeProduits(pdtserv.getProduct(id).getDesignation(),qtepdt,datecde,pdtserv.getProduct(id).getPrix());
		cps.saveCommandeProduits(cp);
	   	
	   for (Ventes vte: vs.ListVentes()) { //pour une liste non vide
	    	if (vte.getMois().equals(getMonthByName())) {
	     		vte.setMontant(vte.getMontant()+qtepdt*pdtserv.getProduct(id).getPrix());
	    		vs.saveVentes(vte);
	    		return;  //on sort ici pour éviter ce qui suit
	    	}
	    }
	    Ventes vte1=new Ventes(qtepdt*pdtserv.getProduct(id).getPrix(),getMonthByName());//dans le cas où la liste de ventes est vide
		vs.saveVentes(vte1);
	}
	
	@RequestMapping("/cart")
	//@ResponseBody
	public String showCart(HttpServletRequest request, Model model)
	{
		Panier cart = panserv.getCartSession(request);	
		//return cart;
		model.addAttribute("cart", cart);
		String lang=(String)request.getSession(true).getAttribute("LOCALE");
		String page=(lang.equals("fr"))? "panier" : "cart";	
	    return page;
	}
	
	@RequestMapping("/cart/items/count")
	@ResponseBody
	public Map<String, Object> getCartItemCount(HttpServletRequest request)
	{
		Panier cart = panserv.getCartSession(request);
		int itemCount = cart.getItems().size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", itemCount);
		return map;
	}
	
	@RequestMapping("/cart/update/{id}/{qty}")
	public void updateCartItem(@PathVariable("id") long id, @PathVariable("qty") int qte, HttpServletRequest request)
	{
		Panier cart = panserv.getCartSession(request);
		cart.updateItemQuantity(pdtserv.getProduct(id), qte);
		cartrepo.save(cart);
	}
	
	@RequestMapping("/cart/delete/{id}")
	public String removeCartItem(@PathVariable("id") long id, HttpServletRequest request)
	{
		Panier cart = panserv.getCartSession(request);
		cart.removeItem(id);
		return "redirect:/cart";
	}
	
	@RequestMapping("/cart/clear")
	@ResponseBody
	public void clearCart(HttpServletRequest request)
	{
		Panier cart = panserv.getCartSession(request);
		cart.setItems(null);
	}
}
