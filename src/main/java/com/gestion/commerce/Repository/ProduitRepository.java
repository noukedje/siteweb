package com.gestion.commerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Produit;

/*@Repository
public abstract class ProduitRepository implements CrudRepository<Produit,Long>{
  
}*/

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {  //  
  
	//@Query("Select p from produit p where p.designation like ?1 or p.description like ?1")
	@Query(value="select * from produit where designation like ?1 or description like ?1",nativeQuery = true)
	List<Produit> searchproducts(String param);
	
	@Query(value="select * from produit where nouveau=true",nativeQuery=true)
	List<Produit> getNewProducts();
	
	@Query(value="select * from produit, categorie where nouveau=true and idcateg=idcategorie and idcategorie=?1",nativeQuery=true)
	public List<Produit> getNewProductsByCategory(long id);
}
