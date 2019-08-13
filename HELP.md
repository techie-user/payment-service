##Swagger added for UI/Manual testing.
👉🏼 Link [`/swagger-ui.html`](http://localhost:8080/swagger-ui.html).

##SonarQube
To run sonar, kindly ensure,
- SonarQube server is installed and maven settings.xml points to sonar localhost url.
- Installation Details can be found @ [Installation Link](https://docs.sonarqube.org/latest/setup/get-started-2-minutes/)
```
 Command to run sonar on Maven
 <Project_Root_Dir>>mvn clean install
 <Project_Root_Dir>>mvn sonar:sonar -Dsonar.java.source=1.8
 ```
 [Access report link](http://localhost:<SonarQube_Running_Port>), navigate to payment-service
project and view 

To run the spring boot application,
```
mvn spring-boot:run
```
