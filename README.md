Microservices 
------------------------------------------------------------------

    https://github.com/avamsykiran/Microservices_29Nov21_10Dec21_09001100.git

    Pre-Requisites
    --------------------------------
        Web MVC -       Servlet and JSP
        Spring IoC
        Spring Context
        Spring SpEL
        Spring Web  - REST 
        Spring Data
        Spring Boot
        Spring Test

    Lab Setup
    -----------------------------------
        JDK 1.8
        STS 4.0 or above
        MySQL
        Postman / Isomnia

    Case Study - BudgetTrackingApp
    ----------------------------------------

        1. Each user can register themselves
        2. Each registred user can login and 
            a. record his monthly transactions
            b. modify a recorded transaction
            c. retrive the monthly expenditure analysis / report

        Decomposition By Bussines Capbilitites & Sub-Domain

            User-Management-Service
                UserEntity              userId:long,fullName:string,email:string,password:string,mobile:string
                UserRepo
                UserService
                UserController
            
            Txn-Management-Service
                UserEntity              userId:long,currentBal:double,txns:Set<TransactionEntity>
                TransactionEntity
                UserRepo
                TransactionRepo
                UserService
                TransactionService
                UserController
                TransactionController

            Report-Management-Service
                UserModel
                TxnModel
                MonthlyStatementModel
                UserService
                TxnService
                MomthlyStatementService
                MonthlyStatementController

        API GateWay Pattern

                            AngularApp / AndriodApp /ReactApp..etc
                                            |↑
                                            ↓|
                                API GATEWAY SERVICE / EDGE SERVICE
                                        localhost:9999
                                            |↑
                                            ↓|
                    -------------------------------------------------------------------------
                    ↑↓                          ↑↓                                         ↑↓
            User-Management-Service   <--->  Txn-Management-Service    <---->   Report-Management-Service
                localhost:9100                  localhost:9200                      localhost:9300
             ↑↓          ↑↓                         ↓↑                                  ↓↑
           umsDB         ||                        tmsDB                                ||
                         -----------------------------------------------------------------
            
                
        Log Aggreatation,Performence Metrics and Distributed Tracing and Health Check Pattern 

                             AngularApp / AndriodApp /ReactApp..etc
                                            |↑
                                            ↓|
                            API GATEWAY SERVICE / EDGE SERVICE
                                     localhost:9999
                                            |↑
                                            ↓|
                     -------------------------------------------------------------------------
                     ↑↓                          ↑↓                                         ↑↓
            User-Management-Service   <--->  Txn-Management-Service    <---->   Report-Management-Service
                localhost:9100                  localhost:9200                      localhost:9300
             ↑↓     |    ↑↓                         ↓↑                                  ↓↑    |    
           umsDB    |    ||                        tmsDB                                ||    |    
                    |    -----------------------------------------------------------------    |    
                    ↓                               ↓                                         ↓    
        |------------------[Logs / Performence Metrics / Req Traces / Health Checks ]----------   
        |         
        |         
        ↓                                                                          
    DT Service                                                                     
    localhost:9411                                                                     


    External Configuaration

                             AngularApp / AndriodApp /ReactApp..etc
                                            |↑
                                            ↓|
                            API GATEWAY SERVICE / EDGE SERVICE
                                     localhost:9999
                                            |↑
                                            ↓|
                     -------------------------------------------------------------------------
                     ↑↓                          ↑↓                                         ↑↓
            User-Management-Service   <--->  Txn-Management-Service    <---->   Report-Management-Service
                localhost:9100                  localhost:9200                      localhost:9300
             ↑↓   ↑ |    ↑↓                         ↓↑                                  ↓↑    |    ↑
           umsDB  | |    ||                        tmsDB                                ||    |    |
                  | |    -----------------------------------------------------------------    |    |
                  | ↓                               ↓                                         ↓    |
        |---------|--------[Logs / Performence Metrics / Req Traces / Health Checks ]------------  |  
        |         |                                   ↑                                            |            
        |         ----------------------------------------------------------------------------------
        ↓                                                                                   ↑       
    DT Service                                                                         Config Service
    localhost:9411                                                                     localhost:9797
                                                                                            ↑
                                                                                       Git Repo/SVM Repo
                                                                                        [all config files]

   Discovery Service

                             AngularApp / AndriodApp /ReactApp..etc
                                            |↑
                                            ↓|
                            API GATEWAY SERVICE / EDGE SERVICE   <--------->        Discovery Service
                                     localhost:9999                                   localhost:9000
                                            |↑                                                  ↑↓
                ------------[register self and discover the peer microservice]--------------------
                ↑↓                          ↓|     ↑↓                                           ↑↓
                ||    ------------------------------------------------------------------------- ||
                ||    ↑↓                          ↑↓                                         ↑↓ ||
            User-Management-Service   <--->  Txn-Management-Service    <---->   Report-Management-Service
                localhost:9101                  localhost:9201                      localhost:9301
                localhost:9102                  localhost:9202                  
                localhost:9103                 
             ↑↓   ↑ |    ↑↓                         ↓↑                                  ↓↑    |    ↑
           umsDB  | |    ||                        tmsDB                                ||    |    |
                  | |    -----------------------------------------------------------------    |    |
                  | ↓                               ↓                                         ↓    |
        |---------|--------[Logs / Performence Metrics / Req Traces / Health Checks ]------------  |  
        |         |                                   ↑                                            |            
        |         ----------------------------------------------------------------------------------
        ↓                                                                                   ↑       
    DT Service                                                                         Config Service
    localhost:9411                                                                     localhost:9797
                                                                                            ↑
                                                                                       Git Repo/SVM Repo
                                                                                        [all config files]                                                    
Deve Platforma and Tools
---------------------------------------------------------------------

                                      Rest Api Client
                                        [PostMan]
                                            |↑
                                            ↓|
                            API GATEWAY SERVICE / EDGE SERVICE   <--------->        Discovery Service
                                      Spring Boot                                     Spring Boot
                                      Spring Cloud API Gateway                        Netflix Eureka Server
                                      Spring Discovery Client 
                                      Spring Cloud Config Client
                                      Spring Cloud loadbalancer  
                                     localhost:9999                                   localhost:9000
                                            |↑                                                  ↑↓
                ------------[register self and discover the peer microservice]--------------------
                ↑↓                          ↓|     ↑↓                                           ↑↓
                ||    ------------------------------------------------------------------------- ||
                ||    ↑↓                          ↑↓                                         ↑↓ ||
            User-Management-Service   <--->  Txn-Management-Service    <---->   Report-Management-Service
                Spring Boot                     Spring Boot                     Spring Boot
                Spring Discovery Client         Spring Discovery Client         Spring Discovery Client
                Open Feign (Feign Client)       Open Feign (Feign Client)       Open Feign (Feign Client)       
                Spring Cloud Load balancer      Spring Cloud Load balancer      Spring Cloud Load balancer
                Spring Cloud Resilieance4j      Spring Cloud Resilieance4j      Spring Cloud Resilieance4j
                Spring Boot Actuator            Spring Boot Actuator            Spring Boot Actuator
                Spring Cloud Sleuth             Spring Cloud Sleuth             Spring Cloud Sleuth
                Spring Cloud Config Client      Spring Cloud Config Client      Spring Cloud Config Client
                Spring Web (REST)               Spring Web (REST)               Spring Web (REST)           
                Spring Data JPA                 Spring Data JPA
                MySQL ConnectorJ                MySQL ConnectorJ

                localhost:9101                  localhost:9201                      localhost:9301
                localhost:9102                  localhost:9202                  
                localhost:9103                 
             ↑↓   ↑ |    ↑↓                         ↓↑                                  ↓↑    |    ↑
           umsDB  | |    ||                        tmsDB                                ||    |    |
                  | |    -----------------------------------------------------------------    |    |
                  | ↓                               ↓                                         ↓    |
        |---------|--------[Logs / Performence Metrics / Req Traces / Health Checks ]------------  |  
        |         |                                   ↑                                            |            
        |         ----------------------------------------------------------------------------------
        ↓                                                                                   ↑       
    DT Service                                                                         Config Service
    zipkin-server.jar                                                                   Spring Boot
                                                                                        Spring Cloud Config Server
    localhost:9411                                                                     localhost:9797
                                                                                            ↑
                                                                                       Git Repo/SVM Repo
                                                                                        [all config files]                      

    Case Study Implementation
    --------------------------------------------------

    Step 1:  Decomposition Design Pattern - Develop the microsoervices and provide inter-service communication

        user-management-service spring boot,spring web,spring data jpa,MySql driver,dev tools,spring cloud open feign
                                @SpringBootApplication
                                @EnableFeignClients 
        txn-management-service  spring boot,spring web,spring data jpa,MySql driver,dev tools,spring cloud open feign
                                @SpringBootApplication
                                @EnableFeignClients 
        reporting-service       spring boot,spring web,dev tools,spring cloud open feign
                                @SpringBootApplication
                                @EnableFeignClients 

    Step 2: Cross Cutting Desing Pattern - Discovery Service and Client Side Load Balancing

        bt-discovery-service            spring boot,dev tools,
                                        netflix eureka server
                                        @SpringBootApplication
                                        @EnableEurekaServer

        user-management-service         spring cloud discovery client,
                                        spring cloud loadbaalncer
                                        @SpringBootApplication
                                        @EnableFeignClients 
                                        @EnableDiscoveryClient

        txn-management-service          spring cloud discovery client,
                                        spring cloud loadbaalncer
                                        @SpringBootApplication
                                        @EnableFeignClients 
                                        @EnableDiscoveryClient

        reporting-service               spring cloud discovery client,
                                        spring cloud loadbaalncer
                                        @SpringBootApplication
                                        @EnableFeignClients 
                                        @EnableDiscoveryClient

    Step 3:   Integration Design Patterns - API Gateway

        bt-gateway-service              spring boot,dev tools,
                                        Spring Cloud Api Gateway
                                        spring cloud discovery client,
                                        spring cloud loadbaalncer
                                        @SpringBootApplication
                                        @EnableDiscoveryClient

        bt-discovery-service            
        user-management-service         
        txn-management-service          
        reporting-service               

    Step 4:   Observability Design Pattern - Distributed Tracing and Log Aggregation - Sleuth + Zipkin

        bt-gateway-service             spring cloud sleuth,spring boot actuator,spring cloud starter sipkin
        bt-discovery-service            
        user-management-service        spring cloud sleuth,spring boot actuator,spring cloud starter zipkin 
        txn-management-service         spring cloud sleuth,spring boot actuator,spring cloud starter zipkin 
        reporting-service              spring cloud sleuth,spring boot actuator,spring cloud starter zipkin

        zipkin executable jar            
                https://search.maven.org/remote_content?g=io.zipkin&a=zipkin-server&v=LATEST&c=exec 
                java -jar zipkin.jar

    Step 5:   Cross Cutting Design Pattern - Circuit Breaking and Fallback - Reselieance4j

        bt-gateway-service             
        bt-discovery-service            
        user-management-service        
        txn-management-service        spring cloud resilience4j
        reporting-service             spring cloud resilience4j

    Step 6:   Cross Cutting Design Pattern - External Config - Spring Cloud Config Server

        bt-gateway-service            spring cloud config client,spring cloud starter bootstrap
        bt-discovery-service            
        bt-config-service             spring cloud config server
        user-management-service       spring cloud config client,spring cloud starter bootstrap 
        txn-management-service        spring cloud config client,spring cloud starter bootstrap
        reporting-service             spring cloud config client,spring cloud starter bootstrap


    Recomendations : Prometheus - Metrics Aggregation - Observability Pattern
                     OAuth 2.0 + Spring Security
                     CQRS / Saga / Event Driven Micorservices ----------> RabbitMQ / Kafka
                     Jenkins + Docker + Kubernates + AWS 

    Assignment (D2H Portal)
    -------------------------------------------------------------------------------------

                                AngularApp / AndriodApp /ReactApp..etc
                                            |↑
                                            ↓|
                            API GATEWAY SERVICE / EDGE SERVICE   <--------->        Discovery Service
                                            |↑                                                  ↑↓
                ------------[register self and discover the peer microservice]--------------------
                ↑↓                          ↓|                                                  ↑↓
                ||    --------------------------------------------------------------------      ||
                ||    ↑↓                                                                ↑↓      ||
            Consumer-Service   <-------------------------------------------------->  Billing-Service                 
             ↑↓                                                                       ↓↑
           cDB                                                                        bDB
           /consuemr                                                                /consumer/{mobile}/bills
            ConsumerEntity                                                              ConsumerEntity
                mobile (PK)                                                                mobile (PK)
                fullName                                                                    totalOutStanding
                emailId                                                                      bills (Set<BillEntity>)
                address                                                                 BillEntity
                                                                                            billId
                                                                                            billDate
                                                                                            dueDate
                                                                                            billAmount
                                                                                            status : (PENDING/PAID)
                                                                                            billedFor : Cosnuemr

                
