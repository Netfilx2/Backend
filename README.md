![1](https://user-images.githubusercontent.com/67058000/186458930-d71285c5-1ceb-4ecc-9d04-235b145e98ba.png)


##
### 🙌 프로젝트 소개

넷플릭스 클론 코딩


### 🗓 프로젝트 기간
2022년 08월 19일 ~ 2022년 8월 25일 (1주)

### ⚒️ 프로젝트 아키텍처


### 🛠 기술스택

<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"> 
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> 

 <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">



### 😎 백엔드 팀원 소개
이름 | 깃허브 주소 |
---|---|
박민준	| 
류지우 |	https://github.com/ryujiwoo184
한동훈 | https://github.com/hdonghun

### 📚 와이어 프레임 / API 명세서

<details>
<summary>여기를 눌러주세요</summary>
<div markdown="1">


[노션으로 열기](https://www.notion.so/2-264b9001bbcc4d58a0c1a63ae6f4e369)

</div>
</details>
<br>

### 프로젝트의 패키지 구성

├── 🗂backend  

   ├── member  
   
          |   ├── controller

  	  |    |   └──  MemberContorller

   	  |   ├── domain

  	  |    |   └──  Member

  	  |   ├── service

   	  |    |   └──  MemberService
 
   	 |   ├── repository
   
   	 |    |   └──  MemberRespository

   ├── movie  
   
	  |   ├── controller

  	  |    |   └──  MovieContorller

  	  |   ├── domain

  	  |    |   └──  Movie

  	  |   ├── service

  	  |    |   └──  MovieService
 
  	  |   ├── repository
   
  	  |    |   └──  MovieRespository  
     
     ...



### ✨ 핵심 기능

1. 로그인, 회원가입

        : JWT를 이용하여 로그인과 회원가입을 구현하였습니다.

        : 아이디와 닉네임의 중복확인이 가능합니다.

        : OAuth 2.0을 이용하여 카카오 소셜 로그인 기능이 가능합니다.

2. CRUD

        : 각 카테고리별 영화/드라마을 확인 할 수 있습니다.
        

        : 좋아요 👍 및 찜하기 기능을 사용 할 수 있습니다.
        
3. API
       
        : 영화 오픈API를 가져와, 사용하여 넷플릭스 메인 페이지에서 보여주는 거 같은, 영화,드라마를 보여줍니다.

### 🔥 트러블슈팅
#### 1. 기능별로 계층구조를 통한 명확한 프로젝트 시각화 
<details>
<summary>해결방안</summary>
<div markdown="1">
 <br>
 
      - 기존의 프로젝트에 패키지 구성에 깔끔하지 못하고, 찾고자 하는 데이터에 대한 정리가 잘 되지 않은 것을 느끼고, 프로젝트를 계속 진행하면서 패키지를 어떻게 구성할 것인가에 고민이 생겼다. 프로젝트의 패키지 구성은 계층별, 기능별 구성으로 나눌 수 있다. 
 
<br>  
 
    1.기능별로 나누고 계층별로 나누기 
    - 클래스의 기능과 역할에 따라서 패키지를 구성하는 것이다. 예를 들어 User의 정보를 관여하는 패키지를 구성한다면, User패키지 안에 UserEntity, UserService, UserDTO, UserRepository가 포함되어 구성되게 된다. 프로젝트가 커질수록 패키지 안의 클래스 수가 증가하기 때문에 기능별 구성이 재사용성이 좋고 Package Principle을 잘 지키기는 이점이 있어 많이 사용된다. 

   
 <br>
 <br>
 <br>

</details>
 

#### 2. CORS문제 설정 다 했음에도 안됨
<details>
<summary>해결방안</summary>
<div markdown="1">
 <br>
CORS설정 내역
 <br>
 
 
 ```java
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
 http
          .cors().configurationSource(corsConfigurationSource());
          ...후략...
```

```java
 @Bean
    public CorsConfigurationSource corsConfigurationSource() {
       final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://3.37.127.16:8080"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedHeader("Authorization");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); //preflight 결과를 1시간동안 캐시에 저장
        configuration.addExposedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
```    
위와 같이 필터 부분에 cors설정을 해주고 마찬가지로 필터 부분에 아래의 Bean을 추가하였으나 cors에러가 해결되지 않았다. 

이유는 configure 파트에서 H2console 사용을 위해 추가해놓은 Bean 때문
사실 이유는 모르는데 주석처리하니까 됨 아마 충돌 문제일듯
```java
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
//        return (web) -> web.ignoring()
//                .antMatchers("/h2-console/**")
//                .antMatchers("/api/members/**")
//                .antMatchers("/favicon.ico");
//
//    }
```
 <br>
 <br>
 <br>

</details>
 
 
#### 3. 깃허브 충돌 문제
<details>
<summary>해결방안</summary>
<div markdown="1">
 <br>
최대한 충돌을 발생시키지 않으려고 여러 방법을 시도했는데 그냥 풀리퀘스트 하고 비교해서 처리하는게 가장 편했다!

 <br>
 <br>
</details>

#### 4. 양쪽 클래스에서 서로 참조하는 경우 순환오류 발생
<details>
<summary>해결방안</summary>
<div markdown="1">
 <br>
상호 참조 하는 경우를 만들지 말자
 <br>
 <br>

</details>

#### 5. 이미지 업로드시 기본 용량 제한이 1MB여서 문제가 발생함
<details>
<summary>해결방안</summary>
<div markdown="1"> 
 <br>
application.properties 파일에
 
 
```java
spring.servlet.multipart.maxFileSize=10MB
spring.servlet.multipart.maxRequestSize=10MB
 ```

 와 같이 제한을 설정할 수 있음
 <br>
 <br>
 <br>
</details>

#### 📖 새로 적용해본 기술
- OAuth 2.0을 통한 소셜 로그인
 
 
개선해야할 사항

