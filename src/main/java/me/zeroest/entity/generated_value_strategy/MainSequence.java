package me.zeroest.entity.generated_value_strategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
Hibernate:

    drop table if exists StrategySequence CASCADE
Hibernate:

    drop sequence if exists SEQ
Hibernate: create sequence SEQ start with 1 increment by 10
Hibernate:

    create table StrategySequence (
       id bigint not null,
        name varchar(255),
        primary key (id)
    )
================ SAVE START ===================
Hibernate:
    call next value for SEQ
================= SAVE END ==================
================ SAVE START ===================
Hibernate:
    call next value for SEQ
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
================ SAVE START ===================
================= SAVE END ==================
Hibernate:
    /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
        / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategySequence
         / insert
        into
        StrategySequence
        (name, id)
        values
        (?, ?)
*/
public class MainSequence {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            saveValue(em, "A");
            saveValue(em, "B");
            saveValue(em, "C");
            saveValue(em, "D");
            saveValue(em, "E");
            saveValue(em, "F");
            saveValue(em, "G");
            saveValue(em, "H");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void saveValue(EntityManager em, String name) {
        System.out.println("================ SAVE START ===================");
        StrategySequence strategySequence = new StrategySequence();
        strategySequence.setName(name);
        em.persist(strategySequence);
        System.out.println("================= SAVE END ==================");
    }

}