# TeamMatch Application

A Spring Boot application for managing teams, team members, and join requests. Handles JWT-based authentication and supports team creation, membership requests, and approvals.

---

### **Table of Contents**
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Database Schema](#database-schema)
- [Entities](#entities)
- [Endpoints](#endpoints)
- [OpenApi](#openApi)
- [Test Coverage](#test-coverage)
---

### **Tech Stack**
- Java 24
- Spring Boot 3.2.4
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven

---

## **Setup**

1. Clone the repository:
```bash
git clone <repo-url>
cd teamMatch 
```

2. I used a cloud service for the DataBase. NeonDB is a serverless PostgreSQL provider that handles scaling, backups, and high availability automatically. It is like PostgreSQL in the cloud without the hassle of managing the database server yourself. 
Database URL, user and password are all present in the ```application.poperties``` file.
3. Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

## **Database Schema**
![schema.sql](src/main/resources/static/images/img.png)

## **Entities**

Team: id, name, description, owner, status

Users: id, name, email, roles

Roles: id, name

TeamMembers: team, user, role, approved, joinedAt

TeamRequirements: id, min_count, max_count, team, role (NOT IMPLEMENTED)

TeamJoinRequest: id, team, user, status, createdAt

## **Endpoints**

#### AUTHENTICATION AND LOGIN

1. SIGN UP  
   Route: POST /auth/signup  
   Description: Registers a new user in the system. Returns a JWT token if success.  
   Request Body Example (JSON):

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

Response Example (JSON)

```json
{
  "token" : "token"
}
```

2. SIGN IN  
   Route: POST /auth/signin  
   Description: Login. Returns a JWT token if success  
   Request Body Example (JSON)  

```json
{
  "email" : "email@example.com",
  "password" : "password"
}
```

Response Example (JSON)

```json
{
  "token" : "token"
}
```

#### USERS 

1. GET ALL USERS  
   Route: GET /users  
   Description: Returns all the users present in the DB. Authentication header is required.  
   Response Example (IMAGE)  
![get-all-users-image](src/main/resources/static/images/img_1.png)

2. ADD USER  
   Route: POST /users  
   Description: Add new user. Duplicate emails are not allowed. Custom exception will be thrown.
   Request and Response Examples (IMAGE)

![add new user](src/main/resources/static/images/img_3.png)
![duplicate email error](src/main/resources/static/images/img_2.png)

3. UPDATE USER  
   Route: PUT /users  
   Description: Update user info. Authentication header is required. User can edit only personal info. User Id is passed through the token and verified. In case of Ids not matching custom exception will be thrown.  
   Request and Response Examples (IMAGE)  

![Successful user update](src/main/resources/static/images/img_5.png)
![Conflicting User Ids Handled](src/main/resources/static/images/img_4.png)

4. DELETE USER  
   Route: DELETE /users  
   Description: User Id is extracted from the Authentication header and used to delete user. User cannot delete each other. Custom exception will be thrown in case of Ids not matching. On delete cascade.  
   Request and Response Examples (IMAGE)

![Delete user](src/main/resources/static/images/img_6.png)

5. GET USER BY EMAIL  
   Route: GET /users?find-by-email?email=maftuna@gmail.com  
   Description: Retrieves a user by their email address. Returns a UserDto object. Returns Custom 404 Not Found Exception if no user exists with the given email.  
   Request and Response Examples (IMAGE)

![Valid email result](src/main/resources/static/images/img_8.png)
![Invalid email error](src/main/resources/static/images/img_7.png)

6. GET USER BY NAME  
   Route: GET /users?find-by-name?name={name}  
   Description: Retrieves a user by their name. Returns Custom 404 Not Found Exception if no user exists with the given name. Returns array of all the users if there are multiple users with the same name.   
   Request and Response Examples (IMAGE)

![find-by-name](src/main/resources/static/images/img_9.png)
![Invalid name error](src/main/resources/static/images/img_10.png)

7. GET USER ROLES
   Route: GET /users/get-user-roles?userId={userId}  
   Description: Returns a list of role titles assigned to the user. Returns empty array if there are no roles.  
   Request and Response Example  

![Get user roles](src/main/resources/static/images/img_13.png)
![No roles response](src/main/resources/static/images/img_11.png)

8. ASSIGN ROLE/ROLES TO THE USER  
   Route: POST /users/set-user-role  
   Description: Users can not set roles to each other. User Id extracted from Authentication header and the role from RequestBody assigned to the user with the Id from token.  
   Request and Response Example  
![Add user role](src/main/resources/static/images/img_12.png)

9. DELETE USER ROLE  
   Route: DELETE /users/delete-user-role
   Description: Delete user role. Users cannot delete roles of each other. Auth header is required.  
   Request and Response Example

![Delete user Role](src/main/resources/static/images/img_14.png)

10. GET USER SKILLS/ ASSIGN SKILL/SKILLS TO THE USER / DELETE USER SKILL ROUTES WORK THE SAME WAY WITH A REPLACEMENT OF ROLE KEYWORD TO SKILL IN THE ROUTE.  

#### ROLES AND SKILLS CREATE-UPDATE-DELETE

1. ADD NEW ROLE  
   Route: POST /roles/add  
   Description: Any user can create a new role. Duplicate roles are not allowed. Custom exception will be thrown.  
   Request and Response Example  

![Add-new-role](src/main/resources/static/images/img_15.png)
![Add-already-existing-role-error](src/main/resources/static/images/img_16.png)

2. UPDATE ROLE TITLE  
   Route: PUT /roles/update
   Description: Update role title. Accepts Old Title and a New Title from the RequestBody.  
   Request and Response Example  

![update role title success](src/main/resources/static/images/img_17.png)
![attempt to try update non existing role](src/main/resources/static/images/img_18.png)

3. DELETE ROLE 
   Route: DELETE /roles/remove
   Description: Accepts role title as Request Body, looks up from db and deletes. 
   Request and Response Example  

Before: ![img_19.png](src/main/resources/static/images/img_19.png)
Delete: ![role delete success](src/main/resources/static/images/img_20.png)
After delete: ![img_21.png](src/main/resources/static/images/img_21.png)

4. CREATE-UPDATE-DELETE SKILLS ROUTES HANDLED THE SAME WAY.

#### TEAM ENDPOINTS

1. CREATE TEAM  
   Route: POST /teams  
   Description: Create a new team. Auth header is required. Id from the token is set as Team Owner Id. 
   Request and Response Example


DB View:
![Teams](src/main/resources/static/images/img_22.png)

Create new team.
![create new team](src/main/resources/static/images/img_23.png)

DB View after:
![Teams updated](src/main/resources/static/images/img_24.png)

2. REMOVE TEAM  
   Route: DELETE /teams/{teamId}
   Description: Only team owner can delete the team. Auth header required. In case of mismatch Ids custom exception is thrown. On delete Cascade.  
   Request and Response Example

![delete team](src/main/resources/static/images/img_25.png)
![db after delete](src/main/resources/static/images/img_26.png)

3. FIND TEAM BY STATUS (OPEN, FULL, CLOSED)  
   Route: GET /teams/status/{status}  
   Description: Returns array of the teams based on the requested status  
   Request and Response Example  
![teams by status](src/main/resources/static/images/img_27.png)


#### TEAM MEMBERS ENDPOINTS

1. GET MEMBERS OF THE TEAM  
   Route: GET /teams/team-members/team/{teamId}  
   Description: Returns array of the members of team. Team Id extracted from Path. Owner of team is not displayed in this method.
   Request and Response Example

![Get member of the team](src/main/resources/static/images/img_28.png)
![Db View](src/main/resources/static/images/img_29.png)

2. GET TEAMS OF THE USER  
   Route: GET /teams/user/{userId}
   Description: Returns an array of the Team names a user owns or is part of.


#### TEAM JOIN REQUESTS ENDPOINTS

1. SEND A JOIN REQUEST 
   Route: POST /teams/{teamId}/requests  
   Description: Any user can send a request to join any team. User id extracted from auth header. Default join request status is "PENDING". Team owner can accept or reject the request.  
   Request and Response Example

![send a request to join a team](src/main/resources/static/images/img_30.png)

2. VIEW REQUEST TO JOIN THE TEAM
   Route: GET /teams/view-requests/{teamId}  
   Description: Only a team owner can access this route. User Id is extracted from auth header. In case of Ids mismatch custom exception is thrown.  
   Request and Response Example

![DB View](src/main/resources/static/images/img_31.png)
![Id mismatch error](src/main/resources/static/images/img_32.png)
![view join requests from the owner login](src/main/resources/static/images/img_33.png)

3. RESPOND TO THE JOIN REQUEST  
   Route: POST /teams/{requestId}/respond  
   Description: Team owner can respond to the join requests. Sends a Request Body with the ACCEPT or REJECT status. Custom exception is thrown in case of any mismatch.  
   Request and Response Example  

![join request status update](src/main/resources/static/images/img_34.png)

## **OpenApi**
![OpenAPi](src/main/resources/static/images/openApi.png)

## **Test Coverage**

![teams coverage](src/main/resources/static/images/coverage.png)
![users coverage](src/main/resources/static/images/coverage-1.png)

