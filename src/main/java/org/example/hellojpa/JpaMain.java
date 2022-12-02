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
            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member1.setUsername("B");

            Member member3 = new Member();
            member1.setUsername("C");

            System.out.println("=================================");

            // 처음 호출하면 DB SEQ 값은 1이 됨
            // 어? 나는 DB 50개씩 쓴다고 셋팅되어있는데?

            // 그래서 바로 두번째 호출하면 DB SEQ 값은 51이 됨

            // 여기서 DB SEQ 값을 1, 51 까지 만들고
            em.persist(member1);    // 여기서 부터 메모리에서(?) 호출
            em.persist(member2);
            em.persist(member3);

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());

            System.out.println("=================================");

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