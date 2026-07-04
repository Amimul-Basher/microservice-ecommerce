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

  