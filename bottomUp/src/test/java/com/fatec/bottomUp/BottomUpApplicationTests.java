package com.fatec.bottomUp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fatec.bottomUp.dtos.FuncionarioDto;
import com.fatec.bottomUp.models.Funcionario;
import com.fatec.bottomUp.repositories.FuncionarioRepository;
import com.fatec.bottomUp.services.FuncionarioService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class BottomUpApplicationTests {
		
	
		@Autowired
	    private FuncionarioRepository funcionarioRepository;
		
		@Autowired
	    private FuncionarioService funcionarioService;
		
	    @Autowired
	    private MockMvc mockMvc;
	
		
		//Criação de drivers de testes que partem dos níveis mais baixos da aplicação
		
		@Test
	    public void testGetUserByIdRepository() {

	        var funcionarioBanco = funcionarioRepository.findById(1L);
	        assertEquals(funcionarioBanco.get().getNome(), "João Silva");	
	    }
		
		
		//Testa o converter para Dto integrando o repository
		@Test
		public void testGetUserByIdEConverteSalario() {

			   var funcionarioBanco = funcionarioRepository.findById(1L);
			        
			   var dtoSalario = funcionarioService.FormatarSalario((funcionarioBanco.get().getSalario()));
			      
			   var esperado = "R$ 3500.5";
			   
			   assertEquals(esperado,dtoSalario );	
		        
		}
		
		//Testa o converter para Dto integrando o repository e o método de formatar salário
		@Test
	    public void testGetUserByIdEConverteParaDto() {

	        var funcionarioBanco = funcionarioRepository.findById(1L);
	        
	        var dto = funcionarioService.EntidadeParaDto(funcionarioBanco.get());
	        
	        assertEquals(dto.getClass(),FuncionarioDto.class);	
	    }

		//Testa a integração com o service
		@Test
	    public void testGetUserByIdService() {

	        var funcionarioBanco = funcionarioService.RetornarFuncionariosById(1L);
	        
	        
	        assertEquals(funcionarioBanco.getNome(), "João Silva");	
	    }
		
		  @Test
		    void testGetFuncionarioById() throws Exception {
			  
		        // Realizando o teste de requisição GET
		        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/funcionario/{id}", 1))
		                .andExpect(MockMvcResultMatchers.status().isOk())  // Verifica se o status é 200 OK
		                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João Silva"))  // Verifica o nome do funcionário
		                .andExpect(MockMvcResultMatchers.jsonPath("$.cargo").value("Analista de Sistemas"));  // Verifica o cargo
		    }
		

}
