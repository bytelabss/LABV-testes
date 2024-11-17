package com.fatec.bottomUp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.bottomUp.models.Funcionario;

@Repository
public interface FuncionarioRepository  extends JpaRepository<Funcionario, Long>{

	Optional<Funcionario> findById(Long id);
}
