package jpql;

import jpql.entity.Mem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CriteriaMain {
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

            em.createQuery("select m from Mem m", Mem.class)
                    .getResultList()
                    .forEach(System.out::println);


            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Mem> query = cb.createQuery(Mem.class);

            // 루트 클래스 (조회를 시작할 클래스)
            Root<Mem> m = query.from(Mem.class);

            CriteriaQuery<Mem> cq = query.select(m).where(cb.like(m.get("username"), "%kim"));
            List<Mem> mems = em.createQuery(cq).getResultList();

            System.out.println("mems = " + mems);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
