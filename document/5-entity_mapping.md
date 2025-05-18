
# Entity Mapping

- 객체와 테이블 매핑: @Entity, @Table
- 필드와 컬럼 매핑: @Column
- 기본 키 매핑: @Id
- 연관관계 매핑: @ManyToOne,@JoinColumn

## 테이블

- @Entity가 붙은 클래스는 JPA가 관리, 엔티티라 한다.
- JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수

- 주의
  - 기본 생성자 필수(파라미터가 없는 public 또는 protected 생성자)
  - final 클래스, enum, interface, inner 클래스 사용X
  - 저장할 필드에 final 사용 X

@Entity
- name: JPA에서 사용할 엔티티 이름을 지정

@Table
- name: 매핑할 테이블 이름 (default: 엔티티 이름 사용)
- catalog: 데이터베이스 catalog 매핑
- schema: 데이터베이스 schema 매핑
- uniqueConstraints(DDL): DDL 생성 시에 유니크 제약 조건 생성

## 컬럼

@Column: 컬럼 매핑
- name: 필드와 매핑할 테이블의 컬럼 이름 (default: 객체의 필드 이름)
- insertable, updatable: 등록, 변경 가능 여부 (default: TRUE)
- nullable(DDL): null 값의 허용 여부를 설정한다. false로 설정하면 DDL 생성 시에 not null 제약조건이 붙는다.
- unique(DDL): @Table의 uniqueConstraints와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 때 사용한다.
- columnDefinition(DDL): 데이터베이스 컬럼 정보를 직접 줄 수 있다. (ex. varchar(100) default ‘EMPTY')
- length(DDL): 문자 길이 제약조건, String 타입에만 사용한다. (default: 255) 
- precision, scale(DDL): (default: precision=19, scale=2)
  - BigDecimal 타입에서 사용한다(BigInteger도 사용할 수 있다).
  - precision은 소수점을 포함한 전체 자 릿수를, scale은 소수의 자릿수다. 
  - 참고로 double, float 타입에는 적용되지 않는다. 
  - 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.

@Temporal: 날짜 타입 매핑
- 날짜 타입(java.util.Date, java.util.Calendar)을 매핑할 때 사용
- LocalDate, LocalDateTime을 사용할 때는 생략 가능(최신 하이버네이트 지원)
- value
  - TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑 (예: 2013–10–11)
  - TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑 (예: 11:11:11)
  - TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (예: 2013–10–11 11:11:11)

@Enumerated: enum 타입 매핑 (default ORDINAL)
- 기본값이 ORDINAL이기 때문에 STRING으로 설정 필수로 해줄것 
- value
  - EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
  - EnumType.STRING: enum 이름을 데이터베이스에 저장

@Lob: 데이터베이스 BLOB, CLOB 타입과 매핑
- @Lob에는 지정할 수 있는 속성이 없다.
- 매핑하는 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑
  - CLOB: String, char[], java.sql.CLOB
  - BLOB: byte[], java.sql. BLOB

@Transient: 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
- 필드 매핑X
- 데이터베이스에 저장X, 조회X
- 주로 메모리상에서만 임시로 어떤 값을 보관하고 싶을 때 사용

## 기본 키

권장하는 식별자 전략
- 미래까지 이 조건을 만족하는 자연키는 찾기 어렵다. 대리키(대체키)를 사용하자.
- 예를 들어 주민등록번호도 기본 키로 적절하기 않다.
- 권장: Long형 + 대체키 + 키 생성전략 사용

@Id
- 직접 할당

@GeneratedValue
- 자동 생성
- strategy
  - IDENTITY: 데이터베이스에 위임, MYSQL
    - 주로 MySQL, PostgreSQL, SQL Server, DB2에서 사용 (예: MySQL의 AUTO_ INCREMENT)
    - JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
    - AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
    - IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회
    - ```java
      @Entity
      public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
      }
      ```
  - SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE (@SequenceGenerator 필요)
    - 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
    - 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트 (예: 오라클 시퀀스)
    - ```java
      @Entity
      @SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1
      )
      public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE,
                        generator = "MEMBER_SEQ_GENERATOR")
        private Long id;
      }
      ```
    - @SequenceGenerator
      - name: 식별자 생성기 이름 (필수로 입력)
      - sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름 (default: hibernate_sequence)
      - initialValue: DDL 생성 시에만 사용됨, 시퀀스 DDL을 생성할 때 처음 1 시작하는 수를 지정한다. (default: 1)
      - allocationSize: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) (**default: 50**)
        - **데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다**
      - catalog, schema 데이터베이스 catalog, schema 이름
  - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용 (@TableGenerator 필요)
    - 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
    - 장점: 모든 데이터베이스에 적용 가능
    - 단점: 성능
    - ```sql
      create table MY_SEQUENCES (
        sequence_name varchar(255) not null,
        next_val bigint,
        primary key ( sequence_name )
      )
      ```
    - ```java
      @Entity
      @TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",
        pkColumnValue = "MEMBER_SEQ", allocationSize = 1
      )
      public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE,
                        generator = "MEMBER_SEQ_GENERATOR")
        private Long id;
      }
      ```
    - @TableGenerator
      - name: 식별자 생성기 이름 (필수로 입력)
      - table: 키생성 테이블명 (default: hibernate_sequences)
      - pkColumnName: 시퀀스 컬럼명 (default: sequence_name)
      - valueColumnNa: 시퀀스 값 컬럼명 (default: next_val)
      - pkColumnValue: 키로 사용할 값 이름 (default: 엔티티 이름)
      - initialValue: 초기 값, 마지막으로 생성된 값이 기준이다. (default: 0)
      - allocationSize: 시퀀스 한 번 호출에 증가하는 수(성능 최적화에 사용됨) (**default: 50**)
      - catalog, schema: 데이터베이스 catalog, schema 이름
      - uniqueConstraints(DDL): 유니크 제약 조건을 지정할 수 있다.
  - AUTO: 방언에 따라 자동 지정, 기본값

## 연관 관계