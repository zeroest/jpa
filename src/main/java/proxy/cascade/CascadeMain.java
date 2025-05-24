package proxy.cascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CascadeMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-proxy");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Child c1 = new Child();
            c1.setName("c1");
//            em.persist(c1);
            Child c2 = new Child();
            c2.setName("c2");
//            em.persist(c2);

            Parent p1 = new Parent();
            p1.setName("p1");
            p1.addChild(c1);
            p1.addChild(c2);

            em.persist(p1);
/*
Hibernate:
    / insert proxy.cascade.Parent
        / insert
        into
            Parent
            (name, id)
        values
            (?, ?)
Hibernate:
    / insert proxy.cascade.Child
        / insert
        into
            Child
            (name, parent_id, id)
        values
            (?, ?, ?)
Hibernate:
    / insert proxy.cascade.Child
        /  insert
        into
            Child
            (name, parent_id, id)
        values
            (?, ?, ?)
*/

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
