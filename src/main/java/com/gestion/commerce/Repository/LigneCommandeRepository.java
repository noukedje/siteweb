package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.LigneCommande;
@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
   
}
