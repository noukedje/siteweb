package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.LigneService;

@Repository
public interface LigneServiceRepository extends JpaRepository<LigneService,Long> {

}
