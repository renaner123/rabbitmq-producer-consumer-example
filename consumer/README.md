# Consumer (RabbitMQ)

Este serviço é o consumidor do projeto e foi pensado para uso didático em vídeo.
Aqui você consegue visualizar, na prática, como funciona confirmação de mensagens com RabbitMQ: **ACK**, **NACK com descarte**, **NACK com requeue** e o caso de **sem ACK/NACK**.

## O que este consumer faz

- Escuta a fila `order.queue` com **acknowledge manual**.
- Escuta a fila `log.queue` com **acknowledge automático**.
- Converte mensagens JSON para `OrderDTO`.
- Simula cenários de sucesso e falha com base no campo `description` da mensagem.

## Fluxo simplificado

```text
Producer -> Exchange (order.exchange) -> Filas (order.queue / log.queue) -> Consumer
```

## Comportamentos didáticos (ACK/NACK)

No listener de `order.queue`, o comportamento muda conforme o texto de `description`:

- `description` contém `error`
  - envia `basicNack(..., requeue=false)`
  - mensagem descartada (ou vai para DLQ, se existir)

- `description` contém `requeue`
  - envia `basicNack(..., requeue=true)`
  - mensagem volta para a fila e pode ser consumida novamente

- `description` contém `noack`
  - não envia ACK nem NACK
  - mensagem fica não confirmada até desconexão do consumidor

- qualquer outro valor
  - processamento normal
  - envia `basicAck(...)`

## Filas consumidas

- `order.queue`
  - listener com ack manual (`@RabbitListener(queues = "order.queue")`)

- `log.queue`
  - listener com factory `autoAckRabbitListenerContainerFactory`
  - ack automatico (modo `AUTO`)

## Configuracao

As configuracoes ficam em `src/main/resources/application.yml`:

- `spring.main.web-application-type: none`
  - aplicacao non-web (nao sobe servidor HTTP)
- `spring.rabbitmq.*`
  - host, porta, usuario e senha
- `spring.rabbitmq.listener.simple.acknowledge-mode: manual`
  - padrao manual para os listeners

Voce pode sobrescrever via variaveis de ambiente:

- `SPRING_RABBITMQ_HOST`
- `SPRING_RABBITMQ_PORT`
- `SPRING_RABBITMQ_USERNAME`
- `SPRING_RABBITMQ_PASSWORD`

## Pre-requisitos

- Java 17
- Maven (ou wrapper `mvnw` / `mvnw.cmd`)
- RabbitMQ em execucao (ex.: `docker-compose.yml` na raiz do repositorio)

## Como rodar

1. Subir o RabbitMQ na raiz do projeto:

```powershell
docker-compose up -d
```

2. Rodar o consumer (Windows PowerShell):

```powershell
cd consumer
mvnw.cmd spring-boot:run
```

3. (Opcional) Rodar o producer para enviar mensagens:

```powershell
cd producer
mvnw.cmd spring-boot:run
```