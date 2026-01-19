# RabbitMQ + Spring Boot: Fundamentos e Setup

Este repositório acompanha o **[Vídeo 1](https://www.youtube.com/watch?v=tyn0aS41ybw) da série RabbitMQ com Spring Boot**.

Neste primeiro vídeo o foco é:

- Entender os conceitos principais do RabbitMQ
- Visualizar o fluxo de mensagens
- Subir o RabbitMQ com Docker
- Explorar o painel de administração

---

## Conteúdo atual do projeto

Este repositório contém apenas o que foi apresentado no **Vídeo 1**:

- [Diagrama](fluxo-rabbit-direct.png)
- [Docker Compose](docker-compose.yml)
## Como subir o RabbitMQ

Execute:

Clone o projeto e acesse a pasta raiz:

Execute:

```bash
docker-compose up -d
```

Acesse o painel em:

> http://localhost:15672

Usuário e senha:

> admin / admin123

## Continuação da série

Nos próximos vídeos vamos evoluir esse projeto com:

- Producer em Spring Boot
- Consumer em Spring Boot + cenários com Ack, Nack e Requeue
- Lidando com mensagens em produção pela interface

Esse repositório será atualizado conforme os próximos vídeos forem publicados.
