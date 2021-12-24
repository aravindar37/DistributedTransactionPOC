# DistributedTransactionPOC

Spring Boot Microservice Sample for Distributed transaction with rollback using temporal.io (Uber Cadence)

Package contains different repositories with sample code for different functionalities such as:
* Asynchronous execution
* Distributed rollback
* Cron jobs

Dependency:
Docker
Temporal.io server.
(clone https://github.com/temporalio/docker-compose and docker-compose up in the directory)

Service demonstrates a distributed implementation. Code for each use case is divided into 4 packages:
* rest server
* activity service
* workflow service
* shared

Rest service is a client which starts the temporal.io workflow in the backend. Alternatively it could be a polling server or cron job as well

Workflow service is a microservice that describes various workflows consisting of one or more activities, error handling, retries, compensations etc.

Activities service is a microserive that encapsulates the business logic. Activity services should be domain bounded.

Shared is non-executable package that will be used as dependency across modules. In a real world application, shared will be multiple packages each supporting an activity.
