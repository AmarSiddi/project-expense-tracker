# Spring boot(Maven) - Spring Security(Annotations) - JWT/MySql(Authorization,Authentication)

## Getting Started

Need jdk, spring boot, working machine.

### Prerequisites

Install Spring Boot.
Configure your java in environmental variables.

### What is it?

 Spring boot project with, spring security.
 Signin and Signup API's with proper authentication and authorization
 Return a JWT token
 
 This project uses Bcrypt to encrypt the passwords.
 	
### Maven dependencies used
	* spring-boot-starter-data-jpa
	* spring-boot-starter-security
	* spring-boot-starter-web
	* mysql-connector-java
	* jjwt

### API Endpoints

SignUP - POST

```
http://localhost:5000/api/auth/signup

{
	"name": "user",
	"username" : "user",
	"email": "user@user.com",
	"password": "user"
}

```

SignIn - POST

```
http://localhost:5000/api/auth/signin

{
	"usernameOrEmail" : "user@user.com",
	"password" : "user"
}

```
