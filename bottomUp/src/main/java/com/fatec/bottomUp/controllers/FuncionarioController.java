package com.fatec.bottomUp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.bottomUp.dtos.FuncionarioDto;
import com.fatec.bottomUp.services.FuncionarioService;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioController {

	 @Autowired
	 private FuncionarioService service;
	 
	 @GetMapping("/{id}")
	    public FuncionarioDto getFuncionariosById(@PathVariable Long id) {
	        FuncionarioDto funcionario = service.RetornarFuncionariosById(id);
	        return funcionario;
	    }
	
}
