package org.example.hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
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
            // 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            // team.getMembers().add(member);

            team.addMember(member);

            em.flush();
            em.clear();

            // fixme - flush, clear 안하면 얘는 1차캐시에 조회된게 그대로 튀어나온다.
            Team findTeam = em.find(Team.class, team.getId());

            // fixme - 때문에 여기 컬렉션에는 값이 없다.
            List<Member> members = findTeam.getMembers();

            System.out.println("============================================");
            for (Member member1 : members) {
                System.out.println("m = " + member.getUsername());
            }
            System.out.println("============================================");

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