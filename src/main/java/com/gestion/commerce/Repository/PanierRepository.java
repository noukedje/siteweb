package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.commerce.Model.Panier;

public interface PanierRepository extends JpaRepository<Panier,Long>{
   //public Panier findBySessionToken(String sessionToken);
}
