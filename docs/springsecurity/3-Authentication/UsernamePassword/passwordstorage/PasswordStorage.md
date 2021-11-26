# Storage Mechanisms
username/password을 읽는데 지원되는 메커니즘(form, basic, digest)은 어떤 저장소 메커니즘이라도 사용할 수 있습니다:
* In-Memory 인증을 하는 간단한 저장소
* JDBC 인증을 하는 RDB
* UserDetailsService을 사용하는 커스텀 정보 저장소
* LDAP 인증을 사용하는 LDAP 저장소

## Section Summary
* In Memory
* JDBC
* UserDetails
* UserDetailsService
* PasswordEncoder
* DaoAuthenticationProvider
* LDAP