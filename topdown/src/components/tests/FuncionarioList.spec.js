import { render, screen, fireEvent, waitFor } from '@testing-library/vue';
import FuncionarioList from '../FuncionarioList.vue';
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { vi } from 'vitest';

// Criar o mock do axios
const mock = new MockAdapter(axios);

describe('FuncionarioList.vue', () => {
  afterEach(() => {
    // Limpar os mocks após cada teste
    mock.reset();
  });

  it('deve exibir a lista de funcionários após a requisição', async () => {
    // Dados mockados que a API deve retornar
    const mockFuncionarios = [
      { id: 1, nome: 'João Silva', cargo: 'Desenvolvedor' },
      { id: 2, nome: 'Maria Oliveira', cargo: 'Analista' },
    ];

    // Configurar o mock para retornar os dados
    mock.onGet('http://localhost:8080/api/funcionario').reply(200, mockFuncionarios);

    // Renderizar o componente
    render(FuncionarioList);

    // Verificar se o componente exibe a mensagem "Carregando..."
    expect(screen.getByText(/carregando/i)).toBeInTheDocument();

    // Esperar a requisição e a lista ser carregada
    await waitFor(() => expect(screen.getByText('João Silva')).toBeInTheDocument());
    expect(screen.getByText('Maria Oliveira')).toBeInTheDocument();
  });

  it('deve exibir uma mensagem de erro caso a requisição falhe', async () => {
    // Configurar o mock para retornar um erro
    mock.onGet('http://localhost:8080/api/funcionario').reply(500);

    // Renderizar o componente
    render(FuncionarioList);

    // Esperar a requisição falhar
    await waitFor(() => expect(screen.getByText('Erro ao carregar os dados')).toBeInTheDocument());
  });

  it('deve exibir a mensagem "Carregando..." enquanto os dados estão sendo carregados', async () => {
    // Dados mockados
    const mockFuncionarios = [{ id: 1, nome: 'João Silva', cargo: 'Desenvolvedor' }];
    
    // Configurar o mock para retornar os dados após algum tempo
    mock.onGet('http://localhost:8080/api/funcionario').reply(() => {
      return [200, mockFuncionarios];
    });

    // Renderizar o componente
    render(FuncionarioList);

    // Verificar se "Carregando..." é exibido inicialmente
    expect(screen.getByText('Carregando...')).toBeInTheDocument();

    // Esperar a requisição e os dados serem exibidos
    await waitFor(() => expect(screen.getByText('João Silva')).toBeInTheDocument());

    // Verificar se "Carregando..." desapareceu
    expect(screen.queryByText('Carregando...')).toBeNull();
  });
});
