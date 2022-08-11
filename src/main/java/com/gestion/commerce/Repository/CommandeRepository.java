package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Commande;
@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

}
