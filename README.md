# Users application back-end

## Requirements

install Java 1.7 or higher

install tomcat 8.x or higher

install mysql 5.7.17 or higher

## Build & development

To start a project use:
`git clone https://github.com/valerik931/user-app.git`

`cd user-app`

`mvn clean install`

`java -jar target/dependency/webapp-runner.jar target/*.war`

project will be available on http://localhost:8080/

## SQL

Before start project on your machine, add this tables to your MySQL database

`CREATE TABLE Feedback (
    feedbackId INT NOT NULL ,
    subject VARCHAR(128) NULL ,
    body VARCHAR(256) NULL ,
    fromEmail VARCHAR(128) NOT NULL ,
    createdTimestamp VARCHAR(25) NOT NULL ,
    PRIMARY KEY (feedbackId)
);`

`CREATE TABLE User
(
    userId INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    firstName VARCHAR(20) NOT NULL,
    lastName VARCHAR(20) NOT NULL,
    age INT(11) NOT NULL,
    sex VARCHAR(6) NOT NULL,
    city VARCHAR(20) NOT NULL,
    income INT(11) NOT NULL,
    createdTimestamp VARCHAR(25) NOT NULL,
    modifiedTimestamp VARCHAR(25)
);`

`CREATE TABLE FacebookUser
 (
     userId VARCHAR(20) PRIMARY KEY NOT NULL,
     firstName VARCHAR(20) NOT NULL,
     lastName VARCHAR(20) NOT NULL,
     birthDay INT(10),
     sex VARCHAR(6),
     createdTimestamp VARCHAR(25) NOT NULL,
     modifiedTimestamp VARCHAR(25)
 );`

## Preview

https://valera-user-app.herokuapp.com/api/users

## Specification

https://docs.google.com/document/d/1YB5Ow1lHN82gWVCLLsBPHrFJ67o5Oh-8laBbWgSic2c/