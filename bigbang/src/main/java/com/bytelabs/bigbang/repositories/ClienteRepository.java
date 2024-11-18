package com.bytelabs.bigbang.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytelabs.bigbang.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
