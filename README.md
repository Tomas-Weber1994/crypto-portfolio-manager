# Spring Boot web application - Crypto Portfolio Manager

## Description
A Spring Boot application for managing a cryptocurrency portfolio. It supports real-time price fetching from the CoinGecko API, portfolio valuation, and a RESTful API for adding, updating, sorting, and retrieving cryptocurrencies.

## Structure of the Application

### Core Components:
- **Crypto** – Represents a cryptocurrency, including ID, name, symbol, price, and quantity.
- **CryptoManager** – Handles core logic for adding, retrieving, and sorting cryptocurrencies.
- **CryptoPriceService** – Retrieves current CZK prices from the CoinGecko API. Portfolio value is updated at intervals defined in application.properties.
- **CryptoValidationService** – Loads and caches supported cryptocurrencies from CoinGecko for validation.
- **CryptoController** – Handles REST API requests (add, retrieve, sort, update).
- **GlobalExceptionHandler** – Handles exceptions such as invalid input or missing cryptocurrency.

## Endpoints:
- **POST /api/cryptos** – Adds a new cryptocurrency to3 the portfolio. The price is automatically fetched from the CoinGecko API.
- **GET /api/cryptos** – Retrieves all cryptocurrencies, with optional sorting by `price`, `name`, or `quantity` using the `sort` query parameter.
- **GET /api/cryptos/{id}** – Retrieves a specific cryptocurrency by its ID.
- **PUT /api/cryptos/{id}** – Updates the details of a cryptocurrency by its ID. The request body should contain the updated information for the cryptocurrency.
- **GET /api/portfolio-value** – Retrieves the total value of the cryptocurrency portfolio, calculated by summing the `price * quantity` of all cryptocurrencies in the portfolio.


## Usage
After running the application, you can access the API documentation via Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html#/
```

This interface allows you to easily interact with the API and test various endpoints.
Alternatively, you can import the provided Postman collection (available in the repository) and send HTTP requests directly using Postman.
