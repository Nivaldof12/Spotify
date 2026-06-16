# 🎵 StreamMuse — Plataforma de Streaming de Música

Projeto acadêmico de um aplicativo de streaming de música (inspirado no Spotify), desenvolvido em **Java** com **Spring Boot**, **Maven**, **JPA/Hibernate** e banco de dados **H2**.

## 📌 Sobre o projeto

Este projeto simula um serviço de streaming de música onde o usuário pode:

- ✅ Criar uma conta
- 💳 Realizar transações (pagamentos)
- ❤️ Favoritar músicas
- 📋 Criar e gerenciar playlists
- 🎫 Assinar um plano (Básico, Premium ou Família)

Além disso, o sistema possui um **módulo anti-fraude** que analisa as transações e bloqueia operações suspeitas, como tentativas de pagamento com cartão inválido ou muitas compras em pouco tempo.

O objetivo principal do trabalho não é só "fazer funcionar", mas sim **organizar o código de forma profissional**, usando conceitos de **DDD (Domain-Driven Design)**, **SOLID** e **Clean Code**, como se fosse um sistema real de uma empresa.

## 🛠️ Tecnologias utilizadas


| Tecnologia          | Para que serve (de forma simples)                               |
| ------------------- | --------------------------------------------------------------- |
| **Java 17**         | Linguagem principal do projeto                                  |
| **Spring Boot 3**   | Framework que facilita criar APIs REST e conectar tudo          |
| **Maven**           | Gerenciador de dependências e build do projeto                  |
| **JPA / Hibernate** | Camada que conversa com o banco de dados usando objetos Java    |
| **H2 Database**     | Banco de dados em memória (ideal para desenvolvimento e testes) |


## 🧠 O que é DDD e por que usei?

**DDD (Domain-Driven Design)** é uma forma de organizar o código pensando no **negócio** (domínio), e não só em "telas" ou "tabelas".

Em vez de jogar tudo num lugar só, o projeto foi dividido em **partes menores e independentes**, chamadas de **Bounded Contexts**. Cada parte cuida de uma responsabilidade específica.

### 🗺️ Contextos do projeto


| Contexto            | O que faz                                           | Analogia simples                |
| ------------------- | --------------------------------------------------- | ------------------------------- |
| 🧑 **Conta**        | Cadastro do usuário, cartão de crédito e assinatura | A "ficha" do cliente            |
| 💰 **Pagamento**    | Autoriza e registra transações financeiras          | O "caixa" do sistema            |
| 📚 **Biblioteca**   | Favoritos e playlists de músicas                    | A "estante" pessoal de músicas  |
| 🛡️ **Anti-Fraude** | Verifica se a transação é segura ou suspeita        | O "segurança" que barra fraudes |


### 🔗 Context Mapping

No DDD, os contextos não ficam completamente soltos, eles se relacionam de formas específicas:


| Relacionamento                  | Onde aparece                   | Explicação leiga                                                                                                                     |
| ------------------------------- | ------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------ |
| **Partnership**                 | Conta ↔ Pagamento ↔ Biblioteca | Os módulos trabalham juntos como parceiros para entregar a experiência completa ao usuário                                           |
| **Customer-Supplier**           | Anti-Fraude ← Conta/Pagamento  | O anti-fraude *consome* informações dos outros módulos para fazer a análise                                                          |
| **Anti-Corruption Layer (ACL)** | Adaptadores anti-fraude        | Uma "tradutora" que pega dados de outros módulos e converte pro formato que o anti-fraude entende, sem misturar as responsabilidades |




### Por que separar assim?


| Camada             | Responsabilidade                                        |
| ------------------ | ------------------------------------------------------- |
| **domain**         | *O quê* o sistema faz (regras de negócio puras)         |
| **application**    | *Como* executar cada operação (casos de uso)            |
| **infrastructure** | *Onde* persistir e *como* expor via HTTP                |
| **api**            | Contratos claros da API (Intention Revealing Interface) |


Isso segue o princípio **SOLID**, especialmente o **S** (Single Responsibility cada classe tem uma responsabilidade) e o **D** (Dependency Inversion camadas de alto nível não dependem de detalhes técnicos).

## 🚀 Como rodar o projeto

### Pré-requisitos

- ☕ Java 17 ou superior instalado
- 📦 Maven instalado

### Passo a passo

```bash
# 1. Entre na pasta do projeto
cd music-streaming

# 2. Execute a aplicação
mvn spring-boot:run
```

A API ficará disponível em: **[http://localhost:8080](http://localhost:8080)**

### 🗄️ Console do banco H2

Para visualizar as tabelas e dados salvos:

1. Acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. Preencha:
  - **JDBC URL:** `jdbc:h2:mem:streammuse`
  - **User:** `sa`
  - **Password:** *deixar em branco*

## 🌐 API REST — Endpoints disponíveis

A API foi construída seguindo o padrão **REST**, ou seja, cada operação do sistema pode ser feita via requisições HTTP.


|     | Operação                     | Método | Endpoint                            |
| --- | ---------------------------- | ------ | ----------------------------------- |
| 1   | Criar conta                  | `POST` | `/api/contas`                       |
| 2   | Autorizar transação          | `POST` | `/api/transacoes/autorizar`         |
| 3   | Favoritar música             | `POST` | `/api/biblioteca/favoritas`         |
| 4   | Criar playlist               | `POST` | `/api/biblioteca/playlists`         |
| 5   | Adicionar música na playlist | `POST` | `/api/biblioteca/playlists/musicas` |
| 6   | Assinar plano                | `POST` | `/api/assinaturas`                  |
| 7   | Processar operação por linha | `POST` | `/api/operacoes`                    |
| 8   | Processar lote de operações  | `POST` | `/api/operacoes/lote`               |


## 📝 Exemplos de uso (com curl)

### 1️⃣ Criar uma conta

```bash
curl -X POST http://localhost:8080/api/contas \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12253504416",
    "nome": "Nivaldo",
    "email": "nivaldopvp@gmail.com",
    "numeroCartao": "4111111111111111",
    "cartaoValido": true,
    "cartaoAtivo": true
  }'
```

### 2️⃣ Autorizar uma transação (pagamento)

```bash
curl -X POST http://localhost:8080/api/transacoes/autorizar \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12253504416",
    "valor": 29.90,
    "comerciante": "StreamMuse"
  }'
```

### 3️⃣ Favoritar uma música

```bash
curl -X POST http://localhost:8080/api/biblioteca/favoritas \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12253504416",
    "musicaId": "mus001",
    "titulo": "Bohemian Rhapsody",
    "artista": "Queen"
  }'
```

### 4️⃣ Assinar um plano

```bash
curl -X POST http://localhost:8080/api/assinaturas \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12253504416",
    "plano": "PREMIUM"
  }'
```

Planos disponíveis: `BASICO`, `PREMIUM`, `FAMILIA`

## 📋 Processamento por linha de texto (Parte 1 do enunciado)

Além da API REST, o sistema também aceita **comandos em formato de linha de texto**. Isso simula um processador que lê entradas e decide qual operação executar com base na primeira letra.


| Código | Operação                 | Formato da linha                                                                                   |
| ------ | ------------------------ | -------------------------------------------------------------------------------------------------- |
| `C`    | Criação de conta         | `C <cpf> <nome> <email> <numeroCartao> <cartaoValido> <cartaoAtivo>`                               |
| `T`    | Autorização de transação | `T <cpf> <valor> <comerciante>`                                                                    |
| `F`    | Favoritar música         | `F <cpf> <musicaId> <titulo> <artista>`                                                            |
| `P`    | Playlist                 | `P CRIAR <cpf> <nomePlaylist>` ou `P ADICIONAR <cpf> <nomePlaylist> <musicaId> <titulo> <artista>` |
| `A`    | Assinatura               | `A <cpf> <plano>`                                                                                  |


### Exemplo de envio por linha

```bash
curl -X POST http://localhost:8080/api/operacoes \
  -H "Content-Type: application/json" \
  -d '{"linha": "C 12253504416 Nivaldo nivaldopvp@gmail.com 4111111111111111 true true"}'
```

### Exemplo de envio em lote

```bash
curl -X POST http://localhost:8080/api/operacoes/lote \
  -H "Content-Type: application/json" \
  -d '[
    "C 12253504416 Nivaldo nivaldopvp@gmail.com 4111111111111111 true true",
    "A 12253504416 PREMIUM",
    "F 12253504416 mus001 Bohemian Rhapsody Queen"
  ]'
```



## 🛡️ Regras Anti-Fraude (Parte 2 do enunciado)

O módulo anti-fraude funciona como um **filtro de segurança**. Antes de aprovar uma transação ou assinatura, ele verifica uma série de regras. Se alguma for violada, a operação é **recusada** e o sistema informa o motivo.


| #   | Regra de negócio           | O que significa na prática                                                                        | Código retornado                    |
| --- | -------------------------- | ------------------------------------------------------------------------------------------------- | ----------------------------------- |
| 1   | Um plano ativo por usuário | O usuário não pode assinar dois planos ao mesmo tempo                                             | `plano-ativo-existente`             |
| 2   | Cartão de crédito válido   | O cartão precisa ser considerado válido no cadastro                                               | `cartao-invalido`                   |
| 3   | Cartão ativo               | Transações são bloqueadas se o cartão estiver desativado                                          | `cartao-nao-ativo`                  |
| 4   | Alta frequência            | Não pode haver mais de **3 transações** em um intervalo de **2 minutos**                          | `alta-frequencia-pequeno-intervalo` |
| 5   | Transação duplicada        | Não pode haver mais de **2 transações iguais** (mesmo valor + mesmo comerciante) em **2 minutos** | `transacao-duplicada`               |




## 🏗️ Conceitos de DDD aplicados no projeto


| Conceito DDD              | Onde está no projeto                                       | Explicação simples                                                 |
| ------------------------- | ---------------------------------------------------------- | ------------------------------------------------------------------ |
| **Aggregate (Agregado)**  | `Usuario`, `Transacao`, `Playlist`                         | Grupo de objetos que sempre mudam juntos e protegem a consistência |
| **Domain Service**        | `ServicoAntiFraude`                                        | Lógica de negócio que não pertence a um único objeto               |
| **Repository**            | `RepositorioUsuario`, `RepositorioTransacao`               | Abstração para salvar e buscar dados no banco                      |
| **Rich Domain Model**     | Métodos como `favoritarMusica()`, `ativarAssinatura()`     | O domínio tem comportamento, não é só "caixa de dados"             |
| **Ubiquitous Language**   | Nomes em português do negócio                              | Código e documentação falam a mesma língua                         |
| **Anti-Corruption Layer** | `AdaptadorAntiFraudeConta`, `AdaptadorAntiFraudePagamento` | Traduz dados entre contextos sem acoplar um ao outro               |




**Desenvolvido com ☕ Java + 🍃 Spring Boot**