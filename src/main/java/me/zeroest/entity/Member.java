package me.zeroest.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
public class Member {
    // Class 'Member' should have [public, protected] no-arg constructor
    // 리플렉션과 같이 동적으로 객체를 생성해야 하기 때문에 기본 생성자가 필수로 존재해야한다
    protected Member() {
    }

    public Member(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    @Id
    private Long id;
    @Column(name = "name")
    private String username;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob
    private String description;

}
