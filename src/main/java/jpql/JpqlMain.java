package jpql;

import jpql.entity.Mem;

import javax.persistence.*;
import java.util.List;

public class JpqlMain {
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

            List<Mem> allmems = em.createQuery("select m from Mem m", Mem.class).getResultList();
            System.out.println("allmems = " + allmems);

            List<Mem> mems = em.createQuery(
                    "select  m from Mem m where m.username like '%kim'",
                    Mem.class
            ).getResultList();
            System.out.println("mems = " + mems);

            // TypedQuery: 반환 타입이 명확할 때 사용
            TypedQuery<Mem> q1 = em.createQuery("select m from Mem m", Mem.class);
            // Query: 반환 타입이 명확하지 않을 때 사용
            Query q2 = em.createQuery("select m.username, m.age from Mem m");

            // getResultList: 결과가 하나 이상일 때, 리스트 반환, 결과가 없으면 빈 리스트 반환
            q1.getResultList();
            // getSingleResult: 결과가 정확히 하나, 단일 객체 반환
            // 결과가 없으면 NoResultException
            // 둘 이상이면 NonUniqueResultException
            try {
                q1.getSingleResult();
            } catch (NonUniqueResultException | NoResultException e) {
                e.printStackTrace();
            }

            // 파라미터 바인딩
            TypedQuery<Mem> qp1 = em.createQuery("select m from Mem m where m.username = :username", Mem.class);
            qp1.setParameter("username", "a kim");
            Mem sr1 = qp1.getSingleResult();
            System.out.println("singleResult1 = " + sr1);

            TypedQuery<Mem> qp2 = em.createQuery("select m from Mem m where m.username = ?1", Mem.class);
            qp2.setParameter(1, "b kim");
            Mem sr2 = qp2.getSingleResult();
            System.out.println("singleResult2 = " + sr2);



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
