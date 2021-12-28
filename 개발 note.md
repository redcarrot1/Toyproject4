# jpa 헷갈렸던 부분들





- 연관관계 편의 메서드
  - 한 곳만 작성하거나, 양쪽 다 작성할 수 있다.
  - 단, 양쪽에 다 작성하면 무한루프에 빠지므로 반드시 검사 검사 로직이 필요하다.

```java
public class Member {
  private Team team;
  public void setTeam(Team team){
    this.team = team;
    team.getMembers().add(this);
  }
}

또는

public class Team{
  private List<Member> members = new ArrayList<Member>();
  public void addMember(Member member){
    this.members.add(member);
    member.setTeam(this);
  }
}
```



- 양방향 매핑된 상태에서 연관 관계를 끊을 때는 양쪽 다 처리를 해줘야함

```java
...
public void setTeam(Team team){
  if(this.team != null){
    this.team.getMembers().remove(this);
  }
  this.team=team;
  team.getMembers().add(this);
}
```



- JPA 기본 페치 전략
  - `@ManyToOne`,  `@OneToOne` : 즉시 로딩 -> 변경해줘야함
  - `@OneToMany`, `@ManyToMany`: 지연 로딩





- 영속성 전이: cascade
  - 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때

```java
//부모
Parent parent = new Parent();
em.persist(parent);

//자식
Child child = new Child();
child.setParent(parent); //자식->부모 연관관계
parent.getChildren().add(child); //부모->자식
em.persist(child);

JPA에서 엔티티를 저장할 때 연관된 모든 엔티티는 영속 상태여야 한다.(아니면 오류 발생)
따라서 부모 엔티티 영속을 만든 후 -> 자식 엔티티를 만들고 연관 시키고 영속시켜야 함.
```

```java
Child child = new Child();
Parent parent = new Parent();
child.setParent(parent);
parent.getChildren().add(child);

em.persist(parent); //부모만 저장해도 연관된 자식들도 자동으로 저장됨
```





- cascade.remove와 orphanRemoval=true
  - 사실 둘은 매우 비슷한 기능이다. 일반적으로 사용하면 큰 차이는 없다.
  - casecade.remove: 부모가 삭제될때 자식도 함께 삭제.
  - orphanRemoval=true: 부모 엔티티와 연관관계가 끊어지면 자식 삭제
- 둘의 차이는 다음과 같은 상황에 발생한다.
  - 부모를 삭제: 같은 기능
  - 부모에서 자식 관계를 끊는 경우(예를 들어 List<child>에서 제거): orphanRemoval=true만 자식 삭제
- 단, 사용에 매우 주의해야 한다. 자식이 연관된 엔티티가 하나인 경우에만 사용해야함
  - 아닐 경우, 다른 엔티티와 연관 관계는 그대로 남아있는데, db에서 삭제되는 불상사 발생







- @Enumerated

```java
enum RoleType{
  ADMIN, USER
}

@Enumerated(EnumType.STRING)
private RoleType roleType;

member.setRoleType(RoleType.ADMIN) //db에 문자 ADMIN으로 저장됨
```









### 스프링 데이터 jpa 페이징

- 페이징을 위해 제공하는 반환 타입
  - `org.springframework.data.domain.Page` : 추가 count 쿼리 결과를 포함하는 페이징 `org.springframework.data.domain.Slice` : 추가 count 쿼리 없이 다음 페이지만 확인 가능 (내부적으로 limit + 1조회)
  - 여기서 page는 일반적으로 사용하는 게시판 페이지, slice는 모바일 등에서 아래로 스크롤하면 자동으로 다음 페이지를 가져오는 페이징이라 생각하면 된다.
  - 특히 slice는 각 페이지의 게시물수+1 개를 가져와서 +1이 존재하면 자동 페이지 가져오기를 수행하는데, 이 또한 스프링데이터jpa에서 기능을 제공한다.
- 사용할 메소드

```java
Page<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
Slice<Member> findByUsername(String name, Pageable pageable); //count 쿼리 사용
안함
```



- 사용 예제(주의: 페이지는 0부터 시작이다.)

```java
//1. 먼저 repository에 page를 리턴하는 메소드가 필요함
public interface MemberRepository extends Repository<Member, Long> {
 Page<Member> findByAge(int age, Pageable pageable);
}

//2. 나이가 10살인 멤버들을 이름순으로 내림차순, 페이지당 보여줄 데이터는 3건, 첫페이지 원함
PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,
"username"));
 Page<Member> page = memberRepository.findByAge(10, pageRequest);

List<Member> content = page.getContent(); //조회된 데이터
assertThat(content.size()).isEqualTo(3); //조회된 데이터 수
assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호
assertThat(page.isFirst()).isTrue(); //첫번째 항목인가?
assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
```



- 전체 count 쿼리는 매우 무겁다. 따라서 데이터가 복잡할 때는 카운트 쿼리를 분리하는게 매우 중요하다.











이미지 보여주기

```java
@ResponseBody
@GetMapping("/images/{filename}")
public Resource showImage(@PathVariable String filename) throws MalformedURLException {
 	return new UrlResource("file:" + file.getFullPath(filename));
}
```

- 인텔리제이 내부 경로로 직접 접근하면 `404`에러가 뜬다.
  - 컴파일 후 이미지에 접근하면 정상작동한다.
  - 하지만 사용자가 업로드 후 접근하려하면 에러뜸
- 따라서 이미지 리소스를 리턴해주는 컨트롤러가 필요함
- 위와 같은 코드를 사용해 `file:/USERS/...`와 같은 경로로 `UrlResource`를 요청하면된다.
- 이때 `Resource`는 스프링에 있는 라이브러리를 사용하면 된다.







첨부파일 다운로드

```java
@GetMapping("/attach/{fileDataId}")
public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileDataId) throws MalformedURLException {
  FileData fileData = fileDataService.findById(fileDataId);
  String storeFileName = fileData.getStoreName();
  String uploadFileName = fileData.getUploadName();

  UrlResource resource = new UrlResource("file:" + fileStorePath + storeFileName);

  String encodeUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
  String contentDisposition = "attachment; filename=\"" + encodeUploadFileName + "\"";

  return ResponseEntity.ok()
    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
    .body(resource);
}
```

- `encodeUploadFileName` : 한글 이름이 깨질 가능성도 있음. 따라서 `UTF_8`로 인코딩 해주기
- `contentDisposition`: 브라우저에서 다운로드 되는 첨부파일로 설정하기 위해서는 특별한 헤더가 필요하다.
