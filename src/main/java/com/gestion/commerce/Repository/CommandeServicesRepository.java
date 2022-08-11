package com.gestion.commerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.commerce.Model.CommandeServices;

@Repository
public interface CommandeServicesRepository extends JpaRepository<CommandeServices,Long> {

}
