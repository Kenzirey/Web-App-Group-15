-- Category
-- INSERT INTO category (categoryId, categoryName)
-- VALUES (int, VARCHAR(255)

-- courseProvider
-- INSERT INTO courseProvider (courseProviderId, providerName)
-- VALUES (int, VARCHAR(255)

-- productProvider
-- INSERT INTO productProvider (productId, courseProviderId, price)
-- values (int, int, int));

-- product
-- INSERT INTO product (productId, categoryId,
-- courseTitle, difficultyLevel, courseStartDate, courseEndDate,
-- courseCredits, hoursPerWeek, relatedCertifications, courseDescription)
-- VALUES (int, int, VARCHAR (255), VARCHAR(20),
-- DATE, DATE, DECIMAL, int, VARCHAR(100), TEXT))

-- image
-- INSERT INTO image (imageId, productId, imageUrl)
-- VALUES (int, int, VARCHAR)

-- user
-- INSERT INTO user (userId, userName, password, email, isAdmin)
-- VALUES (int, VARCHAR(200), VARCHAR(200), VARCHAR(200), BOOLEAN)

-- favorite
-- INSERT INTO favorite (userId, productId)
-- VALUES (int, int)

