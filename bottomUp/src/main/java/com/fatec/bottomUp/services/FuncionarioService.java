package com.fatec.bottomUp.services;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fatec.bottomUp.dtos.FuncionarioDto;
import com.fatec.bottomUp.models.Funcionario;
import com.fatec.bottomUp.repositories.FuncionarioRepository;
@Service
public class FuncionarioService {
	
	
	@Autowired
    private FuncionarioRepository funcionarioRepository;
	
	public FuncionarioDto RetornarFuncionariosById(Long id) {

		var funcionario = funcionarioRepository.findById(id);
		
		if(funcionario.isEmpty()) {
			 throw new NoSuchElementException("Funcionário não encontrado com o id: " + id);
		}
		
		return EntidadeParaDto(funcionario.get());
	}
	
	public FuncionarioDto EntidadeParaDto(Funcionario funcionario) {
		
		var dto = new FuncionarioDto();
		dto.setCargo(funcionario.getCargo());
		dto.setCpf(funcionario.getCpf());
		dto.setNome(funcionario.getNome());
		dto.setSalario(FormatarSalario(funcionario.getSalario()));
		
		return dto;
	}
	
	public String FormatarSalario(Double salario) {
	        
	     return "R$ " + salario;
	}
	
}
