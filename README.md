# Java Spring Data Access
The target of this exercise is to practice Spring MVC with Java 17.

## Features
- Spring MVC and Thymeleaf
- Usage of JPA to interact with Persistence API in a booking backend.
- Usage of Hibernate
- Based on https://github.com/santiagocb/java-task04-spring-data

## Requirements
- Install Docker
- Download Postgres Docker image
- Install psql client

## Run the project
1. Run postgres through Docker with following command: `docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres`
2. Run psql command to create DB: `psql -h localhost -U postgres -f database.sql` and enter the password: `password`
3. Run maven spring boot run.
4. Run command to stop Docker execution: `docker stop lil-postgres`

## Outputs
Index page
![Screenshot 2024-10-26 at 12 22 43 PM](https://github.com/user-attachments/assets/4df5380a-5517-4b59-b339-a50710be34d4)

Users page
![Screenshot 2024-10-26 at 12 22 53 PM](https://github.com/user-attachments/assets/cd8c85ad-a155-47d0-9a67-0d39878c77ee)

- Users page / Refill account
![Screenshot 2024-10-26 at 12 23 17 PM](https://github.com/user-attachments/assets/e940e41e-fff6-47bb-82c1-1e3fb4330234)

- Events page
![Screenshot 2024-10-26 at 12 23 29 PM](https://github.com/user-attachments/assets/cb8cb08f-29bc-49d6-8702-22b8818ed06a)

- Tickets page
![Screenshot 2024-10-26 at 12 23 47 PM](https://github.com/user-attachments/assets/0af73048-8453-45b3-b911-7305b0b5f4d8)

- Tickets page / Insufficient funds
![Screenshot 2024-10-26 at 12 24 01 PM](https://github.com/user-attachments/assets/b23bb9fc-d140-4868-801a-d92d7eeac409)

- Search tickets page
![Screenshot 2024-10-26 at 12 24 52 PM](https://github.com/user-attachments/assets/9b2b8da2-cef0-476b-93b4-09f76a853a04)

- Search tickets page / Download pdf
![Screenshot 2024-10-26 at 12 25 07 PM](https://github.com/user-attachments/assets/46a48afd-abbb-4769-8ac6-b137e2a3215c)

- Preload tickets page
![Screenshot 2024-10-26 at 12 25 31 PM](https://github.com/user-attachments/assets/f1f457b1-9369-4b8c-811e-ad8cdad7c44e)

- Tickets page with preload tickets
![Screenshot 2024-10-26 at 12 25 51 PM](https://github.com/user-attachments/assets/1b5791b8-fac7-495c-96fb-4a0e5aa41d9a)

## Test output
![Screenshot 2024-10-26 at 12 28 09 PM](https://github.com/user-attachments/assets/226843b4-ea69-426c-82c2-65452223df84)

