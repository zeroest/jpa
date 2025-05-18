package me.zeroest.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class Member {
    // Class 'Member' should have [public, protected] no-arg constructor
    // 리플렉션과 같이 동적으로 객체를 생성해야 하기 때문에 기본 생성자가 필수로 존재해야한다
    protected Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private Long id;

    private String name;

}
