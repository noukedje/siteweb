package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Ventes;

@Repository
public interface VentesRepository extends JpaRepository<Ventes, Long> {

}
