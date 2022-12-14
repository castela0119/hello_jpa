package org.example.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // 데이터베이스 커넥션 받았다고 생각할 것
        EntityManager em = emf.createEntityManager();

        // 트랜젝션을 호출하고 begin 으로 데이터베이스 트랜젝션을 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setUsername("user1");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

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