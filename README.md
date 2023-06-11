# Accenture Tech Hub 
## Java Back End Developer Challenge

### Brief Project Description

This project aims to improve the appointment control system of the AccWe hospital. The following needs have been developed:

1. Implement the creation of appointments through the API in the **AppointmentController.java** file.


2. Perform "JUnit" tests for entities in the **EntityUnitTest.java** file, as well as for entity controllers in the **EntityControllerUnitTest.java** file.


3. Write clean and secure code, following the strict measures of the hospital environment.


4. Deploy the API in a scalable way, with separate Dockerfiles for the MySQL database (**Dockerfile.mysql**) and the microservice (**Dockerfile.maven**).

### Instructions for running with Docker

Follow these steps to run the application using Docker.

#### Step 1: Build the Docker images

To build the necessary Docker images, run the following commands in the root directory of the project:

1. Build the MySQL container image:

        docker build -t mysql-db -f Dockerfile.mysql .

2. Build the application container image:

        docker build -t myapp -f Dockerfile.maven .

#### Step 2: Create a Docker network

It is necessary to create a Docker network to allow the containers to communicate with each other. Run the following commands:

1. Create a new network called "my-network":

        docker network create my-network

2. Verify that the network has been created correctly:

        docker network ls

#### Step 3: Run the containers

Now you are able to run the MySQL containers and the application. Follow the steps below:

1. Run the MySQL container:

        docker run --name mysql-container --network=my-network -e MYSQL_ROOT_PASSWORD=root -d mysql-db

2. Log into the MySQL container:

        docker exec -it mysql-container bash

3. Access to MySQL:

        mysql -u root -p

   Enter the root password (in this case, password is "root").


4. Check existing databases:

        SHOW DATABASES;

   Check if the database "accwe-hospital" is in the list.


5. Log out from MySQL session:

        exit

6. Exit from the MySQL container:

        exit

7. Run the application container:

        docker run --name myapp-container --network=my-network -p 8080:8080 -d myapp

The application should now be running on port 8080 on your machine.

**Note:** Make sure you have the necessary ports available and that there are no conflicts with other running services.

