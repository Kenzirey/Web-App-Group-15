# Instructions for running the backend application
The backend part can be found via a directory named "backend".
1. An environmental file called "application.properties" is required to run CourseApplication (SpringApplication run class).
It should be placed in the resources folder. MySQL needs to be installed and set up on the computer being used to run the backend application.
The required information for application.properties:
1. spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
2. spring.datasource.username=username
    <p>Note, after username=, you need to replace username with your own MySQL username.</p>
3. spring.datasource.password=password \n
    <p>Note, after password=, you need to replace password with your own MySQL password.</p>
4. spring.jpa.generate-ddl=true
5. spring.datasource.url=jdbc:mysql://localhost:3306/DatabaseName \n
     <p>Note, you need to replace the port 3306 (or keep) and DatabaseName 
       to match your own localhost port and the name of your database. </p>
6. spring.jpa.hibernate.ddl-auto=update
7. exchange-rates.api-key=ca_live_dRut7mcwQz60d4Om7n1ei550LoECjIgmr6UIoJHY
<p>The exchange rate's api is not a security concern, so we will just place it here. </p>
8. jwt_secret_key=Jonas,WhatWeDoHere?
<p>As the jwt secret key is a security concern, this will be uploaded in Inspera, or whatever we decide upon later. This line will be edited./p>


# Instructions for running the frontend application
#### <p>node.js v21.x needs to be installed (and set up as environmental path variable).</p>
##### From the root of the project folder, find the directory named "frontend" and open it via integrated terminal in VSC, or "open in terminal" for IntelliJ. Alternatively, pure command line can be used to find the directory. From the frontend directory, run the following node.js commands:
1. npm install
2. npm install axios
##### To run the frontend, in the "frontend" directory, run the command "npm run dev". In this project, "http://localhost:3000/" is used to run the frontend locally.


