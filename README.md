# Projeto RabbitMQ (Exemplo Producer/Consumer)

Este repositório contém um exemplo simples de integração com RabbitMQ usando Spring Boot.
O objetivo é demonstrar o fluxo completo: publicar mensagens (producer) em um Exchange e consumi-las (consumer), explorando cenários didáticos de ACK/NACK.

## Resumo rápido
- Producer: implementado em [producer/](producer/), recebe requisições HTTP e publica mensagens no RabbitMQ.
- Consumer: implementado em [consumer/](consumer/), consome mensagens das filas e demonstra cenários de ACK/NACK.
- RabbitMQ: configuração de execução via [docker-compose.yml](docker-compose.yml) na raiz do repositório.

## Estrutura do repositório

- `docker-compose.yml` - configura e sobe um container RabbitMQ (management UI incluída).
- `producer/` - microserviço Spring Boot que publica mensagens no RabbitMQ. Guia em `producer/README.md`.
- `consumer/` - microserviço Spring Boot que consome mensagens do RabbitMQ. Guia em `consumer/README.md`.

## 🎥 Playlist do YouTube

Se você quiser acompanhar a explicação completa passo a passo deste projeto, veja a playlist:

👉 [Assistir playlist completa](https://youtube.com/playlist?list=PL1mqgZnEEC2IxUb9SDZlqCzVKQLoiRiHC&si=QNjDV9qH84whEL8E)

## Requisitos
- Docker + Docker Compose

> Java e Maven são necessários apenas se você quiser rodar `producer`/`consumer` fora dos containers.

## Configurações importantes
- O `docker-compose.yml` sobe 3 serviços: `rabbitmq`, `producer` e `consumer`.
- O RabbitMQ expõe as portas 5672 (AMQP) e 15672 (management UI).
- O Producer expõe a porta 8080 para receber requisições HTTP.
- Credenciais padrão do docker-compose: usuário `admin`, senha `admin123`.
- As propriedades de conexão (producer e consumer) podem ser sobrepostas por variáveis de ambiente:
  - SPRING_RABBITMQ_HOST
  - SPRING_RABBITMQ_PORT
  - SPRING_RABBITMQ_USERNAME
  - SPRING_RABBITMQ_PASSWORD

## Como rodar localmente

Suba tudo com **um comando** (a partir da raiz do repositório):

```powershell
docker compose up -d --build
```

Após subir:

- RabbitMQ Management: http://localhost:15672 (usuário: `admin`, senha: `admin123`)
- API do producer: http://localhost:8080

Comandos úteis:

```powershell
# Ver logs de todos os serviços
docker compose logs -f

# Ver logs apenas do consumer
docker compose logs -f consumer

# Parar e remover os containers
docker compose down
```

Teste rápido do fluxo (producer -> RabbitMQ -> consumer):

```powershell
curl -X POST http://localhost:8080/order -H "Content-Type: application/json" -d "{\"orderId\":\"101\",\"quantity\":1,\"description\":\"pedido normal\"}"
```

Guias detalhados por serviço:

- Producer: veja o [producer/README.md](producer/README.md).
- Consumer: veja o [consumer/README.md](consumer/README.md).


## Cenários / Arquivos de exemplo (imagens)

Na pasta [cenarios](imagens/cenarios) há imagens que representam os cenários de Exchange usados no projeto. Elas servem como referência visual para testar e entender o comportamento do producer com diferentes tipos de Exchange.

## Diagramas de conceitos

Em [conceitos](imagens/conceitos) há diagramas que explicam os conceitos básicos de cada tipo de Exchange. São úteis para entender por que cada topologia roteia mensagens de forma diferente:

- [Direct](imagens/conceitos/rabbit-direct.png) — Diagrama explicando o funcionamento do Direct Exchange (roteamento por chave exata).
- [Fanout](imagens/conceitos/rabbit-fanout.png) — Diagrama explicando o Fanout Exchange (replicação para todas as filas vinculadas).
- [Topic](imagens/conceitos/rabbit-topic.png) — Diagrama explicando o Topic Exchange (roteamento por padrões de routing key, por exemplo `order.*`).

Consulte esses diagramas quando for escolher e testar os diferentes cenários no projeto.

## Conteúdo didático

- O projeto foi estruturado para apoiar vídeos e estudos práticos de RabbitMQ com Spring Boot.
- O `producer` mostra publicação em exchange e routing keys.
- O `consumer` mostra consumo com confirmação manual (ACK/NACK), requeue e comportamento sem ACK para fins de aprendizado.

## Contribuições
- Pull requests e issues são bem-vindos. Abra uma issue se quiser sugerir melhorias ou relatar problemas.

## Licença
- Este projeto está sob a licença presente no arquivo [LICENSE](LICENSE).
