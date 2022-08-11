package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service,Long> {

}
