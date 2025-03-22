# Crypto Portfolio Manager

## Description
This application is a simple Spring Boot project for managing a list of cryptocurrencies. It provides basic functionalities to add, retrieve, sort, and search for cryptocurrencies using various endpoints.

## Structure of the Application

### Main Classes:
- **Crypto** – A model class representing a cryptocurrency, containing information about ID, name, symbol, price, and quantity.
- **CryptoManager** – A service class managing the cryptocurrencies, providing methods for adding, sorting, and retrieving cryptocurrency data.
- **CryptoController** – A REST controller handling HTTP requests related to cryptocurrencies, such as adding, sorting, retrieving, and searching by ID.
- **GlobalExceptionHandler** – A controller advice class handling exceptions, for example when a cryptocurrency with a given ID is not found.

## Endpoints:
- **POST /cryptos** – Adds a new cryptocurrency.
- **GET /cryptos** – Retrieves all cryptocurrencies, with optional sorting by `price`, `name`, or `quantity` using the `sort` query parameter.
- **GET /cryptos/{id}** – Retrieves a specific cryptocurrency by its ID.
- **PUT /cryptos/{id}** – Updates the details of a cryptocurrency by its ID. The request body should contain the updated information for the cryptocurrency.
- **GET /portfolio-value** – Retrieves the total value of the cryptocurrency portfolio, calculated by summing the `price * quantity` of all cryptocurrencies in the portfolio.


## Usage
After running the application, you can access the API documentation via Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html#/
```

This interface allows you to easily interact with the API and test various endpoints.
