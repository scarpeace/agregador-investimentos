# 🏪 Marketplace Multi-ONG - Desafio Full Stack

## 1. Visão Geral do Projeto

Este projeto foi desenvolvido como parte de um desafio técnico de Java + Springboot, focado na criação de um app que agregue e organize investimentos de um usuário.
O objetivo principal era demonstrar proficiência no SprinBoot e nas ferramentas do seu ecossistema visando a implementação de um sistema de camadas e boas práticas de código.

<br/>

## 2. 📋 Requisitos e Funcionalidades Principais

O projeto foi construído para atender aos seguintes requisitos:

* **CRUD de Produtos:** Funcionalidades completas de Criação, Leitura, Atualização, Associação e Exclusão de usuários, contas e ações.

* **API Externas:** Consulta em API externa para buscar informações sobre preços de ações.

* **Testes unitários:** Cobertura de no mínimo 80% em testes unitários utilizando JUnit e Mockito.

<br/>

## 3. 🧰 Tecnologias Utilizadas

| Categoria | Tecnologia        | Detalhe                                                          |
| :--- |:------------------|:-----------------------------------------------------------------|
| **Backend** | Java              | Spring Boot (OpenFeign, JUnit, Mockito, JPA)                     |
| **Banco de Dados** | MySql             | Armazenamento persistente e eficiente de dados                   |

[//]: # (| **Containerização** | Docker            | Docker e Docker Compose para ambiente de desenvolvimento isolado |)

<br/>

## 4. 🚀 Como Instalar e Rodar Localmente

Para iniciar o projeto, você precisará ter o **Docker** e o **Docker Compose** instalados, além de uma **Chave de API da Brapi**.

### 5.1. Pré-requisitos

* [**Docker**](https://www.docker.com/get-started) e [**Docker Compose**](https://docs.docker.com/compose/install/)

* [**Git**](https://git-scm.com/) para clonar o repositório.

* Uma **Chave de API do Brapi** (obtida no [Site da Brapi](https://brapi.dev/)).

### 5.2. Configuração e Inicialização

1. **Clone o Repositório:**

2. **Crie o arquivo `.env`:**

Dentro da pasta raiz, crie um arquivo chamado `.env` para armazenar as variáveis de ambiente com segurança:

---

BRAPI_DEV=sua_chave_secreta_super_longa_e_segura_aqui

MYSQL_DATABSE=nome_do_banco_de_dados

MYSQL_PASSWORD=password_do_banco_de_dados

MYSQL_ROOT_PASSOWRD=password_root_do_banco_de_dados

MYSQL_USER=seu_usuario_do_banco_de_dados

---
