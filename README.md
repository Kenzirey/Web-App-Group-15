Disclaimer in this project, Chatgpt was used for help for ideas on how to solve things though code was never purely used copy-pasted as is,
but some code is adapted from it,
and Github Copilot was used to auto-finish documentation based on previously written documentation by the human.

# Instructions for running the backend application
The backend part can be found via a directory named "backend".
1. An environmental file called "application.properties" is required to run CourseApplication (SpringApplication run class).
   It should be placed in the "resources" folder. MySQL needs to be installed and set up on the computer being used to run the backend application.
   The required information for application.properties:
2. spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
3. spring.datasource.username=username
    <p>Note, after username=, you need to replace username with your own MySQL username.</p>
4. spring.datasource.password=password
    <p>Note, after password=, you need to replace password with your own MySQL password.</p>
5. spring.jpa.generate-ddl=true
6. spring.datasource.url=jdbc:mysql://localhost:3306/DatabaseName
     <p>Note, you need to replace the port 3306 (or keep) and DatabaseName
       to match your own localhost port and the name of your database. </p>
7. spring.jpa.hibernate.ddl-auto=update
8. exchange-rates.api-key=fca_live_dRut7mcwQz60d4Om7n1ei550LoECjIgmr6UIoJHY
   <p>The exchange rate's api is not a security concern, so we will just place it here. This token will be deleted after project examination is complete. </p>
9. server.port=8080
   <p>Replace or keep the 8080 port with the localhost port you wish to use.</p>
10. jwt_secret_key=PasteInWhatYouGetFromBelow
    <p>Use this website to generate a JWT key to use: https://jwt.io/#debugger ./p>


# Instructions for running the frontend application
#### <p>node.js v21.x needs to be installed (and set up as environmental path variable).</p>
##### From the root of the project folder, find the directory named "frontend" and open it via integrated terminal in VSC, or "open in terminal" for IntelliJ. Alternatively, pure command line can be used to find the directory. From the frontend directory, run the following node.js commands:
1. npm install
2. npm install axios
3. npm install swiper
##### To run the frontend, in the "frontend" directory, run the command "npm run dev". In this project, "http://localhost:3000/" is used to run the frontend locally. This comes from vite.config.js, you can alter the port there from 3000 if you wish.

#### Home page on laptop:
![img_2.png](screenshots/img_2.png)

#### Footer:
![img_1.png](screenshots/img_1.png)

#### Admin Dashboard:
![img_3.png](screenshots/img_3.png)

#### Admin Add Course, NB NOT MOBILE COMPLIANT!
![img_4.png](screenshots/img_4.png)


#### Home Page on mobile:
![img.png](screenshots/img.png)

#### About us on mobile, scrollable, vertical:
![img_5.png](screenshots/img_5.png)

#### Admin Account Details:
![img_6.png](screenshots/img_6.png)

#### Non-Admin Account Details:
![img_7.png](screenshots/img_7.png)

#### Search bar on mobile:
![img_8.png](screenshots/img_8.png)


