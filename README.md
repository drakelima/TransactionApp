# TransactionApp

TransactionApp is a Spring Boot application that allows storing and retrieving purchase transactions with a description, date, and amount in USD. It also supports retrieving the stored transaction with the amount converted to a specified country's currency using the Treasury Reporting Rates of Exchange API.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 11 or higher
- Maven
- MySQL Server

### Installing

A step-by-step series of examples that tell you how to get a development environment running:

1. Clone the repository to your local machine.
2. Create a MySQL database named `transactiondb` and a user with access to it.
3. Update the `src/main/resources/application.properties` file with your MySQL user and password.
4. Navigate to the root directory of the project in your terminal and run:

```shell
mvn spring-boot:run
```

## Running Tests

To run the automated unit tests for this system, use the following command:

```shell
mvn test
```

