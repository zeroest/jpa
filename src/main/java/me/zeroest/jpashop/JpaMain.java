package me.zeroest.jpashop;

import me.zeroest.jpashop.domain.Member;
import me.zeroest.jpashop.domain.Order;
import me.zeroest.jpashop.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql-jpashop");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member member = new Member();
            member.setName("a");
            em.persist(member);

            Order order = new Order();
            order.setStatus(OrderStatus.ORDER);
            order.setMember(member);

            em.persist(order);

            em.flush();
            em.clear();

            Order findedOrder = em.find(Order.class, order.getId());
            System.out.println("findedOrder = " + findedOrder);
            Member findedMember = findedOrder.getMember();
            System.out.println("findedMember = " + findedMember);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
