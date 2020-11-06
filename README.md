# rabbit-mq-spring-boot
Demo project Rabbit MQ for Spring Boot.

# Producer with 3 type Exchanges
* How to send message with:
1. Start service RabbitMQ Producer with ``localhost:8080``
## Direct Exchange
1. Request:
``
curl --location --request GET 'http://localhost:8080/tranning-rabbitmq/direct/producer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "77",
    "name": "Sonny Camera",
    "price": 30000000,
    "groupName": "camera"
}'
``

## Topic Exchange
``
curl --location --request GET 'http://localhost:8080/tranning-rabbitmq/topic/producer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "1",
    "name": "Sieu Nhan fashion",
    "price": 500000,
    "groupName": "fashion"
}'
``
## Fanout Exchange
``
curl --location --request GET 'http://localhost:8080/tranning-rabbitmq/fanout/producer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": "7711",
    "name": "SieuNhan Lego",
    "price": 30000000,
    "groupName": "doy"
}'
``

# Consumer
* How to check rabbitMQ consumer message.
1. Start service RabbitMQ Producer with ``localhost:8081``

NOTE: Build RabbitMQ by Docker or install on your device
