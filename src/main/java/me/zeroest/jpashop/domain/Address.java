package me.zeroest.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@ToString
@Setter
@Getter
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id")
    private Long id;

    @ToString.Exclude
    @OneToOne(mappedBy = "address")
    private MemberInfo memberInfo;

    private String city;

    private String street;

    private String zipCode;

}
