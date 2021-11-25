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

