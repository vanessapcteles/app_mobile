# Relatório de Desenvolvimento - Aplicação A.M.I.G.O.

## 1. Introdução
A aplicação **A.M.I.G.O.** foi desenvolvida como uma ferramenta de apoio à gestão pedagógica, permitindo a consulta e gestão de informações sobre formadores, formandos, cursos e salas. A aplicação foca-se na usabilidade, oferecendo uma interface moderna, responsiva e integrada com serviços externos.

---

## 2. Tecnologias Utilizadas
*   **Linguagem:** Kotlin
*   **Interface UI:** Jetpack Compose (Moderno e Declarativo)
*   **Comunicação API:** Retrofit 2 & Gson (Consumo de dados JSON)
*   **Carregamento de Imagens:** Coil (Gestão eficiente de imagens e fotos)
*   **Arquitetura:** Navegação baseada em estados no Compose, com integração de corrotinas para operações assíncronas.

---

## 3. Funcionalidades Principais

### 3.1. Autenticação (Login)
*   Integração com API para validação de credenciais.
*   Gestão de Tokens de segurança (Bearer Token) para pedidos subsequentes.
*   Tratamento de erros visual (dados inválidos ou servidor offline).

### 3.2. Dashboard
*   Ecrã central de navegação com cartões intuitivos.
*   Saudação personalizada ao utilizador logado.
*   Opção de Logout segura.

### 3.3. Gestão de Formandos
*   **Listagem Híbrida:** Exibe dados provenientes da API e permite a adição temporária de novos formandos localmente.
*   **Ficha do Formando:** Vista detalhada com foto, nome, email e telemóvel.
*   **Registo Responsivo:** Formulário otimizado com suporte para scroll e adaptação ao teclado do telemóvel.
*   **Galeria:** Possibilidade de escolher fotos diretamente da galeria do dispositivo.

### 3.4. Equipa Pedagógica (Formadores)
*   Consulta em tempo real dos formadores registados na base de dados.
*   **Barra de Pesquisa:** Filtragem instantânea por nome ou email.
*   **Vista Detalhada:** Acesso à ficha completa de cada formador.

### 3.5. Oferta Formativa (Cursos)
*   Listagem completa de cursos disponíveis.
*   Detalhe de curso incluindo a lista de módulos, carga horária e área de formação.

### 3.6. Gestão de Salas
*   Visualização rápida do estado das salas (Livre/Ocupada).
*   Organização por blocos.

---

## 4. Estrutura Técnica
O projeto está organizado nos seguintes módulos principais:
*   `MainActivity.kt`: Controlador de navegação central.
*   `APIService.kt`: Interface central de comunicação com o servidor e modelos de dados (Login, Formadores e Formandos).
*   `LoginScreen.kt`, `DashboardScreen.kt`, `FormandosScreen.kt`, etc.: Implementação da lógica de interface e comportamento de cada ecrã.

---

## 5. Conclusão
Em suma, a aplicação **A.M.I.G.O.** demonstra uma implementação sólida e profissional, cumprindo rigorosamente os requisitos de integração com APIs externas e gestão de dados em tempo real. Destaca-se pela fluidez da performance, pela interface intuitiva e totalmente adaptável a diversos dispositivos, e pela robustez das suas funcionalidades, como a gestão híbrida de formandos e as vistas detalhadas de perfis. A documentação exaustiva do código em Português de Portugal, aliada a uma arquitetura limpa em Jetpack Compose, assegura que o projeto é escalável, facilitando futuras expansões e garantindo uma manutenção simplificada e eficiente.
