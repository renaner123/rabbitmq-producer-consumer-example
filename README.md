# Projeto RabbitMQ (Exemplo Producer/Consumer)

Este repositório contém um exemplo simples de integração com RabbitMQ usando Spring Boot.
O objetivo é demonstrar como publicar mensagens (producer) em um Exchange e, em um vídeo futuro, criar o consumer para consumir essas mensagens.

Resumo rápido
- Producer: implementado em [producer/demo](producer/demo), recebe requisições HTTP e publica mensagens no RabbitMQ.
- Consumer: ainda será implementado (próximo vídeo).
- RabbitMQ: configuração de execução via [docker-compose.yml](docker-compose.yml) na raiz do repositório.

Estrutura do repositório

- `docker-compose.yml` - configura e sobe um container RabbitMQ (management UI incluída).
- `producer/` - microserviço Spring Boot que publica mensagens no RabbitMQ. O README do producer está em `producer/README.md`.
- `consumer/` - esqueleto do consumer (implementação será feita no próximo vídeo).

Requisitos
- Java 17
- Maven (ou usar o wrapper incluído `mvnw` / `mvnw.cmd`)
- Docker + Docker Compose (para subir o RabbitMQ localmente)

Configurações importantes
- O RabbitMQ usado no docker-compose expõe as portas 5672 (AMQP) e 15672 (management UI).
- Credenciais padrão do docker-compose: usuário `admin`, senha `admin123`.
- As propriedades do producer (host, port, username, password) podem ser sobrepostas por variáveis de ambiente:
  - SPRING_RABBITMQ_HOST
  - SPRING_RABBITMQ_PORT
  - SPRING_RABBITMQ_USERNAME
  - SPRING_RABBITMQ_PASSWORD

Como rodar localmente

1) Subir o RabbitMQ com Docker Compose (a partir da raiz do repositório):

```powershell
# Windows PowerShell
docker-compose up -d
```

O management UI ficará disponível em: http://localhost:15672 (usuário: `admin`, senha: `admin123`).

2) Rodar o Producer:

- Veja o [producer/README.md](producer/README.md) para instruções e detalhes específicos do producer.


Próximo passo / Próximo vídeo
- No próximo vídeo será implementado o consumer que irá se conectar às filas e processar as mensagens publicadas pelo producer. O código será adicionado à pasta `consumer/`.

Contribuições
- Pull requests e issues são bem-vindos. Abra uma issue se quiser sugerir melhorias ou relatar problemas.

Licença
- Este projeto está sob a licença presente no arquivo `LICENSE`.

