# Url-Shortner : url shortner service

## Tech Stack
- Spring Boot with kotlin (spring mvc)
- internal hash map used for cache purpose (adding DB is pending.. so once server restart all data will be lost)

## Inhancement 
- can add service layer, I have done all in controller for just demo
- need to add storage layer, auth layer 
- we can add LRU facility in hashmap to stop growing large size

## Routes suported

###### POST /api/v1/routes
- to save short url

![image](https://user-images.githubusercontent.com/47358181/210709931-23444ea8-a70e-4a4c-9eb9-b36b8f816d50.png)


###### GET /api/v1/routes
- list all routes

![image](https://user-images.githubusercontent.com/47358181/210709972-086b7e1c-d87b-4752-b3f3-8972d7ed5b91.png)


###### ANY /{short-url}
- to redirect original url

![image](https://user-images.githubusercontent.com/47358181/210710019-c6e61a6c-8ec7-42c3-ae0b-e06deda0576c.png)


###### GET / 
- for url service home page

![image](https://user-images.githubusercontent.com/47358181/210710081-bc89f304-b92b-44ce-a4a3-0ab45636f48b.png)
