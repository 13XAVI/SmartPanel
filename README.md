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
| PUT        | http://smartpanel-production.up.railway.app/downloadFile/{filecode}     | Endpoint to Download a file content                                     |
-------------------------------------------------------------------------------------------------------------------------------------------------------------------

Features
  
