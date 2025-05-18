package me.zeroest.jpashop.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest extends JpaTestContext {

    @Test
    void persistTest() {
        Member member = new Member();
        em.persist(member);

        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setMember(member);
        em.persist(order);

        Item pen = new Item();
        pen.setPrice(1000);
        pen.setName("pen");
        pen.setStockQuantity(1000);
        em.persist(pen);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrder(order);
        orderItem1.setOrderPrice(1000);
        orderItem1.setCount(1);
        orderItem1.setItem(pen);
        em.persist(orderItem1);

        Item iphone = new Item();
        iphone.setPrice(500);
        iphone.setName("iphone");
        iphone.setStockQuantity(10);
        em.persist(iphone);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrder(order);
        orderItem2.setOrderPrice(3000);
        orderItem2.setCount(6);
        orderItem2.setItem(iphone);
        em.persist(orderItem2);

        em.flush();
        em.clear();

        Order findedOrder = em.find(Order.class, order.getId());
        System.out.println("findedOrder = " + findedOrder);
        assertEquals(order, findedOrder);

        Member findedMember = findedOrder.getMember();
        System.out.println("findedMember = " + findedMember);
        assertEquals(member, findedMember);

        List<OrderItem> orderItems = findedOrder.getOrderItems();
        System.out.println("orderItems = " + orderItems);
        assertIterableEquals(order.getOrderItems(), orderItems);
    }

}