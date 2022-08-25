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
박민준 |	https://github.com/pmjn1025 
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
 
      - 기존의 프로젝트에 패키지 구성에 깔끔하지 못하고, 찾고자 하는 데이터에 대한 정리가 잘 되지 않은 것을 느끼고, 
	프로젝트를 계속 진행하면서 패키지를 어떻게 구성할 것인가에 고민이 생겼다. 프로젝트의 패키지 구성은 계층별, 기능별 구성으로 나눌 수 있다. 
 
<br>  
 
    1.기능별로 나누고 계층별로 나누기 
    - 클래스의 기능과 역할에 따라서 패키지를 구성하는 것이다. 
	예를 들어 Member의 정보를 관여하는 패키지를 구성한다면, member패키지 안에 domain, service, dto, repository가 포함되어 구성되게 된다. 
	프로젝트가 커질수록 패키지 안의 클래스 수가 증가하기 때문에 기능별 구성이 재사용성이 좋고 Package Principle을 잘 지키기는 이점이 있어, 이러한 방식으로 정리를 하였음.
![image](https://user-images.githubusercontent.com/67058000/186604944-fa5c1258-fd32-4860-a797-40c684ab700b.png)
 <br>
 <br>
 <br>

</details>


#### 2. 좋아요 기능
	
<details>
<summary>해결방안</summary>
<div markdown="1">
	 <br>
 
      - - ERD를 참고하여 작성했지만 연관관계, oneTomany와 manyToOne에 대한 각각 이해가 부족해, 에러가 발생하였음
![image](https://user-images.githubusercontent.com/67058000/186610246-87c00deb-eafe-4e1c-af51-776e00095245.png)


 
<br>  
 OneToMany(1 : N) 를 사용시 : 참조값 Like가 여러 movie에 적용된다 생각해 OneToMany를 적용시켜봄  

	⇒ 연관관계 재확인 및 수정 후 재실행시 비디오 전체에 좋아요 카운트 증가

 ManyToOne(N : 1) 사용 : 여러 movie를 좋아요 할 수 있고 고유 계정으로 하나의 영화에만 좋아요 가능

	⇒ 어떤 엔티티를 중심으로 상대 엔티티를 바라보느냐에 따라 다중성이 다른게 됨
 <br>
 <br>
 <br>

</details>
	
 
#### 3. Pagination시 nullpointException 발생
<details>
<summary>해결방안</summary>
<div markdown="1">
 <br>
	최초 문제 발생

- Spring으로 페이징을 공부하던 중 기본예제를 응용하여 내 프로젝트에 적용한 후 서버 시작시 바로 nullpointException 발생.
- A.클라이언트 상태
    - 클라이언트에 internal server error 500 발생
- B. 서버상태
    - videoRepository.findAll(pageable)에서 null발생.
- c. 어플리케이션 정보
    - DB에는 영화데이터가 정상적으로 저장되어있음.
    - Video테이블을 사용하는 다른 기능은 정상적으로 작동함.
    - 

---

## Trouble Shooting

### 원인 탐색 과정

1. 클라이언트는 당연히 500error가 발생 할 수 밖에 없음.
2. DB에 정상적으로 저장되고 관련기능은 오류가 발생하지 않음
3. 서버나 기타 여러 다른 부분에서 이상한점과 오류가 발생하지 않음
4. videoRepository.findAll(pageable) 부분에서만 nullpointException 이 발생함.
- 따라서 내가 가져온 예제 코드가 잘못된 코드라고 판단함.

```java
 @GetMapping("/video/pagination")
    public ResponseEntity MoviePagination(final Pageable pageable) {
        Page<Video> videos =
        videoRepository.findAll(pageable);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
```

```java
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

   }
```

### 원인 파악

1. DB에서 해당 데이터 전부를 가져온다.
2. 가져온 데이터를 요청받은 기준으로 분류한다(Sort) --> null이됨.
3. 분류한 데이터를 비즈니스로직에서 받아서 다시 컨트롤러로 전송한다.
4. 컨트롤러에서 클라이언트로 페이징한 데이터를 응답한다.
- 2번에서 문제가 발생함.

### 문제가 발생한 이유.
![image](https://user-images.githubusercontent.com/67058000/186611356-83b25029-b8db-4456-acdf-1c76dd3c1e34.png)

- JpaRepository를 상속받은 Repository는 페이징관련 Repository도 상속받아서 db에서 가져올때 페이징한 데이터를 분류해서 가져올 수 있다.
- 그런데 나는 Pageable만 사용했는데 이 Pageable만 사용했지 해당 분류내용을 내가 지정하지 않았던 것이다.

![image](https://user-images.githubusercontent.com/67058000/186611643-207b1f30-0f53-4cc1-bf72-f379af6fe3ac.png)

- 따라서 해당 분류된 내용을 지정하지 않았으니 null이 되고 nullpointException이 발생한 것이었다.

### 문제 해결

- Pageable의 값을 지정해주기 위해 각 데이터값을 요청받음

```java
@GetMapping("/video/pagination")
    public ResponseDto<?> getPaging(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc

    ) {
        page = page - 1;
        return pagingService.getPagenation(page, size,sortBy,isAsc);
    }
```

• 참고 : @RequestParam("sortBy") String sortBy 이부분 DESC나 ASC와 같이 OrderBy가 아니라 id, title과 같은 분류기준이다.

```java
 @Transactional
    public ResponseDto<?> getPagenation(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Video> videos = videoRepository.findAll(pageable);

        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();

        for (Video video : videos){

            videoResponseDtoList.add(
                    VideoResponseDto.builder()
                            .id(video.getId())
                            .title(video.getTitle())
                            .poster_path(video.getPosterPath())
                            .overview(video.getOverview())
                            .first_date(video.getRelease_date())
                            .grade(video.getPopularity())
                            .youtubePath(video.getYoutubePath())
                            .backdrop_path(video.getBackdrop_path())
                            .homepage(video.getHomepage())
                            .likeCnt(video.getLikeCnt())
                            .build()
            );

        }

        return ResponseDto.success(videoResponseDtoList);

    }
```

• 마지막으로 Repository에서도 정의 해주어야 한다. 그래야 해당데이터를 가져와서 분류해서 데이터를 전달해준다.

```java
@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    Page<Video> findAll(Pageable pageable);
}
```

# 해결

포스트맨에서 http://localhost:8080/video/pagination?page=1&size=3&sortBy=id&isAsc=true 

 

```json
{
    "statusCode": 200,
    "msg": "OK",
    "data": [
        {
            "id": 2,
            "title": "드래곤볼 슈퍼: 슈퍼 히어로",
            "poster_path": "https://image.tmdb.org/t/p/w500/uohymzBVaIYjbnoQstbnlia6ZPJ.jpg",
            "overview": "2018년에 개봉한 에 이은  시리즈의 두 번째 영화",
            "first_date": "2022-06-11",
            "grade": 7195.285,
            "youtubePath": "https://www.youtube.com/embed/GD8nCSr54PA?autoplay=1&mute=1",
            "backdrop_path": "https://image.tmdb.org/t/p/w500/ugS5FVfCI3RV0ZwZtBV3HAV75OX.jpg",
            "homepage": "https://www.2022dbs-global.com",
            "likeCnt": 0
        },
        {
            "id": 9,
            "title": "토르: 러브 앤 썬더",
            "poster_path": "https://image.tmdb.org/t/p/w500/bZLrpWM065h5bu1msUcPmLFsHBe.jpg",
            "overview": "이너피스를 위해 자아 찾기 여정을 떠난 천둥의 신 토르. 그러나, 우주의 모든 신들을 몰살하려는 신 도살자 고르의 등장으로 토르의 안식년 계획은 산산조각 나버린다. 토르는새로운 위협에 맞서기 위해, 킹 발키리, 코르그, 그리고 전 여자친구 제인과 재회하게 되는데. 그녀가 묠니르를 휘두르는 마이티 토르가 되어 나타나 모두를 놀라게 한다. 이제, 팀 토르는 고르의 복수에 얽힌 미스터리를 밝히고 더 큰 전쟁을 막기 위한 전 우주적 스케일의 모험을 시작하는데...",
            "first_date": "2022-07-06",
            "grade": 7623.514,
            "youtubePath": "https://www.youtube.com/embed/Go8nTmfrQd8?autoplay=1&mute=1",
            "backdrop_path": "https://image.tmdb.org/t/p/w500/p1F51Lvj3sMopG948F5HsBbl43C.jpg",
            "homepage": "https://www.marvel.com/movies/thor-love-and-thunder",
            "likeCnt": 0
        },
        {
            "id": 15,
            "title": "프레이",
            "poster_path": "https://image.tmdb.org/t/p/w500/eicYAopFKOL3orcNTJZ4TGtZQQ1.jpg",
            "overview": "300년 전 아메리카, 용맹한 전사를 꿈꾸는 원주민 소녀 나루는 갑작스러운 곰의 습격으로 절체절명의 위기에 놓인 순간, 정체를 알 수 없는 외계 포식자 프레데터를 목격하게 된다.  자신보다 강한 상대를 향한 무자비한 사냥을 시작한 프레데터. 최첨단 기술과 무기로 진화된 외계 포식자 프레데터의 위협이 점점 다가오고 나루는 부족을 지키기 위해 자신만의 기지와 무기로 생존을 건 사투를 시작하는데…",
            "first_date": "2022-08-02",
            "grade": 5763.164,
            "youtubePath": "https://www.youtube.com/embed/wZ7LytagKlc?autoplay=1&mute=1",
            "backdrop_path": "https://image.tmdb.org/t/p/w500/7ZO9yoEU2fAHKhmJWfAc2QIPWJg.jpg",
            "homepage": "https://www.20thcenturystudios.com/movies/prey",
            "likeCnt": 0
        }
    ]
}
```

## Retrospection

- 페이징의 구조 및 작동방식 참고
    - [Spring Boot] JPA + Pageable 을 이용한 페이징 처리  [http://devstory.ibksplatform.com/2020/03/spring-boot-jpa-pageable.html](http://devstory.ibksplatform.com/2020/03/spring-boot-jpa-pageable.html)
    - 스프링부트 검색, 페이징처리 하기 Pageable
    - [https://gonyda.tistory.com/15](https://gonyda.tistory.com/15)
    - [Spring] Spring Data JPA에서 Paging 간단하게 구현하는 법
    - [https://devlog-wjdrbs96.tistory.com/414](https://devlog-wjdrbs96.tistory.com/414)
    - 스파르타 코딩클럽 스프링 심화 4주차 강의자료
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

