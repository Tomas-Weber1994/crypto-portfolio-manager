# Spring Boot web application - Crypto Portfolio Manager

## Description
A Spring Boot application for managing a cryptocurrency portfolio. It supports real-time price fetching from the CoinGecko API, portfolio valuation, and a RESTful API for adding, updating, sorting, and retrieving cryptocurrencies.

## Structure of the Application

### Core Components:
- **Crypto** – Represents a cryptocurrency, including ID, name, symbol, price, and quantity.
- **CryptoManager** – Handles core logic for adding, retrieving, and sorting cryptocurrencies.
- **CryptoPriceService** – Retrieves current CZK prices from the CoinGecko API. Portfolio value is updated at intervals defined in `application.properties`.
- **CryptoValidationService** – Loads and caches supported cryptocurrencies from CoinGecko for validation.
- **CryptoController** – Handles REST API requests (add, retrieve, sort, update).
- **GlobalExceptionHandler** – Handles exceptions such as invalid input or missing cryptocurrency.

## Endpoints:
- **POST /api/cryptos** – Adds a new cryptocurrency to the portfolio. The price is automatically fetched from the CoinGecko API.
- **GET /api/cryptos** – Retrieves all cryptocurrencies, with optional sorting by `price`, `name`, or `quantity` using the `sort` query parameter.
- **GET /api/cryptos/{id}** – Returns details of a cryptocurrency with the given `id`.
- **PUT /api/cryptos/{id}** – Updates an existing cryptocurrency. The `id` in the request path and body must match. Trying to manually update the price has no effect.
- **GET /api/portfolio-value** – Returns the total value of the portfolio, calculated as `price * quantity` for each cryptocurrency.

## Usage
After running the application, you can access the API documentation via Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html#/
```

This interface allows you to easily interact with the API and test various endpoints.
Alternatively, you can import the provided Postman collection (available in the repository) and send HTTP requests directly using Postman.
