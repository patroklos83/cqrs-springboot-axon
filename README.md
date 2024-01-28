

# Sample CQRS Pattern using Spring Boot version 3 including Axon Framework/Server

An example of CQRS implementation using event-driven architecture, including event-sourcing using Axon Server and Framework.


## Requirements

For building and running the application you need:

-    JDK 19 or newer
-   [Eclipse IDE or other Java compatible IDE](https://www.eclipse.org/ide/)
-   [Docker 4.25.2 or newer ](https://www.docker.com/products/docker-desktop/)

## Some useful information

 - Built using Spring Boot 3
 - [Event-Driven and Event-Sourcing using Axon Server](https://developer.axoniq.io/en/axon-server/overview)
 - Spring Boot Axon Framework version 4.9.1

## Case Study

An Inventory Management system uses a microservice in the backend to execute and process Inventory requests [create new item / item stock adjustment]. When a new Item request is created it is being send as command to Axon Server for processing. All the commands stay in a queue until they are executed successfully.

![enter image description here](/images/create.png)

## Running the CQRS Service

To run the service there are 2 options. 

 1. Running the whole system in Docker containers. 
 2. Run only Axon Server on Docker container and launch the Spring Boot
    service from the IDE.

## Running the CQRS  Service on Docker containers

Step 1. Run Docker Compose file
*from the root directory run command* 

    docker compose up


## Running the Service from IDE 

Step 1. Import project in the IDE 

Step 2. From root directory run command

     docker compose -f docker-compose-dev up
 
Step 3. Clean and build the nested projects/springboot applications 
 
Step 4. Replace application.yaml file contents with application-local-dev.yaml [ *axon: axonserver: servers: localhost:8124* ]

Step 5. Go to SpringBoot project [in IDE] and run com.patroclos.cqrs.CqrsApplication as java application.

**Wait until the log message appears on screen indicating the service is started, for example

> ***************************************
> *            CQRS service started             *
> ***************************************

## Microservices REST API URLs

 - http://localhost:8080/order-service/swagger-ui/index.html#/   

## Creating a successful Event

When all services are up and running, go to below URL

http://localhost:8080/swagger-ui/index.html

Try creating an Inventory Create request with below sample JSON request

/inventoryitems/create

    {
      "initialStock": 10,
      "name": "Smartphone"
    }

![enter image description here](/images/create.PNG)

## Creating a Failed Event with Rollback

When all services are up and running, go to below URL

http://localhost:8080/order-service/swagger-ui/index.html#/

Try creating an Order with below sample JSON request
Customer with Id = 2 has inciefficient funds to pay for the Order Item.
This will result in a failed payment debit attempt. In continuation the Order will be updated from the event, as **CANCELLED**;

/order/create

    {
      "customerId": 2,
      "itemId": "a9112c62-0206-4711-beaf-220c2d1bbfb0"
    }


![enter image description here](/images/notnoughfunds.PNG)

/order/all

![enter image description here](/images/ordercancelled.PNG)

Fell free to grab a copy of this sample code, and play it yourself.