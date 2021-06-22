## 1. Introduction

 	 

​	 **Project requirements**: job content contains COBOL internal functions

​	 This project implements a simple COBOL & JCL job submission system.  



## 2. Environment



OS：>=Windows 7

Deployment Software：

   - **Visual Studio Code**
   - **Intellij Idea** 2020.2.3

Deployment framework:

	- Springboot 2.4.1
	- React

You can read the HELP.md to learn how to start our program.





## 3. System Design



### 3.1 Interface Design



#####  3.1.1 User

















##### 3.2.2 Data Process



























##  4. Main Functions&Function Verification



- Users can access the system by enter correct account and password.
- Users can click button ''logout" to exit login status
- Users can edit their COBOL or JCL program in a text editor
- Users can submit their job
- Users can read the status of the jobs they submitted



To verify the functions of the system, we will submit a piece of COBOL codes which contains some internal functions:







and the JCL codes to run the procedure:







the result shows as below:





## 5. Reference Documentation & Guides

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.6/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.6/maven-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.0/reference/htmlsingle/#boot-features-developing-web-applications)



The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)







## 6. Teamwork



### 6.1 Division of labor

| parts     | members              |
| --------- | -------------------- |
| front-end | 邓斯语               |
| back-end  | 聂义鑫、刘春涵、胡冲 |
| document  | all members          |



### 6.2 Cooperation

We use Github for code version control，here you can get access to our code warehouse using the url below:



In order to carry out the separation of front-end and backend deployment, we use Postman to do the controller testing and to generate the controller document. We also published our controller document,you can check it by clicking  [here](https://documenter.getpostman.com/view/13655793/TzeZFSYQ).



















