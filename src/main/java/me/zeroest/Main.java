package me.zeroest;

import me.zeroest.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            saveMember(em, 1, "a");
            saveMember(em, 2, "b");

            Member aMember = findMember(em, 1);
            Member bMember = findMember(em, 2);

            List<Member> allMembers = findAllMembers(em);
            System.out.println("allMembers.size() = " + allMembers.size());

            removeMember(em, aMember);

            updateMember(bMember, "B");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void saveMember(EntityManager em, long id, String name) {
        Member member = new Member();
        member.setId(id);
        member.setName(name);

        em.persist(member);
    }

    private static Member findMember(EntityManager em, long id) {
        Member member = em.find(Member.class, id);
        System.out.println("find member = " + member);
        return member;
    }

    private static void removeMember(EntityManager em, Member member) {
        System.out.println("remove member = " + member);
        em.remove(member);
    }

    private static void updateMember(Member member, String name) {
        // jpa 를 통해 엔티티를 가져오면 트랜젝션 커밋 시점에 변경내역을 체크
        member.setName(name);
        System.out.println("update member = " + member);
    }

    private static List<Member> findAllMembers(EntityManager em) {
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        System.out.println("find all members = " + members);
        return members;
    }

}