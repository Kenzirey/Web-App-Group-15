-- category table
CREATE TABLE IF NOT EXISTS category (
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

-- course table
CREATE TABLE IF NOT EXISTS course (
                         courseId int NOT NULL AUTO_INCREMENT,
                         categoryId int,
                         courseTitle VARCHAR (255),
                         difficultyLevel VARCHAR (20),
                         courseStartDate DATE,
                         courseEndDate DATE,
                         courseCredits DECIMAL,
                         hoursPerWeek int,
                         relatedCertifications VARCHAR (100),
                         courseDescription TEXT,
                         PRIMARY KEY (courseId),
                         FOREIGN KEY (categoryId) REFERENCES category(categoryId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- courseProviderLink table
-- Links a course with a course Provider.
CREATE TABLE IF NOT EXISTS courseProviderLink (
                                 courseId int NOT NULL,
                                 courseProviderId int,
                                 price int,
                                 PRIMARY KEY (courseId, courseProviderId),
                                 FOREIGN KEY (courseId) REFERENCES course (courseId),
                                 FOREIGN KEY (courseProviderId) REFERENCES courseProvider (courseProviderId)
);

-- image table
CREATE TABLE IF NOT EXISTS image (
                        imageId int NOT NULL AUTO_INCREMENT,
                        courseId int,
                        imageUrl TEXT,
                        PRIMARY KEY (imageId),
                        FOREIGN KEY (courseId) REFERENCES course (courseId)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
-- Change autoincrement to start somewhere else?

-- user table
CREATE TABLE IF NOT EXISTS user (
                      userId int NOT NULL AUTO_INCREMENT,
                      userName VARCHAR (200),
                      email VARCHAR (200),
                      password VARCHAR (200),
                      isAdmin BOOLEAN,
                      PRIMARY KEY (userId)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- favorite table
CREATE TABLE IF NOT EXISTS favorite (
                          userId int,
                          courseId int,
                          PRIMARY KEY (userId, courseId),
                          FOREIGN KEY (userId) REFERENCES user(userId),
                          FOREIGN KEY (courseId) REFERENCES course (courseId)
);

-- users_2fa table
CREATE TABLE IF NOT EXISTS users_2fa (
                           username VARCHAR(255) NOT NULL,
                           two_factor_secret VARCHAR(255),
                           is_2fa_enabled BOOLEAN NOT NULL DEFAULT FALSE,
                           PRIMARY KEY (username)
)                          ENGINE=InnoDB DEFAULT CHARSET=utf8;