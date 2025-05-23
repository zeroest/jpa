package proxy;

import org.hibernate.Hibernate;
import org.hibernate.LazyInitializationException;

import javax.persistence.*;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-proxy");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();


            Member member = new Member();
            member.setId(1L);
            member.setUsername("a");

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=====================================");

//            Member aMem = em.find(Member.class, 1L);
//            System.out.println("aMem = " + aMem);

            // getReference 시점에 쿼리하지 않음
            Member amem = em.getReference(Member.class, 1L);

            PersistenceUnitUtil persistenceUnitUtil = emf.getPersistenceUnitUtil();
            boolean loaded1 = persistenceUnitUtil.isLoaded(amem);
            System.out.println("loaded1 = " + loaded1);

            // proxy.Member$HibernateProxy$5JFX3jcj 프록시 객체
            System.out.println("amem = " + amem.getClass());
            // amem 객체 사용시 실제 쿼리하여 데이터 가져옮
            System.out.println("amem = " + amem.getUsername());

            boolean loaded2 = persistenceUnitUtil.isLoaded(amem);
            System.out.println("loaded2 = " + loaded2); // loaded2 = true


            em.flush();
            em.clear();

            System.out.println("=====================================");

            Member am = em.find(Member.class, 1L);
            System.out.println("am = " + am.getClass()); // am = class proxy.Member

            // 영속성 컨텍스트, 1차 캐시에 존재하면 원본을 반환
            Member amReference = em.getReference(Member.class, 1L);
            System.out.println("amReference = " + amReference.getClass()); // amReference = class proxy.Member

            System.out.println("am == amReference: " + (am == amReference)); // am == amReference: true

            em.flush();
            em.clear();

            System.out.println("=====================================");

            Member amr1 = em.getReference(Member.class, 1L);
            System.out.println("amr1 = " + amr1.getClass()); // amr1 = class proxy.Member$HibernateProxy$BBJMnN91
            Member amr2 = em.getReference(Member.class, 1L);
            System.out.println("amr2 = " + amr2.getClass()); // amr2 = class proxy.Member$HibernateProxy$BBJMnN91

            System.out.println("amr1 == amr2: " + (amr1 == amr2)); // amr1 == amr2: true

            em.flush();
            em.clear();

            System.out.println("=====================================");

            // 프록시로 최조 조회시 이후 em.find 에서도 프록시로 반환
            // 객체 동일성 보장을 위해서
            Member amr = em.getReference(Member.class, 1L);
            System.out.println("amr = " + amr.getClass()); // amr = class proxy.Member$HibernateProxy$lPKg1qqc

            Member ame = em.find(Member.class, 1L);
            System.out.println("ame = " + ame.getClass()); // ame = class proxy.Member$HibernateProxy$lPKg1qqc

            System.out.println("amr == ame: " + (amr == ame)); // amr == ame: true

            em.flush();
            em.clear();

            System.out.println("=====================================");

            // 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일때 프록시를 초기화시 LazyInitializationException
            Member amre = em.getReference(Member.class, 1L);
            System.out.println("amre = " + amre.getClass()); // amre = class proxy.Member$HibernateProxy$2hXAWuBc

            em.detach(amre);
            // or em.clear()
            // or em.close()


            try {
                amre.getUsername();
            } catch (LazyInitializationException e) {
                e.printStackTrace();
/*
org.hibernate.LazyInitializationException: could not initialize proxy [proxy.Member#1] - no Session
	at org.hibernate.proxy.AbstractLazyInitializer.initialize(AbstractLazyInitializer.java:176)
	at org.hibernate.proxy.AbstractLazyInitializer.getImplementation(AbstractLazyInitializer.java:322)
	at org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor.intercept(ByteBuddyInterceptor.java:45)
	at org.hibernate.proxy.ProxyConfiguration$InterceptorDispatcher.intercept(ProxyConfiguration.java:95)
	at proxy.Member$HibernateProxy$2hXAWuBc.getUsername(Unknown Source)
	at proxy.Main.main(Main.java:96)
5월 24, 2025 1:27:12 오전 org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl$PoolState stop
INFO: HHH10001008: Cleaning up connection pool [jdbc:h2:mem:test]
*/
            }

            em.flush();
            em.clear();

            System.out.println("=====================================");

            Member amref = em.getReference(Member.class, 1L);
            System.out.println("amref = " + amref.getClass()); // amref = class proxy.Member$HibernateProxy$2hXAWuBc
            Hibernate.initialize(amref); // 강제 초기화
            // 하이버네이트 제공 기능으로 JPA 표준은 강제 초기화 기능 없음

            boolean initializeLoaded = persistenceUnitUtil.isLoaded(amref);
            System.out.println("initializeLoaded = " + initializeLoaded);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
