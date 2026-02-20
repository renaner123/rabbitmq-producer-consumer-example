# Projeto RabbitMQ (Exemplo Producer/Consumer)

Este repositório contém um exemplo simples de integração com RabbitMQ usando Spring Boot.
O objetivo é demonstrar como publicar mensagens (producer) em um Exchange e, em um vídeo futuro, criar o consumer para consumir essas mensagens.

## Resumo rápido
- Producer: implementado em [producer/demo](producer/demo), recebe requisições HTTP e publica mensagens no RabbitMQ.
- Consumer: ainda será implementado (próximo vídeo).
- RabbitMQ: configuração de execução via [docker-compose.yml](docker-compose.yml) na raiz do repositório.

## Estrutura do repositório

- `docker-compose.yml` - configura e sobe um container RabbitMQ (management UI incluída).
- `producer/` - microserviço Spring Boot que publica mensagens no RabbitMQ. O README do producer está em `producer/README.md`.
- `consumer/` - esqueleto do consumer (implementação será feita no próximo vídeo).

## Requisitos
- Java 17
- Maven (ou usar o wrapper incluído `mvnw` / `mvnw.cmd`)
- Docker + Docker Compose (para subir o RabbitMQ localmente)

## Configurações importantes
- O RabbitMQ usado no docker-compose expõe as portas 5672 (AMQP) e 15672 (management UI).
- Credenciais padrão do docker-compose: usuário `admin`, senha `admin123`.
- As propriedades do producer (host, port, username, password) podem ser sobrepostas por variáveis de ambiente:
  - SPRING_RABBITMQ_HOST
  - SPRING_RABBITMQ_PORT
  - SPRING_RABBITMQ_USERNAME
  - SPRING_RABBITMQ_PASSWORD

## Como rodar localmente

1) Subir o RabbitMQ com Docker Compose (a partir da raiz do repositório):

```powershell
# Windows PowerShell
docker-compose up -d
```

O management UI ficará disponível em: http://localhost:15672 (usuário: `admin`, senha: `admin123`).

2) Rodar o Producer:

- Veja o [producer/README.md](producer/README.md) para instruções e detalhes específicos do producer.

## Cenários / Arquivos de exemplo (imagens)

Na pasta [cenarios](imagens/cenarios) há imagens que representam os cenários de Exchange usados no projeto. Elas servem como referência visual para testar e entender o comportamento do producer com diferentes tipos de Exchange.

## Diagramas de conceitos

Em [conceitos](imagens/conceitos) há diagramas que explicam os conceitos básicos de cada tipo de Exchange. São úteis para entender por que cada topologia roteia mensagens de forma diferente:

- [Direct](imagens/conceitos/rabbit-direct.png) — Diagrama explicando o funcionamento do Direct Exchange (roteamento por chave exata).
- [Fanout](imagens/conceitos/rabbit-fanout.png) — Diagrama explicando o Fanout Exchange (replicação para todas as filas vinculadas).
- [Topic](imagens/conceitos/rabbit-topic.png) — Diagrama explicando o Topic Exchange (roteamento por padrões de routing key, por exemplo `order.*`).

Consulte esses diagramas quando for escolher e testar os diferentes cenários no projeto.


## Próximo passo / Próximo vídeo
- No próximo vídeo será implementado o consumer que irá se conectar às filas e processar as mensagens publicadas pelo producer. O código será adicionado à pasta `consumer/`.

## Contribuições
- Pull requests e issues são bem-vindos. Abra uma issue se quiser sugerir melhorias ou relatar problemas.

## Licença
- Este projeto está sob a licença presente no arquivo [LICENSE](LICENSE).
