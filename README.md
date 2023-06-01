# Spring Boot with MercadoPago
Simple web application with Spring Boot using *redirect* aproach
![Sequence](sequence.png)

## Requirements
1. MercadoPago account
2. Application created at MercadoPago console
3. Access Token (from Credentials)

## Usage
1. Run the server
```bash
MP_ACCESS_TOKEN=[PASTE_YOUR_ACCESS_TOKEN_HERE] ./gradlew bootRun
```
2. Go to
```bash
http://localhost:8080/createAndRedirect
``` 
3. Make your payment journey using **Credit Card**

Status | Credit Card | Number | Holder | Expiration | CVV | DNI | Email 
------ | ----------- | ------ | ------ | ---------- | --- | --- | ----- 
REJECTED | Mastercard | 5031755734530604| EXPI GOMEZ | 11/25 | 123 | 31256588 | expi_gomez@gmail.com |
APROVED | Mastercard |  5031755734530604 | APRO GOMEZ | 11/25 | 123 | 31256588 | apro_gomez@gmail.com