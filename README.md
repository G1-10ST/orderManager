OrderManager

A simple Spring Boot application for managing products and orders with JPA, Hibernate, and MySQL.

Features

Add, update, and list products

Create orders with multiple products (many-to-many)

Add and remove products from orders

RESTful APIs with Spring Web MVC

In-memory and MySQL persistence

Basic authentication (HTTP Basic) via Spring Security

API documentation with SpringDoc OpenAPI (Swagger UI)

Prerequisites

Java 21

Maven 3.6+

MySQL server (or any supported JDBC database)

Git

Getting Started

Clone the repository

git clone git@github.com:G1-10ST/orderManager.git
cd orderManager

Configure database Edit src/main/resources/application.properties:

spring.datasource.url=jdbc:mysql://localhost:3306/order_manager?useSSL=false&serverTimezone=UTC
spring.datasource.username=appuser
spring.datasource.password=s3cr3tP@ss
spring.jpa.hibernate.ddl-auto=update

Build the project

mvn clean package

Run the application

mvn spring-boot:run

Access the API docs

Swagger UI: http://localhost:8080/swagger-ui.html

Common Maven Commands

Compile: mvn compile

Test: mvn test

Package: mvn package

Clean: mvn clean

Usage Examples

Add a product

curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d '{"name":"iPhone 13","description":"Apple smartphone","price":799}'

Create an order

curl -X POST http://localhost:8080/api/orders \
-H "Content-Type: application/json" \
-d '{"items":[{"productId":1,"quantity":2}]}'

Add product to order

curl -X POST http://localhost:8080/api/orders/1/items \
-H "Content-Type: application/json" \
-d '{"productId":2,"quantity":1}'

Remove product from order

curl -X DELETE http://localhost:8080/api/orders/1/items/2

Contributing

Fork the repository

Create a feature branch (git checkout -b feature/YourFeature)

Commit your changes (git commit -m 'Add feature')

Push to the branch (git push origin feature/YourFeature)

Open a Pull Request

License

This project is licensed under the MIT License. Feel free to use and modify.

