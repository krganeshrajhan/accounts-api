# Account Api
Accounts Api

## Instructions

Following instructions would guide you through running Accounts Api application

### Prerequisites
- Requires Java 8 JDK
- Maven installed with PATH set

### Steps
1 Run build and compile as below
```
mvn clean package

``` 
2. Start the application by running the below command 
```
mvn spring-boot:run
```
3.a Login to Db Console
```
http://localhost:9000/h2-console

username: sa
password: sa
url: jdbc:h2:mem:ACCOUNTS;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

click connect
```
3.b. Execute create scripts
```
DELETE FROM TRANSACTIONS;
DELETE FROM ACCOUNTS;
DELETE FROM USERS;
INSERT INTO USERS(ID, USER_NAME) VALUES('1', 'user');
INSERT INTO ACCOUNTS VALUES('123456', 'SGDEBITAC', 'SAVINGS', parsedatetime('04-07-2021', 'dd-MM-yyyy'), 'SGD', 345.80, '1');
INSERT INTO ACCOUNTS VALUES('134567', 'SGDEBITAC', 'CURRENT', parsedatetime('04-07-2021', 'dd-MM-yyyy'), 'SGD', 2456.53, '1');
INSERT INTO ACCOUNTS VALUES('123534', 'SGDEBITAC', 'SAVINGS', parsedatetime('04-07-2021', 'dd-MM-yyyy'), 'SGD', 2456.27, '1');
INSERT INTO TRANSACTIONS VALUES('1', parsedatetime('04-07-2021', 'dd-MM-yyyy'), 20.56, 'Debit', '', '123456', '134567');
INSERT INTO TRANSACTIONS VALUES('2', parsedatetime('05-07-2021', 'dd-MM-yyyy'), 84.52, 'Credit', '', '134567', '123534');
```
4.a. View Accounts: Search for accounts in the following endpoint
```
http://localhost:9000/accounts/{userId}?pageStart={pageNo}&pageSize={pageSize}

e.g.
http://localhost:9000/accounts/1?pageStart=0&pageSize=250
```
4.b. View Transactions: Search Transaction for the respective account
```
http://localhost:9000/transactions/134567?pageStart={pageNo}&pageSize={pageSize}

e.g.
http://localhost:9000/transactions/134567?pageStart=0&pageSize=250
```