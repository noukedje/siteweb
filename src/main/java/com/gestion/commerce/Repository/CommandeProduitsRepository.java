package com.gestion.commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.CommandeProduits;
import com.gestion.commerce.Model.Ventes;

@Repository
public interface CommandeProduitsRepository extends JpaRepository<CommandeProduits,Long> {
  
	//@Query(value="select sum(qtecdee*prix) as mont, mois from commandeproduits group by mois",nativeQuery=true)
	//public List<Ventes> getSalesByMonth();
}
