package proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class FetchMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-proxy");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Team team = new Team();
//            team.setId(1L);
            team.setName("t");
            em.persist(team);

            Member member = new Member();
            member.setId(1L);
            member.setUsername("a");
            member.setTeam(team);
            team.getMembers().add(member);
            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("================================================");

            Member amem = em.find(Member.class, member.getId());
            System.out.println("amem = " + amem.getUsername());

            System.out.println("aTeam = " + amem.getTeam());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new IllegalStateException(e);
        } finally {
            em.close();
        }

        emf.close();
    }
}
