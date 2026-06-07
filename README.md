# Kafka Notification System

Микросервисный пет-проект на Java и Spring Boot.

## Описание

Проект состоит из двух сервисов:

- `order-service` — принимает REST-запрос на создание заказа и отправляет событие в Kafka
- `notification-service` — читает событие из Kafka и сохраняет уведомление в PostgreSQL

## Стек

- Java 17
- Spring Boot 3
- Spring Kafka
- Spring Data JPA
- PostgreSQL
- Docker Compose
- Swagger / OpenAPI

## Запуск

1. Поднять инфраструктуру:

   docker-compose up -d

2. Запустить `order-service`:

   mvn -pl order-service spring-boot:run

3. Запустить `notification-service`:

   mvn -pl notification-service spring-boot:run

## Swagger

- Order Service: http://localhost:8081/swagger-ui.html
- Notification Service: http://localhost:8082/swagger-ui.html

## Основные endpoints

### order-service
- `POST /api/orders` — создать заказ

Пример тела запроса:

    {
      "customerName": "Имя Фамилия",
      "customerEmail": "random@example.com",
      "productName": "Название ноутбука",
      "quantity": 1,
      "pricePerUnit": 75000
    }

### notification-service
- `GET /api/notifications` — получить все уведомления
- `GET /api/notifications/by-email?email=ivan@example.com` — получить уведомления по email
- `GET /api/notifications/by-order/{orderId}` — получить уведомления по ID заказа

## Как это работает

1. Клиент отправляет запрос в `order-service`
2. `order-service` создаёт заказ и отправляет событие в Kafka
3. `notification-service` читает событие из Kafka
4. Уведомление сохраняется в PostgreSQL

## Особенности

- Используется Kafka в режиме **KRaft** 
- Для тестирования API подключен Swagger UI
- Архитектура построена на асинхронном взаимодействии через Kafka
