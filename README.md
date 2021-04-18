# Escopo e objetivo

_Implementação de um desafio proposto para avaliação de um desenvolvedor_.

Características: Estrutura JavaBeand e DAO; PrimeFaces 8; Hibernate.

## Pré-requisitos

- Banco de dados PostgreSQL (ou outro suportado pelo Hibernate e seu respectivo driver).
- Apache TomCat (ou outro compatível).

_O banco de dados e o nome da base podem ser facilmente modificadas nas configurações do Hibernate, porém o schema `desafio` está definido em cada entidade_.

## Instruções para execução
_Instruções simplificadas para quem não tem experiência na manipulação de projetos Java_.

- Repositorio para download do projeto `https://github.com/brenodangelo/desafio`.
- Criação da Base de dados com o nome `desafio` e Schema também com o nome `desafio` e as devidas regras de acesso (não é preciso criar tabelas ou executar scripts).
- Para configuração inicial e eventual personalização, recomendo uso da IDE Eclipse.
- Configurar o arquivo `src\main\java\hibernate.properties` para definir os parametros de conexão com o banco de dados `url`, `username` e `password`.
- Para execução local através do próprio Eclipse, poderá ser usada no menu do Eclipse `Run->Run` e configurar o servidor (recomendo o Tomcat 9), caso prefira exportar o arquivo WAR para um deploy em outro servidor, acessar no menu do Eclipse `File->Export->Web->WAR File`.
- Após iniciar o servidor, acessar pelo navegador `http://IP:PORTA/Desafio/`, caso esteja executando localmente e na porta 8080, o endereço será: `http://localhost:8080/Desafio/`.
