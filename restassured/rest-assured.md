#GettingStarted

---
#Contents

---
1. Maven / Gradle
    1. Rest Assured
    2. JsonPath
    3. XmlPath
    4. JSON Schema Validation
    5. Spring Mock MVC
    6. Spring Web Test Client
    7. Scala Module
    8. Kotlin Extensions Module
    9. Java 9+
2. Non-maven users
3. Static Imports
4. Version 2.x
5. Documentation

##Maven / Gradle Users

---
다음 의존성을 pom.xml이나 build.gradle에 넣으세요.

##REST Assured
JsonPath와 XmlPath를 포함하고 있습니다.

Maven:

    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>4.4.0</version>
        <scope>test</scope>
    </dependency>

Gradle:

    testImplementation 'io.rest-assured:rest-assured:4.4.0'

기억할 것

1. pom.xml과 build.gradle에서 rest-assured 의존성을 JUnit 의존성 선언부 앞에 위치시켜야합니다. (올바른 버전의 Hamcrest가 사용되는지 확인하기 위해서 그렇습니다.)
2. rest-assured는 transitive 의존성으로 JsonPath와 XmlPath를 포함하고 있습니다.



---
Usage

Static imports

---
REST assured를 효과적으로 쓰기 위해서 statically methods를 사용하는걸 추천합니다. 밑의 클래스들입니다:

      io.restassured.RestAssured.*
      io.restassured.matcher.RestAssuredMatchers.*
      org.hamcrest.Matchers.*
   

---
예제

---
예제 1 - JSON

---
http://localhost:8080/lotto로 GET 요청을 한다고 가정합시다. 결과 JSON은 이렇습니다:

      {
         "lotto":{
            "lottoId":5,
            "winning-numbers":[2,45,34,23,7,5,3],
            "winners":[
               {
                  "winnerId":23,
                  "numbers":[2,45,34,23,3,5]
               },
               {
                  "winnerId":54,
                  "numbers":[52,3,12,11,18,22]
               }
            ]
         }
      }

