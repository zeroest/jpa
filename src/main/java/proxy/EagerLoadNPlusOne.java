package proxy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class EagerLoadNPlusOne {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-proxy");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Team team = new Team();
//            team.setId(1L);
            team.setName("t1");
            em.persist(team);

            Member member = new Member();
//            member.setId(1L);
            member.setUsername("a");
            member.setTeam(team);
            team.getMembers().add(member);
            em.persist(member);

            Team team2 = new Team();
//            team.setId(1L);
            team2.setName("t2");
            em.persist(team2);

            Member member2 = new Member();
//            member.setId(2L);
            member2.setUsername("b");
            member2.setTeam(team2);
            team2.getMembers().add(member2);
            em.persist(member2);

            Team team3 = new Team();
//            team.setId(1L);
            team3.setName("t3");
            em.persist(team3);

            Member member3 = new Member();
//            member.setId(3L);
            member3.setUsername("c");
            member3.setTeam(team3);
            team3.getMembers().add(member3);
            em.persist(member3);

            em.flush();
            em.clear();

            System.out.println("================================================");

            // JPQL은 그대로 SQL로 변경되어 나감
            // 따라서 EAGER 설정이 먹지 않음
            // Member 가 10개면 Team 조회 쿼리 10개가 나감
            List<Member> members = em.createQuery("select m from Member m ", Member.class)
                    .getResultList();

            System.out.println("members = " + members);
/*
Hibernate:
    /* select
        m
    from
        Member m  / select
            member0_.member_id as member_i1_0_,
            member0_.age as age2_0_,
            member0_.createdDate as createdd3_0_,
            member0_.description as descript4_0_,
            member0_.lastModifiedDate as lastmodi5_0_,
            member0_.roleType as roletype6_0_,
            member0_.team_id as team_id8_0_,
            member0_.name as name7_0_
        from
            Member member0_
Hibernate:
    select
        team0_.team_id as team_id1_1_0_,
        team0_.name as name2_1_0_
    from
        Team team0_
    where
        team0_.team_id=?
Hibernate:
    select
        team0_.team_id as team_id1_1_0_,
        team0_.name as name2_1_0_
    from
        Team team0_
    where
        team0_.team_id=?
Hibernate:
    select
        team0_.team_id as team_id1_1_0_,
        team0_.name as name2_1_0_
    from
        Team team0_
    where
        team0_.team_id=?
members = [Member(id=2, username=a, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=1, name=t1)), Member(id=4, username=b, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=3, name=t2)), Member(id=6, username=c, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=5, name=t3))]
*/

            // 해결
            // JPQL fetch join
            List<Member> ms = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();
            System.out.println("ms = " + ms);

/*
Hibernate:
    /* select
        m
    from
        Member m
    join
        fetch m.team / select
            member0_.member_id as member_i1_0_0_,
            team1_.team_id as team_id1_1_1_,
            member0_.age as age2_0_0_,
            member0_.createdDate as createdd3_0_0_,
            member0_.description as descript4_0_0_,
            member0_.lastModifiedDate as lastmodi5_0_0_,
            member0_.roleType as roletype6_0_0_,
            member0_.team_id as team_id8_0_0_,
            member0_.name as name7_0_0_,
            team1_.name as name2_1_1_
        from
            Member member0_
        inner join
            Team team1_
                on member0_.team_id=team1_.team_id
ms = [Member(id=2, username=a, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=1, name=t1)), Member(id=4, username=b, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=3, name=t2)), Member(id=6, username=c, age=null, roleType=null, createdDate=null, lastModifiedDate=null, description=null, team=Team(id=5, name=t3))]
*/

            // 해결
            // EntityGraph

            // 해결
            // Batch Size

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
