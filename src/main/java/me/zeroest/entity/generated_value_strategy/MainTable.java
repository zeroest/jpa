package me.zeroest.entity.generated_value_strategy;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
Hibernate:

    drop table if exists Member
Hibernate:

    drop table if exists MY_SEQUENCES
Hibernate:

    drop table if exists StrategyTable
Hibernate:

    create table Member (
       id bigint not null,
        age integer,
        createdDate datetime(6),
        description longtext,
        lastModifiedDate datetime(6),
        roleType varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=InnoDB
Hibernate:

    create table MY_SEQUENCES (
       sequence_name varchar(255) not null,
        next_val bigint,
        primary key (sequence_name)
    ) engine=InnoDB
Hibernate:

    insert into MY_SEQUENCES(sequence_name, next_val) values ('SEQ',0)
Hibernate:

    create table StrategyTable (
       id bigint not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB
================ SAVE START ===================
Hibernate:
    select
        tbl.next_val
    from
        MY_SEQUENCES tbl
    where
        tbl.sequence_name=? for update

Hibernate:
    update
        MY_SEQUENCES
    set
        next_val=?
    where
        next_val=?
        and sequence_name=?
================= SAVE END ==================
================ SAVE START ===================
Hibernate:
    select
        tbl.next_val
    from
        MY_SEQUENCES tbl
    where
        tbl.sequence_name=? for update

Hibernate:
    update
        MY_SEQUENCES
    set
        next_val=?
    where
        next_val=?
        and sequence_name=?
================= SAVE END ==================
Hibernate:
    /* insert me.zeroest.entity.generated_value_strategy.StrategyTable
        / insert
        into
        StrategyTable
        (name, id)
        values
        (?, ?)
        Hibernate:
        /* insert me.zeroest.entity.generated_value_strategy.StrategyTable
         / insert
        into
        StrategyTable
        (name, id)
        values
        (?, ?)
*/
public class MainTable {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysql");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            saveValue(em, "A");
            saveValue(em, "B");

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
        StrategyTable strategyTable = new StrategyTable();
        strategyTable.setName(name);
        em.persist(strategyTable);
        System.out.println("================= SAVE END ==================");
    }

}