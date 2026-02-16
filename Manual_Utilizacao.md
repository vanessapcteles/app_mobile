# Manual de Utilização e Instalação - Aplicação A.M.I.G.O.

Este guia descreve os passos necessários para configurar e executar a aplicação num novo ambiente de desenvolvimento.

## 1. Pré-requisitos
*   **Android Studio** (versão Ladybug ou superior).
*   **Docker & Docker Compose** (essencial para correr o Backend/API).
*   **Java JDK 17**.

## 2. Configuração do Backend (API)
A aplicação depende de um servidor que corre via Docker.
1. Certifica-te de que o ficheiro `docker-compose.yml` está na raiz.
2. No terminal, dentro da pasta do projeto, executa:
   ```bash
   docker-compose up -d
   ```
3. Isto irá iniciar a base de dados e a API na porta `3001`.

## 3. Configuração do Android
1. Abre o **Android Studio** e importa o projeto `mobile_app`.
2. Aguarda a sincronização do Gradle.
3. No ficheiro `APIService.kt`, verifica se o `BASE_URL` aponta para `http://10.0.2.2:3001/api/` (necessário para o emulador aceder ao localhost).

## 4. Execução
1. Liga um emulador ou dispositivo físico.
2. Clica em **Run**.

---

## 5. Como colocar no GitHub (Instruções para o Aluno)
1. `git init`
2. `git add .` (Isto vai incluir o docker-compose.yml automaticamente)
3. `git commit -m "Versão Final - Mobile + Docker Backend"`
4. `git remote add origin O_TEU_URL`
5. `git push -u origin main`
