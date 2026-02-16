# A.M.I.G.O. - Aplicação de Gestão Pedagógica

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

A **A.M.I.G.O** é uma aplicação mobile moderna desenvolvida para otimizar a gestão pedagógica de centros de formação. Com uma interface focada na experiência do utilizador (UX) e design "clean", a aplicação permite o controlo eficiente de formadores, formandos, oferta formativa e disponibilidade de salas.

---

## 🚀 Funcionalidades Principais

*   **Autenticação Segura:** Sistema de login integrado com API REST utilizando Bearer Tokens.
*   **Dashboard Inteligente:** Painel central com navegação intuitiva e transições suaves.
*   **Gestão de Formandos:**
    *   Sincronização em tempo real com a base de dados remota.
    *   Criação de novos registos com validação rigorosa (Telemóvel 9 dígitos e Email padrão RFC).
    *   Fichas individuais detalhadas com suporte a fotografia.
*   **Equipa Pedagógica:** Listagem completa de formadores com pesquisa instantânea.
*   **Oferta Formativa:** Consulta detalhada de cursos e respetivos módulos.
*   **Gestão de Salas:** Visualização do estado de ocupação (Livre/Ocupada) por bloco.

---

## 🛠️ Tecnologias e Ferramentas

*   **Linguagem:** [Kotlin](https://kotlinlang.org/) (Corrotinas para chamadas assíncronas).
*   **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Arquitetura declarativa).
*   **Rede:** [Retrofit 2](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson).
*   **Imagens:** [Coil](https://coil-kt.github.io/coil/) (Carregamento e cache de imagens).
*   **Backend:** Integrado via [Docker Compose](https://www.docker.com/).
*   **Animações:** Transições direcionais dinâmicas para uma navegação fluida.

---

## 💻 Configuração e Instalação

Para executar este projeto localmente, siga os passos abaixo:

### 1. Preparar o Backend
A aplicação requer o backend ativo. Certifique-se de que tem o Docker instalado e execute:
```bash
docker-compose up -d
```

### 2. Abrir no Android Studio
1. Importe a pasta `mobile_app` no **Android Studio**.
2. Aguarde a sincronização do Gradle.
3. Verifique o ficheiro `APIService.kt` para garantir que o `BASE_URL` aponta para o endereço correto.

### 3. Execução
Selecione um emulador ou dispositivo físico e clique no botão **Run**.

---

## 👥 Autores

Este projeto foi realizado no âmbito da unidade curricular de Programação Mobile por:

*   **Vanessa Teles**
*   **Ricardo Evans**

---

## 📄 Licença

Este projeto foi desenvolvido para fins académicos. Todos os direitos reservados aos autores.
