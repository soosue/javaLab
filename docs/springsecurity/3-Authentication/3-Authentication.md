# Authentication

스프링 시큐리티는 인증에 대한 포괄적인 지원을 제공한다. Servlet authentication Architecture를 보면서 시작해보자. 예상했을거지만, 이 섹션은 어떻게 적용되는가에 대한 구체적인 흐름 없이 추상적으로 아키텍쳐를 설명한다.

원한다면, 사용자를 인증할 수 있는 구체적인 방법인 Authentication Mechanisms를 참조할 수 있다.

## Authentication Mechanisms
- Username and Password - 유저이름/비밀번호로 인증하는 방법
- OAuth 2.0 Login - OAuth 2.0 Log In with OpenID Connect and non-standard OAuth 2.0 Login (i.e. GitHub)
- SAML 2.0 Login - SAML 2.0 Log In
- Central Authentication Server (CAS) - Central Authentication Server (CAS) Support
- Remember Me - how to remember a user past session expiration
- JAAS Authentication - authenticate with JAAS
- OpenID - OpenID Authentication (not to be confused with OpenID Connect)
- Pre-Authentication Scenarios - authenticate with an external mechanism such as SiteMinder or Java EE security but still use Spring Security for authorization and protection against common exploits.
- X509 Authentication - X509 Authentication