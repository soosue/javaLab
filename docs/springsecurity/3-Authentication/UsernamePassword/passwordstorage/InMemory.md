# In-Memory 인증
스프링 시큐리티의 `InMemoryUserDetailsManager`는 username/password기반 인증을 지원하기 위해 UserDetailsService를 구현합니다. 그리고 그건 메모리에 저장되죠. `InMemoryUserDetailsManager`는 `UserDetailsManager`인터페이스를 구현해서 `UserDetails`를 관리합니다. `UserDetails` 기반 인증은 username/password 인증이 허용되게 설정되었을 때, 스프링 시큐리티에서 사용됩니다.

이 예에서 우리는 Spring Boot CLI를 사용합니다. `password`라는 글자의 password를 인코딩하고, 인코딩 된 값`{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW`을 얻기 위해서요.

*Example 1. InMemoryUserDetailsManager Java Configuration*
~~~java
@Bean
public UserDetailsService users() {
    UserDetails user = User.builder()
            .username("user")
            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
            .roles("USER")
            .build();
    UserDetails admin = User.builder()
            .username("admin")
            .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
            .roles("USER", "ADMIN")
            .build();
    return new InMemoryUserDetailsManager(user, admin);
}
~~~

위의 예시는 보안된 형식으로 passwords를 저장합니다. 하지만 시작 예시로는 아직 많이 아쉽습니다.

아래 예시에서는 메모리에 저장된 비밀번호를 확인하기 위해 User.withDefaultPasswordEncoder를 활용합니다. 하지만 이건 소스코드를 디컴파일해서 비밀번호 얻는걸 보호해주진 않기 때문에, `User.withDefaultPasswordEncoder`는 오직 "getting started"에서만 사용되어야합니다. production을 위해 만들어진게 아닙니다.

*Example 2. InMemoryUserDetailsManager with User.withDefaultPasswordEncoder*
~~~java
@Bean
public UserDetailsService users(){
    // The builder will ensure the passwords ar encoded before saving in memory
    UserBuilder users=User.withDefaultPasswordEncoder();
    UserDetails user=users
            .username("user")
            .password("password")
            .roles("USER")
            .build();
    UserDetails admin=users
            .username("admin")
            .password("password")
            .roles("USER","ADMIN")
            .build();
    return new InMemoryUserDetailsManager(user,admin);
}
~~~

XML 설정도 있지만 생략하겠습니다.