package proxy.cascade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class OrphanMain {
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

            em.flush();
            em.clear();

            System.out.println("================================");
            System.out.println("================================");

//            p1.getChildren().remove(c2);
            Parent pa1 = em.find(Parent.class, p1.getId());

            pa1.getChildren().remove(0);

/*
Hibernate:
    select
        parent0_.id as id1_2_0_,
        parent0_.name as name2_2_0_
    from
        Parent parent0_
    where
        parent0_.id=?
Hibernate:
    select
        children0_.parent_id as parent_i3_0_0_,
        children0_.id as id1_0_0_,
        children0_.id as id1_0_1_,
        children0_.name as name2_0_1_,
        children0_.parent_id as parent_i3_0_1_
    from
        Child children0_
    where
        children0_.parent_id=?
Hibernate:
    / delete proxy.cascade.Child / delete
        from
            Child
        where
            id=?
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
