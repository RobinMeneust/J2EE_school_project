CREATE DATABASE IF NOT EXISTS j2ee_project_db;
USE j2ee_project_db;
# DROP DATABASE j2ee_project_db;

CREATE TABLE IF NOT EXISTS Address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    streetAddress VARCHAR(60),
    postalCode VARCHAR(30),
    city VARCHAR(60),
    country VARCHAR(60)
);

CREATE TABLE IF NOT EXISTS User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    email VARCHAR(50),
    password VARCHAR(30),
    phoneNumber VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS Administrator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT,
    FOREIGN KEY (idUser) REFERENCES User(id)
);


CREATE TABLE IF NOT EXISTS LoyaltyAccount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    loyaltyPoints INT,
    startDate DATE,
    description VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS Discount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    startDate DATE,
    endDate DATE,
    discountPercentage INT
);

CREATE TABLE IF NOT EXISTS Category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description VARCHAR(300),
    idDiscount INT,
    FOREIGN KEY (idDiscount) REFERENCES Discount(id)
);

CREATE TABLE IF NOT EXISTS LoyaltyProgram (
    id INT PRIMARY KEY AUTO_INCREMENT,
    durationNbDays INT,
    idLoyaltyAccount INT,
    FOREIGN KEY (idLoyaltyAccount) REFERENCES LoyaltyAccount(id)
);


CREATE TABLE IF NOT EXISTS LoyaltyLevel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    requiredPoints INT,
    idLoyaltyProgram INT,
    idDiscount INT,
    FOREIGN KEY (idLoyaltyProgram) REFERENCES LoyaltyProgram(id),
    FOREIGN KEY (idDiscount) REFERENCES Discount(id)
);


CREATE TABLE IF NOT EXISTS Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    stockQuantity INT,
    unitPrice FLOAT,
    description VARCHAR(300),
    imageUrl VARCHAR(500),
    weight FLOAT,
    idCategory INT,
    FOREIGN KEY (idCategory) REFERENCES Category(id)
);

CREATE TABLE IF NOT EXISTS ShippingMethod (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    price FLOAT,
    maxDaysTransit INT
);

CREATE TABLE IF NOT EXISTS Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT,
    idAddress INT NOT NULL,
    idLoyaltyAccount INT,
    FOREIGN KEY (idUser) REFERENCES User(id),
    FOREIGN KEY (idAddress) REFERENCES Address (id),
    FOREIGN KEY (idLoyaltyAccount) REFERENCES LoyaltyAccount (id)
);

CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    total INT,
    date DATE,
    orderStatus VARCHAR(30),
    idCustomer INT,
    idShippingMethod INT,
    idAddress INT,
    FOREIGN KEY (idCustomer) REFERENCES Customer(id),
    FOREIGN KEY (idShippingMethod) REFERENCES ShippingMethod(id),
    FOREIGN KEY (idAddress) REFERENCES Address(id)
);

CREATE TABLE IF NOT EXISTS Cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idDiscount INT,
    idCustomer INT,
    FOREIGN KEY (idDiscount) REFERENCES Discount(id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(id)
);

CREATE TABLE IF NOT EXISTS CartItem (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT,
    idCart INT,
    idProduct INT,
    idOrder INT,
    FOREIGN KEY (idCart) REFERENCES Cart(id),
    FOREIGN KEY (idProduct) REFERENCES Product(id),
    FOREIGN KEY (idOrder) REFERENCES Orders(id)
);

CREATE TABLE IF NOT EXISTS Mail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fromAddress VARCHAR(50),
    toAddress VARCHAR(50),
    subject VARCHAR(50),
    body VARCHAR(300),
    date Date
);