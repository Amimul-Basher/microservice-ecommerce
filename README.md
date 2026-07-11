 # Microservice Ecommerce
 ## Product Service

## Order Service

## Inventory Service

## Discovery Service
Created discovery Server

**Step 1**  
Add spring cloud BOM dependency to the Parents [pom.xml](pom.xml) file  
Add netflix eureka server to its dependencies  
Adding properties [application-ds.properties](discovery-server/src/main/resources/application-ds.properties)
Annotate the main class with `@EnableEurekaServer`  
**Step 2** : Configure Discovery Client  
Add netflix Eureka client dependency  
Adding annotation @EnableEurekaClient  
Add name to each application to uniquely identify in Eureka dashboard  
For multiple instance server port should be defined as 0  
**Step 3** : Configure Web Client for load balancing  
Create WebClient builder using @Bean and annotate it with @LoadBalanced  
Create webClient bean with that load balancer

Order Service
│ WebClient  
▼  
http://inventory-service  
│  
   ▼  
Spring Cloud LoadBalancer  
     │  
┌────┴────┐  
│                     │  
▼                     ▼  
Inventory #1   Inventory #2  
8082           8083


## Circuit Breaker  
Check circuit breaker status using http://localhost:8081/actuator/health  
Add Dependency Spring Cloud circuit Breaker Resilience4j, Spring Boot starter actuator [POM File](/order-service/pom.xml)
Configure application properties for open, halfOpen and closed status  
Add TimeLimiter and Reload properties in [Properties](/order-service/src/main/resources)  
Add circuit breaker annotation to the methods to add resilience on it [InventoryClient](/order-service/src/main/java/com/bdpd/orderservice/clients/InventoryClient.java).  
For time limiter add TimeLimiter annotation with name and fallBack method
To check the timeout and retry event visit using http://localhost:8081/actuator  

## Distributed tracing implementation
Add micrometer dependency to the pom file  
Add zipkin dependency to the [pom](/api-gateway/pom.xml)  
Install zipkin and open port using [docker file](docker-compose.yml)  
Add properties to give access to the metrices and allow 100% to be traced in [properties](api-gateway/src/main/resources/application-ag.properties)

## Implementation of event driven architecture

**Step 1** : Develop producer and configure to send message to the broker
Add dependencies of Apache kafka ( Broker ), ZooKeeper (Orchestrator), to the [POM](order-service/pom.xml)  
Add the service to be running as docker container using [Docker Compose](docker-compose.yml)
Add properties with topics and the broker url in [Applicaiton Properties](order-service/src/main/resources/application-os.properties)  
Use KafkaTemplate to send message to the broker kafka as key value pair. [Order Service](order-service/src/main/java/com/bdpd/orderservice/service/OrderServiceImpl.java)  
The key is the topics name and the value should be an object defined as an event object in [OrderPlacedEvent](order-service/src/main/java/com/bdpd/orderservice/event/OrderPlacedEvent.java)  
Now to serialize and deserialize the key and object add properties to [Application properties](order-service/src/main/resources/application-os.properties)  
Use localhost:8086 to access the kakfa broker dashboard  

**Step 2** : Develop consumer to send notification to the end user



## Improvement
Want to make a request from inventory to order to check if order is placed so that inventory can be updated  
**Chalanges**
Order service doing async call to the inventory service, Now the inventory service should make a async call too.  

