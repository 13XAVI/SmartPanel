Backend API Link : http://smartpanel-production.up.railway.app/
------------------------------------------------------------------------------------------------------------------------------------------------------------------
| HTTP Method | Endpoint                                                               | Description                                                             |
| ----------- | -----------------------------------------------------------------------| ------------------------------------------------------------------------|
| POST        | http://smartpanel-production.up.railway.app/auth/Register              | Endpoint to create new user and account                                 |
| POST        | http://smartpanel-production.up.railway.app/auth/login                 | Endpoint to login user and Disstributor in the application              |
| POST        | http://smartpanel-production.up.railway.app/auth/login                 | Endpoint to login admin in the application (only accessible by ADMIN)   |
| POST        | http://smartpanel-production.up.railway.app/grantRole?userId     		   | Endpoint to grant role to user's email  (only accessible by ADMIN)      |
| DELETE      | http://smartpanel-production.up.railway.app/Users/Delete/12            | Endpoint  for deltete user (only accessible by ADMIN)                   |
| PUT        | http://smartpanel-production.up.railway.app/Users/Update/1              | Endpoint to Update user   account (only accessible by ADMIN)            |
| GET        | http://smartpanel-production.up.railway.app/Users/List                  | Endpoint to retrieve user List  (only accessible by ADMIN)              |
| GET        | http://smartpanel-production.up.railway.app/find/2                      | Endpoint to find a user (only accessible by ADMIN)                      |
| POST       | http://smartpanel-production.up.railway.app/auth/HomePage               | Endpoint to View Home Page                                              |
| GET        | http://smartpanel-production.up.railway.app/ListProducts                | Endpoint to List all Product                                            |
| POST       | http://smartpanel-production.up.railway.app/Create/products             | Endpoint to create Product (only accessible by ADMIN and Distributor)   |
| GET        | http://smartpanel-production.up.railway.app/product/1                   | Endpoint to get one product                                             |
| PUT        | http://smartpanel-production.up.railway.app/Update/product/1            | Endpoint to update one Product (only accessible by Distributor)         |
| DELETE     | http://smartpanel-production.up.railway.app/Delete/product/1            | Endpoint to Delete one Product                                          |
| GET        | http://smartpanel-production.up.railway.app/products/find/              | Endpoint to Search for Product                                          |
| POST       | http://smartpanel-production.up.railway.app/fileUpload                  | Endpoint to Upload a file content                                       |
| PUT        | http://smartpanel-production.up.railway.app/downloadFile/Zhby75S9       | Endpoint to Download a file content                                     |
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

Features

.Signup 
.Login
.Grant Role By Admin
.CRUD Operation For User
.Home Page
.CRUD Operation For Product
.Upload File
.Download File
.Search
.App Cashing

System Design
https://github.com/13XAVI/SmartPanel/assets/101405347/829fcea6-7ff2-4540-a959-d07223545fb7

https://github.com/13XAVI/SmartPanel/assets/101405347/c98c1a10-79af-474a-9b6e-bc139ec7a203

Role DataBase
https://github.com/13XAVI/SmartPanel/assets/101405347/f7ae21e8-b7c6-4dc6-94db-b9f622db2da3

Access all endpoints on this url

  http://localhost:8080
  
  Documentation
  
  users Collection    : https://web.postman.co/workspace/My-Workspace~1c2db209-d6df-40b1-81d3-3e8cfcd80f14/documentation/27734545-476833ce-4881-46b3-801c-a7c6bf5bff3b                        
 
 Product Collectoion : https://web.postman.co/workspace/1c2db209-d6df-40b1-81d3-3e8cfcd80f14/documentation/27734545-b7a57e01-6c8c-4953-bd86-405b94c059cd
  



  
