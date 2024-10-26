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
- Index page
![Screenshot 2024-10-26 at 12.09.32 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_PoMPnK%2FScreenshot%202024-10-26%20at%2012.09.32%E2%80%AFPM.png)

- Users page
![Screenshot 2024-10-26 at 12.10.40 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_WDgtl2%2FScreenshot%202024-10-26%20at%2012.10.40%E2%80%AFPM.png)

- Users page / Refill account
![Screenshot 2024-10-26 at 12.11.26 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_R0oI3u%2FScreenshot%202024-10-26%20at%2012.11.26%E2%80%AFPM.png)

- Events page
![Screenshot 2024-10-26 at 12.11.50 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_lnh1Gn%2FScreenshot%202024-10-26%20at%2012.11.50%E2%80%AFPM.png)

- Tickets page
![Screenshot 2024-10-26 at 12.12.49 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_StyrdW%2FScreenshot%202024-10-26%20at%2012.12.49%E2%80%AFPM.png)

- Tickets page / Insufficient funds
![Screenshot 2024-10-26 at 12.13.18 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_p7fNND%2FScreenshot%202024-10-26%20at%2012.13.18%E2%80%AFPM.png)

- Search tickets page
![Screenshot 2024-10-26 at 12.13.47 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_MH2toC%2FScreenshot%202024-10-26%20at%2012.13.47%E2%80%AFPM.png)

- Search tickets page / Download pdf
![Screenshot 2024-10-26 at 12.14.39 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_1QsVEj%2FScreenshot%202024-10-26%20at%2012.14.39%E2%80%AFPM.png)

- Preload tickets
![Screenshot 2024-10-26 at 12.15.11 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_MROdys%2FScreenshot%202024-10-26%20at%2012.15.11%E2%80%AFPM.png)

- Tickets page with preload tickets
![Screenshot 2024-10-26 at 12.15.51 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_edHiGu%2FScreenshot%202024-10-26%20at%2012.15.51%E2%80%AFPM.png)

## Test output
![Screenshot 2024-10-26 at 12.08.40 PM.png](..%2F..%2F..%2F..%2F..%2F..%2Fvar%2Ffolders%2F9p%2F6p3ccs9j0x3dsqpwxx4lh3540000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_jDNMLQ%2FScreenshot%202024-10-26%20at%2012.08.40%E2%80%AFPM.png)