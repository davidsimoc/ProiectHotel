package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByCnp(String cnp);
}
