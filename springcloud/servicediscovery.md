https://cloud.spring.io/spring-cloud-netflix/multi/multi__service_discovery_eureka_clients.html

## 1.2 유레카에 등록하기

클라이언트가 유레카에 등록할 때, 클라이언트는 자신에 대한 메타데이터를 제공한다. - host, port, headlth indicator URL, home page 그리고 다른 세부사항들 같은. 유레카는 heartbeat 메시지들을 서비스에 속한 각각의 인스턴스들로부터 받는다. 만약 heartbeat이 설정가능한 시간표를 넘어 실패한다면,그 인스턴스는 일반적으로 저장소로부터 제거된다.

다음 예는 유레카 클라이언트 어플리케이션의 최소이다:
~~~java
@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/")
    public String home() {
        return "Hello world";
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

}
~~~
위의 예는 일반적인 Spring Boot 어플리케이션입니다. spring-cloud-starter-netflix-eureka-client를 classpath 경로에 두면, 어플리케이션은 자동으로 유레카 서버에 등록합니다. 유레카 서버를 찾기 위해선 설정이 필요합니다, 다음 예시를 보시죠:
~~~yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
~~~
위의 예에서 "defaultZone"