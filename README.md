# Project-fullstack

This project has been made by a group of 4 people including myself. Each worked on it during an average time of 125 hours.

The front-end is in Javascript and the back-end in Java.

Before lauching the project, you need to replace the values of these keys by yours in the prod.properties and test.properties files: dbusername, dbpassword, dbname, dbip, dbport, svPort, jwtSecret. The server IP is assumed to be local.
 
You also need to create your own database with those database values (using pgadmin for example) and run the script init.sql to fill this database.

This project helps the students and teachers to manage the Erasmus mobility requests in order to keep a track of the state of each mobility. You can create an account, connect to the website and interact with it depending on your status. The first user added in the database will have a manager status and all the other ones will have a student status. Only the manager can change the status of the students to a teacher status.

Please note that this project has been realized in French.
