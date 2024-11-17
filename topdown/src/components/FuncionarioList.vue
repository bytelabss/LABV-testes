<template>
    <div class="funcionario-list">
      <h1>Lista de Funcionários</h1>
      <div v-if="loading">Carregando...</div>
      <div v-if="error" class="error">{{ error }}</div>
      <ul v-if="funcionarios.length">
        <li v-for="funcionario in funcionarios" :key="funcionario.id">
          <strong>{{ funcionario.nome }}</strong> - {{ funcionario.cargo }}
        </li>
      </ul>
      <div v-if="!loading && funcionarios.length === 0">
        Nenhum funcionário encontrado.
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "FuncionarioList",
    data() {
      return {
        funcionarios: [], // Lista de funcionários
        loading: false, // Indicador de carregamento
        error: null, // Mensagem de erro
      };
    },
    created() {
      this.fetchFuncionarios();
    },
    methods: {
      async fetchFuncionarios() {
        this.loading = true;
        this.error = null;
        try {
          // Substitua pela URL do seu servidor
          const response = await axios.get("http://localhost:8080/api/funcionario");
          this.funcionarios = response.data;
        } catch (err) {
          this.error = "Erro ao carregar os dados";
        } finally {
          this.loading = false;
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .funcionario-list {
    font-family: Arial, sans-serif;
  }
  
  .error {
    color: red;
  }
  
  ul {
    list-style-type: none;
    padding: 0;
  }
  
  li {
    margin: 10px 0;
  }
  </style>
  