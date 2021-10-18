## 그래들이 할 수 있는 일 찾기

이제 그래들을 설치했고 뭘 할 수 있는지 봅시다. 프로젝트에 build.gradle 파일을 만들기 전에 어떤 tasks들이 가능한 지 물어볼 수 있습니다.

     gradle tasks

이용 가능한 tasks 목록들을 볼 수 있습니다. build.gradle이 없는 폴더에서 그래들을 돌린다고 하면, 몇 가지 매우 기초적인 tasks 들만 아래처럼 볼 수 있습니다:

    :tasks

    ------------------------------------------------------------
    Tasks runnable from root project
    ------------------------------------------------------------
    
    Build Setup tasks
    -----------------
    init - Initializes a new Gradle build.
    wrapper - Generates Gradle wrapper files.
    
    Help tasks
    ----------
    buildEnvironment - Displays all buildscript dependencies declared in root project 'gs-gradle'.
    components - Displays the components produced by root project 'gs-gradle'. [incubating]
    dependencies - Displays all dependencies declared in root project 'gs-gradle'.
    dependencyInsight - Displays the insight into a specific dependency in root project 'gs-gradle'.
    dependentComponents - Displays the dependent components of components in root project 'gs-gradle'. [incubating]
    help - Displays a help message.
    model - Displays the configuration model of root project 'gs-gradle'. [incubating]
    outgoingVariants - Displays the outgoing variants of root project 'gs-gradle'.
    projects - Displays the sub-projects of root project 'gs-gradle'.
    properties - Displays the properties of root project 'gs-gradle'.
    tasks - Displays the tasks runnable from root project 'gs-gradle'.
    
    To see all tasks and more detail, run gradle tasks --all
    
    To see more detail about a task, run gradle help --task <task>
    
    Deprecated Gradle features were used in this build, making it incompatible with Gradle 7.0.
    Use '--warning-mode all' to show the individual deprecation warnings.
    See https://docs.gradle.org/6.0.1/userguide/command_line_interface.html#sec:command_line_warnings
    
    BUILD SUCCESSFUL in 477ms
    1 actionable task: 1 executed

이런 명령들이 가능하지만, 프로젝트의 빌드 설정 없이 큰 가치를 갖지는 않습니다. `build.gradle`파일을 더 살찌울 수록 몇몇 명렁들은 더 유용해질겁니다. 명령 목록들은 `build.gradle`에 플러그인을 추가할 수록 점점 늘어날 것입니다, 그래서 가끔 이용가능한 명령어들을 보기 위해 tasks 명령을 돌리고 싶을 수도 있습니다.

플러그인 추가관련해서 말하자면, 다음 부분에서 기본적인 자바 빌드 기능을 사용할 수 있는 플러그인을 추가할겁니다.


## 자바 코드 빌드하기

간단히 시작해서, 매우 기초적인 `build.gradle`파일을 만들어놓은 프로젝트 폴더에 생성합시다. 그리고 한 줄 적으세요:

    apply plugin: 'java'

빌드 설정파일 안의 이 한 줄이 상당한 양의 힘을 가져옵니다. gradle tasks 명령을 다시 돌려보세요. 그럼 building the project, creating JavaDoc, 그리고 running tests를 포함한 새로운 tasks가 목록에 추가된 걸 볼 수 있습니다. 

**gradle build** 작업을 자주 사용할 겁니다. 이 작업은 jar파일 안으로 코드를 컴파일하고, 테스트하고 모아줍니다. 이렇게 사용할 수 있죠:

    gradle build

몇 초 뒤에, "BUILD SUCCESSFUL" 문구가 뜨면서 빌드가 완료된 걸 알려줄겁니다.

빌드 작업의 결과를 보려면, *build* 폴더를 보세요. 몇 가지 아래와 같은 디렉토리들이 보일겁니다:
* *classes.* 프로젝트의 컴파일된 .class 파일들입니다. 
* *reports.* 빌드에 의해 생긴 보고서입니다.(test reports 같은)
* *libs.* 모아진 프로젝트 라이브러리들입니다.(보통 JAR나 WAR 파일들입니다)

classes 폴더는 자바코드를 컴파일링해서 생겨난 .class 파일들을 가지고 있습니다. 특히 여기선 HelloWorld.class와 Greeter.class를 찾을 수 있어야합니다.

여기서 잠깐, 현 시점에서 프로젝트는 다른 라이브러리 의존성들이 없기 때문에, dependency_cache 폴더에 아무 것도 없습니다. 

reports 폴더는 프로젝트의 단위 테스트를 실행시킨 보고서 포함하고 있어야 합니다. 프로젝트가 지금 단위 테스트가 없기 때문에, 그 보고서는 재미없을 겁니다.

libs 폴더는 프로젝트 폴더의 으름을 딴 JAR 파일이 하나 존재해야 합니다. 아래로 가면, JAR의 이름과 버전을 지정하는 방법이 나와있습니다.


## 의존성 선언하기

Hello World 예시는 완벽히 자기자신만 피룡하며, 다른 추가 라이브러리에 의존하지 않습니다. 하지만 대부분의 어플리케이션은 일반적이거나 복잡한 기능들을 다루는 외부 라이브러리에 의존되어 있죠.

예를 들어, "Hello World!" 라고 말하는 것에, 어플리케이션이 현재 날짜와 시간을 같이 출력해주길 원한다고 가정해봅시다. 순수 자바 라이브러리에서 날짜와 시간을 사용할 수 있지만, Joda Time 라이브러리를 사용하면 더 재미있는 일을 할 수 있습니다.

우선, HelloWorld.java를 이렇게 바꿔보죠:
~~~java
package hello;

import org.joda.time.LocalTime;

public class HelloWorld {
  public static void main(String[] args) {
    LocalTime currentTime = new LocalTime();
    System.out.println("The current local time is: " + currentTime);

    Greeter greeter = new Greeter();
    System.out.println(greeter.sayHello());
  }
}
~~~
여기 `HelloWorld`가 현재 시간을 가져와 출력하려고, Joda Time의 `LocalTime`클래스를 사용했습니다.

만약 지금 `gradle build`를 프로젝트를 빌드하려고 돌린다면, 빌드는 실패할겁니다. 왜냐하면 Joda Time을 빌드에서 컴파일 의존성으로 선언하지 않았기 때문이죠.

우선, 3rd party 라이브러리를 위한 소스를 추가해야합니다.

    repositories {
        mavenCentral()
    }

`repositories`블록은 지시합니다. 빌드는 자신의 의존성들을 Maven Central repository에서 해결해야한다고 말이죠. 그래들은 메이븐 빌드 도구가 만든 많은 관행과 틀에 크게 의존하고 있습니다.(Maven Central을 라이브러리 의존성들의 원천으로 사용하는 옵션을 포함해서) 

이제 3rd party 라이브러리가 준비되었으니, 몇 가지를 선언해보죠.

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    dependencies {
        implementation "joda-time:joda-time:2.2"
        testImplementation "junit:junit:4.12"
    }

`dependencies`블록과 함께, Joda Time을 위한 의존성을 선언했습니다. 특히 (오른쪽부터 왼쪽으로) 버전 2.2의 joda-time 라이브러리를, joda-time 그룹에 요청하고 있습니다.

이 의존성에서 알아두어야할 다른 한 가지는, `compile`의존성입니다. 이건 컴파일타임 동안에 사용가능해야한다는걸 나타냅니다.(그리고 만약 WAR 파일을 빌드한다면, WAR파일의 /WEB-INF/libs 폴더 안에 있을겁니다) 다른 주목할만한 타입의 의존성은 다음과 같습니다:
* `implementation`. 프로젝트 코드를 컴파일링 할 때 필요한 의존성이지만, 코드를 돌리는 컨테이너에 의해 런타임에도 제공이 됩니다.(예: Java Servlet API)

* `testImplementation`. 컴파일과 테스트를 돌릴 때는 필요하지만 빌드하거나 프로젝트의 런타임 코드를 돌릴 때는 필요없는 의존성들.

마지막으로, 우리의 JAR 아티팩트의 이름을 지정해봅시다.

    jar {
        archiveBaseName = 'gs-gradle'
        archiveVersion = '0.1.0'
    }

`jar`블록은 JAR 파일이 어떻게 명명될지 지정해줍니다. 이 경우에 `gs-gradle-0.1.0.jar`로 만들어질겁니다.

이제 `gradle build`를 돌리면, 그래들은 Joda Time 의존성을 Maven Central Repository 로부터 해결하고 빌드는 성공할겁니다.

## Gradle Wrapper로 프로젝트 빌드하기

그래들 래퍼는 그래들 빌드를 시작하는 더 선호되는 방법입니다. 윈도우에선 batch 스크립트로, linux 등에선 shell 스크립트로 이루어져 있습니다. 이 스크립트는 그래들이 시스템에 설치되어 있지 않아도 gradle build를 돌릴 수 있게 해줍니다. This used to be something added to your build file, but it’s been folded into Gradle, so there is no longer any need. 대신 아래 명령어로 간단히 사용하면 됩니다.

    $ gradle wrapper --gradle-version 6.0.1

이 작업이 완료되면, 몇 개의 새로운 파일을 볼 수 있을겁니다. 2개의 스크립트가 폴더의 처음에 있고, wrapper jar와 properties 파일들은 gradle/wrapper 폴더에 있습니다.

    └── <project folder>
        └── gradlew
        └── gradlew.bat
        └── gradle
            └── wrapperㅎ
                └── gradle-wrapper.jar
                └── gradle-wrapper.properties

이제 프로젝트를 빌드하기 위해 그래들 래퍼를 사용할 수 있습니다. vcs에 같이 올리면, 당신의 프로젝트를 클론하는 사람들이 당신과 같은 방식으로 build 할 수 있습니다. 이건 설치된 버전의 그래들과 동일한 방법으로 사용될 수 있습니다. build 작업을 하기 위해 wrapper 스크립트를 이전에 했던 것처럼 돌리세요:

    ./gradlew build

지정된 그래들 버전의 wrapper를 처음 실행하면, wrapper는 해당 버전의 그래들 binaries를 다운로드하고 캐시합니다. The Gradle Wrapper files are designed to be committed to source control 그래서 누구든 특정 버전의 그래들을 설치하고 설정할 필요 없이 프로젝트를 빌드할 수 있습니다.

이 단계에서 코드를 빌드 해볼겁니다. 이런 결과를 얻을 수 있습니다:

    build
    ├── classes
    │   └── main
    │       └── hello
    │           ├── Greeter.class
    │           └── HelloWorld.class
    ├── dependency-cache
    ├── libs
    │   └── gs-gradle-0.1.0.jar
    └── tmp
        └── jar
            └── MANIFEST.MF

JAR 파일 뿐만 아니라, 예상했던 `Greeter`와 `HelloWorld` 두 클래스파일들이 포함되어 있습니다. 빠르게 보죠:

    $ jar tvf build/libs/gs-gradle-0.1.0.jar
    0 Fri May 30 16:02:32 CDT 2014 META-INF/
    25 Fri May 30 16:02:32 CDT 2014 META-INF/MANIFEST.MF
    0 Fri May 30 16:02:32 CDT 2014 hello/
    369 Fri May 30 16:02:32 CDT 2014 hello/Greeter.class
    988 Fri May 30 16:02:32 CDT 2014 hello/HelloWorld.class

클래스 파일들은 묶여져 있습니다. joda-time을 의존성에 선언했어도, 라이브러리는 여기 포함되어 있지 않은걸 알아야합니다. 그리고 JAR 파일은 역시 실행시킬 수 없죠.

이 코드가 실행가능하게 하기 위해, 우리는 그래들의 `application` 플러그인을 쓸 수 있습니다. 이 코드를 `build.gradle` 파일에 추가하세요.

    apply plugin: 'application'
    mainClassName = 'hello.HelloWorld'

이제 당신의 앱을 실행할 수 있습니다!

    $ ./gradlew run
    :compileJava UP-TO-DATE
    :processResources UP-TO-DATE
    :classes UP-TO-DATE
    :run
    The current local time is: 16:16:20.544
    Hello world!
    
    BUILD SUCCESSFUL
    
    Total time: 3.798 secs

의존성들을 묶기 위해서는 더 많은 생각이 필요합니다. 예를 들면, 우리가 형식이 보통 3rd party 의존성들에 연관된 WAR 파일을 빌드하려 한다면, 그래들의 WAR 플러그인을 이용할 수 있습니다. 만약 스프링 부트를 사용하고 돌릴 수 있는 JAR 파일을 원한다면, spring-boot-gradle-plugin이 쓰기 좋을겁니다.  
 이 단계에선 그래들은 그런 것들을 선택하기에 당신의 시스템 정보에 대해 충분히 알지 못합니다. 하지만 이젠 


[출처] https://spring.io/guides/gs/gradle/