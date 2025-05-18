
# Start Project

## Dependency

```
    implementation("org.hibernate:hibernate-entitymanager:5.6.15.Final")
```

hibernate-entitymanager 디펜던시로 아래의 라이브러리들이 자동으로 세팅  
JPA 디펜던시도 있기 때문에 따로 디펜던시를 걸 필요가 없음 

- javax.persistence-api
- hibernate-core
- hibernate-entitymanager
- hibernate-common-annotations

## persistence.xml

jpa 설정 파일

- /META-INF/persistence.xml 위치
- persistence-unit name으로 이름 지정
- javax.persistence로 시작: JPA 표준 속성
- hibernate로 시작: 하이버네이트 전용 속성

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="2.2"
             xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_2.xsd">
    <persistence-unit name="hello">
        <properties>
            <!-- 필수 속성 -->
            <property name="javax.persistence.jdbc.driver" value="org.h2.Driver"/>
            <property name="javax.persistence.jdbc.user" value="sa"/>
            <property name="javax.persistence.jdbc.password" value=""/>
            <property name="javax.persistence.jdbc.url" value="jdbc:h2:tcp://localhost/~/test"/>
            <property name="hibernate.dialect" value="org.hibernate.dialect.H2Dialect"/>
            <!-- 옵션 -->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.use_sql_comments" value="true"/>
            <!--<property name="hibernate.hbm2ddl.auto" value="create" />-->
        </properties>
    </persistence-unit>
</persistence>
```

- Dialect (방언): SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능
  - JPA는 특정 데이터베이스에 종속 X
  - 각각의 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다름
  - 가변 문자: MySQL은 VARCHAR, Oracle은 VARCHAR2
  - 문자열을 자르는 함수: SQL 표준은 SUBSTRING(), Oracle은 SUBSTR()
  - 페이징: MySQL은 LIMIT , Oracle은 ROWNUM

- hibernate.hbm2ddl.auto: SessionFactory가 생성될때 스키마 DDL을 db로 검증하고 내보내는 기능을 상황에 따라 다르게 설정할수 있도록 하는 프로퍼티
  - DDL 생성 기능은 DDL을 자동 생성할 때만 사용되고 JPA의 실행 로직에는 영향을 주지 않는다. 
  - create : SessionFactory 시작시 스키마를 삭제하고 다시 생성
    - 기존테이블 삭제 후 다시 생성 (DROP + CREATE)
  - create-drop : SessionFactory 종료시 스키마를 삭제
    - create와 같으나 종료시점에 테이블 DROP
  - update : SessionFactory 시작시 객체 구성와 스키마를 비교하여 컬럼 추가/삭제 작업을 진행함. 기존의 스키마를 삭제하지 않고 유지.
    - 변경분만 반영(운영DB에는 사용하면 안됨)
  - validate : SessionFactory 시작시 객체구성과 스키마가 다르다면 예외 발생시킴.
    - 엔티티와 테이블이 정상 매핑되었는지만 확인
