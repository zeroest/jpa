package supersub.mappedsuperclass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-supersub-mappedsuper");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
