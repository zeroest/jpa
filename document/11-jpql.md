
# 객체지향 쿼리 언어 (JPQL)

- JPQL
- JPA Criteria
- QueryDSL
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

[JPQL Example](../src/main/java/jpql/JpqlMain.java)

[JPQL Projection Example](../src/main/java/jpql/JpqlProjectionMain.java)

[JPQL Paging Example](../src/main/java/jpql/JpqlPagingMain.java)

- setFirstResult(int startPosition) : 조회 시작 위치 (0부터 시작)
- setMaxResults(int maxResult) : 조회할 데이터 수

## Join

종류

- 내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
- 외부 조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
- 세타 조인: SELECT count(m) FROM Member m, Team t where m.username = t.name

On 절 

ON절을 활용한 조인(JPA 2.1부터 지원)

1. 조인 대상 필터링

예) 회원과 팀을 조인하면서, 팀 이름이 A인 팀만 조인

```
JPQL:
SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'

SQL:
SELECT m.*, t.* FROM
Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'
```

2. 연관관계 없는 엔티티 외부 조인(하이버네이트 5.1부터)

예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인

```
JPQL:
SELECT m, t FROM
Member m LEFT JOIN Team t on m.username = t.name

SQL:
SELECT m.*, t.* FROM
Member m LEFT JOIN Team t ON m.username = t.name
```

## Sub Query

### 지원 함수

- 하이버네이트6 부터는 FROM 절의 서브쿼리를 지원
- [NOT] EXISTS (subquery): 서브쿼리에 결과가 존재하면 참
    - {ALL | ANY | SOME} (subquery)
    - ALL 모두 만족하면 참
    - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
- [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

### 예제

나이가 평균보다 많은 회원
```
select m from Member m
where m.age > (select avg(m2.age) from Member m2)
```

한 건이라도 주문한 고객
```
select m from Member m
where (select count(o) from Order o where m = o.member) > 0
```

팀A 소속인 회원
```
select m from Member m
where exists (select t from m.team t where t.name = ‘팀A')
```

전체 상품 각각의 재고보다 주문량이 많은 주문들
```
select o from Order o
where o.orderAmount > ALL (select p.stockAmount from Product p)
```

어떤 팀이든 팀에 소속된 회원
```
select m from Member m
where m.team = ANY (select t from Team t)
```

### JPA 서브 쿼리 한계

- JPA는 WHERE, HAVING 절에서만 서브 쿼리 사용 가능
- SELECT 절도 가능(하이버네이트에서 지원)
- FROM 절의 서브 쿼리는 현재 JPQL에서 불가능
- 조인으로 풀 수 있으면 풀어서 해결

## JPQL 타입 표현

[Example Code](../src/main/java/jpql/JpqlTypeMain.java)

- 문자: ‘HELLO’, ‘She’’s’
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: jpabook.MemberType.Admin (패키지명 포함)
- 엔티티 타입: TYPE(m) = Member (상속 관계에서 사용)
  - [Item](../src/main/java/supersub/join/Item.java)
  - [Book](../src/main/java/supersub/join/Book.java)
  - ```
    em.createQuery("select i from Item i where type(i) = Book", Item.class);
    ``` 
  - ```
    select ...
    from 
        Item item0_
    where 
        item0_.DTYPE='Book'
    ```

## 조건식

기본 CASE 식
```
select
  case when m.age <= 10 then '학생요금'
       when m.age >= 60 then '경로요금'
       else '일반요금'
  end
from Member m
```

단순 CASE 식
```
select
  case t.name
      when '팀A' then '인센티브110%'
      when '팀B' then '인센티브120%'
      else '인센티브105%'
  end
from Team t
```

사용자 이름이 없으면 이름 없는 회원을 반환
- COALESCE: 하나씩 조회해서 null이 아니면 반환
```
select coalesce(m.username,'이름 없는 회원') from Member m
```

사용자 이름이 ‘관리자’면 null을 반환하고 나머지는 본인의 이름을 반환
- NULLIF: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
```
select NULLIF(m.username, '관리자') from Member m
```

## 함수 

### JPQL 기본 함수

- CONCAT
- SUBSTRING
- TRIM
- LOWER, UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)

### 사용자 정의 함수 호출
- 하이버네이트는 사용전 방언에 추가해야 한다.
- 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.
  - ```
    select function('group_concat', i.name) from Item i
    ```
- org.hibernate.dialect.MySQL57Dialect
  - registerFunction( "now", currentTimestampFunction );
