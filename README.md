 Getting Started

## Build Project

```
./mvnw package -DskipTests=true
```

## Run Tests
```
./mvnw verify
```


## Run Project

```
java -jar target/employees-0.0.1-SNAPSHOT.jar
```


### Application API


Criar novo funcionário:

curl -X POST "http://localhost:8080/api/employees" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"email@mail.com\", \"firstName\": \"nome1\", \"lastName\": \"nome2\", \"pisNumber\": \"74836250552\"}"

Consultar funcionário:

curl -X GET "http://localhost:8080/api/employees/1" -H "accept: */*"

Atualizar dados do funcionário:

curl -X PUT "http://localhost:8080/api/employees/1" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"email@mail.com\", \"firstName\": \"nome3\", \"lastName\": \"nome4\", \"pisNumber\": \"74836250552\"}"


Remover funcionário:

curl -X DELETE "http://localhost:8080/api/employees/1" -H "accept: */*"


### Swagger Documentation

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


### Database Access

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

`JDBC URL`: jdbc:h2:mem:ciss


