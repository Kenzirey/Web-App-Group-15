-- Categories table
CREATE TABLE Categories (
                            categoryId int NOT NULL,
                            categoryName VARCHAR (255) NOT NULL,
                            PRIMARY KEY (categoryId)
);

-- CourseProvider table
CREATE TABLE CourseProvider (
                                courseProviderId int NOT NULL,
                                providerName VARCHAR (255),
                                PRIMARY KEY (courseProviderId)
);


-- ProductProviders table
CREATE TABLE ProductProviders (
                                  productId int,
                                  courseProviderId int,
                                  price int,
                                  PRIMARY KEY (productId, courseProviderId),
                                  FOREIGN KEY (productId) REFERENCES Product(productId),
                                  FOREIGN KEY (courseProviderId) REFERENCES CourseProvider(courseProviderId)
);

-- Product table
CREATE TABLE Product (
                         productId int NOT NULL,
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
);