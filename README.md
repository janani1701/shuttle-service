Explaining how the application works:

Name of the back-end application : shuttle-boot

Technologies used:

Back-end framework: Spring

Programming language: Java 1.8

Database tool: My Workbench SQL

API testing: postman

Project scope: Maven Module

Protocol used for communication(sending requests): http

Infrastructure:

Project will be running at port 8085 inside a localhost server.

Inside com.shuttle package, we have controllers which controls the flow of data into the model object.

1. AuthController:

root api is /auth/api/ which is a request mapping for the class AuthController.

Postmappings for corresponding methods inside AuthController:

public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) is invoked when we initiate a POST request with /auth/api/signin API.

public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) is invoked when we initiate a POST request with /auth/api/signup API.

Getmapping :

public ResponseEntity<?> getUserNameWithToken(HttpServletRequest request) is invoked when we initiate a GET request with /auth/api/username API. I want to use this API to check if the username/ID already exists in the system with the help of a bearer token.
The bearer token is passed in the authorization tab in postman to check for the existence of a student inside the system and also covers the validity of the user with the help of jwttoken security.

2. ShuttleController:

public ResponseEntity<?> addPassenger(@Valid @RequestBody AddPassengerRequest addPassangerRequest) is invoked when we initiate a POST request with /addPassenger API.

Here, only the users having either a DRIVER or an ADMIN role can add a passenger(student).
Note that student will be added to the shuttle only when the jwttoken is valid.

public ResponseEntity<?> shuttleLocation(@Valid @RequestBody ShuttleLocationRequest shuttleLocationRequest, HttpServletRequest request) is invoked when we initiate a POST request with /shuttleLocation API.

The driver basically updates the shuttle coordinates which takes the form of latitude and longitude coordinates.

public ResponseEntity<?> shuttleLocationETA() is invoked when we initiate a POST request with /checkETA API.

Here, only the users having either a DRIVER or an ADMIN role can display ETA for shuttle to student.

if the driver is in the process of dropping another student, then the system displays service unavailable message.
if both the latitude and longitude coordinates are 0, then the ETA is shown as zero because the shuttle is in the campus.

3. TestController:

root api is /api/test which is a request mapping to the class TestController.

public String allAccess() is invoked when we initiate a GET request with /api/test/all API. 
It returns Validated Successfully message on successful working of methods setID() and setName().

Checking the method validation by saving all kinds of roles into rolerepository.

public String userAccess() is invoked when we initiate a GET request with /api/test/user API.
It returns User Content message when the user has any of these roles like student, admin and driver.

public String moderatorAccess() is invoked when we initiate a GET request with /api/test/driver API.
It returns Driver Board message when the user has only driver role.

public String adminAccess() is invoked when we initiate a GETrequest with /api/test/admin API.
It returns Admin Board message when the user has only admin role.

MODELS:

I have created 5 models namely DriverShuttle, ERole, Role, StudentShuttle and User.

DriverShuttle:
This is treated as a table named driver_shuttle with 4 columns namely id, latitude, longitude and user_id.

ERole:
I defined an enumeration ERole where i am storing all the roles accepted by our system like ROLE_STUDENT,  ROLE_DRIVER and ROLE_ADMIN.

Role:
This is treated as a table named roles where i maintained all the roles with their respective id's(role_id).

StudentShuttle:
This is treated as a table named student_shuttle with 4 columns namely id, status, address(student address) and user_id.

User:
This is treated as a table named users with 4 columns namely id, email, password and username.

Payload package:
This package consists of request and response packages. The request package consists of request classes for login, signup, shuttlelocation and adding student to shuttle.
The response package consists of response classes for sending out the jwt access tokens as a response when student tries to login into the system and MessageResponse class for building the successful or failure scenario messages to the student.
We are using the bearer token as a type of token for validating the credentials of the students.

repository package:

Interface DriverShuttleRepository
Defined a query method "public DriverShuttle findTopByOrderByIdDesc()" using Spring Data JPA that would give me the DriverShuttle with the biggest id (latest inserted). So everytime student gets updated with the latest location coordinates.

Interface RoleRepository
Defined a finder method "Optional<Role> findByName(ERole name)" using Spring Data JPA that would give me the role object or nothing based on name.

Interface UserRepository
Defined 3 finder methods:
Optional<User> findByUsername(String username) - it may or may not return a non-null User. 
Boolean existsByUsername(String username) - it checks if a specific user exists with username.
Boolean existsByEmail(String email) - it checks if a specific user exists with email.

All the security related code exists inside the security package if you want to take a look at the components implemented or taken care of spring security objects.

Application execution entry-point:

public class SpringBootSecurityJwtApplication { 
public static void main(String[] args) { 
SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
    }
}

How to run the code??

Right-click on the main method in SpringBootSecurityJwtApplication class which runs the project at port 8085 and starts listening to the requests from postman.

1. how to signup a student inside the system?

Example request:
Request body: 
{
"username": "harsha",
"email": "harsha@gmail.com",
"password": "123456", //min length 6 and max length 40
"role":[] // empty list indicates the user is of type student.
}

Response:
{
"message": "User registered successfully!"
}

2. how to signin a student inside the system?

Example request:
Request body:
{
"username": "harsha",
"password": "123456"
}

Response:
{
"id": 3,
"username": "harsha",
"email": "harsha@gmail.com",
"roles": [],
"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJoYXJzaGEiLCJpYXQiOjE2NjgzODEzNDMsImV4cCI6MTY2ODQ2Nzc0M30.oNFTEZzL8k0k5SlckdZsxJUYaHf3cskgAIOTfH1eo8ZVHi3pXIhhq7y6NYzJpRlzULxI9KGXiFu2i-LYqGfxvA",
"tokenType": "Bearer"
}

3. How to validate the user with the bearer token generated after signin?

Example request
Get request:
In the authorization tab, select the token type as bearer and paste the access token generated for any specific user after signing in.

Response:
{
"message": "User name harsha"
}

4. How to add a student to a shuttle?

Example request
Request body:
{
"studentUsername":"harsha",
"address":"College place"
}

Response:
{
"message": "Passenger is added in Shuttle"
}

5. How to request for a shuttle location?

- Only the users with admin or driver role can actually update their shuttle coordinates constantly for students to live track.

Example request
Request body:
{
"latitude":20.5,
"longitude":55.1
}

please ensure that you only pass the access token of a user of type driver/admin role before sending a request in postman.

Response:
{
"message": "Shuttle Location added."
}

6. How to check ETA?

Example request 1:
Request body:
{
"latitude":55.22,
"longitude":10.1
}

Response:
{
"message": "0"
}

We got ETA as 0 because I gave the univerity.longitude=10.1
and univerity.latitude= 55.22 as the base location for a shuttle which indicates it's at college place.

Example request 2:
Request body:
{
"latitude":5.5,
"longitude":10.9
}

Response:
{
"message": "Service unavailable" //shuttle is in the process of dropping another student.
}














































