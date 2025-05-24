package jpql;

import jpql.entity.Ads;
import jpql.entity.Mem;
import jpql.entity.Tem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpqlTypeMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Tem t1 = new Tem("t1");
            em.persist(t1);
            Tem t2 = new Tem("t2");
            em.persist(t2);

            Ads ads1 = new Ads("c1", "s1", "z");
            Ads ads2 = new Ads("c2", "s2", "z");

            Mem aKim = new Mem("a kim", 1, ads1, t1);
            em.persist(aKim);
            Mem bKim = new Mem("b kim", 2, ads2, t1);
            em.persist(bKim);
            Mem cPark = new Mem("c park", 3, ads2, t2);
            em.persist(cPark);

            List<Mem> members = em.createQuery("select m, 'HELLO', 10L, 2.0D, 3.0F, true from Mem m " +
                    "where m.type = jpql.entity.MemType.ADMIN order by m.age desc", Mem.class).getResultList();
            System.out.println("members = " + members);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
