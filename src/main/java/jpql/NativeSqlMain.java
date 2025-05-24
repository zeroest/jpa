package jpql;

import jpql.entity.Mem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class NativeSqlMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-jpql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Mem aKim = new Mem("a kim");
            em.persist(aKim);
            Mem bKim = new Mem("b kim");
            em.persist(bKim);
            Mem cPark = new Mem("c park");
            em.persist(cPark);

            List<Mem> allMems = em.createNativeQuery("SELECT * FROM MEM WHERE name LIKE '%kim'", Mem.class).getResultList();
            System.out.println("allMems = " + allMems);

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
