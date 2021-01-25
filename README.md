![Main workflow](https://github.com/mibeth/businesses-directory/workflows/Main%20workflow/badge.svg?branch=master)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=mibeth_businesses-directory&metric=coverage)](https://sonarcloud.io/dashboard?id=mibeth_businesses-directory)

# International Creative Woman directory
Springboot application which works as CRUD for local businesses that will later be queried from the main ICW portal by using tags

## Prerequisites:
	* JDK 11
	* Maven 3.6
	* Mongodb - could be a docker container with Mongo express (https://hub.docker.com/_/mongo-express/)

## How to run the App locally?:
1. Clone the source code out of gitlab from the following url: https://github.com/michaelrodas/businesses-directory
   (i.e `git clone git@github.com:michaelrodas/businesses-directory.git`)
2. Step into it (i.e `cd businesses-directory`)
3. Launch the app by typing:  `mvn clean spring-boot:run -Dspring-boot.run.jvmArguments="-Djdk.tls.client.protocols=TLSv1.2"`
4. Use the app locally by going to: `http://localhost:8080/swagger-ui.html`
5. To run all unit tests use the command: `mvn clean test`
   * Code test coverage report is generated in path `jacoco/`
   
### Configuration
A connection to a MongoDB database must be defined in the file application.yml under the spring entry