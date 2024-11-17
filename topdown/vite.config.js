import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  test: {
    globals: true, // Permite usar as funções de testes globais como 'describe', 'it', etc.
    environment: 'jsdom', // Simula o ambiente de navegador
    transformMode: {
      web: [/\.vue$/], // Assegura que os arquivos .vue sejam transformados corretamente
    },
  },
});
