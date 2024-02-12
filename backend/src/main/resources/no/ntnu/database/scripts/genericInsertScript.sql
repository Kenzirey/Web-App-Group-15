-- Categories
-- INSERT INTO Categories (categoryId, categoryName)
-- VALUES (int, VARCHAR(255)

-- CourseProvider
-- INSERT INTO CourseProvider (courseProviderId, providerName)
-- VALUES (int, VARCHAR(255)

-- Images
-- INSERT INTO Images (imageId, productId, imageUrl)
-- VALUES (int, int, VARCHAR)

-- ProductProviders
-- INSERT INTO ProductProviders (productId, courseProviderId, price)
-- values (int, int, int));

-- Product
-- INSERT INTO Product (productId, categoryId,
-- courseTitle, difficultyLevel, courseStartDate, courseEndDate,
-- courseCredits, hoursPerWeek, relatedCertifications, courseDescription)
-- VALUES (int, int, VARCHAR (255), VARCHAR(20),
-- DATE, DATE, DECIMAL, int, VARCHAR(100), TEXT))

-- Users
-- INSERT INTO Users (userId, userName, password, email, isAdmin)
-- VALUES (int, VARCHAR(200), VARCHAR(200), VARCHAR(200), BOOLEAN)

-- Favorites
-- INSERT INTO Favorites (userId, productId)
-- VALUES (int, int)

