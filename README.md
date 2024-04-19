# Carometro

### Objetivo
___

Criar um programa que seja capaz e armazenar fotos e dados no Banco de Dados usando SGBD JDBC.
<p align="lefth" >
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,mysql,git,eclipse" />
  </a>
</p>

### Imagem ilustrativa do App
____

![carometropronto](https://github.com/PauloCruz34/Carometro/assets/126684518/ef0b1722-2b6b-47d3-bef8-f3a46377465f)


### Instruções para instalação e uso do aplicativo
____

* Ter instalado no computador a versao do java JDK 17 ou superior. que pode ser obtida pelo link : [Jdk](https://adoptium.net/)
* Ter instalado o sgbd MySQL 8, MariaDB ou Xaamp, o MySQL 8 pode ser obtido no link: [MySQL](https://dev.mysql.com/downloads/)
* No MySQL cole o sequinte script:
 ```
CREATE SCHEMA CarometroDb;
USE CarometroDB;

CREATE TABLE funcionários(
	re INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(30) NOT NULL,
    foto LONGBLOB NOT NULL
);
```
### Instalação do App
* Em Releases faça o dowload do arquivo Carometro.jar [Carometro.jar](https://github.com/PauloCruz34/Carometro/releases/tag/Carometro)
* Execute e verefique se o simbolo que representa o Banco de dados esta ativado na parte inferior direita da tela conforme as imagens a seguir:
  #### Desativado

  ![Carometro desativado](https://github.com/PauloCruz34/Carometro/assets/126684518/9b3e2340-abbc-4bfc-9d45-68a2387405cf)

  #### Ativado

  ![Carometro Ativado](https://github.com/PauloCruz34/Carometro/assets/126684518/8834e024-7c9a-404b-bed8-c5d7b3ce34c0)

  
### Tecnologias Utilizadas

* Criação de banco de dados e tabelas no MySQL
* CRUD (Create Read Update e Delete)
* IDE Eclipse
* Java SE
* Window Builder
* JDBC (Java Database Connectivity)
* Validação de dados
* Uso da biblioteca iTextpdf para gerar listagem de alunos com foto

### Projeto terminado
![java3](https://github.com/PauloCruz34/Carometro/assets/126684518/b7bb8bb3-04f5-4a78-8376-f5a413c50be4)

