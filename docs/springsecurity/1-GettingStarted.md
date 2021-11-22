[출처]https://docs.spring.io/spring-security/reference/servlet/getting-started.html

## Spring Boot Auto Configuration

스프링 부트는 자동적으로
- 스프링 시큐리티의 기본 설정을 사용 가능하게 한다. (`springSecurityFilterChain`이라는 빈이름의 서블릿 `Filter`를 생성함) 이 빈은 Application에서 모든 시큐리티와 관련된 일들을 책임진다.(application의 URLs를 보호하기, 로그인, 로그인 화면보여주기 등등...)
- `user`라는 username을 가진 `UserDetailsService`빈을 생성한다. 그리고 랜덤으로 비밀번호를 생성하고 콘솔에 뿌려준다.
- `springSecurityFilterChain`필터를 Servlet Container에 모든 request에 대해 등록한다.

스프링 부트는 많은 것을 설정하진 않지만, 많은 일을 해준다. 기능 요약은 다음과 같다:
- 인증된 사용자를 필요로 한다. (app과 상호작용하는데)
- 로그인 폼을 만들어준다.
- 기본 사용자를 제공해준다. `user`, 비밀번호는 console에 보여줌.
- 비밀번호 저장소를 Bcrypt로 보호해준다.
- 로그아웃 가능함.
- CSRF 공격 예방
- 세션 고정 보호
- Security Header 통합
  - HTTP Strict Transport Security for secure requests
  - X-Content-Type-Options integration
  - Cache Control (can be overridden later by your application to allow caching of your static resources)
  - X-XSS-Protection integration
  - X-Frame-Options integration to help prevent Clickjacking
- Servlet API 메소드들 통합
  - HttpServletRequest#getRemoteUser()
  - HttpServletRequest.html#getUserPrincipal()
  - HttpServletRequest.html#isUserInRole(java.lang.String)
  - HttpServletRequest.html#login(java.lang.String, java.lang.String)
  - HttpServletRequest.html#logout()
    