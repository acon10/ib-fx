# Getting Started

### Requirements
* Min Java 11 Version
* Maven
* Lombok Enabling
* PostgreSql
* RabbitMQ
* Docker, Docker-compose (in case of need)

### Running the application locally
* Active profile is dev
* RabbitMQ and Postgres can be used with docker-compose
  * Just need to uncomment the RabbitMQ information in property file. 
  * Note:I used remote free RabbitMQ server with url
  * For using docker-compose:
    * Install Docker Compose
    * Clone this repository
    * Go to project path
    * Run command: docker-compose up
* Execute the main method in the com.example.ib.IbFxApplication class from your IDE.
* Getting min instrument price example: http://localhost:8080/api/price/bid?instrument=GBP/USD
* Getting max instrument price example: http://localhost:8080/api/price/ask?instrument=GBP/USD
* Feed market data:  POST http://localhost:8080/api/price/feed Request body Ex: {"priceFeed":"106,EUR/USD,1.1000,1.2000,01-06-2020 12:01:01:001"}
* When project started dummy data will be saved to PostgreSql

