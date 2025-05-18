package me.zeroest.jpashop.domain;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTestContext {

    private static EntityManagerFactory emf;

    protected EntityManager em;

    private EntityTransaction tx;

    @BeforeAll
    static void setup() {
        emf = Persistence.createEntityManagerFactory("mysql-jpashop-test");
    }

    @BeforeEach
    void beforeEach() {
        em = emf.createEntityManager();
        tx = em.getTransaction();
        tx.begin();
    }

    @AfterEach
    void afterEach() {
        tx.rollback();
        em.close();
    }

    @AfterAll
    static void close() {
        emf.close();
    }

}
