
# Racing Challenge - Week XII

## Introduction

This project is a microservices-based application for managing car racing events. It provides APIs for creating, updating, and deleting car information, creating racing events, simulating races, and retrieving race results. The project is built using Java 17, Maven, Spring Boot, Spring Data MongoDB, OpenFeign to make RESTful calls to other microservices, and RabbitMQ to allow the microservices to communicate with each other asynchronously.

## Table of Contents
- [Features](#features)
- [Technologies](#technologies)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
- [API Endpoints](#api-endpoints)
- [Tests](#tests)




## Features

#### Car Management:

- Create, update, and delete car information.
- Retrieve car details by ID.
- List all available cars.

#### Race Management:
- Create racing events with a list of participating cars.
- Simulate a race and store race results.
- Retrieve race details by ID.

#### Race Result Management:
- Fetch race results for analysis.
- Retrieve race results by ID.

#### Integration with Other Microservices:
- Communicate with other microservices using OpenFeign.
- Utilize RabbitMQ for asynchronous event-driven communication.

## Technologies

- Java 17
- Maven
- Spring Boot
- Spring Data MongoDB
- RabbitMQ
- OpenFeign


## Getting Started
### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK): This project is built with Java, so you'll need to have a compatible JDK installed. We recommend using OpenJDK version 11 or later.

- Maven: Maven is used for building and managing project dependencies. You can download and install Maven from the official website.

- MongoDB: This project uses MongoDB as its database. You should have MongoDB installed and running.

- RabbitMQ: RabbitMQ is used for message queuing. Ensure RabbitMQ is installed and running. You can download RabbitMQ from the official website.

- Postman (Optional): For testing API endpoints, you can use tools like Postman to make requests and verify responses.

Follow these steps to set up and run the Racing Compass project locally:

1 .   Clone the repository

```bash
git clone https://github.com/RafaelRSR/Racing-Compass.git
cd racing-compass
```
2 . Build the project
```bash
mvn clean install
```
3 . Run the application
```bash
java -jar target/racing-compass-0.0.1-SNAPSHOT.jar
```
4 . The API will be accessible at:
``` 
http://localhost:8080
```

## API Endpoints

### Car Management
- `GET /v1/cars` : List all cars.
-  `GET /v1/cars/get/{carId}` : Retrieve car details by ID.
- `POST /v1/cars/create` : Create a new car.
- `PUT /v1/cars/update/{carId}` : Update car information by ID.
- `DELETE /v1/cars/delete/{carId}` : Delete a car by ID.
### Race Management
- `POST /v1/race/create` : Create a new racing event.
- `POST /v1/race/simulate/{raceId}` : Simulate a race and store results.
### Race Result Management
- `GET /v1/race-results` : List all race results.
- `GET /v1/race-results/get/{id}`: Retrieve race results by ID.

## Tests

1 . Make sure you have the project set up and running as explained in the Getting Started section.

2 . Open a terminal and navigate to the project's root directory.

3 . Run the tests using the following command:

```bash
  ./mvnw test
```

This command will execute all the test cases in the project and provide a summary of the results.

**Note: If you're using Windows, you can use mvnw.cmd instead of ./mvnw in the command.**

After the tests are completed, you'll see an output indicating the number of tests run, passed, and failed.
