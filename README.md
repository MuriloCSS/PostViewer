# PostViewer

**Aluno(a):** Murilo Corrêa Soares Silva  
**Disciplina:** PROGRAMAÇÃO PARA DISPOSITIVOS MÓVEIS  

---

## Visão Geral do Projeto
O **PostViewer** é um aplicativo Android desenvolvido para consumir dados de uma API externa e exibi-los em uma interface moderna. O sistema integra dados remotos com armazenamento local.

## Funcionalidades Principais
* **Tela Inicial:** Consome a API REST (JSONPlaceholder) e exibe uma lista de posts contendo o título e o corpo de cada publicação.
* **Navegação:** Roteamento dinâmico que direciona o usuário para os detalhes de um post específico ao clicar em um item da lista.
* **Tela de Detalhes:** Busca e exibe os comentários do post, apresentando o nome do autor em destaque como título.
* **Comentários Locais:** Permite adicionar novos comentários via campo de texto. Esses dados são salvos localmente (persistência), sobrevivem ao fechamento do app e são mesclados aos comentários da API na mesma tela.

---

## Requisitos do Aplicativo
O projeto foi desenvolvido para cumprir os seguintes requisitos:
* Consumo de uma API REST (JSONPlaceholder).
* Exibição de uma lista de itens (Posts) contendo título e corpo.
* Navegação estruturada entre diferentes telas.
* Tela de detalhes exibindo informações atreladas ao item selecionado (Comentários com título, autor e texto).
* Inserção e salvamento de novos dados utilizando persistência local (Banco de Dados).
* Tratamento e exibição visual dos estados de "Carregamento" e de "Erro".

---

## Tecnologias e Bibliotecas

| Tecnologias Utilizadas |
| :--- |
| Kotlin (versão 2.2.10) |
| Jetpack Compose (Material 3) |
| Navigation Compose |
| Retrofit com conversor Gson |
| Room Database |
| KSP (versão 2.2.10-2.0.2) |
| ViewModel com StateFlow |

---

## Como Executar Localmente

1. Instale o **Android Studio**.
2. Faça o download ou clone o repositório do projeto para sua máquina.
3. Abra a pasta raiz do projeto diretamente no Android Studio.
4. Aguarde o processo de sincronização (Sync) do Gradle finalizar.
5. Conecte seu dispositivo físico ou inicie um emulador (certifique-se de ter conexão com a internet).
6. Clique no botão **Run (Play)** na barra superior para compilar e abrir o aplicativo.

---

## Decisões de Design e Arquitetura

### Arquitetura Direta e Simplificada
Optou-se por não utilizar padrões excessivamente complexos (como Clean Architecture. Essa decisão garante uma base de código acessível e direta, evitando a fragmentação desnecessária de arquivos e priorizando a clareza para o atual escopo do projeto.

### Estrutura de Pacotes Padrão
O código-fonte foi organizado em pacotes lógicos focados em suas responsabilidades (`model`, `data`, `viewmodel` e `ui`), agrupando as classes de forma clara e facilitando a manutenção e navegação pelo código.

### Gerenciamento do Room com AndroidViewModel
Foi escolhida a herança de `AndroidViewModel` para gerenciar a instância do Room Database. Isso permite acessar o contexto da aplicação de maneira segura para inicializar o banco de dados, sem precisar adicionar ferramentas extras de injeção.

### Resiliência de Interface
A UI gerencia ativamente os estados de Sucesso, Carregamento e Erro. Falhas de conexão são interceptadas pelo ViewModel para exibir mensagens amigáveis em vez de causar fechamentos inesperados (crashes).
