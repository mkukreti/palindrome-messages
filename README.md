Palindrome Messages Webapp

A web application that allows users to read, post, delete and update messages and show details about messages - eg if it is a palindomre or not. 

Available on - http://ec2-18-188-71-248.us-east-2.compute.amazonaws.com:8090/palindrome-messages-webapp/

Technologies Used:
- Backend - Jersey API in Java to be hosted on a Tomcat server
- Database - Amazon RDS running Postgres
- Client - ReactJS
- Deployment - Tomcat server running on an Amazon EC2 instance

Running Locally:
- Checkout the repository
- Add you database connection url to the settings file - src/params/settings.json
- Create table schema using schema/palindrome-messages.ddl
- Run ./gradlew appRun (to run the application on your localhost)
- Run ./gradlew test to run functional tests against TEST_HOST_URL defined src/params/settings.json

Generate deployable WAR:
- Run ./gradlew war
- Deployable WAR will be generated in build/libs/palindrome-messages-webapp.war
- This can be directly be placed in webapps directory of a tomcat server

