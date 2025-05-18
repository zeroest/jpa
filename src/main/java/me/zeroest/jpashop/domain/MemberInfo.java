package me.zeroest.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@ToString
@Setter
@Getter
@Entity
public class MemberInfo {

    @Id
    @Column(name = "member_id")
    private Long id;

    @ToString.Exclude
    @OneToOne
    @MapsId
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Member member;

    private String name;

    @OneToOne
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Address address;

    public void setMember(Member member) {
        this.member = member;
        member.setInfo(this);
    }

    public void setAddress(Address address) {
        this.address = address;
        address.setMemberInfo(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberInfo that = (MemberInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
