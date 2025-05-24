package jpql;

import jpql.dto.UserDto;
import jpql.entity.Ads;
import jpql.entity.Mem;
import jpql.entity.Tem;

import javax.persistence.*;
import java.util.List;

public class JpqlProjectionMain {
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


            List<Mem> members = em.createQuery("select m from Mem m", Mem.class).getResultList(); // 엔티티 프로젝션
            System.out.println("members = " + members);

//            List<Tem> teams = em.createQuery("select m.team from Mem m", Tem.class).getResultList(); // 엔티티 프로젝션 (묵시적 조인)
            List<Tem> teams = em.createQuery("select t from Mem m join m.team t", Tem.class).getResultList(); // 위와 같이 JPQL 작성해도 되지만 조인문을 명확하게 작성해주도록 한다 (명시적 조인)
            System.out.println("teams = " + teams);

            List<Ads> addresses = em.createQuery("select distinct m.address from Mem m", Ads.class).getResultList(); // 임베디드 타입 프로젝션
            System.out.println("addresses = " + addresses);

            List<Object[]> scalas = em.createQuery("select m.username, m.age from Mem m").getResultList();// 스칼라 타입 프로젝션 (숫자, 문자등 기본 데이터 타입)
//            System.out.println("scalas = " + scalas);
            scalas.forEach(os -> {
                System.out.println("os[0] = " + os[0]);
                System.out.println("os[1] = " + os[1]);
            });
/*
Hibernate:
    / select
        m.username,
        m.age
    from
        Mem m / select
            mem0_.name as col_0_0_,
            mem0_.age as col_1_0_
        from
            Mem mem0_
os[0] = a kim
os[1] = 1
os[0] = b kim
os[1] = 2
os[0] = c park
os[1] = 3
*/

            List<UserDto> userDtos = em.createQuery("select new jpql.dto.UserDto(m.username, m.age) from Mem m", UserDto.class).getResultList();// 스칼라 타입 프로젝션 (숫자, 문자등 기본 데이터 타입)
            System.out.println("userDtos = " + userDtos);

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
