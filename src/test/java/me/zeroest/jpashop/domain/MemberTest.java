package me.zeroest.jpashop.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest extends JpaTestContext {

    @Test
    void findOrdersTest() {
        Member member = new Member();
        member.setName("a");
        em.persist(member);

        Order order1 = new Order();
        order1.setStatus(OrderStatus.ORDER);
        order1.setMember(member);
        em.persist(order1);

        Order order2 = new Order();
        order2.setStatus(OrderStatus.CANCEL);
        order2.setMember(member);
        em.persist(order2);

        em.flush();
        em.clear();

        Member findedMember = em.find(Member.class, member.getId());
        System.out.println("findedMember = " + findedMember);
        System.out.println("findedMember.getOrders() = " + findedMember.getOrders());
        assertEquals(member, findedMember);
        assertEquals(2, findedMember.getOrders().size());
        assertEquals(2, member.getOrders().size());
        assertArrayEquals(member.getOrders().toArray(), findedMember.getOrders().toArray());
    }

}