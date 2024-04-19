# Carometro

### Objetivo
___

Criar um programa que seja capaz e armazenar fotos e dados no Banco de Dados usando SGBD JDBC.
<p align="lefth" >
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,mysql,git," />
  </a>
</p>

## Instruções para instalação e uso do aplicativo
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
  
### 1° Gif do status atual do projeto
___

![java](https://github.com/PauloCruz34/Carometro/assets/126684518/e9443e2a-ad70-46a8-b7b1-d2c342c20cd2)


### 2° Gif do Status do projeto, gravando nomes e fotos no banco de dados
___

![java2](https://github.com/PauloCruz34/Carometro/assets/126684518/59b24175-335d-4a39-89e7-e2accd74ca75)


### 3° Gif do Status do Projeto, gravando, alterando e excluindo dados
___

![java3](https://github.com/PauloCruz34/Carometro/assets/126684518/b98242d5-62fc-40fe-b052-d31f7ddd6b68)

