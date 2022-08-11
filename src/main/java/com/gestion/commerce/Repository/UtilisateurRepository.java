package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
	
	@Query(value="select * from utilisateur where username = :nom",nativeQuery = true)
	Utilisateur findByUsername(@Param("nom") String username);
	//@Query(value="select * from utilisateur where username = :nom"), ->  findByUsername(@Param("nom") String username)
	//?1 -> 1er paramètre, ?2 -> 2eme paramètre, etc.
}
