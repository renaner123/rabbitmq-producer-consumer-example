# Producer (RabbitMQ)

Este serviço é um pequeno microserviço Spring Boot que publica pedidos (orders) em um Exchange do RabbitMQ.

## Visão geral

- Propósito: Receber requisições HTTP (POST /order) com um payload de pedido e publicar a mensagem no RabbitMQ.
- Fluxo: HTTP client -> Producer (este projeto) -> Exchange (`order.exchange`) -> Filas (bindings por routing key) -> Consumer(s).

##  Arquitetura

```
HTTP Request → Controller → Service → RabbitMQ (Exchange → Queue)
```

```
┌─────────────┐
│   Cliente   │
│   (cURL)    │
└──────┬──────┘
       │ POST /orders
       │ { orderId, description, quantity }
       ▼
┌──────────────────┐
│ OrderController  │ ← @RestController
│ createOrder()    │
└────────┬─────────┘
         │ order
         ▼
┌────────────────────────┐
│ OrderProducerService   │
│ publishOrder()         │
└────────┬───────────────┘
         │ RabbitTemplate.send()
         │ (exchange, routingKey, message)
         ▼
┌────────────────────────┐
│   RabbitMQ Exchange    │
│   "order.exchange"     │
└────────┬───────────────┘
         │ routingKey = "order.created"
         │ verifica bindings...
         ▼
┌────────────────────────┐
│   RabbitMQ Queue       │
│   "order.queue"        │
│   [mensagem aguarda]   │
└────────────────────────┘
         │
         ▼
┌────────────────────────┐
│   Consumer             │ ← Será criado no próximo vídeo!
│   (próximo vídeo)      │
└────────────────────────┘
```

## Estrutura RabbitMQ (valores definidos em `RabbitMQConfig`)

- Exchange: `order.exchange` (TopicExchange, durable)
- Filas:
  - `order.queue` (durable) - binding: `order.*`
  - `user.queue` (durable) - binding: `user.*`
  - `log.queue` (durable) - binding: `#` (tudo)
- Routing keys usados pela aplicação (constantes):
  - `order.created`
  - `order.updated`
  - `user.updated`
  - `user.created.ok`
  - `payment.created` (observação: nome `ROUTING_KEY_USER_DELETED` aponta para `payment.created` no código)

## Dependências / Pré-requisitos

- Java 17
- Maven (o projeto já contém `mvnw`/`mvnw.cmd`)
- RabbitMQ (o repositório já traz um `docker-compose.yml` para facilitar)

## Configuração

As configurações do RabbitMQ estão em `src/main/resources/application.yml` (exemplo usado no projeto):

```yaml
spring:
  rabbitmq:
    host: localhost
    username: admin
    password: admin123
    port: 5672
    application:
      name: rabbit-producer
  server: 8080
```

Você pode sobrescrever via variáveis de ambiente:
- SPRING_RABBITMQ_HOST
- SPRING_RABBITMQ_PORT
- SPRING_RABBITMQ_USERNAME
- SPRING_RABBITMQ_PASSWORD

## Executando localmente

1. Subir o RabbitMQ com Docker Compose (na raiz do repositório):

```powershell
# Windows PowerShell
docker-compose up -d
```

O management UI ficará disponível em http://localhost:15672 (usuário: `admin`, senha: `admin123`).

2. Rodar a aplicação (Windows):

```powershell
cd producer\demo
mvnw.cmd spring-boot:run
```

Unix / macOS:

```bash
cd producer/demo
./mvnw spring-boot:run
```

A aplicação escuta por padrão na porta 8080 (ver `application.yml`).

## Endpoints

- POST /order
  - Descrição: Recebe um `OrderDTO` e publica a mensagem no exchange configurado.
  - Payload (JSON):

```json
{
  "orderId": "123",
  "quantity": 2,
  "description": "Pedido de exemplo"
}
```

- Resposta: HTTP 201 Created com o corpo `"Pedido criado e publicado no RabbitMQ"`.

## Exemplo de teste rápido (curl)

```powershell
curl -X POST http://localhost:8080/order -H "Content-Type: application/json" -d "{\"orderId\":\"123\",\"quantity\":1,\"description\":\"teste\"}"
```

Ou usando http (httpie):

```bash
http POST :8080/order orderId=123 quantity:=1 description="teste"
```

## Observações técnicas

- Mensagens são publicadas como JSON e marcadas como PERSISTENT pelo `OrderProducerService`.
- O `RabbitAdmin` inicializa as filas/exchange/bindings automaticamente na inicialização da aplicação.
- O `TopicExchange` permite rotear mensagens por routing key usando padrões (`order.*`, `user.*`, `#`).