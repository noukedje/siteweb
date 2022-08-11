package com.gestion.commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Categorie;
import com.gestion.commerce.Model.Produit;
@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
   
	@Query(value="select * from categorie",nativeQuery=true)
	public List<Categorie> ListerLesCategories();
	
}
