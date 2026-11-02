import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// O build vai direto para a pasta static do Spring Boot,
// assim a mesma aplicação Java serve a API e o front.
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: '../src/main/resources/static',
    emptyOutDir: true,
  },
  server: {
    // Em desenvolvimento (npm run dev), manda /repasses para o Spring Boot
    proxy: {
      '/repasses': 'http://localhost:8080',
    },
  },
})
