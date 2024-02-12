-- Categories table
CREATE TABLE Categories (
                            categoryId int NOT NULL AUTO_INCREMENT,
                            categoryName VARCHAR (255) NOT NULL,
                            PRIMARY KEY (categoryId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- CourseProvider table
CREATE TABLE CourseProvider (
                                courseProviderId int NOT NULL AUTO_INCREMENT,
                                providerName VARCHAR (255),
                                PRIMARY KEY (courseProviderId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- ProductProviders table
CREATE TABLE ProductProviders (
                                  productId int NOT NULL,
                                  courseProviderId int,
                                  price int,
                                  PRIMARY KEY (productId, courseProviderId),
                                  FOREIGN KEY (productId) REFERENCES Product(productId),
                                  FOREIGN KEY (courseProviderId) REFERENCES CourseProvider(courseProviderId)
);

-- Images table
CREATE TABLE Images (
                        imageId int NOT NULL AUTO_INCREMENT,
                        productId int,
                        imageUrl VARCHAR (255),
                        PRIMARY KEY (imageId),
                        FOREIGN KEY (productId) REFERENCES Product(productId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- Change autoincrement to start somewhere else?

-- Product table
CREATE TABLE Product (
                         productId int NOT NULL AUTO_INCREMENT,
                         categoryId int,
                         courseTitle VARCHAR (255),
                         difficultyLevel VARCHAR (20),
                         courseStartDate DATE,
                         courseEndDate DATE,
                         courseCredits DECIMAL,
                         hoursPerWeek int,
                         relatedCertifications VARCHAR (100),
                         courseDescription TEXT,
                         PRIMARY KEY (productId),
                         FOREIGN KEY (categoryId) REFERENCES Categories(categoryId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Users table
CREATE TABLE Users (
                      userId int NOT NULL AUTO_INCREMENT,
                      userName VARCHAR (200),
                      email VARCHAR (200),
                      password VARCHAR (200),
                      isAdmin BOOLEAN,
                      PRIMARY KEY (userId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- Favorites table
CREATE TABLE Favorites (
                          userId int,
                          productId int,
                          PRIMARY KEY (userId, productId),
                          FOREIGN KEY (userId) REFERENCES Users(userId),
                          FOREIGN KEY (productId) REFERENCES Product(productId)
);
