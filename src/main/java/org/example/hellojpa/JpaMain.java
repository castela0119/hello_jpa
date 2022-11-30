package org.example.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 데이터베이스 커넥션 받았다고 생각할 것
        EntityManager em = emf.createEntityManager();

        // 트랜젝션을 호출하고 begin 으로 데이터베이스 트랜젝션을 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            // detach() 하는 경우 더 이상 JPA에서 관리하지 않는다.
            em.detach(member);

            System.out.println("============================");

            // 그러면 트랜잭션을 commit 할때 아무일도 일어나지 않는다.
            tx.commit();

        } catch (Exception e) {
            tx.rollback();

        } finally {
            // 사용을 다하고나면 EntityManager 를 꼭 닫아줘야 한다.
            em.close();

        }

        // 전체 어플리케이션이 끝나면 EntitiyFactoryManager 까지 닫아주자.
        emf.close();
    }
}