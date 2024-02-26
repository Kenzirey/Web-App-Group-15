-- categories table
CREATE TABLE category (
                            categoryId int NOT NULL AUTO_INCREMENT,
                            categoryName VARCHAR (255) NOT NULL,
                            PRIMARY KEY (categoryId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- courseProvider table
-- The names of the various providers of courses.
CREATE TABLE courseProvider (
                                courseProviderId int NOT NULL AUTO_INCREMENT,
                                providerName VARCHAR (255),
                                PRIMARY KEY (courseProviderId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- product table
CREATE TABLE product (
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
                         FOREIGN KEY (categoryId) REFERENCES category(categoryId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- productProviders table
-- Links a course with a course(product)Provider.
CREATE TABLE productProvider (
                                 productId int NOT NULL,
                                 courseProviderId int,
                                 price int,
                                 PRIMARY KEY (productId, courseProviderId),
                                 FOREIGN KEY (productId) REFERENCES product (productId),
                                 FOREIGN KEY (courseProviderId) REFERENCES courseProvider (courseProviderId)
);

-- images table
CREATE TABLE image (
                        imageId int NOT NULL AUTO_INCREMENT,
                        productId int,
                        imageUrl TEXT,
                        PRIMARY KEY (imageId),
                        FOREIGN KEY (productId) REFERENCES product (productId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- Change autoincrement to start somewhere else?

-- user table
CREATE TABLE user (
                      userId int NOT NULL AUTO_INCREMENT,
                      userName VARCHAR (200),
                      email VARCHAR (200),
                      password VARCHAR (200),
                      isAdmin BOOLEAN,
                      PRIMARY KEY (userId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- favorite table
CREATE TABLE favorite (
                          userId int,
                          productId int,
                          PRIMARY KEY (userId, productId),
                          FOREIGN KEY (userId) REFERENCES user(userId),
                          FOREIGN KEY (productId) REFERENCES product (productId)
);

-- users_2fa table
CREATE TABLE IF NOT EXISTS users_2fa (
                           username VARCHAR(255) NOT NULL,
                           two_factor_secret VARCHAR(255),
                           is_2fa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
                           PRIMARY KEY (username)
)                          ENGINE=InnoDB DEFAULT CHARSET=utf8;