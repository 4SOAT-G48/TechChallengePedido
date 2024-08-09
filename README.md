# TechChallengePedido

Aplicação do projeto TechChallenge 4SOAT grupo 48.  
Este projeto contém a aplicação backend do projeto TechChallenge 4SOAT grupo 48, módulo de Pedido.

Para a parte técnica, veja as sessões abaixo. Para demais documentações, acesse a [wiki deste projeto](https://github.com/4SOAT-G48/TechChallenge/wiki).

## Pré-requisitos

Tanto para executar quanto para desenvolver, são necessários os seguintes itens:

1. Instalar o Docker;
2. Instalar o Docker Compose;
3. Instalar o Git.

## Executar aplicação via Docker Compose

1. Clonar o repositório do projeto;
2. Crie o arquivo **.env** no mesmo diretório que o arquivo **docker-compose.yml**, com a estrutura do arquivo **.env.example**.
    - _As informações contidas no .env serão enviadas via email e/ou Discord._
3. Via terminal, entre na pasta do projeto;
4. Execute o comando:
    ``` sh
    docker-compose up --build -d
    ```

## Desenvolvimento

### Pré-requisitos adicionais

1. Instalar o Java versão 21;
    1. Link para download e instruções: https://adoptium.net/installation/;
2. Maven versão 3.9.5 ou superior;
3. Instalar a IDE IntelliJ;
    1. https://www.jetbrains.com/idea/;
    2. Baixe a versão _IntelliJ IDEA Community Edition_;
4. DBeaver como cliente de conexão de banco;

### Para executar a aplicação diretamente na máquina

1. Na primeira vez que executar o projeto, será necessário criar o arquivo **.env** na raiz do projeto, com a estrutura do arquivo **.env.example**;

### Configuração do PostgreSQL

1. Para rodar o PostgreSQL, você terá duas opções:
    1. Opção 1, usando o Docker Compose do projeto (recomendado):
        1. Certifique-se de que o arquivo **.env** esteja configurado corretamente.
        2. Via terminal, entre na pasta do projeto;
        3. Execute o comando:
            ``` sh
            docker-compose up db --build -d
            ```
    2. Opção 2, diretamente via Docker:
        1. Construa o container do PostgreSQL com o seguinte comando:
            ``` sh
            docker run --rm -d --name postgres --network fiap -e POSTGRES_PASSWORD={SENHA} -e POSTGRES_USER={USUARIO} -e POSTGRES_DB={BANCO_DE_DADOS} -p 5432:5432 -v ./pgdata:/var/lib/postgresql/data postgres
            ```
        2. Para parar o container:
            ``` sh
            docker stop postgres
            ```
        3. Para executar o container novamente:
            ``` sh
            docker start postgres
            ```

2. Na primeira vez que executar a base, será necessário criar o schema:
    1. Para saber o nome da base a ser criada, consulte o arquivo **.env** na variável **POSTGRES_DB**.

### Configuração do RabbitMQ

1. Para rodar o RabbitMQ, você terá duas opções:
    1. Opção 1, usando o Docker Compose do projeto (recomendado):
        1. Certifique-se de que o arquivo **.env** esteja configurado corretamente.
        2. Via terminal, entre na pasta do projeto;
        3. Execute o comando:
            ``` sh
            docker-compose up rabbitmq --build -d
            ```
    2. Opção 2, diretamente via Docker:
        1. Construa o container do RabbitMQ com o seguinte comando:
            ``` sh
            docker run -d --hostname my-rabbit --name rabbitmq --network fiap -e RABBITMQ_DEFAULT_USER={USUARIO} -e RABBITMQ_DEFAULT_PASS={SENHA} -p 5672:5672 -p 15672:15672 rabbitmq:3-management
            ```
        2. Para parar o container:
            ``` sh
            docker stop rabbitmq
            ```
        3. Para executar o container novamente:
            ``` sh
            docker start rabbitmq
            ```
2. A interface de gerenciamento do RabbitMQ estará disponível em _http://localhost:15672_. Use as credenciais configuradas para acessar.

### Executando a verificação OWASP

1. No seu terminal rode o seguinte comando para que o relatório esteja dentro da pasta `target/dependency-check-report.html`
```
mvn dependency-check:check
```

### Executando a aplicação na IntelliJ

1. Na primeira vez que executar a aplicação:
    1. Procure na árvore do projeto o arquivo _src/main/java/br/com/fiap/soat/grupo48/Grupo48PedidoApplication.java_;
    2. Clique com o botão direito do mouse sobre ele e selecione a opção _Modify Run Configuration..._;
    3. Na janela que abrir, selecione a opção _Enable EnvFile_, depois clique no _+_, selecione a opção **.env file** e escolha o arquivo **.env** que foi criado;
    4. Clique nos botões _Apply_ e depois _Ok_;
2. Após a primeira vez:
    1. Clique no botão de Run ou Debug da IDE ao lado da opção _TechChallengePagamento_.
    2. A aplicação será executada e estará disponível no endereço _http://localhost:8081_;
        1. A porta pode depender do que foi configurado no arquivo **.env**, na variável **SERVER_PORT**;
        2. Para acessar a documentação da API, acesse o endereço _http://localhost:8081/swagger-ui.html_.
