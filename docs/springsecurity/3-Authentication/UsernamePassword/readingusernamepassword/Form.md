# Form Login
스프링 시큐리티는 html form을 통해서 제공된 username과 password에 대한 지원을 제공하고 있습니다. 이 section에서는 form 기반 인증이 스프링 시큐리티에서 어떻게 돌아가는지 자세히 보겠습니다.

스프링 시큐리티에서 form 기반 로그인이 어떻게 도나 한 번 보죠. 첫 번째로, user가 로그인 form으로 어떻게 redirect 되는지 봅시다.

![LoginUrlAuthenticationEntryPointImage](../../../image/loginurlauthenticationentrypoint.png)

이 그림은 `SecurityFilterChain` 다이어그램을 그린 겁니다.

![number1Image](../../../image/number_1.png) 첫 번째로, user는 인증되지 않은 요청을 resource `/private`에 대해 합니다. 물론 권한도 없죠.

![number2Image](../../../image/number_2.png) 스프링 시큐리티의 `FilterSecurityInterceptor`는 인증되지 않은 요청이 거부되었다고 `AccessDeniedException`을 발생시킵니다.

![number3Image](../../../image/number_3.png) user가 인증되지 않았기 때문에, `ExceptionTranslationFilter`는 *Start Authentication*을 시작하고 설정된 `AuthenticationEnrtyPoint`와 함께 로그인 페이지로 redirect합니다. 대부분의 경우 `AuthenticationEntryPoint`는 `LoginUrlAuthenticationEntryPoint`의 인스턴스입니다.

![number4Image](../../../image/number_4.png) 브라우저는 redirect 됐기 때문에 로그인 페이지를 요청할겁니다.

![number5Image](../../../image/number_5.png) 몇몇 처리가 app에서 있고, 로그인 페이지가 render됩니다.

username과 password가 제출되면, `UsernamePasswordAuthenticationFilter`는 username과 password를 인증합니다. `UsernamePasswordAuthenticationFilter`는 AbstractAuthenticationProcessingFilter를 확장합니다. 그래서 diagram은 아래와 좀 더 유사합니다.

![UsernamePasswordAuthenticationFilterImage](../../../image/usernamepasswordauthenticationfilter.png)

![number1Image](../../../image/number_1.png) user가 username과 password를 제출하면 `UsernamePasswordAuthenticationFilter`가 `HttpServletRequest`에서 username과 password를 뽑아서 `UsernamePasswordAuthenticaionToken`을 생성합니다.(`Authentication`의 타입입니다)

![number2Image](../../../image/number_2.png) 다음으로, `UsernamePasswordAuthenticationToken`은 `AuthenticationManager`에게 인증받기 위해 전달됩니다. `AuthenticationManager`의 상세정보가 어떻게 되는지는 user 정보가 어떻게 저장되어 있느냐에 따라 다릅니다.

![number3Image](../../../image/number_3.png) 만약 인증이 실패하면,
- SecurityContextHolder는 비워지고,
- `RememberMeServices.loginFail`이 실행됩니다. remember me가 설정되어 있지 않으면, 아무 일도 일어나지 않습니다.
- `AuthenticationFailureHandler`가 실행됩니다.

![number4Image](../image/number_4.png) 인증이 성공하면
- SessionAuthenticationStrategy는 새로 로그인 된 사실을 알게 됩니다.
- Authentication은 SecurityContextHolder에 저장됩니다.
- `RememberMeServices.loginSuccess`가 실행됩니다. remember me가 설정되어 있지 않으면, 아무 일도 일어나지 않습니다.
- `ApplicationEventPublisher`가 `InteractiveAuthenticationSuccessEvent`를 발행합니다.
- `AuthenticationSuccessHandler`가 실행됩니다. 보통 `SimpleUrlAuthenticationSuccessHandler`입니다. 그리고 그건 ExceptionTranslationFilter가 로그인 페이지로 redirect할 때 저장했던, 요청으로 redirect 해줄겁니다.

스프링 시큐리티 form 로그인은 기본적으로 가능합니다만, 

*Example 1. Form Log In*
~~~java
protected void configure(HttpSecurity http) {
    http
            // ...
            .formLogin(withDefaults());
}
~~~
이 설정에서 스프링 시큐리티는 기본 로그인 페이지를 뿌려줍니다. 대부분의 production app들은 자신만의 로그인 form이 요구되죠.

밑의 설정은 custom한 로그인 form을 제공하는 방법을 보여줍니다.

*Example 2. Custom Log In Form Configuration*
~~~java
protected void configure(HttpSecurity http) throws Exception {
    http
            // ...
            .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
            );
}
~~~
로그인 페이지가 스프링 시큐리티 설정에 명시되면, page를 뿌려줘야할 책임을 가지게 됩니다. 아래는 HTML 로그인 form을 생성하는 Thymeleaf 템플릿입니다. `/login`과 묶여있습니다:
*Example 3. Log In Form*
~~~html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">
    <head>
        <title>Please Log In</title>
    </head>
    <body>
        <h1>Please Log In</h1>
        <div th:if="${param.error}">
            Invalid username and password.</div>
        <div th:if="${param.logout}">
            You have been logged out.</div>
        <form th:action="@{/login}" method="post">
            <div>
                <input type="text" name="username" placeholder="Username"/>
            </div>
            <div>
                <input type="password" name="password" placeholder="Password"/>
            </div>
            <input type="submit" value="Log in" />
        </form>
    </body>
</html>
~~~

default HTML form에 대한 몇가지 키포인트가 있습니다:
- form은 `post`로 `/login`을 수행해야합니다.
- form은 CSRF Token을 가지고 있어야합니다.(Thymeleaf가 자동으로 포함하고 있습니다)
- form은 `username`이라는 파라미터로 username을 특정해야합니다.
- form은 `password`이라는 파라미터로 password를 특정해야합니다.
- 만약 HTTP 파라미터 error가 발견된다면, user가 제공한 username / password가 틀렸단걸 보여줍니다.
- 만약 HTTP 파라미터 logout이 발견된다면, user가 logout이 성공적으로 됐다는걸 보여줍니다.

많은 user들이 커스텀한 로그인 페이지가 필요하진 않겠지만, 필요하다면 위의 모든 내용이 추가 설정으로 커스터마이징 될 수 있습니다.

만약 스프링 MVC를 이용한다면, `GET /login`을 매핑하는 controller가 필요합니다. `LoginController`의 간단한 약식 샘플입니다.

*Example 4. LoginController*
~~~java
@Controller
class LoginController {
    @GetMapping("/login")
    String login() {
        return "login";
    }
}
~~~