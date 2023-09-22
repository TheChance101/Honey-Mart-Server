

# Honey Mart Server

> Honey Mart Server acts as the backend app for the Honey Mart shopping application, serving as the backend infrastructure that powers the entire shopping experience. This server application is implemented using Ktor, a lightweight yet powerful Kotlin framework for building asynchronous servers and clients, and it relies on PostgreSQL as the database to store and manage the app's data.

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Clone the Project](#clone-the-project)
  - [Set Up the Database](#set-up-the-database)
  - [Configure IntelliJ IDEA](#configure-intellij-idea)
- [Usage](#usage)
- [Endpoints](#endpoints)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## Introduction
Honey Mart is a shopping Android app designed to provide an effortless and enjoyable shopping experience for its users. The backend server plays a pivotal role in ensuring that users can browse products, add items to their cart, and access their order history seamlessly.

This README serves as a guide for developers and contributors to understand the Honey Mart Backend, set it up locally, and contribute to its ongoing development.

## Features
- **Market Management**: Owners can manage markets, and administrators can approve new markets to join the app.
- **Category and Product Management**: Easily manage categories and products within the app, ensuring an up-to-date product catalog.
- **User Account Management**: Users can create accounts, access wish lists, and track their orders seamlessly.
- **Coupon Utilization**: Users can utilize coupons to avail discounts, and owners can efficiently handle tasks related to coupon management.
- **Add to Cart**: Users can add products to their cart before proceeding to the ordering process.
- **Order Notifications**: Users and owners will receive real-time notifications when the order state changes, keeping them informed throughout the order process.
- **Reviews**: Users can add reviews to completed orders, providing valuable feedback and insights to improve the shopping experience.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- **PostgreSQL**:
  - [Download PostgreSQL](https://www.postgresql.org/download/)

- **Docker**:
  - [Download Docker Desktop for Windows and macOS](https://www.docker.com/products/docker-desktop)

- **IntelliJ IDEA with Kotlin support**:
  - [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

- **pgAdmin** (optional, for database management):
  - [Download pgAdmin](https://www.pgadmin.org/download/)
 - **Firebase App**:
  - [Create Firebase Project](https://console.firebase.google.com/u/0/)

## Getting Started

### Clone the Project

1. Open your terminal or command prompt.

2. Clone the project repository:

   ```shell
   git clone https://github.com/yourusername/honey-mart-server.git
   cd honey-mart-server
### Set Up the Database

1.  **Create a Database**:
    
    -   Open a terminal or command prompt and log in to PostgreSQL using the `psql` command or pgAdmin:
`psql -U postgres`
	-   Replace `postgres` with your PostgreSQL username if different.
	-   Create a new database (replace `honey_mart_db` with your preferred name):
    `CREATE DATABASE honey_mart_db;`
### Configure IntelliJ IDEA

1.  In IntelliJ IDEA, configure the database connection using the following details:
    
    -   Host: `localhost` (or your PostgreSQL server's hostname/IP)
    -   Port: `5432` (or your PostgreSQL server's port)
    -   Database: `honey_mart_db` (or your database name)
    -   User: Your PostgreSQL username
    -   Password: Your PostgreSQL password (if set)
 2. Configure Environment Variables to contain the following key-value pairs:
   - adminEmail = Your admin email
	 - adminFullName = Your admin full name
	 - adminPassword = Your admin password
	 - databaseName = `honey_mart_db` (or your database name)
	 - databasePassword = Your PostgreSQL password (if set)
	 - databaseUsername = Your PostgreSQL username
	 - firebase_key = Firebase admin SDK private key
	 - HONEY_JWT_SECRET = JWT for the Token
	 - honey_secret_api_key = any Api key you want
	 - host = `localhost` (or your PostgreSQL server's hostname/IP)
	 - port = `5432` (or your PostgreSQL server's port)
    
**Build and Run the Docker Container**:
-   Open a terminal or command prompt in your project directory (where the `Dockerfile` is located).
-   Build a Docker image for your server:
    
    `docker build -t honey-mart-server .`
1.  Your server is now running in a Docker container and accessible at [http://localhost:8080](http://localhost:8080/).
    

## Usage

Explore the various endpoints and features of the Honey Mart Server to build your online shopping application.

## Endpoints

Here are the main endpoints provided by the server:

-   **Market**
-   **Category**
-   **Product**
-   **Cart**
-   **User**
-   **Wishlist**
-   **Owner**
-   **Order**
-   **Coupon**
-   **Admin**
-   **Notifications**

#### Explore [HoneyMart Postman Collection](https://elements.getpostman.com/redirect?entityId=585001-fa3932c0-4021-4696-aa82-148961fc62d5&entityType=collection)

## Screenshots
![postman](https://github.com/TheChance101/Honey-Mart-Server/assets/63457278/85a7ada4-029c-4c34-b853-b5ac5e92089a)
![project](https://github.com/TheChance101/Honey-Mart-Server/assets/63457278/124ed9da-2202-4433-b8b7-a1d02437f34c)
## ERD
![Untitled](https://github.com/TheChance101/Honey-Mart-Server/assets/63457278/4ead1aae-4827-461c-a333-8e862e8348cf)
## Contributing
<a href="https://github.com/TheChance101/Honey-Mart-Server/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=TheChance101/Honey-Mart-Server" />
</a>

## License
	Copyright (c) 2023 The Chance
- This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/M7mdSh3banX/Honey-Weather/blob/master/LICENSE) file for details.
