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
    password VARCHAR(168) NOT NULL,
    phoneNumber VARCHAR(15) UNIQUE
);

CREATE TABLE IF NOT EXISTS Moderator (
    -- id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT PRIMARY KEY ,
    FOREIGN KEY (idUser) REFERENCES User(id)
);

CREATE TABLE IF NOT EXISTS Administrator (
    -- id INT PRIMARY KEY AUTO_INCREMENT,
    idModerator INT PRIMARY KEY ,
    FOREIGN KEY (idModerator) REFERENCES Moderator(idUser)
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
    FOREIGN KEY (idLoyaltyAccount) REFERENCES LoyaltyAccount(id),
    FOREIGN KEY (idLoyaltyLevel) REFERENCES LoyaltyLevel(id),
    PRIMARY KEY(idLoyaltyAccount, idLoyaltyLevel)
);

CREATE TABLE IF NOT EXISTS Product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    stockQuantity INT NOT NULL DEFAULT 0,
    unitPrice FLOAT NOT NULL,
    description VARCHAR(1000),
    imageUrl VARCHAR(500),
    weight FLOAT,
    idCategory INT NOT NULL,
    FOREIGN KEY (idCategory) REFERENCES Category(id),
    CONSTRAINT valid_unit_price CHECK(unitPrice >= 0),
    CONSTRAINT valid_stock_quantity CHECK(stockQuantity >= 0)
);

CREATE TABLE IF NOT EXISTS FeaturedProduct (
    idProduct INT PRIMARY KEY,
    FOREIGN KEY (idProduct) REFERENCES Product(id)
);

CREATE TABLE IF NOT EXISTS Customer (
    -- id INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT PRIMARY KEY ,
    idAddress INT DEFAULT NULL,
    idLoyaltyAccount INT DEFAULT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(id),
    FOREIGN KEY (idAddress) REFERENCES Address (id),
    FOREIGN KEY (idLoyaltyAccount) REFERENCES LoyaltyAccount (id)
);


CREATE TABLE IF NOT EXISTS Orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    total FLOAT NOT NULL,
    date DATE NOT NULL,
    orderStatus VARCHAR(30) NOT NULL,
    idCustomer INT NOT NULL,
    idAddress INT NOT NULL,
    FOREIGN KEY (idCustomer) REFERENCES Customer(idUser),
    FOREIGN KEY (idAddress) REFERENCES Address(id),
    CONSTRAINT valid_total_price CHECK(total >= 0)
);

CREATE TABLE IF NOT EXISTS Cart (
    id INT PRIMARY KEY AUTO_INCREMENT,
    idDiscount INT DEFAULT NULL,
    idCustomer INT NOT NULL UNIQUE,
    FOREIGN KEY (idDiscount) REFERENCES Discount(id),
    FOREIGN KEY (idCustomer) REFERENCES Customer(idUser)
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

CREATE TABLE IF NOT EXISTS Permission (
    id INT PRIMARY KEY AUTO_INCREMENT,
    permission VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS ModeratorPermission (
    idModerator INT NOT NULL,
    idPermission INT NOT NULL,
    FOREIGN KEY (idModerator) REFERENCES Moderator(idUser),
    FOREIGN KEY (idPermission) REFERENCES Permission(id),
    PRIMARY KEY(idModerator, idPermission)
);

INSERT INTO LoyaltyProgram(durationNbDays) VALUES(365);

INSERT INTO Discount(name, startDate, endDate, discountPercentage) VALUES('Loyalty level reward', STR_TO_DATE('28/10/2023', '%d/%m/%Y'), STR_TO_DATE('28/12/2023', '%d/%m/%Y'), 10); -- This type of discount will be created only when it's claimed and will be filled with the current date to the current date + N days
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

INSERT INTO User(firstName, lastName, email, password) VALUES ('root', 'root', 'jeewebproject@gmail.com', '1000:65460f50162f1d94b21e59fb1c516338:fd32f025e305407e41daa397b16ebb183502f2a5e4aaaf6ab2ce427f04b1470f74c044e4b66e2ffeee6df7921b9be8631f3e03e01fa13609a3ef401c02049875'); -- @root123

INSERT INTO User(firstName, lastName, email, password) VALUES ('Robin', 'Meneust', 'robin@example.com', '1000:61f68dce346f7bf72925ec13259aa771:41027a218217d6bfaf0ab69951d1fed568159c180e58c94c2e08eaf272b9bf536bf52716c254d3f0598f9eb87585834b76ae935231f27a8ff9d8d372bab79191'); -- @robin123
INSERT INTO User(firstName, lastName, email, password) VALUES ('Lucas', 'Velay', 'lucas@example.com', '1000:2284d7f466ddab45de8e66cf31f9cd01:9814d3a4822ff220bc48fbd58ae28551fc37ba923d5363a1a98afdc3df258d8f66b797149b570c19bc8adb8f7ca37ce1ceb55e9a3eae135566953563fb16eb93'); -- @lucas123
INSERT INTO User(firstName, lastName, email, password) VALUES ('Jérémy', 'Saëlen', 'jeremy@example.com', '1000:7931d0679ddd85495fb3164ed9d9f035:0462d9322d5be6e390d4cdc2aefc54cd51f88d9271e330e6c92ba888ca974783b35ea58134d6329407bc95f024f87937fe362ad04506ba077de38f089b84d92f'); -- @jeremy123
INSERT INTO User(firstName, lastName, email, password) VALUES ('Théo', 'Gandy', 'theo@example.com', '1000:b777892652b967f9a2755b41293d305d:b8e9cf0a82df4449a54fdeea6eec824dadbe61ae4579d20880f752d8dede6e77830da6c7281fc01fbef615a4a76c67da80de17a4536a8182542b1d95faf19a50'); -- @theo123

INSERT INTO User(firstName, lastName, email, password) VALUES ('modo1', 'modo1', 'modo1@example.com', '1000:4201fdea5d6c99792fd016b927503c2c:e99384846781bef8d34ae1f410375162b3dc082245162f41f7d2d08269841dd372716ac8cc6aa2f182aab05f68e36eb4510481875da04e023a4140b0ca4f71cf'); -- @modo123
INSERT INTO User(firstName, lastName, email, password) VALUES ('modo2', 'modo2', 'modo2@example.com', '1000:64a3fce30ec521d5d40097c493688bf8:656037907c9966323e2beb3ee3c8be46e5f569f6e8b732a29f13bb94e3f6f325dd076cd5051a2d281dc20bd8608e2a36166e819c4aed5e1f68765299a5e91a58'); -- @modo234
INSERT INTO User(firstName, lastName, email, password) VALUES ('modo3', 'modo3', 'modo3@example.com', '1000:c93a9f45bc613a3efc601d1d6f3eb701:b6a8f38f202ea47fd3b08d30ffeb0aeb197254a59800d1770721e5a75e3662fbafae7b6639ab4e39a6373e0399173b8c5c70d6a7085874c876e6e455dfa19942'); -- @modo345

INSERT INTO Moderator(idUser) VALUES(1);
INSERT INTO Administrator(idModerator) VALUES (1);

INSERT INTO Moderator(idUser) VALUES(6);
INSERT INTO Moderator(idUser) VALUES(7);
INSERT INTO Moderator(idUser) VALUES(8);

INSERT INTO Permission(permission) VALUES('CAN_MANAGE_DISCOUNT');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_MODERATOR');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_PRODUCT');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_CATEGORY');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_CUSTOMER');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_LOYALTY');
INSERT INTO Permission(permission) VALUES('CAN_MANAGE_ORDER');

-- Admin has every permissions
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,1);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,2);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,3);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,4);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,5);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,6);
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(1,7);

INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(6,5); -- Can manage customer

INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(7,3); -- Can manage product
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(7,4); -- Can manage category
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(7,7); -- Can manage order

INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(8,6); -- Can manage loyalty
INSERT INTO ModeratorPermission(idModerator, idPermission) VALUES(8,1); -- Can manage discount

INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(2,1,1);
INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(3,2,2);
INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(4,3,NULL);
INSERT INTO Customer(idUser, idAddress, idLoyaltyAccount) VALUES(5,NULL,NULL);

INSERT INTO Cart(idCustomer) VALUES(2);

INSERT INTO Category(name, description, idDiscount) VALUES('strategy', 'A strategy game or strategic game is a game (e.g. a board game) in which the players\' uncoerced, and often autonomous, decision-making skills have a high significance in determining the outcome.', 1);
INSERT INTO Category(name, description) VALUES('card game', 'A card game is any game using playing cards as the primary device with which the game is played, be they traditional or game-specific.');

INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('Chess Board', 50, 15, 'A chessboard is a gameboard used to play chess. It consists of 64 squares, 8 rows by 8 columns, on which the chess pieces are placed. It is square in shape and uses two colours of squares, one light and one dark, in a chequered pattern. During play, the board is oriented such that each player\'s near-right corner square is a light square.', 1, 'https://upload.wikimedia.org/wikipedia/commons/c/c3/Chess_board_opening_staunton.jpg');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards2', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards3', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards4', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards5', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards6', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards7', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards8', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards9', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards10', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards11', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards12', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards13', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards14', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards15', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards16', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards17', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards18', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards19', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards20', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards21', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');
INSERT INTO Product(name, stockQuantity, unitPrice, description, idCategory, imageUrl) VALUES('UNO cards22', 10, 5, 'Uno, stylized as UNO, is a proprietary American shedding-type card game originally developed in 1971 by Merle Robbins in Reading, Ohio, a suburb of Cincinnati, that housed International Games Inc., a gaming company acquired by Mattel on January 23, 1992.\nPlayed with a specially printed deck, the game is derived from the crazy eights family of card games which, in turn, is based on the traditional German game of mau-mau.', 2, 'https://upload.wikimedia.org/wikipedia/commons/2/28/Baraja_de_UNO.JPG');

INSERT INTO CartItem(quantity, idCart, idProduct) VALUES(1,1,1);
INSERT INTO CartItem(quantity, idCart, idProduct) VALUES(3,1,2);

INSERT INTO LoyaltyAccountLevelUsed(idLoyaltyAccount, idLoyaltyLevel) VALUES(1,1);

INSERT INTO Mail(fromAddress, toAddress, subject, body, date) VALUES('example@example.com', 'example@example.com', 'Test mail', 'This is the body of a mail used for testing purposes', STR_TO_DATE('30/10/2023', '%d/%m/%Y'));


INSERT INTO Orders(total, date, orderStatus, idCustomer, idAddress) VALUES(30, STR_TO_DATE('30/10/2023', '%d/%m/%Y'), 'SHIPPED', 2, 1);
INSERT INTO CartItem(quantity, idOrder, idProduct) VALUES(2,1,1);

INSERT INTO FeaturedProduct(idProduct) VALUES(1);
INSERT INTO FeaturedProduct(idProduct) VALUES(2);
INSERT INTO FeaturedProduct(idProduct) VALUES(3);
INSERT INTO FeaturedProduct(idProduct) VALUES(4);
INSERT INTO FeaturedProduct(idProduct) VALUES(5);