-- DROP DATABASE j2ee_project_db;
CREATE DATABASE IF NOT EXISTS j2ee_project_db;
USE j2ee_project_db;

CREATE TABLE IF NOT EXISTS Address (
    id INT PRIMARY KEY AUTO_INCREMENT,
    streetAddress VARCHAR(60) NOT NULL,
    postalCode VARCHAR(30) NOT NULL,
    city VARCHAR(60) NOT NULL,
    country VARCHAR(60) NOT NULL,
    UNIQUE(streetAddress, postalCode, city, country)
);

CREATE TABLE IF NOT EXISTS User (
    id INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(30) NOT NULL,
    phoneNumber VARCHAR(15) UNIQUE
);

CREATE TABLE IF NOT EXISTS Administrator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(id)
);



CREATE TABLE IF NOT EXISTS Discount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    discountPercentage INT NOT NULL,
    CONSTRAINT valid_percentage CHECK (discountPercentage > 0 AND discountPercentage <= 100),
    CONSTRAINT valid_dates CHECK (startDate <= endDate)
);

CREATE TABLE IF NOT EXISTS Category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30) UNIQUE NOT NULL,
    description VARCHAR(300),
    idDiscount INT DEFAULT NULL,
    FOREIGN KEY (idDiscount) REFERENCES Discount(id)
);

CREATE TABLE IF NOT EXISTS LoyaltyProgram (
    id INT PRIMARY KEY AUTO_INCREMENT,
    durationNbDays INT NOT NULL,
    CONSTRAINT valid_duration CHECK (durationNbDays > 0)
);

CREATE TABLE IF NOT EXISTS LoyaltyAccount (
    id INT PRIMARY KEY AUTO_INCREMENT,
    loyaltyPoints INT NOT NULL,
    startDate DATE NOT NULL,
    idLoyaltyProgram INT NOT NULL,
    FOREIGN KEY (idLoyaltyProgram) REFERENCES LoyaltyProgram(id)
);

CREATE TABLE IF NOT EXISTS LoyaltyLevel (
    id INT PRIMARY KEY AUTO_INCREMENT,
    requiredPoints INT NOT NULL,
    idLoyaltyProgram INT NOT NULL,
    idDiscount INT NOT NULL,
    FOREIGN KEY (idLoyaltyProgram) REFERENCES LoyaltyProgram(id),
    FOREIGN KEY (idDiscount) REFERENCES Discount(id),
    CONSTRAINT valid_required_points_nb CHECK (requiredPoints >= 0)
);

CREATE TABLE IF NOT EXISTS LoyaltyAccountLevelUsed (
    idLoyaltyAccount INT NOT NULL,
    idLoyaltyLevel INT NOT NULL,
    PRIMARY KEY(idLoyaltyAccount, idLoyaltyLevel),
    FOREIGN KEY(idLoyaltyAccount) REFERENCES LoyaltyAccount(id),
    FOREIGN KEY(idLoyaltyLevel) REFERENCES LoyaltyLevel(id)
);


CREATE TABLE IF NOT EXISTS Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    stockQuantity INT NOT NULL DEFAULT 0,
    unitPrice FLOAT NOT NULL,
    description VARCHAR(300),
    imageUrl VARCHAR(500),
    weight FLOAT,
    idCategory INT NOT NULL,
    FOREIGN KEY (idCategory) REFERENCES Category(id),
    CONSTRAINT valid_unit_price CHECK(unitPrice >= 0),
    CONSTRAINT valid_stock_quantity CHECK(stockQuantity >= 0)
);

CREATE TABLE IF NOT EXISTS ShippingMethod (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    price FLOAT NOT NULL,
    maxDaysTransit INT NOT NULL,
    CONSTRAINT valid_price CHECK(price >= 0),
    CONSTRAINT valid_duration CHECK(maxDaysTransit >= 0)
);

CREATE TABLE IF NOT EXISTS Customer (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL UNIQUE,
    idAddress INT DEFAULT NULL,
    idLoyaltyAccount INT DEFAULT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(id),
    FOREIGN KEY (idAddress) REFERENCES Address (id),
    FOREIGN KEY (idLoyaltyAccount) REFERENCES LoyaltyAccount (id)
);

CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    total INT NOT NULL,
    date DATE NOT NULL,
    orderStatus VARCHAR(30) NOT NULL,
    idCustomer INT NOT NULL,
    idShippingMethod INT NOT NULL,
    idAddress INT NOT NULL,
    FOREIGN KEY (idCustomer) REFERENCES Customer(id),
    FOREIGN KEY (idShippingMethod) REFERENCES ShippingMethod(id),
    FOREIGN KEY (idAddress) REFERENCES Address(id),
    CONSTRAINT valid_total_price CHECK(total >= 0)
);

CREATE TABLE IF NOT EXISTS Cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idDiscount INT DEFAULT NULL,
    idCustomer INT NOT NULL,
    FOREIGN KEY (idDiscount) REFERENCES Discount(id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(id)
);

CREATE TABLE IF NOT EXISTS CartItem (
    id INT PRIMARY KEY AUTO_INCREMENT,
    quantity INT NOT NULL,
    idCart INT,
    idProduct INT NOT NULL,
    idOrder INT,
    FOREIGN KEY (idCart) REFERENCES Cart(id),
    FOREIGN KEY (idProduct) REFERENCES Product(id),
    FOREIGN KEY (idOrder) REFERENCES Orders(id),
    CONSTRAINT valid_quantity CHECK(quantity >= 0)
);

CREATE TABLE IF NOT EXISTS Mail (
    id INT PRIMARY KEY AUTO_INCREMENT,
    fromAddress VARCHAR(50) NOT NULL,
    toAddress VARCHAR(50) NOT NULL,
    subject VARCHAR(50) NOT NULL,
    body VARCHAR(300) NOT NULL,
    date Date NOT NULL
);

CREATE TABLE IF NOT EXISTS Moderator (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS ModeratorPermission (
    idModerator INT NOT NULL,
    permission VARCHAR(50) NOT NULL,
    PRIMARY KEY(idModerator, permission)
);

INSERT INTO LoyaltyProgram(durationNbDays) VALUES(365);

INSERT INTO Discount(name, startDate, endDate, discountPercentage) VALUES('Loyalty level reward', STR_TO_DATE('28/10/2023', '%d/%m/%Y'), STR_TO_DATE('28/12/2023', '%d/%m/%Y'), 10); # This type of discount will be created only when it's claimed and will be filled with the current date to the current date + N days
INSERT INTO Discount(name, startDate, endDate, discountPercentage) VALUES('Loyalty level reward', STR_TO_DATE('28/10/2023', '%d/%m/%Y'), STR_TO_DATE('28/12/2023', '%d/%m/%Y'), 20);

INSERT INTO Discount(name, startDate, endDate, discountPercentage) VALUES('Halloween sales', STR_TO_DATE('31/10/2023', '%d/%m/%Y'), STR_TO_DATE('31/10/2023', '%d/%m/%Y'), 15);


INSERT INTO LoyaltyLevel(requiredPoints, idLoyaltyProgram, idDiscount) VALUES(15,1,1);
INSERT INTO LoyaltyLevel(requiredPoints, idLoyaltyProgram, idDiscount) VALUES(25,1,2);

INSERT INTO LoyaltyAccount(loyaltyPoints, startDate, idLoyaltyProgram) VALUES(55, STR_TO_DATE('28/10/2023', '%d/%m/%Y'), 1);
INSERT INTO LoyaltyAccount(loyaltyPoints, startDate, idLoyaltyProgram) VALUES(60, STR_TO_DATE('30/10/2023', '%d/%m/%Y'), 1);

INSERT INTO Address(streetAddress, postalCode, city, country) VALUES ('26 rue de la Mare', '34080', 'Montpellier', 'France');
INSERT INTO Address(streetAddress, postalCode, city, country) VALUES ('33 rue Sadi Carnot', '32000', 'Auch', 'France');
INSERT INTO Address(streetAddress, postalCode, city, country) VALUES ('60 rue de Lille', '91200', 'Athis-mons', 'France');
INSERT INTO Address(streetAddress, postalCode, city, country) VALUES ('6 avenue de Provence', '26000', 'Valence', 'France');
INSERT INTO Address(streetAddress, postalCode, city, country) VALUES ('18 Avenue Millies Lacroix', '78990', 'Élancourt', 'France');

INSERT INTO User(firstName, lastName, email, password) VALUES ('root', 'root', 'jeewebproject@gmail.com', 'root_password');

INSERT INTO User(firstName, lastName, email, password) VALUES ('Robin', 'Meneust', 'robin@example.com', 'p1');
INSERT INTO User(firstName, lastName, email, password) VALUES ('Lucas', 'Velay', 'lucas@example.com', 'p2');
INSERT INTO User(firstName, lastName, email, password) VALUES ('Jérémy', 'Saëlen', 'jeremy@example.com', 'p3');
INSERT INTO User(firstName, lastName, email, password) VALUES ('Théo', 'Gandy', 'theo@example.com', 'p4');

INSERT INTO Administrator(idUser) VALUES (1);

INSERT INTO Moderator(idUser) VALUES(5);
INSERT INTO ModeratorPermission(idModerator, permission) VALUES(1,'CAN_CREATE_CUSTOMER');

INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(2,1,1);
INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(3,2,2);
INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(4,3,NULL);

INSERT INTO Cart(idCustomer) VALUES(1);

INSERT INTO Category(name, description) VALUES('strategy', 'A strategy game or strategic game is a game (e.g. a board game) in which the players\' uncoerced, and often autonomous, decision-making skills have a high significance in determining the outcome.');
INSERT INTO Category(name, description) VALUES('card game', 'A card game is any game using playing cards as the primary device with which the game is played, be they traditional or game-specific.');

INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory) VALUES('Chess Board', 50, 15, 'A chess board', 1);
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory) VALUES('UNO cards', 10, 5, 'A deck of UNO cards', 2);

INSERT INTO CartItem(quantity, idCart, idProduct) VALUES(1,1,1);
INSERT INTO CartItem(quantity, idCart, idProduct) VALUES(3,1,2);

INSERT INTO LoyaltyAccountLevelUsed(idLoyaltyAccount, idLoyaltyLevel) VALUES(1,1);

INSERT INTO Mail(fromAddress, toAddress, subject, body, date) VALUES('example@example.com', 'example@example.com', 'Test mail', 'This is the body of a mail used for testing purposes', STR_TO_DATE('30/10/2023', '%d/%m/%Y'));

INSERT INTO ShippingMethod(name, price, maxDaysTransit) VALUES('standard', 5, 10);

INSERT INTO Orders(total, date, orderStatus, idCustomer, idShippingMethod, idAddress) VALUES(30, STR_TO_DATE('30/10/2023', '%d/%m/%Y'), 'SHIPPED', 1, 1, 1);
INSERT INTO CartItem(quantity, idOrder, idProduct) VALUES(2,1,1);

