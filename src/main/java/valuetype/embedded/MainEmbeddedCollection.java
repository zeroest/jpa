package valuetype.embedded;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class MainEmbeddedCollection {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-valuetype");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            LocalDateTime now = LocalDateTime.now();

            Address homeAddress = new Address("Seoul", "Anywhere", "09123");
            MemberEmbedded m1 = new MemberEmbedded(
                    "m1",
                    new Period(now.minusDays(1), now.plusMonths(1)),
                    homeAddress,
//                    new Address("Busan", "Anywhere", "09123")
                    null
            );

            em.persist(m1);

            m1.addFavoriteFood("치킨");
            m1.addFavoriteFood("피자");
            m1.addFavoriteFood("광어회");

            m1.addAddress(new Address("a", "a", "a"));
            m1.addAddress(new Address("b", "b", "b"));

            em.flush();
            em.clear();
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
    / insert collection
        row valuetype.embedded.MemberEmbedded.addressHistory / insert
        into
            address
            (member_id, city, street, zipcode)
        values
            (?, ?, ?, ?)
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.addressHistory / insert
        into
            address
            (member_id, city, street, zipcode)
        values
            (?, ?, ?, ?)
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.favoriteFoods / insert
        into
            favorite_food
            (member_id, food_name)
        values
            (?, ?)
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.favoriteFoods / insert
        into
            favorite_food
            (member_id, food_name)
        values
            (?, ?)
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.favoriteFoods / insert
        into
            favorite_food
            (member_id, food_name)
        values
            (?, ?)
*/

            System.out.println("===========================================");
            System.out.println("===========================================");

            MemberEmbedded m1Finded = em.find(MemberEmbedded.class, m1.getId());
/*
Hibernate:
    select
        memberembe0_.member_id as member_i1_2_0_,
        memberembe0_.city as city2_2_0_,
        memberembe0_.street as street3_2_0_,
        memberembe0_.zipcode as zipcode4_2_0_,
        memberembe0_.username as username5_2_0_,
        memberembe0_.work_city as work_cit6_2_0_,
        memberembe0_.work_street as work_str7_2_0_,
        memberembe0_.work_zipcode as work_zip8_2_0_,
        memberembe0_.endDate as enddate9_2_0_,
        memberembe0_.startDate as startda10_2_0_
    from
        MemberEmbedded memberembe0_
    where
        memberembe0_.member_id=?
*/

            // 지연 로딩으로 favorite_food, address 테이블 쿼리하지 않음
            System.out.println("m1Finded.getUsername() = " + m1Finded.getUsername());

            System.out.println("m1Finded = " + m1Finded);
/*
Hibernate:
    select
        favoritefo0_.member_id as member_i1_1_0_,
        favoritefo0_.food_name as food_nam2_1_0_
    from
        favorite_food favoritefo0_
    where
        favoritefo0_.member_id=?
Hibernate:
    select
        addresshis0_.member_id as member_i1_0_0_,
        addresshis0_.city as city2_0_0_,
        addresshis0_.street as street3_0_0_,
        addresshis0_.zipcode as zipcode4_0_0_
    from
        address addresshis0_
    where
        addresshis0_.member_id=?
*/
/*
m1Finded = MemberEmbedded(
    id=1,
    username=m1,
    workPeriod=Period(startDate=2025-05-23T19:19:56.643511, endDate=2025-06-24T19:19:56.643511),
    homeAddress=Address(city=Seoul, street=Anywhere, zipcode=09123),
    workAddress=null,
    favoriteFoods=[치킨, 광어회, 피자],
    addressHistory=[Address(city=a, street=a, zipcode=a), Address(city=b, street=b, zipcode=b)]
)
*/

            m1Finded.getFavoriteFoods().remove("치킨");
            m1Finded.getFavoriteFoods().add("햄버거");
/*
Hibernate:
    / delete collection row valuetype.embedded.MemberEmbedded.favoriteFoods /  delete
        from
            favorite_food
        where
            member_id=?
            and food_name=?
    / insert collection
        row valuetype.embedded.MemberEmbedded.favoriteFoods / insert
        into
            favorite_food
            (member_id, food_name)
        values
            (?, ?)
*/

            System.out.println("m1Finded = " + m1Finded);

            m1Finded.getAddressHistory().remove(new Address("a", "a", "a"));
            m1Finded.getAddressHistory().add(new Address("c", "c", "c"));

/*
Hibernate:
    / delete collection valuetype.embedded.MemberEmbedded.addressHistory / delete
        from
            address
        where
            member_id=?
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.addressHistory / insert
        into
            address
            (member_id, city, street, zipcode)
        values
            (?, ?, ?, ?)
Hibernate:
    / insert collection
        row valuetype.embedded.MemberEmbedded.addressHistory / insert
        into
            address
            (member_id, city, street, zipcode)
        values
            (?, ?, ?, ?)
*/

            System.out.println("m1Finded = " + m1Finded);

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
