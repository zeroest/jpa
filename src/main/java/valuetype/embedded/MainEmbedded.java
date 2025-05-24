package valuetype.embedded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class MainEmbedded {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-valuetype");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            LocalDateTime now = LocalDateTime.now();

            MemberEmbedded m1 = new MemberEmbedded(
                    "m1",
                    new Period(now.minusDays(1), now.plusMonths(1)),
                    new Address("Seoul", "Anywhere", "09123"),
//                    new Address("Busan", "Anywhere", "09123")
                    null
            );


            em.persist(m1);
/*
Hibernate:
    / insert valuetype.embedded.MemberEmbedded
        / insert
        into
            MemberEmbedded
            (city, street, zipcode, username, work_city, work_street, work_zipcode, endDate, startDate, member_id)
        values
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
*/

            em.flush();
            em.clear();

            System.out.println("==============================");

            MemberEmbedded m1finded = em.find(MemberEmbedded.class, m1.getId());

            System.out.println("m1finded = " + m1finded);
/*
m1finded = MemberEmbedded(
    id=1,
    username=m1,
    workPeriod=Period(startDate=2025-05-23T18:23:39.335920, endDate=2025-06-24T18:23:39.335920),
    homeAddress=Address(city=Seoul, street=Anywhere, zipcode=09123),
    workAddress=null
)
*/

            em.flush();
            em.clear();

            System.out.println("==============================");

            Address homeAddress = new Address("Seoul", "Anywhere", "09123");
            MemberEmbedded m2 = new MemberEmbedded(
                    "m2",
                    new Period(now.minusDays(1), now.plusMonths(1)),
                    homeAddress,
//                    new Address("Busan", "Anywhere", "09123")
                    null
            );
            em.persist(m2);

            MemberEmbedded m3 = new MemberEmbedded(
                    "m3",
                    new Period(now.minusDays(1), now.plusMonths(1)),
                    homeAddress,
//                    new Address("Busan", "Anywhere", "09123")
                    null
            );
            em.persist(m3);

//            m3.getHomeAddress().updateCity("newCity");

/*
Hibernate:
    / insert valuetype.embedded.MemberEmbedded
        / insert
        into
            MemberEmbedded
            (city, street, zipcode, username, work_city, work_street, work_zipcode, endDate, startDate, member_id)
        values
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
Hibernate:
    / insert valuetype.embedded.MemberEmbedded
        / insert
        into
            MemberEmbedded
            (city, street, zipcode, username, work_city, work_street, work_zipcode, endDate, startDate, member_id)
        values
            (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
Hibernate:
    / update
        valuetype.embedded.MemberEmbedded / update
            MemberEmbedded
        set
            city=?,
            street=?,
            zipcode=?,
            username=?,
            work_city=?,
            work_street=?,
            work_zipcode=?,
            endDate=?,
            startDate=?
        where
            member_id=?
Hibernate:
    / update
        valuetype.embedded.MemberEmbedded / update
            MemberEmbedded
        set
            city=?,
            street=?,
            zipcode=?,
            username=?,
            work_city=?,
            work_street=?,
            work_zipcode=?,
            endDate=?,
            startDate=?
        where
            member_id=?
*/

            em.flush();
            em.clear();

            System.out.println("==============================");

            Address a1 = new Address("city", "street", "zipcode");
//            Address a2 = new Address("city", "street", "zipcode");
            Address a2 = a1.copy();

            System.out.println("a1 == a2 : " + (a1 == a2)); // a1 == a2 : false
            System.out.println("a2.equals(a2) : " + (a1.equals(a2))); // a1.equals(a2) : true

            Period p1 = new Period(now.minusDays(1), now.plusMonths(1));
            Period p2 = p1.copy();

            System.out.println("p1 == p2 : " + (p1 == p2)); // p1 == p2 : false
            System.out.println("p2.equals(p2) : " + (p1.equals(p2))); // p1.equals(p2) : true

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
