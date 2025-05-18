package me.zeroest.jpashop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest extends JpaTestContext {

    @Test
    void findOrdersTest() {
        Member member = new Member();
        em.persist(member);

        Address address = new Address();
        address.setCity("seoul");
        address.setZipCode("0123");
        em.persist(address);

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setName("a");
        memberInfo.setMember(member);
        memberInfo.setAddress(address);
        em.persist(memberInfo);

        em.flush();
        em.clear();

        Member findedMember = em.find(Member.class, member.getId());
        System.out.println("findedMember = " + findedMember);
        System.out.println("findedMember.getOrders() = " + findedMember.getOrders());
        assertEquals(member, findedMember);
        MemberInfo findedMemberInfo = findedMember.getInfo();
        System.out.println("findedMemberInfo = " + findedMemberInfo);
        assertEquals(memberInfo, findedMemberInfo);
        Address findedAddress = memberInfo.getAddress();
        System.out.println("findedAddress = " + findedAddress);
        assertEquals(address, findedAddress);
        MemberInfo memberInfoByAddress = findedAddress.getMemberInfo();
        System.out.println("memberInfoByAddress = " + memberInfoByAddress);
    }

}