# Getting Started

This sample project demonstrates how to use AWS secret manager service with spring-boot framework as application properties configuration.

Read more : https://medium.com/@sopheamak/aws-secret-manager-service-as-application-properties-with-spring-boot-f46fe6bd44f7


# Requirements
1 - Java OpenJDK 1.8 to up
2 - Spring boot version 2x
3 - spring cloud framework

# dependencies with spring cloud AWS

pom.xml - add the following dependencies
````
<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-context</artifactId>
      <version>2.1.0.RELEASE</version>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-aws-secrets-manager-config</artifactId>
    <version>2.1.0.RELEASE</version>
</dependency>
````

Resources folder : add the bootstarp.yml properties for spring cloud aws see the following
````
resources
    application.yml
    application.local.yml
    bootstrap.yml  ## it is used for aws cloud
    bootstrap-local.yml  ## it is used by appliaction-local.yml
````
bootstrap.yml
````
aws:
    secretsmanager:
        name: backend
cloud:
    aws:
        region:
            static: ap-southeast-1

````

How to create properties in AWS secret manager : https://docs.google.com/document/d/1UhMUOAI1y50unYE79lYZKAvuQXLRaEG25_HxaLd7ip0/edit?usp=sharing


# Run springboot in local environment

````
mvn clean spring-boot:run -Dspring-boot.run.profiles=local
````
Browser : http://localhost:8090/api/test/v1

# deploy the jar file in EC2 AWS server

The EC2 instance must attache the roles with permissions Secret Manager see above

The server must install java 8 JDK ( Amazon Linux OS)


````
sudo yum install java-1.8.0-openjdk
````

suppose we put the jar file in /home/ec2-user/app.jar

````
cd /home/ec2-user
java -jar app.jar


````



````
curl http://13.250.38.249:8090/api/test/v1

{
environment: "aws",
types:- [
"AWSzone001"
],
applicationId: "backend-test"
}

````
