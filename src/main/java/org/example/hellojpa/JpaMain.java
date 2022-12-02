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
            Team team = new Team();
            team.setName("TeamA");

            // em.persist() 하면 항상 id 에 값이 들어간다. (영속상태가되면 무조건 pk값이 셋팅되고 영속상태가 된다.)
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeamId(team.getId());     // fixme - 이 부분이 객체지향스럽지 못하다.
            em.persist(member);

            Member findMember = em.find(Member.class, member.getId());     // fixme - 조회할때도 객체지향스럽지 못하다.

            Long findTeamId = findMember.getTeamId();       // fixme - 연관관계가 없기때문에 DB를 통해 계속 물어봐야 한다.
            Team findTeam = em.find(Team.class, findTeamId);

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