package com.gestion.commerce.Service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gestion.commerce.Model.Panier;


@Service
public class PanierService {
	
	@Autowired 
	ProduitService pdtserv;
	@Autowired
	LigneCommandeService lss;
	public Panier getCartSession(HttpServletRequest request)
	{
		Panier cart = null;
		cart = (Panier) request.getSession(true).getAttribute("CART_KEY");
		if(cart == null){
			cart = new Panier();
			request.getSession().setAttribute("CART_KEY", cart);
		}
		return cart;
	}
	
}
